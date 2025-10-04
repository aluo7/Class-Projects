// DO NOT SUBMIT THIS FILE TO GRADESCOPE
/**
 * This interface models an Iterable Stack Abstract Data Type
 *
 * @param <T> the type of objects to be stored in this stack
 * @author Mouna
 */
public interface StackADT<T> {
  
    /**
     * Add an element to this stack
     * 
     * @param element an element to be added
     * @throws java.lang.IllegalArgumentException with a descriptive error message if the input
     *         element is null
     */
    public void push(T element);
  
    /**
     * Remove the element on the top of this stack and return it
     * 
     * @return the element removed from the top of the stack
     * @throws java.util.EmptyStackException without error message if the stack is empty
     */
    public T pop();
  
    /**
     * Get the element on the top of this stack
     * 
     * @return the element on the stack top
     * @throws java.util.EmptyStackException without error message if the stack is empty
     */
    public T peek();
  
    /**
     * Check whether this stack is empty or not
     * 
     * @return true if this stack contains no elements, otherwise false
     */
    public boolean isEmpty();
  
    /**
     * Get the number of elements in this stack
     * 
     * @return the size of the stack
     */
    public int size();
  
  }