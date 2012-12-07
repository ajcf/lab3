package lab3;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

  public static void main(String[] args) {
    //This is not a used class.

    /*String host = (args.length < 1) ? null : args[0];
    try {

      // Get the registry from the specified host (defaults to localhost)
      // on the port we designed for the registry in Server.
      Registry registry = LocateRegistry.getRegistry(host, Server.REGISTRY_PORT);

      // Get an object by looking up the name it was bound to.
      Hello stub = (Hello) registry.lookup("Hello");

      // Now we can make remote method calls on the object.
      //String response = stub.sayHello();

      //System.out.println("response: " + response);


      int asold = stub.sell("A Book", 6);

      System.out.println("Sold 6 copies of \"A Book\". There were previously " + asold + ".");

      int abought = stub.buy("A Book", 2);

      System.out.println("Bought " + abought + " copies of \"A Book\".");

      int aabought = stub.buy("A Book", 3);

      System.out.println("Bought " + aabought + " copies of \"A Book\".");

      int aaabought = stub.buy("A Book", 4);

      System.out.println("Bought " + aaabought + " copies of \"A Book\".");

      stub.sell("Another Book", 5);

      int bsold = stub.sell("Another Book", 6);

      int bbought = stub.buy("Another Book", 15);
      
      System.out.println("There were  " + bsold + ", then I sold 6, then I bought " + bbought);

      stub.buy("A Fake Book", 5);

    } catch (Exception e) {
      System.err.println("Client exception: " + e.toString());
      e.printStackTrace();
    }
    */
  }
}
