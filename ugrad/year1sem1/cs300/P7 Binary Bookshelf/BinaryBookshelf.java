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

import java.util.ArrayList;

public class BinaryBookshelf {
  // the root node of the BST
  private TreeNode<Book> root;
  // the number of nodes currently contained in the BST
  private int size;
  // the ordered array of attributes by which the BST nodes are sorted
  private Attribute[] sortList;

  /**
   * one-argument constructor, initializes an empty BinaryBookshelf
   * 
   * @param sortList an ordered array of attributes
   */
  public BinaryBookshelf(Attribute[] sortList) {
    this.size = 0;
    this.root = new TreeNode<Book>(null);
    boolean id = false;
    boolean title = false;
    boolean pageCount = false;
    boolean uniqueAtt = false;
    for (int i = 1; i < sortList.length; i++) {
      if (sortList[i] == Attribute.ID) {
        id = true;
      }
      if (sortList[i] == Attribute.TITLE) {
        title = true;
      }
      if (sortList[i] == Attribute.PAGECOUNT) {
        pageCount = true;
      }
    }
    if (id && title && pageCount) {
      uniqueAtt = true;
    }

    if (sortList.length != 4 || sortList[0] != (Attribute.AUTHOR) || !uniqueAtt) {
      throw new IllegalArgumentException("The sortList argument is not a four-element "
          + "array of unique attributes, beginning with Attribute.AUTHOR");
    } else
      this.sortList = sortList;
  }

  /**
   * getter for the number of nodes currently in the BST
   * 
   * @return the number of nodes currently in the BST
   */
  public int size() {
    return this.size;
  }

  /**
   * determines whether the BST is empty
   * 
   * @return boolean representing whether the BST is empty or not
   */
  public boolean isEmpty() {
    if (this.size == 0) {
      return true;
    }
    return false;
  }

  /**
   * provides a String-formatted list of the attributes in the order in which they
   * are used
   * 
   * @return String formatted list of the attributes
   */
  public String getAttributeOrder() {
    String result = "";

    for (int i = 0; i < this.sortList.length; i++) {
      result += (i + 1) + ":" + this.sortList[i] + " ";

    }
    return result;
  }

  /**
   * searches for the input in the bookshelf
   * 
   * @param book book to be searched for in bookshelf
   * @return boolean representing whether the book is in the bookshelf or not
   */
  public boolean contains(Book book) {
    if (this.size == 0) {
      return false;
    }
    return containsHelper(book, this.root);

  }

  /**
   * recursive helper method, searches for the input in the rooted subtree
   * 
   * @param book    book to be searched for in bookshelf
   * @param current the current rooted subtree to be searched
   * @return boolean representing whether the book is in the bookshelf or not
   */
  protected boolean containsHelper(Book book, TreeNode<Book> current) {
    if (current == null) {
      return false;
    }

    if (book.equals(current.getData())) {
      return true;
    } else if (this.compareToHelper(book, current.getData()) < 0) {
      return containsHelper(book, current.getLeft());
    } else {
      return containsHelper(book, current.getRight());
    }
  }

  /**
   * helper method to compare two Book objects according to the sortList of
   * attributes
   * 
   * @param one the first book
   * @param two the second book
   * @return a negative value if one < two, a positive value if one > two, and 0
   *         if they are equal
   */
  protected int compareToHelper(Book one, Book two) {
    if (one.compareTo(two, Attribute.AUTHOR) != 0) {
      return one.compareTo(two, Attribute.AUTHOR);
    } else if (one.compareTo(two, this.sortList[1]) != 0) {
      return one.compareTo(two, this.sortList[1]);
    } else if (one.compareTo(two, this.sortList[2]) != 0) {
      return one.compareTo(two, this.sortList[2]);
    }
    return one.compareTo(two, this.sortList[3]);
  }

  /**
   * @param authorName author's name
   * @return a list of books in the bookshelf written by the given author
   */
  public ArrayList<Book> getBooksByAuthor(String authorName) {
    if (size == 0) {
      return new ArrayList<Book>();

    }

    return getBooksByAuthorHelper(authorName, root);
  }

  /**
   * recursive helper method to get the books written by the given author
   * 
   * @param authorName the author name to filter on
   * @param current    the root of the current subtree
   * @return a list of books in the subtree rooted at current which were written
   *         by the given author
   */
  protected ArrayList<Book> getBooksByAuthorHelper(String authorName, TreeNode<Book> current) {
    ArrayList<Book> list = new ArrayList<Book>();
    if (current == null)
      return list;
    if (current.getData().getAuthor().equals(authorName)) {
      list.add(current.getData());
    }
    if (current.getLeft() != null) {
      list.addAll(getBooksByAuthorHelper(authorName, current.getLeft()));
    }
    if (current.getRight() != null) {
      list.addAll(getBooksByAuthorHelper(authorName, current.getRight()));
    }
    return list;
  }

  @Override
  /**
   * @return an in-order traversal of the BST, with each Book on a separate line
   */
  public String toString() {
    if (size == 0) {
      return null;
    }
    return toStringHelper(root);
  }

  /**
   * recursive helper method to create the String reprsesentation of the subtree
   * rooted at the current node
   * 
   * @param current the root of the current subtree
   * @return the string representtion of the subtree
   */
  protected String toStringHelper(TreeNode<Book> current) {
    String str = "";
    if (current.getLeft() != null) {
      str += toStringHelper(current.getLeft());
    }
    str += current.toString() + "\n";
    if (current.getRight() != null) {
      str += toStringHelper(current.getRight());
    }
    return str;
  }

  /**
   * getter for the root node
   * 
   * @return returns a shallow copy of the root node
   */
  protected TreeNode<Book> getRoot() {
    if (size == 0) {
      return null;
    }
    return this.root;
  }

  /**
   * resets the BST to be empty
   */
  public void clear() {
    this.root = null;
    this.root.setLeft(null);
    this.root.setRight(null);
    this.size = 0;
  }

  /**
   * adds a new Book to the BST in sorted order, relative to this BST's sortList
   * attributes
   * 
   * @param book book to be added
   * @throws IllegalArgumentException if the book is already in the BST
   */
  public void insertBook(Book book) {
    if (contains(book)) {
      throw new IllegalArgumentException("This Book is already in the BST!");
    }

    if (this.size == 0) {
      this.root = new TreeNode<Book>(book);
    } else {
      insertBookHelper(book, root);
    }
    this.size++;
  }

  /**
   * recursive helper method; adds the given Book to the subtree rooted at current
   * 
   * @param book    book to be added
   * @param current the root of the current subtree
   */
  protected void insertBookHelper(Book book, TreeNode<Book> current) {
    if (this.compareToHelper(book, current.getData()) < 0) {
      if (current.getLeft() == null) {
        current.setLeft(new TreeNode<Book>(book));
      } else {
        insertBookHelper(book, current.getLeft());
      }
    }

    else {
      if (current.getRight() == null) {
        current.setRight(new TreeNode<Book>(book));
      } else {
        insertBookHelper(book, current.getRight());
      }
    }
  }
}
