public class LinkedBookshelf {

    // the LinkedNode<Book> at the front of the list
    private LinkedNode<Book> front;
    // the LinkedNode<Book> at the end of the list
    private LinkedNode<Book> back;
    // the number of Books on the bookshelf
    private int size;
    // the Attribute by which the list is currently sorted; defaults to Attribute.ID
    private Attribute sortedBy;

    // creates an empty bookshelf sorted by ID.
    public LinkedBookshelf() {
        front = null;
        back = null;
        size = 0;
        sortedBy = Attribute.ID;
    }

    /*
     * @returns the current number of books on the shelf
     */
    public int size() {
        return this.size;
    }

    /*
     * @returns true if and only if the shelf contains no books, false otherwise
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /*
     * @returns a String representation of the current state of the shelf, beginning
     * with the current value of sortedBy and followed by each Book’s String
     * representation on a separate line in order from front to back
     */
    @Override
    public String toString() {
        LinkedNode<Book> b = front;
        String s = new String();
        while (b != null) {
            s += "\n" + b.getData().toString();
            b = b.getNext();
        }
        return sortedBy + s;
    }

    /*
     * @returns the LinkedNode<Book> at the given index
     * 
     * @throws an IndexOutOfBoundsException if the index is not in the range
     * 0-(size-1)
     * 
     * @param int index is the location of the Book
     */
    public LinkedNode<Book> getNode(int index) {
        if (!(index >= 0) && !(index <= (size - 1))) {
            throw new IndexOutOfBoundsException(index + "is out of bounds.");
        } else {
            LinkedNode<Book> b = front;
            int count = 0;
            while (count != index) {
                b = b.getNext();
                count++;
            }
            return b;
        }
    }

    /*
     * returns the Book at the given index throws an IndexOutOfBoundsException if
     * the index is not in the range 0-(size-1)
     * 
     * @param int index is the location of the Book
     */
    public Book get(int index) throws IndexOutOfBoundsException {
        if (!(index >= 0) && !(index <= (size - 1))) {
            throw new IndexOutOfBoundsException(index + "is out of bounds.");
        } else {
            return getNode(index).getData();
        }
    }

    /*
     * @returns the Book at the front of the list
     */
    public Book getFirst() {
        return front.getData();
    }

    /*
     * @returns the Book at the end of the list
     */
    public Book getLast() {
        return back.getData();
    }

    /*
     * restores the shelf to an empty state
     */
    public void clear() {
        front = null;
        back = null;
        size = 0;
    }

    /*
     * adds the provided Book object to the end of the linked list and increases the
     * bookshelf’s size accordingly; does not consider the value of sortedBy
     * 
     * @param Book toAdd is object to be added to shelf
     */
    public void appendBook(Book toAdd) {
        LinkedNode<Book> a = new LinkedNode<Book>(toAdd, null);
        if (isEmpty()) {
            front = a;
            back = a;
        } else {
            back.setNext(a);
            back = a;
        }
        size++;
    }

    /*
     * inserts the provided book at the correct location in the list, which you may
     * assume has been sorted based on the value of sortedBy, and increases the
     * bookshelf’s size accordingly
     * 
     * @param Book toAdd is object to be added to shelf
     */
    public void insertBook(Book toAdd) {

        LinkedNode<Book> nodeToAdd = new LinkedNode<Book>(toAdd);

        if (isEmpty()) {
            front = nodeToAdd;
            size++;
            return;
        } else if (front.getData().compareTo(toAdd, sortedBy) > 0) {
            nodeToAdd.setNext(front);
            front = nodeToAdd;
            size++;
            return;
        } else {
            LinkedNode<Book> part = front;
            while(part.getNext() != null) {
                if(part.getNext().getData().compareTo(toAdd, sortedBy) > 0) {
                    nodeToAdd.setNext(part.getNext());
                    part.setNext(nodeToAdd);
                    size++;
                    return;
                }
                part = part.getNext();
            }
            part.setNext(nodeToAdd);
            back = nodeToAdd;
            size++;
        }
    }

    /*
     * runs insertion sort on the provided shelf, using the given Attribute for
     * comparing Book objects.
     */
    public static void sort(LinkedBookshelf b, Attribute sortedBy) {
        LinkedNode<Book> c = b.front;
        b.sortedBy = sortedBy;
        b.clear();

        while(c != null) {
            LinkedNode<Book> next = c.getNext();
            b.insertBook(c.getData());
            c = next;
        }
    }
}
