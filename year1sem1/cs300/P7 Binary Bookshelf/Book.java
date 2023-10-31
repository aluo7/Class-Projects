//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: P10 Binary Bookshelf
// Course: CS 300 Fall 2021
//
// Author: Alan Luo
// Email: aluo7@wisc.edu
// Lecturer: Mouna Kacem
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name: (name of your pair programming partner)
// Partner Email: (email address of your programming partner)
// Partner Lecturer's Name: (name of your partner's lecturer)
//
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
// ___ Write-up states that pair programming is allowed for this assignment.
// ___ We have both read and understand the course Pair Programming Policy.
// ___ We have registered our team prior to the team registration deadline.
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons: Isha Rustagi (irustagi@wisc.edu)
// Online Sources: none
//
///////////////////////////////////////////////////////////////////////////////

/**
 * An instantiable class representing information about a Book for our LinkedBookshelf project.
 * 
 * @author hobbes
 *
 */
public class Book extends Object{

  // data
  private static int idGenerator = 0;
  private String authorLastname;
  private String authorFirstname;
  private String title;
  private int pageCount;

  /**
   * The ID value for this Book.
   */
  public final int ID;

  /**
   * A constructor for a Book with no recorded author
   * 
   * @param title     the title of the Book
   * @param pageCount the number of pages in the Book
   */
  public Book(String title, int pageCount) {
    if (pageCount <= 0)
      throw new IllegalArgumentException("Invalid page count");
    this.title = title;
    this.pageCount = pageCount;
    this.ID = idGenerator++;

    // set defaults for an anonymous book
    this.authorFirstname = "";
    this.authorLastname = "";
  }

  /**
   * A constructor for a Book with an author
   * 
   * @param title     the title of the Book
   * @param pageCount the number of pages in the Book
   * @param last      the author's last name
   * @param first     the author's first name
   */
  public Book(String title, int pageCount, String last, String first) {
    this(title, pageCount);
    this.authorFirstname = first;
    this.authorLastname = last;
  }

  /**
   * Accessor for the author value
   * 
   * @return the author of this Book, as "Lastname, Firstname"
   */
  public String getAuthor() {
    return authorLastname + ", " + authorFirstname;
  }

  /**
   * Accessor for the title value
   * 
   * @return the title of this Book
   */
  public String getTitle() {
    return this.title;
  }

  /**
   * Accessor for the pageCount value
   * 
   * @return the number of pages in this Book
   */
  public int getPageCount() {
    return this.pageCount;
  }

  /**
   * A nonstandard compareTo - based on the attribute argument, returns a comparison of this Book to
   * otherBook based on one of four possible pieces of data.
   * 
   * @param otherBook the Book to compare this one to
   * @param a         the attribute on which to compare them
   * @return negative if this Book is greater than otherBook, positive if less than otherBook, 0 if
   *         equal.
   */
  public int compareTo(Book otherBook, Attribute a) {
    switch (a) {
      case ID:
        return this.ID - otherBook.ID;
      case TITLE:
        return this.title.compareTo(otherBook.title);
      case AUTHOR:
        return this.getAuthor().compareTo(otherBook.getAuthor());
      case PAGECOUNT:
        return this.pageCount - otherBook.pageCount;
      default:
        return 0;
    }
  }

  /**
   * Checks if two books are equal by a given attribute
   * 
   * @return the boolean value if two books are equal or not by an attribute
   */
  @Override
  public boolean equals(Object otherBook) {
    
      Book other=(Book)otherBook;
      return (this.ID == other.ID && this.title.equals(other.title)
          && this.getAuthor().equals(other.getAuthor()) && this.pageCount == other.pageCount);
    

  }


  /**
   * Constructs a String representation of this Book's data for printing
   * 
   * @return the String representation of this Book
   */
  public String toString() {
    return this.ID + ": \"" + this.title + "\", " + this.getAuthor() + " (" + this.pageCount + ")";
  }

  /**
   * Resets the ID generator for testing purposes. Call this method at the beginning of every test
   * method you write to assure that all Book ID numbers begin from 0 again.
   */
  public static void resetGenerator() {
    idGenerator = 0;
  }
}
