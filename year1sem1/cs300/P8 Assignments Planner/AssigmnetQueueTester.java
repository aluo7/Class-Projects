// FILE HEADER COMES HERE

import java.util.NoSuchElementException;

/**
 * Tester class for the AssignmentQueue implementation of PriorityQueueADT
 */
public class AssignmentQueueTester {
  /**
   * Tests the functionality of the constructor for AssignmentQueue Should implement at least the
   * following tests:
   *
   * - Calling the AssignmentQueue with an invalid capacity should throw an IllegalArgumentException
   * - Calling the AssignmentQueue with a valid capacity should not throw any errors, and should
   * result in a new AssignmentQueue which is empty, and has size 0
   *
   * @return true if the constructor of AssignmentQueue functions properly
   * @see AssignmentQueue#AssignmentQueue(int)
   */
  public static boolean testConstructor() {
    // TODO complete the implementation of this tester method

    return false; // default return statement added to resolve compiler errors
  }

  /**
   * Tests the functionality of the enqueue() and peek() methods Should implement at least the
   * following tests:
   *
   * - Calling peek on an empty queue should throw a NoSuchElementException 
   * - Calling enqueue on a queue which is empty should add the Assignment, and increment the size 
   *   of the queue
   * - Calling enqueue on a non-empty queue should add the Assignment at the proper position, 
   *   and increment the size of the queue. Try add at least 5 assignments 
   * - Calling peek on a non-empty queue should always return the Assignment with the earliest due date
   * - Calling enqueue on a full queue should throw an IllegalStateException 
   * - Calling enqueue with a null Assignment should throw a NullPointerException
   *
   * @return true if AssignmentQueue.enqueue() and AssignmentQueue.peek() function properly
   */
  public static boolean testEnqueue() {
    // TODO complete the implementation of this tester method

    return false; // default return statement added to resolve compiler errors
  }

  /**
   * Tests the functionality of dequeue() and peek() methods. The peek() method must return without
   * removing the assignment with the highest priority in the queue. The dequeue() method must
   * remove, and return the assignment with the highest priority in the queue. The size must be
   * decremented by one, each time the dequeue() method is successfully called. Try to check the
   * edge cases (calling peek and dequeue on an empty queue, and calling dequeue on a queue of size
   * one). For normal cases, try to consider dequeuing from a queue whose size is at least 6. Try to
   * consider cases where percolate-down recurses on left and right.
   * 
   * @return true if AssignmentQueue.dequeue() and AssignmentQueue.peek() function properly
   */
  public static boolean testDequeuePeek() {
    // TODO complete the implementation of this tester method

    return false; // default return statement added to resolve compiler errors
  }

  /**
   * Tests the functionality of the clear() method Should implement at least the following scenarios: 
   * - clear can be called on an empty queue with no errors 
   *  - clear can be called on a non-empty queue with no errors - After calling clear, the queue should contain no Assignments
   *
   * @return true if AssignmentQueue.clear() functions properly
   */
  public static boolean testClear() {
    // TODO complete the implementation of this tester method

    return false; // default return statement added to resolve compiler errors
  }

  /**
   * Tests all the methods of the AssignmentQueue class
   * 
   */
  public static boolean runAllTests() {
    // TODO complete the implementation of this tester method

    return false; // default return statement added to resolve compiler errors
  }
  
  /**
   * Main method
   * @param args input arguments if any
   */
  public static void main(String[] args) {
    
  }
}