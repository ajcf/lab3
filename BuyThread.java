package lab3;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

class BuyThread implements Runnable {

  Thread runner;
  String bookname;
  int copies;
  int times;
  int waittime;
  String ipAddress;
  int bought;
 
  public BuyThread(){}

  public BuyThread(String book, int num, int reps, int pause, String ip){
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
      for(int i = 0; i < times; i ++){
        Thread.sleep(waittime);
        //keep track of when the request was made
        long timeStarted = System.currentTimeMillis();
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
