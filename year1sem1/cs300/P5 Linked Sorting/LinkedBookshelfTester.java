//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: P07 Folder Explorer
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
// Persons: Alexander Scheibe - Taught me how to optimize my tester and write it properly
// Online Sources: none
//
///////////////////////////////////////////////////////////////////////////////

public class LinkedBookshelfTester {
    /**
     * tests the LinkedNode constructors, getters, and mutators
     * 
     * @return true if test passes, false otherwise
     */
    public static boolean testLinkedNode() {
        Book.resetGenerator();
        LinkedNode<Book> n1 = new LinkedNode<Book>(new Book("Good Omens", 288, "Gaiman", "Neil"));
        LinkedNode<Book> n2 = new LinkedNode<Book>(new Book("FEED", 608, "Grant", "Mira"));
        n1.setNext(n2);

        if (n1.getNext() != null) {
            return n2.equals(n1.getNext());
        }

        return false;
    }

    /**
     * tests the clear() method of LinkedBookshelf
     * 
     * @return true if test passes, false otherwise
     */
    public static boolean testClear() {
        LinkedBookshelf testShelf = new LinkedBookshelf();

        testShelf.appendBook(new Book("Good Omens", 288, "Gaiman", "Neil"));
        testShelf.appendBook(new Book("FEED", 608, "Grant", "Mira"));
        testShelf.appendBook(new Book("Snow Crash", 468, "Stephenson", "Neal"));
        testShelf.appendBook(new Book("2001", 296, "Clarke", "Arthur C"));

        boolean testFirst = false;
        boolean testLast = false;
        boolean testSize = false;
        boolean isEmpty = false;

        try {
            testShelf.clear();

            isEmpty = testShelf.isEmpty();
            testSize = testShelf.size() == 0;
            testFirst = testShelf.getFirst() != null;
            testLast = testShelf.getLast() != null;
        } catch (NullPointerException e) {
            return testSize && isEmpty && !testFirst && !testLast;
        }
        return false;
    }

    /**
     * tests the appendBook() method of LinkedBookshelf
     * 
     * @return true if test passes, false otherwise
     */
    public static boolean testAddBooks() {

        LinkedBookshelf testShelf = new LinkedBookshelf();

        boolean testAdding = false;
        boolean testSize = false;
        boolean testLast = false;

        try {
            testShelf.appendBook(new Book("Good Omens", 288, "Gaiman", "Neil"));
            testAdding = testShelf.getLast().equals(testShelf.getFirst());
            testShelf.appendBook(new Book("FEED", 608, "Grant", "Mira"));
            testShelf.appendBook(new Book("Snow Crash", 468, "Stephenson", "Neal"));
            Book lastB = new Book("2001", 296, "Clarke", "Arthur C");
            testShelf.appendBook(lastB);
            testSize = testShelf.size() == 4;
            testLast = testShelf.get(3).equals(lastB);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            return false;
        }
        return testAdding && testLast && testSize;
    }

    /**
     * tests the sort() method of LinkedBookshelf
     * 
     * @return true if test passes, false otherwise
     */
    public static boolean testSortBooks() {
        boolean authorTest = false;
        boolean idTest = false;
        boolean pageCountTest = false;
        boolean titleTest = false;
        LinkedBookshelf testShelf = new LinkedBookshelf();
        Book.resetGenerator();

        testShelf.appendBook(new Book("Good Omens", 288, "Gaiman", "Neil"));
        testShelf.appendBook(new Book("FEED", 608, "Grant", "Mira"));
        testShelf.appendBook(new Book("Snow Crash", 468, "Stephenson", "Neal"));
        testShelf.appendBook(new Book("2001", 296, "Clarke", "Arthur C"));

        try {
            LinkedBookshelf.sort(testShelf, Attribute.AUTHOR);
            System.out.println(testShelf);
            authorTest = testShelf.get(0).getAuthor().equals("Clarke, Arthur C") && testShelf.get(1).getAuthor().equals("Gaiman, Neil")
                    && testShelf.get(2).getAuthor().equals("Grant, Mira") && testShelf.get(3).getAuthor().equals("Stephenson, Neal");

            LinkedBookshelf.sort(testShelf, Attribute.ID);
            idTest = testShelf.get(0).ID == 0 && testShelf.get(1).ID == 1 && testShelf.get(2).ID == 2 && testShelf.get(3).ID == 3;

            LinkedBookshelf.sort(testShelf, Attribute.TITLE);
            titleTest = testShelf.get(0).getTitle().equals("2001") && testShelf.get(1).getTitle().equals("FEED")
                    && testShelf.get(2).getTitle().equals("Good Omens") && testShelf.get(3).getTitle().equals("Snow Crash");

            LinkedBookshelf.sort(testShelf, Attribute.PAGECOUNT);
            pageCountTest = testShelf.get(0).getPageCount() == 288 && testShelf.get(1).getPageCount() == 296
                    && testShelf.get(2).getPageCount() == 468 && testShelf.get(3).getPageCount() == 608;
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            return false;
        }
        return authorTest && titleTest && pageCountTest && idTest;
    }
    /**
     * runs all tests
     * 
     * @return true if every test passes, false otherwise
     */
    public static boolean runAllTests() {
        return testSortBooks() && testClear() && testAddBooks() && testLinkedNode();
    }

    /**
     * Main method that calls runAllTests()
     */
    public static void main(String[] args) {
        runAllTests();

        System.out.println(runAllTests());
    }

}