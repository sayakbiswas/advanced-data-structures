package edu.ufl.cop5536spring2016;

/**
 * Created by Sayak Biswas on 3/4/2016.
 * This class defines the Red Black Tree data structure to be used for the event counter and exposes the below
 * operations:
 * 1. Increase
 * 2. Reduce
 * 3. Count
 * 4. InRange
 * 6. Next
 * 7. Previous
 *
 * @author Sayak Biswas
 * @version 0.1
 */
public class RedBlackTree {

    /**
     * This inner class used to represent the nodes in a Red Black Tree. A node stores the below information:
     * 1. ID
     * 2. count
     * 3. Pointers to left and right children
     * 4. Color
     */
    private class RedBlackTreeNode {
        private int ID;
        private int count;
        private RedBlackTreeNode leftNode = null, rightNode = null;
        private boolean color;

        // The constructor to initialize a node
        public RedBlackTreeNode(int ID, int count, boolean color) {
            this.ID = ID;
            this.count = count;
            this.color = color;
        }
    }

    // The red-black color notations
    private static final boolean RED = false;
    private static final boolean BLACK = true;
}
