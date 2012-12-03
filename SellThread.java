package lab3;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

class SellThread implements Runnable{
  Thread runner;
  String bookname;
  int copies;
  int times;
  int waittime;
  String ipAddress;

  public SellThread (String book, int num, int reps, int pause, String ip)
  {
     bookname = book;
     copies = num;
     ipAddress = ip;
     times = reps;
     waittime = pause;

  }

  public void run(){

    try{
      Registry registry = LocateRegistry.getRegistry(ipAddress, Server.REGISTRY_PORT);
      Hello stub = (Hello) registry.lookup("Hello");
      int sold = 0;
      System.out.println("Attempting to sell " + bookname + ", " + times + " times.");
      for(int i = 0; i < times; i ++){
        Thread.sleep(waittime);
        sold = stub.sell(bookname, copies);
        System.out.println(copies + " copies of " + bookname + " sold, there were originally " + sold);
      }
    } catch (Exception e) {
      System.err.println("Client exception: " + e.toString());
      e.printStackTrace();
//have a loop in here that issues multiple requests
//and sleeps.
      
    }
  }
}
