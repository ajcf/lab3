package lab3;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

class BuyThread implements Runnable {

  Thread runner;
  String bookname; // The name of the book you want this thread to buy.
  int copies; // The number of copies of the above book that you would like to buy.
  int times; // The number of times you would like to buy that many copies of that book.
  int waittime;  // The amount of time to wait between buying that many copies once and buying that many copies again.
  String ipAddress; //The IP address of the server.
  int bought; //How many books were succesfully bought. 
 
  public BuyThread(){}

  public BuyThread(String book, int num, int reps, int pause, String ip){
    //Standard constructor for the BuyThread
    bookname = book;
    copies = num;
    times = reps;
    waittime = pause;
    ipAddress = ip;
    bought = 0;
  }
  
  public void run(){
    try {
      Registry registry = LocateRegistry.getRegistry(ipAddress, Server.REGISTRY_PORT);

      // Get an object by looking up the name it was bound to.
      Hello stub = (Hello) registry.lookup("Hello");

      int bought = 0;
      System.out.println("Attempting to buy " + bookname + ", " + times + " times.");
      //this for loop will repeat the buying action many times.
      for(int i = 0; i < times; i ++){
        Thread.sleep(waittime);
        //keep track of when the request was made
        long timeStarted = System.currentTimeMillis();
        //make a call to buy this many copies of this book.
        bought = stub.buy(bookname, copies);
        //record the time it takes to complete the request over the server.
        long timeToBuy = System.currentTimeMillis() - timeStarted;
        System.out.println("It took " + timeToBuy + " milliseconds buy the book over the server.");
        System.out.println(copies + " copies of " + bookname + " bought, there were originally " + bought);
      }

      System.out.println("Bought " + bought + " copies of " + bookname);

    } catch (Exception e) {
      System.err.println("Client exception: " + e.toString());
      e.printStackTrace();

    }

  }

}
