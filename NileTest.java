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
  }

  static boolean testConnection(String host){
    sell("Book", 1, host, 1);
    return buy("Book", 1, host, 1) == 1;
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

