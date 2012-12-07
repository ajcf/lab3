This is the readme file for Priscilla Briggs and Amanda Cadwell-Frost's RMI Server project.

Our RMI Server has a low number of basic elements. 

In Hello.java, we have the abstract method which our server extends. They are in here so that the RMI Library will know about them, and the client will know how to call them.  The two methods that we went with were buy(book, copies), and sell(book, copies).  

The book object (in Book.java) holds the name and number of copies of each book. Books have unique strings for titles.

In Server.java, we have some data structures that let us keep track of our objects. stock is a synchronized array list of book objects, so that they will be accessible between threads.  numbers is a hashtable of books (as keys) and lists of threads waiting for each book (as values). The lists are ordered where 0 is the first thread to ask for a book, and the last element is the most recent thread to ask for a book.

our sell method is very straightforward.  we check to see if a book exists of the given title. If not, we create that object and add it to the stock. then, we take out a lock on the book and increase the stock.  Finally, we notify all threads that are waiting on that book, and return.

Our buy method is a little bit more complex.  One challenge that we faced was how to prevent both a buy request and a sell request from creating objects for the same book, if the buy request and sell requests came in at the same time. So, if a book with the given title does not exist, we wait for ten seconds. If it still does not exist, instead of creating a book object, we just return because clearly it does not exist. This did introduce a problem in that the server can take longer than 10 seconds to finish processing a buy request a book does not initially exist (causing the thread to wait for 10 seconds to see if it’s been created), then someone sells a few copies of it, but not enough for the thread to be satisfied, causing the thread to try to buy for 10 seconds as well. However, no thread gives up before ten seconds or holds the lock in buy on a book for more than 10 seconds, ensuring some fairness.

Next, we create an object (titled bookmark) and add it to the end of the queue of objects waiting for the book. Later, we can check to see if our object titled bookmark is the same as the first object in the queue, and if so, know that we can enter the critical sector. If it's not, we'll keep waiting.

Once the book object has been found, we synchronize on the book object and buy all available copies. If that's not enough copies, we wait on the book object for up to 10 seconds (unless we're woken before then). Once awake, we check if our bookmark object is at the top of the queue. If it is, we proceed to buy all available books and either fall back asleep for the remainder of the 10 seconds, or exit if we have sufficient books.


