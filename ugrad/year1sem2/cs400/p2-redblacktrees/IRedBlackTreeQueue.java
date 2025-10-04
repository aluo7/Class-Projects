// --== CS400 P2 RB Tree File Header ==--
// Name: Alan Luo
// CSL Username: aluo
// Email: aluo7@wisc.edu
// Lecture #: <001 @11:00am>
// Notes to Grader: <any optional extra notes to your grader>

/**
 * This class is implemented by a red black tree that stores a list of values
 * within each Node representing tickets. These tickets are sorted according to
 * the date in which the ticket was sent in.
 */
public interface IRedBlackTreeQueue<T extends Comparable<T>> extends SortedCollectionInterface<T> {

    /**
     * This method takes in a String data type as its input representing the name we
     * are searching for. Parses through the red black tree until the corresponding
     * Node is found and removes it.
     * 
     * @param node input representing the node to be removed in the red black tree
     * @return true if the node is removed, false otherwise
     */
    public boolean delete(T data);
}
