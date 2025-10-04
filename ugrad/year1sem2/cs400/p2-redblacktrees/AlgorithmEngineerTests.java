// --== CS400 File Header Information ==--
// Name: Alan Luo
// Email: aluo7@wisc.edu
// Lecturer: Gary Dahl
// Notes to Grader: None

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AlgorithmEngineerTests {
    protected RedBlackTreeQueue<Integer> rbt; // rbt instance variable representing the RedBlackTreeQueue object
    					      // that is being tested with

    /**
     * invoked automatically before each test. Instantiates the rbt instance
     * variable and inserts a starting set of Nodes.
     */
    @BeforeEach
    public void createInstance() {
        rbt = new RedBlackTreeQueue<Integer>(); // instantiates the RBT

        rbt.insert(30); // inserts a few starting Nodes
        rbt.insert(20);
        rbt.insert(40);
    }

    /**
     * tests the case in which the node being removed has one red child/is a red
     * child
     */
    @Test
    public void test1() {
        rbt.insert(10); // inserts new node in which the single child is red

        assertEquals(true, rbt.delete(20)); // checks that the node was deleted properly

        // checks to see that the nodes were recolored properly
        assertEquals(1, rbt.root.blackHeight);
        assertEquals(1, rbt.root.leftChild.blackHeight);
        assertEquals(1, rbt.root.rightChild.blackHeight);

	// checks to see that the RBT was fixed
        assertEquals("level order: [ 30, 10, 40 ]\nin order: [ 10, 30, 40 ]", rbt.toString());
    }

    /**
     * tests the case in which the node being removed and its child is black
     */
    @Test
    public void test2() {
        rbt.insert(50); // inserts new node in which the single child is red

        assertEquals(true, rbt.delete(20)); // checks that the node was deleted properly

        // checks to see that the nodes were recolored properly
        assertEquals(1, rbt.root.blackHeight);
        assertEquals(1, rbt.root.rightChild.blackHeight);
        assertEquals(1, rbt.root.leftChild.blackHeight);

	// checks to see that the RBT was fixed
        assertEquals("level order: [ 40, 30, 50 ]\nin order: [ 30, 40, 50 ]", rbt.toString());
    }

    /**
     * tests a case where sibling is right child of its parent and the child is
     * right child of sibling or both children of sibling are red
     */
    @Test
    public void test3() {
        rbt.insert(35); // inserts new node in which the children of the sibling are both red
        rbt.insert(50);

        assertEquals(true, rbt.delete(20)); // checks that the node was deleted properly

        // checks to see that the nodes were recolored properly
        assertEquals(1, rbt.root.blackHeight);
        assertEquals(1, rbt.root.leftChild.blackHeight);
        assertEquals(1, rbt.root.rightChild.blackHeight);
        assertEquals(0, rbt.root.leftChild.rightChild.blackHeight);

	// checks to see that the RBT was fixed
        assertEquals("level order: [ 40, 30, 50, 35 ]\nin order: [ 30, 35, 40, 50 ]", rbt.toString());
    }

    /**
     * sibling is right child of its parent and its child is left child of sibling
     */
    @Test
    public void test4() {
        rbt.insert(35); // inserts new node in which the left child of the sibling is red

        assertEquals(true, rbt.delete(20)); // checks that the node was deleted properly

        // checks to see that the nodes were recolored properly
        assertEquals(1, rbt.root.blackHeight);
        assertEquals(1, rbt.root.leftChild.blackHeight);
        assertEquals(1, rbt.root.rightChild.blackHeight);

	// checks to see that the RBT was fixed
        assertEquals("level order: [ 35, 30, 40 ]\nin order: [ 30, 35, 40 ]", rbt.toString());
    }

    /**
     * sibling is red and both it's children are black
     */
    @Test
    public void test5() {
        rbt.insert(10); // inserts nodes to make sibling red and both children black
        rbt.insert(15);
        rbt.insert(5);

        assertEquals(true, rbt.delete(40)); // checks that the node was deleted properly

        // checks to see that the nodes were recolored properly
        assertEquals(1, rbt.root.blackHeight);
        assertEquals(1, rbt.root.rightChild.blackHeight);
        assertEquals(1, rbt.root.leftChild.blackHeight);
        assertEquals(0, rbt.root.leftChild.leftChild.blackHeight);
        assertEquals(0, rbt.root.rightChild.leftChild.blackHeight);

	// checks to see that the RBT was fixed
        assertEquals("level order: [ 15, 10, 30, 5, 20 ]\nin order: [ 5, 10, 15, 20, 30 ]", rbt.toString());
    }
}
