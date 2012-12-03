package lab3;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

class SellThread implements Runnable{
  Thread runner;
  String bookname;
  int copies;
  String ipAddress;

  public SellThread (String book, int num, String ip)
  {
     bookname = book;
   copies = num;
   ipAddress = ip;

  }

  public void run(){

    try{
      Registry registry = LocateRegistry.getRegistry(ipAddress, Server.REGISTRY_PORT);
      Hello stub = (Hello) registry.lookup("Hello");
      System.out.println("Attempting to sell " + bookname);
      int sold = stub.sell(bookname, copies);
      System.out.println(copies + " copies of " + bookname + " sold, there were originally " + sold);
    } catch (Exception e) {
      System.err.println("Client exception: " + e.toString());
      e.printStackTrace();

    }
  }
}
