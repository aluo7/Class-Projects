// --== CS400 File Header Information ==--
// Name: Alan Luo
// Email: aluo7@wisc.edu
// Lecturer: Gary Dahl
// Notes to Grader: None

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Stack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Red-Black Tree implementation with a Node inner class for representing the
 * nodes of the tree.
 * Currently, this implements a Binary Search Tree that we will turn into a red
 * black tree by
 * modifying the insert functionality. In this activity, we will start with
 * implementing rotations
 * for the binary search tree insert algorithm. You can use this class' insert
 * method to build a
 * regular binary search tree, and its toString method to display a level-order
 * traversal of the
 * tree.
 */
public class RedBlackTree<T extends Comparable<T>> implements SortedCollectionInterface<T> {

    /**
     * This class represents a node holding a single value within a binary tree the
     * parent, left, and
     * right child references are always maintained.
     */
    protected static class Node<T> {
        public T data;
        public int blackHeight; // tracks black height for current node
        public Node<T> parent; // null for root node
        public Node<T> leftChild;
        public Node<T> rightChild;

        public Node(T data) {
            this.data = data;
            blackHeight = 0;
        }

        /**
         * @return true when this node has a parent and is the left child of that
         *         parent, otherwise
         *         return false
         */
        public boolean isLeftChild() {
            return parent != null && parent.leftChild == this;
        }

    }

    protected Node<T> root; // reference to root node of tree, null when empty
    protected int size = 0; // the number of values in the tree

    /**
     * Performs a naive insertion into a binary search tree: adding the input data
     * value to a new node
     * in a leaf position within the tree. After this insertion, no attempt is made
     * to restructure or
     * balance the tree. This tree will not hold null references, nor duplicate data
     * values.
     * 
     * @param data to be added into this binary search tree
     * @return true if the value was inserted, false if not
     * @throws NullPointerException     when the provided data argument is null
     * @throws IllegalArgumentException when the newNode and subtree contain equal
     *                                  data references
     */
    @Override
    public boolean insert(T data) throws NullPointerException, IllegalArgumentException {
        // null references cannot be stored within this tree
        if (data == null)
            throw new NullPointerException("This RedBlackTree cannot store null references.");

        Node<T> newNode = new Node<>(data);
        if (root == null) {
            root = newNode;
            size++;
            root.blackHeight = 1;
            return true;
        } // add first node to an empty tree
        else {
            boolean returnValue = insertHelper(newNode, root); // recursively insert into subtree
            if (returnValue)
                size++;
            else
                throw new IllegalArgumentException("This RedBlackTree already contains that value.");
            root.blackHeight = 1;
            return returnValue;
        }
    }

    /**
     * Recursive helper method to find the subtree with a null reference in the
     * position that the
     * newNode should be inserted, and then extend this tree by the newNode in that
     * position.
     * 
     * @param newNode if the new node that is being added to this tree
     * @param subtree if the reference to a node within this tree which the newNode
     *                should be inserted
     *                as a descenedent beneath
     * @return true if the value was inserted in subtree, false if not
     */
    private boolean insertHelper(Node<T> newNode, Node<T> subtree) {
        int compare = newNode.data.compareTo(subtree.data);
        // do not allow duplicate values to be stored within this tree
        if (compare == 0)
            return false;

        // store newNode within left subtree of subtree
        else if (compare < 0) {
            if (subtree.leftChild == null) { // left subtree empty, add here
                subtree.leftChild = newNode;
                newNode.parent = subtree;
                enforceRBTreePropertiesAfterInsert(newNode);
                return true;
                // otherwise continue recursive search for location to insert
            } else
                return insertHelper(newNode, subtree.leftChild);
        }

        // store newNode within the right subtree of subtree
        else {
            if (subtree.rightChild == null) { // right subtree empty, add here
                subtree.rightChild = newNode;
                newNode.parent = subtree;
                enforceRBTreePropertiesAfterInsert(newNode);
                return true;
                // otherwise continue recursive search for location to insert
            } else
                return insertHelper(newNode, subtree.rightChild);
        }
    }

    /**
     * helper method for enforceRBTreePropertiesAfterInsert, checks if the parent of
     * the Node has a red sibling or not
     * 
     * @param node a Node<T> to check if it has a red sibling
     * @return true if the node has a red sibling, false otherwise
     */
    private boolean hasRedSibling(Node<T> node) {

        if (node == null) {

            return false;
        } else if (node.isLeftChild()) { // if node is left child

            // returns true if the sibling isn't null, and it is a red node
            if (node.parent.rightChild != null && node.parent.rightChild.blackHeight == 0) {

                return true;
            }
        } else { // if the node is right child or root

            // returns true if the sibling isn't null, and it is a red node
            if (node.parent != null && node.parent.leftChild != null && node.parent.leftChild != null
                    && node.parent.leftChild.blackHeight == 0) {

                return true;
            }
        }

        return false; // if the node doesn't have a red sibling or is root
    }

    /**
     * method is called to fix any red-red node violations in the red-black tree
     * after insertion. Deals with three cases: (1) the uncle is red (2) same side
     * as parent (3) opposite side of parent. 
     * 
     * @param node representing the node that was inserted
     */
    protected void enforceRBTreePropertiesAfterInsert(Node<T> node) {

        if (node.parent == root && node.parent.blackHeight == 1) { // no violation, terminates the sequence
            return;
        }

        if (node.parent.blackHeight == 0) { // checks if the parent is red

            if (hasRedSibling(node.parent)) { // case 3 (parent has a red sibling)
                node.parent.blackHeight = 1;
                node.parent.parent.blackHeight = 0;

                if (node.parent.isLeftChild()) { // left child
                    node.parent.parent.rightChild.blackHeight = 1;

                } else { // right child
                    node.parent.parent.leftChild.blackHeight = 1;
                }

                if (node.parent.parent != root) { // recursive call if grandparent isn't root
                    enforceRBTreePropertiesAfterInsert(node.parent.parent);
                }
            } else { // cases 1 or 2 (parent has a black/null sibling)
                Node<T> parent = node.parent; // initializes parent node

                if (parent.isLeftChild() != node.isLeftChild()) { // case 1
                    rotate(node, parent);
                    enforceRBTreePropertiesAfterInsert(parent);

                } else { // case 2
                    rotate(parent, node.parent.parent); // rotates the child's parent and grandparent
                }
            }
        }
    }

    /**
     * Performs the rotation operation on the provided nodes within this tree.
     * When the provided child is a leftChild of the provided parent, this
     * method will perform a right rotation. When the provided child is a
     * rightChild of the provided parent, this method will perform a left rotation.
     * When the provided nodes are not related in one of these ways, this method
     * will throw an IllegalArgumentException.
     * 
     * @param child  is the node being rotated from child to parent position
     *               (between these two node arguments)
     * @param parent is the node being rotated from parent to child position
     *               (between these two node arguments)
     * @throws IllegalArgumentException when the provided child and parent
     *                                  node references are not initially
     *                                  (pre-rotation) related that way
     */
    private void rotate(Node<T> child, Node<T> parent) throws IllegalArgumentException {
        if (parent.leftChild != child && parent.rightChild != child) { // if the given parent node is NOT a parent for
                                                                       // the given child node
            throw new IllegalArgumentException("provided nodes do not harbor child/parent relationship");
        } else if (child.isLeftChild()) { // rotate right around the parent node if the child is a left child

            rotateRight(parent);
        } else if (!child.isLeftChild()) { // rotate left around the parent node if the child is a right child

            rotateLeft(parent);
        }

        // switching color after rotation
        int childColor = child.blackHeight;
        child.blackHeight = parent.blackHeight;
        parent.blackHeight = childColor;
    }

    /**
     * helper method for rotate, rotates the binary search tree to the right if the
     * child is a left child
     * 
     * @param n is the parent node to be rotated around
     */
    public void rotateRight(Node<T> n) {
        Node<T> oldLeft = n.leftChild; // instantiates leftChild

        n.leftChild = oldLeft.rightChild; // sets left child of oldLeft to right child of n

        if (oldLeft.rightChild != null) // change the parent to n if not null
            oldLeft.rightChild.parent = n;

        oldLeft.parent = n.parent; // change parent of oldLeft to parent of n

        if (n.parent == null) // if n is the root, set root to oldLeft
            root = oldLeft;

        else if (n.isLeftChild()) // if n is the left child
            n.parent.leftChild = oldLeft;

        else // if n is the right child
            n.parent.rightChild = oldLeft;

        // make n the right child of oldLeft
        oldLeft.rightChild = n;
        n.parent = oldLeft;
    }

    /**
     * helper method for rotate, rotates the binary search tree to the left if the
     * child is a right child. if the right child is null terminates the sequence.
     * 
     * @param n is the parent node to be rotated around
     */
    public void rotateLeft(Node<T> n) {
        Node<T> oldRight = n.rightChild; // instantiates right child

        n.rightChild = oldRight.leftChild; // sets right child of oldRight to right child of n

        if (oldRight.leftChild != null) // change the parent to n if not null
            oldRight.leftChild.parent = n;

        oldRight.parent = n.parent; // change parent of oldRight to parent of n

        if (n.parent == null) // if n is the root, set root to oldRight
            root = oldRight;

        else if (n.isLeftChild()) // if n is the left child
            n.parent.leftChild = oldRight;

        else // if n is the right child
            n.parent.rightChild = oldRight;

        // make n the left child of oldRight
        oldRight.leftChild = n;
        n.parent = oldRight;
    }

    /**
     * Get the size of the tree (its number of nodes).
     * 
     * @return the number of nodes in the tree
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Method to check if the tree is empty (does not contain any node).
     * 
     * @return true of this.size() return 0, false if this.size() > 0
     */
    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * Checks whether the tree contains the value *data*.
     * 
     * @param data the data value to test for
     * @return true if *data* is in the tree, false if it is not in the tree
     */
    @Override
    public boolean contains(T data) {
        // null references will not be stored within this tree
        if (data == null)
            throw new NullPointerException("This RedBlackTree cannot store null references.");
        return this.containsHelper(data, root);
    }

    /**
     * Recursive helper method that recurses through the tree and looks for the
     * value *data*.
     * 
     * @param data    the data value to look for
     * @param subtree the subtree to search through
     * @return true of the value is in the subtree, false if not
     */
    private boolean containsHelper(T data, Node<T> subtree) {
        if (subtree == null) {
            // we are at a null child, value is not in tree
            return false;
        } else {
            int compare = data.compareTo(subtree.data);
            if (compare < 0) {
                // go left in the tree
                return containsHelper(data, subtree.leftChild);
            } else if (compare > 0) {
                // go right in the tree
                return containsHelper(data, subtree.rightChild);
            } else {
                // we found it :)
                return true;
            }
        }
    }

    /**
     * Returns an iterator over the values in in-order (sorted) order.
     * 
     * @return iterator object that traverses the tree in in-order sequence
     */
    @Override
    public Iterator<T> iterator() {
        // use an anonymous class here that implements the Iterator interface
        // we create a new on-off object of this class every time the iterator
        // method is called
        return new Iterator<T>() {
            // a stack and current reference store the progress of the traversal
            // so that we can return one value at a time with the Iterator
            Stack<Node<T>> stack = null;
            Node<T> current = root;

            /**
             * The next method is called for each value in the traversal sequence. It
             * returns one value at
             * a time.
             * 
             * @return next value in the sequence of the traversal
             * @throws NoSuchElementException if there is no more elements in the sequence
             */
            public T next() {
                // if stack == null, we need to initialize the stack and current element
                if (stack == null) {
                    stack = new Stack<Node<T>>();
                    current = root;
                }
                // go left as far as possible in the sub tree we are in un8til we hit a null
                // leaf (current is null), pushing all the nodes we fund on our way onto the
                // stack to process later
                while (current != null) {
                    stack.push(current);
                    current = current.leftChild;
                }
                // as long as the stack is not empty, we haven't finished the traversal yet;
                // take the next element from the stack and return it, then start to step down
                // its right subtree (set its right sub tree to current)
                if (!stack.isEmpty()) {
                    Node<T> processedNode = stack.pop();
                    current = processedNode.rightChild;
                    return processedNode.data;
                } else {
                    // if the stack is empty, we are done with our traversal
                    throw new NoSuchElementException("There are no more elements in the tree");
                }
            }

            /**
             * Returns a boolean that indicates if the iterator has more elements (true), or
             * if the
             * traversal has finished (false)
             * 
             * @return boolean indicating whether there are more elements / steps for the
             *         traversal
             */
            public boolean hasNext() {
                // return true if we either still have a current reference, or the stack
                // is not empty yet
                return !(current == null && (stack == null || stack.isEmpty()));
            }

        };
    }

    /**
     * This method performs an inorder traversal of the tree. The string
     * representations of each data
     * value within this tree are assembled into a comma separated string within
     * brackets (similar to
     * many implementations of java.util.Collection, like java.util.ArrayList,
     * LinkedList, etc). Note
     * that this RedBlackTree class implementation of toString generates an inorder
     * traversal. The
     * toString of the Node class class above produces a level order traversal of
     * the nodes / values
     * of the tree.
     * 
     * @return string containing the ordered values of this tree (in-order
     *         traversal)
     */
    public String toInOrderString() {
        // use the inorder Iterator that we get by calling the iterator method above
        // to generate a string of all values of the tree in (ordered) in-order
        // traversal sequence
        Iterator<T> treeNodeIterator = this.iterator();
        StringBuffer sb = new StringBuffer();
        sb.append("[ ");
        if (treeNodeIterator.hasNext())
            sb.append(treeNodeIterator.next());
        while (treeNodeIterator.hasNext()) {
            T data = treeNodeIterator.next();
            sb.append(", ");
            sb.append(data.toString());
        }
        sb.append(" ]");
        return sb.toString();
    }

    /**
     * This method performs a level order traversal of the tree rooted at the
     * current node. The string
     * representations of each data value within this tree are assembled into a
     * comma separated string
     * within brackets (similar to many implementations of java.util.Collection).
     * Note that the Node's
     * implementation of toString generates a level order traversal. The toString of
     * the RedBlackTree
     * class below produces an inorder traversal of the nodes / values of the tree.
     * This method will
     * be helpful as a helper for the debugging and testing of your rotation
     * implementation.
     * 
     * @return string containing the values of this tree in level order
     */
    public String toLevelOrderString() {
        String output = "[ ";
        LinkedList<Node<T>> q = new LinkedList<>();
        q.add(this.root);
        while (!q.isEmpty()) {
            Node<T> next = q.removeFirst();
            if (next.leftChild != null)
                q.add(next.leftChild);
            if (next.rightChild != null)
                q.add(next.rightChild);
            output += next.data.toString();
            if (!q.isEmpty())
                output += ", ";
        }
        return output + " ]";
    }

    @Override
    public String toString() {
        return "level order: " + this.toLevelOrderString() + "\nin order: " + this.toInOrderString();
    }

    // In addition to your RedBlackTree.java implementation, you must develop and
    // submit at least three JUnit tests within your RedBlackTree class. Each Test
    // should be designed to test a different case for inserting into a red black
    // tree, and ensuring that your enforceRBTreePropertiesAfterInsert method is
    // correctly following the algorithms discussed in lecture. Be sure to include
    // JavaDoc style method header comments for each of these test methods that
    // clearly describe what high-level "case" each method is testing.

    protected RedBlackTree<Integer> rbt; // rbt instance variable representing the RedBlackTree object that is being
                                         // inserted into

    /**
     * Invoked automatically before each test. Instantiates the rbt instance
     * variable and inserts a starting set of Nodes.
     */
    @BeforeEach
    public void createInstance() {
        rbt = new RedBlackTree<Integer>(); // instantiates the RedBlackTree

        rbt.insert(8); // inserts a few starting Nodes
        rbt.insert(18);
        rbt.insert(5);
    }

    /**
     * Tests a basic RedBlackTree insertion implementation. Inserts four Nodes,
     * requiring the enforceRBTreePropertiesAfterInsert method to be called,
     * requiring only a recolor. Test is passed if the blackHeight, data and size
     * values are accurate.
     * 
     * Specific cases being tested: consecutive red nodes, same-side red uncle
     */
    @Test
    public void test1() {
        rbt.insert(15); // inserts new Node that requires recoloring

        assertEquals(4, rbt.size()); // checks that the size was incremented properly

        // checks to see that the key nodes are the correct color
        assertEquals(1, rbt.root.rightChild.blackHeight);
        assertEquals(0, rbt.root.rightChild.leftChild.blackHeight);

        // checks to see that the nodes were rotated properly
        assertEquals("level order: [ 8, 5, 18, 15 ]\nin order: [ 5, 8, 15, 18 ]", rbt.toString());
    }

    /**
     * Tests a RedBlackTree insertion implementation. Inserts five Nodes, requiring
     * the enforceRBTreePropertiesAfterInsert method to be called, requiring
     * recolors and rotations. Test is passed if the Node blackHeight, data, and
     * size values are accurate.
     * 
     * Specific cases being tested: consecutive red nodes, same-side red uncle, null
     * uncle
     */
    @Test
    public void test2() {
        rbt.insert(15);
        rbt.insert(17); // inserts new Node that requires recoloring and rotation

        assertEquals(5, rbt.size()); // checks that the size was incremented properly

        // checks to see that key nodes are the correct color
        assertEquals(1, rbt.root.rightChild.blackHeight);
        assertEquals(0, rbt.root.rightChild.rightChild.blackHeight);

        // checks to see that the nodes were rotated properly
        assertEquals("level order: [ 8, 5, 17, 15, 18 ]\nin order: [ 5, 8, 15, 17, 18 ]", rbt.toString());
    }

    /**
     * Tests a complex RedBlackTree insertion implementation. Inserts eight Nodes,
     * requiring the enfiroceRBTreePropertiesAfterInsert method to be called,
     * requiring recolors, rotations, and recursive calls. Test is passed if the
     * Node blackheight, data, and size values are accurate.
     * 
     * Specific cases being tested: consecutive red nodes, same-side red uncle, null
     * uncle, non-root grandparent, opposite-side red uncle
     */
    @Test
    public void test3() {
        rbt.insert(15);
        rbt.insert(17);
        rbt.insert(25); // inserts multiple Nodes that require complex rebalancing of tree
        rbt.insert(40);
        rbt.insert(80);

        assertEquals(8, rbt.size()); // checks that the size was incremented properly

        // checks to see that key nodes are the correct color
        assertEquals(1, rbt.root.blackHeight);
        assertEquals(1, rbt.root.rightChild.leftChild.blackHeight);
        assertEquals(1, rbt.root.rightChild.rightChild.blackHeight);
        assertEquals(0, rbt.root.rightChild.rightChild.rightChild.blackHeight);

        // checks to see that the nodes were rotated properly
        assertEquals("level order: [ 17, 8, 25, 5, 15, 18, 40, 80 ]\nin order: [ 5, 8, 15, 17, 18, 25, 40, 80 ]",
                rbt.toString());
    }
}
