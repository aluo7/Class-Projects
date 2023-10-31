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
// Persons: none
// Online Sources: none
//
///////////////////////////////////////////////////////////////////////////////

import java.util.EmptyStackException;
import java.util.NoSuchElementException;

public class TileMatchingTester {

    public static void main(String[] args) {
        tileMatchingGameTester();
    }

    /*
     * tester for the tile equals() method
     * 
     * @returns boolean true if test is passed, false if test is failed
     */
    public static boolean tileEqualsTester() {
        boolean sameColor = false;
        boolean diffColor = false;
        boolean nullTile = false;
        boolean stringTile = false;

        Tile t1 = new Tile(Color.BLACK);
        Tile t2 = new Tile(Color.BLUE);
        Tile t3 = null;
        String t4 = t2.toString();

        try {
            sameColor = t1.equals(t1);
            diffColor = !t1.equals(t2);
            nullTile = !t1.equals(t3);
            stringTile = !t1.equals(t4);
        } catch (NullPointerException e) {
            return false;
        }
        return sameColor && diffColor && nullTile && stringTile;
    }

    /*
     * tester for TileListIterator class
     * 
     * @returns boolean true if test is passed, false if test is failed
     */
    public static boolean tileListIteratorTester() {
        boolean hasNextTest = false;
        boolean nextTest = false;
        boolean nextNull = false;
        boolean noSuchElementTester = false;

        Tile t1 = new Tile(Color.BLUE);
        Tile t2 = new Tile(Color.BLACK);
        Tile t3 = new Tile(Color.ORANGE);
        Tile t4 = new Tile(Color.YELLOW);

        Node n1 = new Node(t1);
        Node n2 = new Node(t2);
        Node n3 = new Node(t3);
        Node n4 = new Node(t4);

        TileListIterator i = new TileListIterator(n1);

        try {
            hasNextTest = i.hasNext();

            n1.setNext(n2);
            n2.setNext(n3);
            n3.setNext(n4);

            nextTest = i.next().equals(t1) && i.next().equals(t2) && i.next().equals(t3) && i.next().equals(t4);

            n4.setNext(null);

            nextNull = !i.hasNext();

            i.next();

        } catch (NoSuchElementException e) {
            noSuchElementTester = true;
        }
        return hasNextTest && nextTest && nextNull && noSuchElementTester;
    }

    /*
     * tester for TileStack class
     * 
     * @returns boolean true if test is passed, false if test is failed
     */
    public static boolean tileStackTester() {
        boolean emptyTest = false;
        boolean sizeTest = false;
        boolean pushTest = false;
        boolean peekTest = false;
        boolean popTest = false;
        boolean iteratorTest = false;
        boolean noSuchElementTester = false;

        TileStack t = new TileStack();
        TileStack tEmpty = new TileStack();

        Tile t1 = new Tile(Color.BLUE);
        Tile t2 = new Tile(Color.BLACK);
        Tile t3 = new Tile(Color.ORANGE);
        Tile t4 = new Tile(Color.YELLOW);

        try {
            emptyTest = t.isEmpty();

            sizeTest = (t.size() == 0);

            t.push(t1);
            t.push(t2);
            t.push(t3);
            t.push(t4);

            pushTest = (t.size() == 4);

            peekTest = (t.peek() == t4);

            popTest = (t.pop() == t4 && t.size() == 3);

            iteratorTest = t.iterator().hasNext() && t.size() == 3;

            tEmpty.pop();

            noSuchElementTester = false;

            tEmpty.peek();

        } catch (EmptyStackException e) {
            noSuchElementTester = true;
        }
        return emptyTest && sizeTest && pushTest && peekTest && popTest && iteratorTest && noSuchElementTester;
    }

    /*
     * tester for TileMatchingGame class
     * 
     * @returns boolean true if test is passed, false if test is failed
     */
    public static boolean tileMatchingGameTester() {
        boolean getColumnNumberTest = false;
        boolean dropTileTest = false;
        boolean columnTest = false;
        boolean toStringTest = false;
        boolean clearTest = false;
        boolean restartTest = false;
        boolean illegalArgumentTest = false;
        boolean outOfBoundsTest1 = false;
        boolean outOfBoundsTest2 = false;
        boolean outOfBoundsTest3 = false;

        Tile t1 = new Tile(Color.BLUE);
        Tile t2 = new Tile(Color.BLACK);
        Tile t3 = new Tile(Color.ORANGE);
        Tile t4 = new Tile(Color.YELLOW);

        TileMatchingGame tg = new TileMatchingGame(2);

        String[] split;
        int col0_length;
        int col1_length;

        try {
            getColumnNumberTest = tg.getColumnsNumber() == 2;

            tg.dropTile(t1, 0);
            tg.dropTile(t2, 0);
            tg.dropTile(t3, 1);
            tg.dropTile(t3, 1);
            tg.dropTile(t4, 1);

            split = tg.column(0).split(" ");
            col0_length = split.length;

            split = tg.column(1).split(" ");
            col1_length = split.length;

            dropTileTest = col0_length == 2 && col1_length == 3;

            columnTest = tg.column(0).equals("BLACK BLUE") && tg.column(1).equals("YELLOW ORANGE ORANGE");

            toStringTest = tg.toString().equals("GAME COLUMNS:\n0: BLACK BLUE\n1: YELLOW ORANGE ORANGE\n");

            tg.clearColumn(0);

            clearTest = tg.column(0).length() == 0 && tg.column(1).equals("YELLOW ORANGE ORANGE");

            tg.restartGame();

            restartTest = tg.column(0).length() == 0 && tg.column(1).length() == 0;

            TileMatchingGame illegal = new TileMatchingGame(-2);

        } catch (IllegalArgumentException e) {
            illegalArgumentTest = true;
        }

        try {
            tg.column(-1);
        } catch (IndexOutOfBoundsException e) {
            outOfBoundsTest1 = true;
        }

        try {
            tg.dropTile(t1, -2);
        } catch (IndexOutOfBoundsException e) {
            outOfBoundsTest2 = true;
        }

        try {
            tg.clearColumn(-3);;
        } catch (IndexOutOfBoundsException e) {
            outOfBoundsTest3 = true;
        }

        return columnTest && toStringTest && dropTileTest && clearTest && restartTest && getColumnNumberTest
                && illegalArgumentTest && outOfBoundsTest1 && outOfBoundsTest2 && outOfBoundsTest3;
    }
}
