package lab3;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import java.util.*;
import java.util.concurrent.*;

/**
 * An object implementing the Remote interface. This allows it to be
 * called through RMI.
 */
public class Server implements Hello {

  public Server () {}

  public String sayHello() {
    System.out.println("sayHello() was called");
    return "Hello, remote world!";
  }

  private CopyOnWriteArrayList<Book> stock = new CopyOnWriteArrayList<Book>();
  private Hashtable<Book, CopyOnWriteArrayList<Object>> numbers = new Hashtable<Book, CopyOnWriteArrayList<Object>>();
  /**
   * This port will be assigned to your group for use on EC2. For local testing, you can use any (nonstandard) port you wish.
   */
  public final static int REGISTRY_PORT = 54001;

  private void printStock(){
    System.out.println(stock.size() + " total books:");
    for(Book b : stock){
      System.out.println("  " + b.getTitle() + ": " + b.getCopies() + " copies.");
    }
  }

  public int sell(String bookname, int copies) throws RemoteException{
    long timeStarted = System.currentTimeMillis();
    if(copies < 0){
      throw new RemoteException();
    }
    int currentStock = 0;
    Book b = null;
    for(Book book : stock){
      if(book.getTitle().equals(bookname)){
        b = book;
      }
    }
    if(b == null){
      synchronized(stock){
        b = new Book(bookname, 0);
        stock.add(b);
      }
    }
    if(!numbers.containsKey(b)){
      numbers.put(b, new CopyOnWriteArrayList<Object>());
    }
    synchronized(b){
      //book exists in stock, so update number of copies
      currentStock = b.getCopies();
      //don't allow decreasing number of copies. that would be rude!
      if(copies > 0) b.setCopies(currentStock + copies);
      //wakes up any threads waiting on this book
      b.notifyAll();
      System.out.println("Someone just sold " + copies + " of " + bookname + ". There are now " + b.getCopies() + ".");
      long timeToSell = System.currentTimeMillis() - timeStarted;
      System.out.println("It took " + timeToSell + " milliseconds to process the request on the server.");
      //returns stock before more copies were added

      return currentStock;
    }
  }

  public int buy(String bookname, int copies){
    long timeStarted = System.currentTimeMillis();
    Book b = null;
    for(Book book : stock){
      if(book.getTitle().equals(bookname)){
        b = book;
        break;
      }
    }
    System.out.println(b);
    if(b == null){
      try{
        Thread.sleep(10000);
      }catch(Exception e){
        System.out.println(e);
      }
      for(Book book : stock){
        if(book.getTitle().equals(bookname)){
          b = book;
          break;
        }
      }
      if(b == null){
        System.out.println("Book Nonexistant in server, gave up.");
        return 0;
      }
    }
    if(!numbers.containsKey(b)){
      numbers.put(b, new CopyOnWriteArrayList<Object>());
    }
    //add ourselves to the queue of threads waiting for the book.
    Object bookmark = new Object();
    numbers.get(b).add(bookmark);
    System.out.println("Someone's buying " + b.getTitle() + ". We have " + b.getCopies() + " copies, and they want " + copies + " copies.");
    //int bought = 0;
    synchronized(b){
      if(copies <= b.getCopies()){
          //all copies can immediately be bought
        b.setCopies(b.getCopies() - copies);
          //Calculate how many milliseconds have gone by since this method was started.
        long timeToBuy = System.currentTimeMillis() - timeStarted;
        System.out.println("It took " + timeToBuy + " milliseconds to buy this.");
        numbers.get(b).remove(numbers.get(b).indexOf(bookmark));
        return copies;
      } else {
          long time = System.currentTimeMillis() + (long)10000; //picks a time 10 seconds from now.
          int bought=0;
          //not enough copies were sold in 10 seconds
          //so, we buy as many as there are
          bought += b.getCopies();
          b.setCopies(0);
          while(System.currentTimeMillis() < time){
            //this makes it wait only for the remainder of the 10 seconds.
            try{
              System.out.println("Waiting for "+bookname);
              b.wait(time - System.currentTimeMillis());
            }catch(InterruptedException e){
             System.out.println("Interrupted: " + e);
           }
           if(bookmark == numbers.get(b).get(0)){
             if(b.getCopies() < (copies-bought)){
                //not enough copies were sold in 10 seconds
                //so, we buy as many as there are
              bought += b.getCopies();
              System.out.println("bought " + bought + " copies.");
              b.setCopies(0);
            } else {
                //enough copies now exist! we buy all we need.
              b.setCopies(b.getCopies() - (copies-bought));
              long timeToBuy = System.currentTimeMillis() - timeStarted;
              System.out.println("It took " + timeToBuy + " milliseconds to buy this.");
              numbers.get(b).remove(numbers.get(b).indexOf(bookmark));
              b.notifyAll();
              return copies;
            }
          }
        }
        long timeToBuy = System.currentTimeMillis() - timeStarted;
        System.out.println("It took " + timeToBuy + " milliseconds to buy this.");
        numbers.get(b).remove(numbers.get(b).indexOf(bookmark));
        b.notifyAll();
        return bought;
      }
    }

    
  }

  public static void main(String args[]) {

    try {
      // create the RMI registry on the local machine
      Registry registry = LocateRegistry.createRegistry(REGISTRY_PORT);

      // create an object we're going to call methods on remotely
      Server obj = new Server();

      // export the object in the registry so it can be retrieved by client,
      // casting to the Remote interface
      Hello stub = (Hello) UnicastRemoteObject.exportObject(obj, 0);

      // bind the remote object's stub in the registry
      registry.bind("Hello", stub);

      System.err.println("Server ready");
    } catch (Exception e) {
      System.err.println("Server exception: " + e.toString());
      e.printStackTrace();
    }
  }
}
