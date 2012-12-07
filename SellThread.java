package lab3;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

class SellThread implements Runnable{
  Thread runner;
  String bookname; //The name of the book you want to sell.
  int copies; // The number of copies of that book you want to sell.
  int times; // The number of times you want to sell that many copies of that many books.
  int waittime; // The amount of time to wait between each time you sell that many copies of that many book. 
  String ipAddress; //The IP address of the server that you want to sell on. 

  public SellThread (String book, int num, int reps, int pause, String ip)
  {
    //This constructor sets most of the global variables. 
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
      //The for loop sells the specified number of copies of the specified book, multiple times.
      for(int i = 0; i < times; i ++){
        Thread.sleep(waittime);
        //keep track of when the request was made
        long timeStarted = System.currentTimeMillis();
        //Sell the book on the server. 
        sold = stub.sell(bookname, copies);        
        //record the time it takes to complete the request over the server.
        long timeToSell = System.currentTimeMillis() - timeStarted;
        System.out.println("It took " + timeToSell + " milliseconds sell the book over the server.");
        
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
