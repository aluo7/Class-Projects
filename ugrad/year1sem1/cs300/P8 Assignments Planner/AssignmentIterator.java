// DO NOT submit this file to gradescope

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implements an iterator for Assignments, which returns the Assignments in order from earliest to
 * latest based on their order in a priority queue.
 */
public class AssignmentIterator implements Iterator<Assignment> {
  private AssignmentQueue queue; // a copy of the priority queue of assignments to iterate over


  /**
   * Creates a new AssignmentIterator which iterates over the elements of the given AssignmentQueue
   * in order from earliest to latest Assignment.
   * 
   * @param queue the AssignmentQueue to iterate over
   */
  public AssignmentIterator(AssignmentQueue queue) {
    this.queue = queue.deepCopy(); // we are going to work on a deep copy of the provided queue
                                   // as input parameter. 
  }

  /**
   * Returns true if the iteration has more elements.
   * 
   * @return {@code true} if the iteration has more elements
   */
  @Override
  public boolean hasNext() {
    return !queue.isEmpty();
  }

  /**
   * Returns the next element in the iteration.
   * 
   * @return the next element in the iteration.
   * @throws NoSuchElementException with a descriptive error message if the iteration has no more
   *                                elements
   */
  @Override
  public Assignment next() {
    if (!hasNext())
      throw new NoSuchElementException("No more assignments in this iteration");
    return queue.dequeue();
  }
}