// --== CS400 File Header Information ==--
// Name: Alan Luo
// Email: aluo7@wisc.edu
// Lecturer: Gary Dahl
// Notes to Grader: None

public class RedBlackTreeQueue<T extends Comparable<T>> extends RedBlackTree<T> implements IRedBlackTreeQueue<T> {

    /**
     * Performs the rotation operation on the provided nodes within this tree.
     * When the provided child is a leftChild of the provided parent, this
     * method will perform a right rotation. When the provided child is a
     * rightChild of the provided parent, this method will perform a left rotation.
     * When the provided nodes are not related in one of these ways, this method
     * will throw an IllegalArgumentException.
     * 
     * @param child  is the node being rotated from child to parent position
     * @param parent is the node being rotated from parent to child position
     * @throws IllegalArgumentException when the provided child and parent
     *                                  node references are not initially
     *                                  (pre-rotation) related that way
     */
    private void rotate(Node<T> child, Node<T> parent) throws IllegalArgumentException {
        // checks if the nodes are parents/children
        if (parent.leftChild != child && parent.rightChild != child)
            throw new IllegalArgumentException("provided nodes do not harbor child/parent relationship");

        // null check and left child check
        if (child != null && child.isLeftChild()) {
            Node<T> p = child; // right rotation

            // node to switch
            Node<T> subtreeSwitch = child.rightChild;
            child = parent;

            // sets the parent of the old parent to now point its child to the new parent
            if (root == parent)
                root = p;
            else if (parent.isLeftChild())
                parent.parent.leftChild = p;
            else
                parent.parent.rightChild = p;

            parent = p;

            // parent and children have proper parents, children and the swapping subtree
            parent.parent = child.parent;
            parent.rightChild = child;
            child.parent = parent;
            child.leftChild = subtreeSwitch;

            if (subtreeSwitch != null)
                subtreeSwitch.parent = child;

        } else if (child != null) {

            // left rotation
            // same implementation as the right rotation except mirrored
            Node<T> p = child;
            Node<T> subtreeSwitch = child.leftChild;
            child = parent;
            if (root == parent) {
                root = p;
            } else if (parent.isLeftChild()) {
                parent.parent.leftChild = p;
            } else {
                parent.parent.rightChild = p;
            }
            parent = p;

            parent.parent = child.parent;
            parent.leftChild = child;
            child.parent = parent;
            child.rightChild = subtreeSwitch;
            if (subtreeSwitch != null)
                subtreeSwitch.parent = child;
        }
    }

    /**
     * gets the sibling of the given node
     * 
     * @param sibling
     * @return null or the sibling
     */
    protected Node<T> getSibling(Node<T> sibling) {
        if (sibling == null || sibling == root)
            return null;
        else if (sibling.isLeftChild())
            return sibling.parent.rightChild;
        else
            return sibling.parent.leftChild;
    }

    /**
     * gets the node closest in value to the current node
     * 
     * @param node
     * @return
     */
    protected Node<T> getClosestNode(Node<T> node) {
        Node<T> current = node;

        if (node != null && node.leftChild != null) { // iterates through the left side to find the greatest predecessor
            current = node.leftChild;

            while (current.rightChild != null)
                current = current.rightChild;
        }

        else if (node != null && node.rightChild != null) { // otherwise if find the least successor
            current = node.rightChild;

            while (current.leftChild != null)
                current = current.leftChild;
        }
        return current;
    }

    /**
     * delete a node from the RBT
     * 
     * @param data
     * @return true if the element is deleted, false if it isnt
     * @throws NullPointerException
     * @throws IllegalArgumentException
     */
    @Override
    public boolean delete(T data) {
        Node<T> current = root;

        while (current != null && current.data != data) { // iterates through the tree to find the node w the data

            if (current.data.compareTo(data) > 0)
                current = current.leftChild;
            else if (current.data.compareTo(data) < 0)
                current = current.rightChild;
            else
                break;
        }

        if (current == null) // if the node isnt found, exception is thrown
            throw new IllegalArgumentException("Element is not in list");
        else // deletes the node whilst keeping the RBT properties
            enforcePropertiesAndDelete(current);

        current = root; // checks to make sure the node is no longer in the tree
        while (current != null && current.data != data) {
            if (current.data.compareTo(data) < 0)
                current = current.leftChild;
            else if (current.data.compareTo(data) > 0)
                current = current.rightChild;
            else
                break;
        }
        return current == null;
    }

    /**
     * enforces the RBT properties after a node is deleted
     * 
     * @param nodeTodelete
     */
    protected void enforcePropertiesAndDelete(Node<T> nodeTodelete) {
        if (nodeTodelete == null) // checks the number of children and calls the appropriate helper method
            return;
        else if (nodeTodelete.leftChild == null && nodeTodelete.rightChild == null)
            zeroChilddelete(nodeTodelete);
        else if (nodeTodelete.leftChild == null || nodeTodelete.rightChild == null)
            oneChilddelete(nodeTodelete);
        else
            twoChilddelete(nodeTodelete);
    }

    /**
     * helper method for enforcePropertiesAndDelete, case with one child
     * removal, that node has to be black and its child has to be red
     * 
     * @param nodeTodelete
     */
    protected void oneChilddelete(Node<T> nodeTodelete) {
        if (nodeTodelete == root) { // special case for root
            if (nodeTodelete.leftChild != null) {
                nodeTodelete.leftChild.blackHeight = 1;
                root = nodeTodelete.leftChild;
            } else {
                nodeTodelete.rightChild.blackHeight = 1;
                root = nodeTodelete.rightChild;
            }
        }

        else if (nodeTodelete != null && nodeTodelete.isLeftChild()) { // sets the child to be black, and deletes the
                                                                       // node based on if the node is a left or right
                                                                       // child
            if (nodeTodelete.leftChild != null) {
                nodeTodelete.leftChild.blackHeight = 1;
                nodeTodelete.leftChild.parent = nodeTodelete.parent;
                nodeTodelete.parent.leftChild = nodeTodelete.leftChild;
            } else {
                nodeTodelete.rightChild.blackHeight = 1;
                nodeTodelete.rightChild.parent = nodeTodelete.parent;
                nodeTodelete.parent.leftChild = nodeTodelete.rightChild;
            }
        } else if (nodeTodelete != null) {
            if (nodeTodelete.leftChild != null) {
                nodeTodelete.leftChild.blackHeight = 1;
                nodeTodelete.leftChild.parent = nodeTodelete.parent;
                nodeTodelete.parent.rightChild = nodeTodelete.leftChild;
            } else {
                nodeTodelete.rightChild.blackHeight = 1;
                nodeTodelete.rightChild.parent = nodeTodelete.parent;
                nodeTodelete.parent.rightChild = nodeTodelete.rightChild;
            }
        }
    }

    /**
     * 0 child delete case that involves a double black. Fixes case when node to
     * delete is black and has a black sibling with no red children
     * 
     * @param doubleBlack
     */
    protected void zeroChilddeleteCase2(Node<T> doubleBlack) {
        if (doubleBlack == null || doubleBlack.blackHeight != 2) {
            return;
        }
        doubleBlack.blackHeight--;
        Node<T> sibling = getSibling(doubleBlack);

        if (sibling != null) // sibling goes from black to red
            sibling.blackHeight = 0;
        // parents black height increases to get rid of the double black
        doubleBlack.parent.blackHeight++;

        if (doubleBlack.parent.blackHeight > 1) { // if the parent is now double black, check which double black
                                                  // case applies
            if (doubleBlack.parent == root)
                root.blackHeight--;
            else {
                sibling = getSibling(doubleBlack.parent);
                if (sibling != null && sibling.blackHeight == 0) {
                    zeroChilddeleteCase3(doubleBlack.parent);
                } else if (sibling == null || ((sibling.leftChild == null || sibling.leftChild.blackHeight != 0)
                        && (sibling.rightChild == null || sibling.rightChild.blackHeight != 0))) {
                    zeroChilddeleteCase2(doubleBlack.parent);
                } else {
                    zeroChilddeleteCase1(doubleBlack.parent);
                }
            }
        }
    }

    /**
     * 0 child delete case that involve a double black. Fixes case when node to
     * delete is a black and has a black sibling with at least one red child
     * 
     * @param doubleBlack
     */
    protected void zeroChilddeleteCase1(Node<T> doubleBlack) {
        if (doubleBlack == null || doubleBlack.blackHeight != 2) {
            return;
        }
        Node<T> sibling = getSibling(doubleBlack);
        doubleBlack.blackHeight--;
        if (doubleBlack.isLeftChild()) {

            if (sibling != null && (sibling.rightChild == null || sibling.rightChild.blackHeight != 0)) { // rotating
                                                                                                          // the
                                                                                                          // red child
                                                                                                          // to
                                                                                                          // be on the
                                                                                                          // opposite
                                                                                                          // side of the
                                                                                                          // node being
                                                                                                          // deleted
                sibling.blackHeight--;
                sibling.leftChild.blackHeight++;
                rotate(sibling.leftChild, sibling);
            }

            sibling = getSibling(doubleBlack); // rotating and color swapping the parent and sibling
            sibling.rightChild.blackHeight++;
            int tempheight = doubleBlack.parent.blackHeight;
            doubleBlack.parent.blackHeight = sibling.blackHeight;
            sibling.blackHeight = tempheight;
            rotate(sibling, doubleBlack.parent);

        } else {
            if (sibling != null && (sibling.leftChild == null || sibling.leftChild.blackHeight != 0)) {
                sibling.blackHeight--;
                sibling.rightChild.blackHeight++;
                rotate(sibling.rightChild, sibling);
            }
            sibling = getSibling(doubleBlack);
            sibling.leftChild.blackHeight++;

            int tempheight = doubleBlack.parent.blackHeight;
            doubleBlack.parent.blackHeight = sibling.blackHeight;
            sibling.blackHeight = tempheight;
            rotate(sibling, doubleBlack.parent);
        }

    }

    /**
     * 0 child delete that involves double black. Fixes case when black node has a
     * red sibling
     * 
     * @param doubleBlack
     */
    protected void zeroChilddeleteCase3(Node<T> doubleBlack) {
        if (doubleBlack == null || doubleBlack.blackHeight != 2)
            return;

        Node<T> sibling = getSibling(doubleBlack);
        // rotation and color swap, checks the other cases afterwards
        int tempheight = doubleBlack.parent.blackHeight;
        doubleBlack.parent.blackHeight = sibling.blackHeight;
        sibling.blackHeight = tempheight;
        rotate(sibling, doubleBlack.parent);
        sibling = getSibling(doubleBlack);

        if ((sibling.leftChild == null || sibling.leftChild.blackHeight != 0)
                && (sibling.rightChild == null || sibling.rightChild.blackHeight != 0)) {
            zeroChilddeleteCase2(doubleBlack);
        } else {
            zeroChilddeleteCase1(doubleBlack);
        }

    }

    /**
     * 0 child case of delete
     * 
     * @param deleteNode
     */
    protected void zeroChilddelete(Node<T> deleteNode) {
        if (deleteNode == root)
            root = null;

        else if (deleteNode != null && deleteNode.blackHeight == 0) { // removing the red node requires no additional
                                                                      // checks

            if (deleteNode.isLeftChild())
                deleteNode.parent.leftChild = null;
            else
                deleteNode.parent.rightChild = null;

        } else if (deleteNode != null) {
            deleteNode.blackHeight++;
            Node<T> sibling = getSibling(deleteNode);

            if (sibling != null && sibling.blackHeight == 0) // checks which double black case needs to apply
                zeroChilddeleteCase3(deleteNode);
            else if (sibling == null || ((sibling.leftChild == null || sibling.leftChild.blackHeight != 0)
                    && (sibling.rightChild == null || sibling.rightChild.blackHeight != 0)))
                zeroChilddeleteCase2(deleteNode);
            else
                zeroChilddeleteCase1(deleteNode);

            if (deleteNode.isLeftChild()) // removing the node after the double black is sorted
                deleteNode.parent.leftChild = null;
            else
                deleteNode.parent.rightChild = null;
        }
    }

    /**
     * 2 child case of removing nodes
     * 
     * @param deleteNode
     */
    protected void twoChilddelete(Node<T> deleteNode) {
        if (deleteNode == null)
            return;

        Node<T> closest = getClosestNode(deleteNode);
        Node<T> copy = new Node<>(closest.data);
        copy.blackHeight = deleteNode.blackHeight;

        if (closest.leftChild == null && closest.rightChild == null) // find the closest node and delete that node from
                                                                     // the tree
            zeroChilddelete(closest);
        else
            oneChilddelete(closest);

        if (deleteNode == root) { // replace the node to delete with the closest node that has same black height
                                  // to not disturb the tree
            root = copy;

            if (deleteNode.leftChild != null)
                deleteNode.leftChild.parent = copy;
            copy.leftChild = deleteNode.leftChild;

            if (deleteNode.leftChild != null)
                deleteNode.rightChild.parent = copy;
            copy.rightChild = deleteNode.rightChild;
        } else if (deleteNode.isLeftChild()) {
            deleteNode.parent.leftChild = copy;

            if (deleteNode.leftChild != null)
                deleteNode.leftChild.parent = copy;
            copy.leftChild = deleteNode.leftChild;

            if (deleteNode.leftChild != null)
                deleteNode.rightChild.parent = copy;
            copy.rightChild = deleteNode.rightChild;

        } else {
            deleteNode.parent.rightChild = copy;

            if (deleteNode.leftChild != null)
                deleteNode.leftChild.parent = copy;
            copy.leftChild = deleteNode.leftChild;
            
            if (deleteNode.leftChild != null)
                deleteNode.rightChild.parent = copy;
            copy.rightChild = deleteNode.rightChild;
        }
    }
}
