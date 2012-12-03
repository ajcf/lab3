package lab3;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class NileTest {
  public static void main (String args[]){
    String hostIP = (args.length < 1) ? null : args[0];
    System.out.println("Began");
    runtests(hostIP);
  }

  static void runtests(String host){
    if(testConnection(host)){
      System.out.println("connection good.");
    } else {
      System.out.println("CONNECTION NOT MADE");
    }
    testSynchronization(host);
    testMultiSynchronization(host);
  }

  static boolean testConnection(String host){
    Thread s = new Thread(new SellThread("ConnBook", 1, host));
    s.start();
    long timeStarted = System.currentTimeMillis();
    boolean bool = (buy("ConnBook", 1, host, 1) == 1);
    long timeToServer = System.currentTimeMillis() - timeStarted;
    System.out.println("It took " + timeToServer + " milliseconds to run over the network.");
    return bool;
  }

  static void testSynchronization(String host){
    Thread b = new Thread(new BuyThread("SynchBook", 4, host));
    b.start();
    Thread s = new Thread(new SellThread("SynchBook", 2, host));
    s.start();
    try{
      b.join();
      s.join();
    } catch(InterruptedException e){
      System.out.println(e);
    }
  }
  static void testMultiSynchronization(String host){

    Thread b = new Thread(new BuyThread("SynchBook2", 20, host));
    b.start();
    for(int i = 0; i < 10; i++){
      new Thread(new SellThread("SynchBook2", 1, host)).start();
    }
    try{
      b.join();
    } catch(InterruptedException e){
      System.out.println(e);
    }
  }
  static int sell (String title, int copies, String ip, int times) {

    String host = ip;
    int a = 0;
    try {
      //setup stuff
      Registry registry = LocateRegistry.getRegistry(host, Server.REGISTRY_PORT);
      Hello stub = (Hello) registry.lookup("Hello");
      //try to sell stuff
      System.out.println("Attempting to sell " + copies + " of " + title + ".");
      for(int i = 0; i < times; i++){
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

