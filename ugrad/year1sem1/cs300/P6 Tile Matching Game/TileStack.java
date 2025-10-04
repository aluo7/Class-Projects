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
import java.util.Iterator;

public class TileStack implements Iterable<Tile>, StackADT<Tile> {
    // refers to the top of the linked stack.
    private Node top;
    // keeps track of the number of tiles stored in the stack
    private int size;

    /*
     * constructor, creates an empty stack
     */
    public TileStack() {
        top = null;
        size = 0;
    }

    @Override
    /*
     * pushes the provided tile at the top of the stack
     */
    public void push(Tile element) {
        if (isEmpty()) {
            Node node = new Node(element);
            top = node;
        } else {
            Node node = new Node(element, top);
            top = node;
        }
        size++;
    }

    @Override
    /*
     * removes the tile at the top of the stack
     * 
     * @returns the tile at the top of the stack
     * 
     * @throws NoSuchElementException if the stack is empty
     */
    public Tile pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        Tile temp = top.getTile();
        top = top.getNext();
        size--;
        return temp;
    }

    @Override
    /*
     * @returns the tile at the top of the stack
     * 
     * @throws NoSuchElementException if the stack is empty
     */
    public Tile peek() {
        if (isEmpty() || size == 0) {
            throw new EmptyStackException();
        }
        return top.getTile();
    }

    @Override
    /*
     * @returns boolean true if the stack is empty, false otherwise
     */
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    @Override
    /*
     * @returns size of the stack
     */
    public int size() {
        return size;
    }

    @Override
    /*
     * @returns an iterator to iterate through this stack starting from its top
     */
    public Iterator<Tile> iterator() {
        return new TileListIterator(top);
    }
}
