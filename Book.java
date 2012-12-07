package lab3;

public class Book {
  private String title;
  private int copies;


//This object keeps track of the name of a book, and the number of copies of that book that are currently in stock. 
  public Book(String title, int copies) {
    System.out.println("Creating new book with title \"" + title + "\" and " + copies + " copies.");
    this.title = title;
    //Ensure we don't keep negative copies in stock. 
    if(copies >= 0){
      this.copies = copies;
    } else {
      this.copies = 0;
      System.out.println("Set copies of \"" + title + "\" to 0 because input was negative.");
    }
  }
  //just returns the title of the book.
  public String getTitle(){
    return this.title;
  }
  //returns true if title is valid, otherwise, returns false
  //so far, the return value is unused.
  public boolean setTitle(String newTitle){
    if(newTitle.length() > 0){
      this.title = newTitle;
      return true;
    }
    return false;
  }
  //just returns the current number of copies of a book
  public int getCopies(){
    return copies;
  }
  //returns true if num of copies is valid, otherwise, returns false
  //so far, the return value is unused.
  public boolean setCopies(int newCopies){    
  //Ensure we don't keep negative copies in stock. 
    if (newCopies >= 0){
      copies = newCopies;
      return true;
    }
    return false;
  }
}
