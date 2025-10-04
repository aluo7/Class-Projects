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

public class TreeNode<T> {
  // the data contained in the node
  private T data;
  // the left child of the node
  private TreeNode<T> left;
  // the right child of the node
  private TreeNode<T> right;
  
  /**
   * constructs a node with the given data and no children
   * 
   * @param data the data contained in the node
   */
  public TreeNode(T data) {
    this.data=data;
    this.left=null;
    this.right=null;
  }

  /**
   * constructs a node with the given data and children
   * 
   * @param data the data contained in the node
   * @param left the left child of the node
   * @param right the right child of the node
   */
  public TreeNode(T data, TreeNode<T> left, TreeNode<T> right) {
    this.data=data;
    this.left=left;
    this.right=right;
  }
  
  /**
   * getter for the data contained in the node
   * 
   * @return the data contained in the node
   */
  public  T getData() {
    return this.data;
    
  }

  /**
   * getter for the left child of the node
   * 
   * @return a reference to the left child of the node
   */
  public TreeNode<T> getLeft(){
    return this.left;
  }

  /**
   * getter for the right child of the node
   * 
   * @return a reference to the right child of the node
   */
  public TreeNode<T> getRight(){
    return this.right;
  }
  
  /**
   * change the left child reference of this node; may be null
   * 
   * @param left the new left child reference
   */
  public void setLeft(TreeNode<T> left) {
    this.left=left;
  }
  
  /**
   * change the right child reference of this node; may be null
   * 
   * @param right the new right child reference
   */
  public void setRight(TreeNode<T> right) {
    this.right=right;
  }
  
  @Override
  /**
   * returns a string representation of this node's data
   * 
   * @return a string representation of this node's data
   */
  public String toString() {
    if (this.data==null) {
      return null;
    }
    return this.getData().toString();
  }
}
