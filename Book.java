package lab3;

public class Book {
  private String title;
  private int copies;

  public Book(String title, int copies) {
    System.out.println("Creating new book with title \"" + title + "\" and " + copies + " copies.");
    this.title = title;
    if(copies > 0){
      this.copies = copies;
    } else {
      this.copies = 0;
      System.out.println("Set copies of \"" + title + "\" to 0 because input was negative.");
    }
  }
  public String getTitle(){
    return this.title;
  }
  //returns true if title is valid, otherwise, returns false
  public boolean setTitle(String newTitle){
    if(newTitle.length() > 0){
      this.title = newTitle;
      return true;
    }
    return false;
  }

  public int getCopies(){
    return copies;
  }
  //returns true if num of copies is valid, otherwise, returns false
  public boolean setCopies(int newCopies){
    if (newCopies >= 0){
      copies = newCopies;
      return true;
    }
    return false;
  }
}
