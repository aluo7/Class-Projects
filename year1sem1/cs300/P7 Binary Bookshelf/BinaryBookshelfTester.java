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

public class BinaryBookshelfTester {

  public static void main(String[] args) {
    System.out.println(runAllTests());
  }

  /**
   * tester for the testTreeNode() method
   * 
   * @return true if the test is passed, false if the test is failed
   */
  public static boolean testTreeNode() {
    // Scenario 1
    TreeNode<String> noChildren = new TreeNode<String>("single");
    if (noChildren.getLeft() != null || noChildren.getRight() != null) {
      return false;
    }
    if (!noChildren.toString().equals("single")) {
      return false;
    }

    // Scenario 2
    TreeNode<String> a = new TreeNode<String>("a");
    TreeNode<String> b = new TreeNode<String>("b");
    TreeNode<String> c = new TreeNode<String>("c");

    a.setLeft(b);
    if (a.getLeft() != b) {
      return false;
    }
    if (a.getRight() != null) {
      return false;
    }

    // Scenario 3
    TreeNode<String> tester = new TreeNode<String>("top", b, c);

    if (tester.getLeft() != b) {
      return false;
    }

    if (tester.getRight() != c) {
      return false;
    }
    return true;
  }

  /**
   * tester for the testEmptyTree() method
   * 
   * @return true if the test is passed, false if the test is failed
   */
  public static boolean testEmptyTree() {
    Book.resetGenerator();
    // Scenario 1
    boolean a = false, b = false, c = false, d = false;
    Attribute[] empty = new Attribute[0];
    try {
      BinaryBookshelf tester = new BinaryBookshelf(empty);
    } catch (IllegalArgumentException e) {
      a = true;

    }

    Attribute[] wrongLength = new Attribute[] { Attribute.AUTHOR, Attribute.PAGECOUNT, Attribute.ID };
    try {
      BinaryBookshelf tester = new BinaryBookshelf(wrongLength);
    } catch (IllegalArgumentException e) {
      b = true;
    }

    Attribute[] duplicate = new Attribute[] { Attribute.AUTHOR, Attribute.PAGECOUNT, Attribute.PAGECOUNT,
        Attribute.ID };
    try {
      BinaryBookshelf tester = new BinaryBookshelf(duplicate);
    } catch (IllegalArgumentException e) {
      c = true;
    }

    Attribute[] wrongFirst = new Attribute[] { Attribute.PAGECOUNT, Attribute.AUTHOR, Attribute.TITLE, Attribute.ID };
    try {
      BinaryBookshelf tester = new BinaryBookshelf(wrongFirst);
    } catch (IllegalArgumentException e) {
      d = true;
    }

    if (!(a && b && c && d)) {
      return false;
    }

    // Scenario 2
    Attribute[] correct = new Attribute[] { Attribute.AUTHOR, Attribute.PAGECOUNT, Attribute.TITLE, Attribute.ID };
    try {
      BinaryBookshelf correctTester = new BinaryBookshelf(correct);
    } catch (Exception e) {
      return false;
    }

    BinaryBookshelf tester = new BinaryBookshelf(correct);

    if (tester.size() != 0 || !tester.isEmpty() || !tester.toString().equals("")
        || tester.getRoot() != null) {
      return false;
    }

    if (!tester.getAttributeOrder().equals("1:AUTHOR 2:PAGECOUNT 3:TITLE 4:ID")) {
      return false;
    }

    Book book = new Book("title", 10, "last", "first");

    try {
      if (tester.contains(book)) {
        return false;
      }
    } catch (Exception e) {
      return false;

    }

    try {
      ArrayList<Book> copy = tester.getBooksByAuthor("first");

      if (copy.size() != 0) {
        return false;
      }
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  /**
   * tester for the testInsertBook() method
   * 
   * @return true if the test is passed, false if the test is failed
   */
  public static boolean testInsertBook() {
    Book.resetGenerator();
    // Scenario 1
    Attribute[] correct = new Attribute[] { Attribute.AUTHOR, Attribute.PAGECOUNT, Attribute.TITLE, Attribute.ID };
    BinaryBookshelf tester = new BinaryBookshelf(correct);

    if (tester.size() != 0) {
      return false;
    }

    Book book = new Book("title", 10, "last", "first");
    try {
      tester.insertBook(book);
    } catch (Exception e) {
      return false;
    }
    if (tester.isEmpty() || tester.size() != 1) {
      return false;
    }
    if (!tester.getRoot().getData().equals(book)) {
      return false;
    }

    // Scenario 2
    Book smallerBook = new Book("smaller", 8, "dune", "bike");

    try {
      tester.insertBook(smallerBook);

      if (tester.size() != 2) {
        return false;
      }
      if (!tester.getRoot().getLeft().getData().equals(smallerBook)) {
        return false;
      }
    } catch (Exception e) {
      return false;
    }

    // Scenario 3
    Book sharedAuthor = new Book("anything", 13, "last", "first");

    try {
      tester.insertBook(sharedAuthor);
    } catch (Exception e) {
      return false;
    }

    if (tester.size() != 3) {
      return false;
    }
    if (!tester.getRoot().getRight().getData().equals(sharedAuthor)) {
      return false;
    }

    boolean a = false;
    // Scenario 4
    try {
      // duplicate
      tester.insertBook(smallerBook);
    } catch (IllegalArgumentException e) {
      a = true;
    }

    if (!a) {
      return false;
    }
    return true;
  }

  /**
   * tester for the testContains() method
   * 
   * @return true if the test is passed, false if the test is failed
   */
  public static boolean testContains() {
    Book.resetGenerator();
    // Scenario 1
    Attribute[] correct = new Attribute[] { Attribute.AUTHOR, Attribute.PAGECOUNT, Attribute.TITLE, Attribute.ID };

    BinaryBookshelf tester = new BinaryBookshelf(correct);

    Book book = new Book("title", 10, "last", "first");

    tester.insertBook(book);

    if (!tester.contains(book)) {
      return false;
    }

    Book smallerBook = new Book("smaller", 10, "giraffe", "dune");

    if (tester.contains(smallerBook)) {
      return false;
    }

    // Scenario 2
    correct = new Attribute[] { Attribute.AUTHOR, Attribute.PAGECOUNT, Attribute.TITLE, Attribute.ID };

    BinaryBookshelf tester1 = new BinaryBookshelf(correct);

    Book rootBook = new Book("title", 10, "last", "first");

    tester1.insertBook(rootBook);

    smallerBook = new Book("smaller", 10, "giraffe", "dune");

    Book smallerBook1 = new Book("smaller", 10, "egg", "basket");
    Book smallerBook2 = new Book("title2", 10, "giraffe", "dune");
    Book largerBook = new Book("title", 25, "last", "first");
    Book notInShelf = new Book("someTitle", 19, "lastName", "firstName");

    TreeNode<Book> testerRoot = tester1.getRoot();

    testerRoot.setLeft(new TreeNode<Book>(smallerBook));
    testerRoot.setRight(new TreeNode<Book>(largerBook));
    testerRoot.getLeft().setLeft(new TreeNode<Book>(smallerBook1));
    testerRoot.getLeft().setRight(new TreeNode<Book>(smallerBook2));

    if (!tester1.contains(rootBook)) {
      return false;
    }

    if (!tester1.contains(smallerBook1)) {
      return false;
    }

    if (!tester1.contains(smallerBook)) {
      return false;
    }

    if (tester1.contains(notInShelf)) {
      return false;
    }
    return true;
  }

  /**
   * tester for the testGetBooksByAuthor() method
   * 
   * @return true if the test is passed, false if the test is failed
   */
  public static boolean testGetBooksByAuthor() {
    Book.resetGenerator();
    // Scenario 1
    Attribute[] correct = new Attribute[] { Attribute.AUTHOR, Attribute.PAGECOUNT, Attribute.TITLE, Attribute.ID };

    BinaryBookshelf tester = new BinaryBookshelf(correct);

    Book book = new Book("title", 10, "last", "first");

    tester.insertBook(book);

    ArrayList<Book> byAuthor = tester.getBooksByAuthor(book.getAuthor());

    if (byAuthor.size() != 1) {
      return false;
    }

    Book smallerBook = new Book("smaller", 10, "giraffe", "dune");
    Book smallerBook1 = new Book("smaller", 10, "egg", "basket");
    Book smallerBook2 = new Book("title2", 10, "giraffe", "dune");
    Book largerBook = new Book("title", 25, "last", "first");
    Book notInShelf = new Book("someTitle", 19, "lastName", "firstName");

    // no other books added yet
    byAuthor = tester.getBooksByAuthor(smallerBook.getAuthor());

    if (byAuthor.size() != 0) {
      return false;
    }

    TreeNode<Book> testerRoot = tester.getRoot();

    testerRoot.setLeft(new TreeNode<Book>(smallerBook));
    testerRoot.setRight(new TreeNode<Book>(largerBook));
    testerRoot.getLeft().setLeft(new TreeNode<Book>(smallerBook1));
    testerRoot.getLeft().setRight(new TreeNode<Book>(smallerBook2));

    // author with only one book: smallerBook1's author
    byAuthor = tester.getBooksByAuthor(smallerBook1.getAuthor());

    if (byAuthor.size() != 1) {
      return false;
    }

    // smallerBook and smallerBook2 have the same author
    byAuthor = tester.getBooksByAuthor(smallerBook.getAuthor());

    if (byAuthor.size() != 2) {
      return false;
    }

    byAuthor = tester.getBooksByAuthor(notInShelf.getAuthor());

    if (byAuthor.size() != 0) {
      return false;
    }
    return true;
  }

  /**
   * runs all methods
   * 
   * @return true if all the tests are passed, false otherwise
   */
  public static boolean runAllTests() {
    if (!testTreeNode() || !testEmptyTree() || !testContains() || !testGetBooksByAuthor() || !testInsertBook()) {
      return false;
    }
    return true;
  }
}
