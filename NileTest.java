package lab3;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

//This class is our testing / client class. 

public class NileTest {

  public static void main (String args[]){
    String hostIP = (args.length < 1) ? null : args[0];
    System.out.println("Began");
    runtests(hostIP);
  }

  //this method chooses which tests to run and calls them.
  static void runtests(String host){
    if(testConnection(host)){
      System.out.println("connection good.");
    } else {
      System.out.println("CONNECTION NOT MADE");
    }
    //If you would like to run other tests, list them here. These are the tests we chose to run right now.
    testSynchronization(host);
    testMultiSynchronization(host);
  }

  static boolean testConnection(String host){
    //This test ensures the connection works by attempting a very simple sell. 
    //It creates a single thread and has that thread sell a single book once. 
    //Then it attempts to buy one copy of the book that was just sold.
    //If this thread is successful, then it will return true. 
    Thread s = new Thread(new SellThread("ConnBook", 1, 1, 0, host));
    s.start();
    boolean bool = (buy("ConnBook", 1, host, 1) == 1);
    return bool;
  }

  static void testSynchronization(String host){
    //This test ensures that our 10 second wait function is working
    //It does this by selling copies of a book after we already asked to buy some. 

    //Try to buy 4 copies of SynchBook once. 
    Thread b = new Thread(new BuyThread("SynchBook", 4, 1, 0, host));
    b.start();
    //Try to sell 2 copies of synch book once.
    Thread s = new Thread(new SellThread("SynchBook", 2, 1, 0, host));
    s.start();
    try{
      b.join();
      s.join();
    } catch(InterruptedException e){
      System.out.println(e);
    }
  }
  static void testMultiSynchronization(String host){
    //This tests sychronization again, however this time we sell 1 copy 10 different times.

    //Try to buy 20 copies of synchbook once. 
    Thread b = new Thread(new BuyThread("SynchBook2", 20, 1, 0, host));
    b.start();
    //Try to sell 1 copy of synchbook 10 times, with a 0 second wait between each time.
    Thread c = new Thread(new SellThread("SynchBook2", 1, 10, 0, host));
    c.start();
    try{
      b.join();
      c.join();
    } catch(InterruptedException e){
      System.out.println(e);
    }
  }
  
  //staic void lotsOfRequests(String host){
    
  
  static int sell (String title, int copies, String ip, int times) {
    //Called by other methods in this class, Used when we don't want to use SellThread
    String host = ip;
    int a = 0;
    try {
      //setup stuff
      Registry registry = LocateRegistry.getRegistry(host, Server.REGISTRY_PORT);
      Hello stub = (Hello) registry.lookup("Hello");
      //try to sell stuff
      System.out.println("Attempting to sell " + copies + " of " + title + ".");
      for(int i = 0; i < times; i++){
        //Sell the book described. 
        a = stub.sell(title, copies);
        System.out.println("Iteration "+i+1+". There were previously " + a + " copies.");
      }
    } catch (Exception e) {
      System.err.println("Client exception: " + e.toString());
      e.printStackTrace();
    }
    return a;
  }

  static int buy (String title, int copies, String ip, int times) {
    //Called by other methods in this class, used when we don't want to use BuyThread
    String host = ip;
    int a = 0;
    try {
      //Setup stuff
      Registry registry = LocateRegistry.getRegistry(host, Server.REGISTRY_PORT);
      Hello stub = (Hello) registry.lookup("Hello");
      //try to buy stuff
      System.out.println("Attempting to buy " + copies + " of " + title + ".");
      for(int i = 0; i < times; i++){
        a = stub.buy(title, copies);
        System.out.println("Iteration "+i+1+". Bought " + a + " copies.");
      }
    } catch (Exception e) {
      System.err.println("Client exception: " + e.toString());
      e.printStackTrace();
    }
    return a;
  }
}

