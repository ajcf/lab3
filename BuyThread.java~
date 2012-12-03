package lab3;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

class BuyThread implements Runnable {

  Thread runner;
  String bookname;
  int copies;
  String ipAddress;
  int bought;

  public BuyThread(){}

  public BuyThread(String book, int num, String ip){
    bookname = book;
    copies = num;
    ipAddress = ip;
    bought = 0;
  }

  public void run(){
    try {
      Registry registry = LocateRegistry.getRegistry(ipAddress, Server.REGISTRY_PORT);

      // Get an object by looking up the name it was bound to.
      Hello stub = (Hello) registry.lookup("Hello");

      bought = stub.buy(bookname, copies);

      System.out.println("Bought " + bought + " copies of " + bookname);

    } catch (Exception e) {
      System.err.println("Client exception: " + e.toString());
      e.printStackTrace();

    }

  }

}
