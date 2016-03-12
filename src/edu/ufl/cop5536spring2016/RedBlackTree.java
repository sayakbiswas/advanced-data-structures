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

        /**
         * Holds the ID of the node
         */
        private int ID;

        /**
         * Holds the count value
         */
        private int count;

        /**
         * Hold the left and right children nodes
         */
        private RedBlackTreeNode leftNode = null, rightNode = null;

        /**
         * Holds the color of the node
         */
        private NodeColor nodeColor;

        /**
         * The constructor to initialize a node
         * @param ID The key of the node
         * @param count The data value of the node
         */
        public RedBlackTreeNode(int ID, int count) {
            this.ID = ID;
            this.count = count;
            this.nodeColor = NodeColor.RED; //Initialize nodes with RED nodeColor
        }

        /**
         * Returns the color of the Red Black tree node.
         * @return NodeColor
         */
        public NodeColor getNodeColor() {
            return this.nodeColor;
        }

        /**
         * Set the color of the Red Black tree node.
         * @param nodeColor The color of the node
         */
        public void setNodeColor(NodeColor nodeColor) {
            this.nodeColor = nodeColor;
        }
    }

    /**
     * Holds the root node of the tree.
     */
    private RedBlackTreeNode rootNode = null;

    // The red-black Color notations
    private enum NodeColor {
        RED,
        BLACK
    }

    /**
     * Inserts a new node in the Red Black tree.
     * @param ID The key of the node to be inserted into the tree.
     * @param count The data value of the node to be inserted into the tree.
     */
    public void insertNode(int ID, int count) {

    }
}
