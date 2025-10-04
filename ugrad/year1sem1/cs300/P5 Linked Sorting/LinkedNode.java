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

public class LinkedNode<T> {
    // a private instance field of the generic type.
    private T data;
    // a private instance field of type LinkedNode<T>, which indicates the next node
    // in the list.
    private LinkedNode<T> next;

    /*
     * constructor that initializes the data field and leaves next null
     * 
     * @param T data represents data provided
     */
    public LinkedNode(T data) {
        this.data = data;
    }

    /*
     * constructor that initializes both data and next fields
     * 
     * @param T data represents data provided, LinkedNode<T> next indicates the next
     * node in the list
     */
    public LinkedNode(T data, LinkedNode<T> next) {
        this.data = data;
        this.next = next;
    }

    /*
     * @returns a reference to the next node in the list
     */
    public LinkedNode<T> getNext() {
        return next;
    }

    /*
     * @returns the value of the data instance field
     */
    public T getData() {
        return data;
    }

    /*
     * @returns the String representation of the node’s data
     */
    @Override
    public String toString() {
        return data + "";
    }

    /*
     * updates the next field to be the provided node (possibly null)
     */
    public void setNext(LinkedNode<T> next) {
        this.next = next;
    }
}
