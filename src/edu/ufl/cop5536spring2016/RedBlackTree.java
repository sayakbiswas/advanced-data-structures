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
         * Hold the left and right children nodes and the parent node
         */
        private RedBlackTreeNode leftChild = null, rightChild = null, parent = null;

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
     * Builds a new node from ID and count and inserts into the tree.
     * @param ID The key of the node to be inserted into the tree.
     * @param count The data value of the node to be inserted into the tree.
     */
    public void redBlackInsert(int ID, int count) {
        RedBlackTreeNode redBlackTreeNode = new RedBlackTreeNode(ID, count);
        RedBlackTreeNode tempNode = rootNode;
        if(tempNode == null) {
            rootNode = redBlackTreeNode;
            rootNode.nodeColor = NodeColor.BLACK; //The root node is always black.
            rootNode.parent = null;
        } else {
            RedBlackTreeNode parent = null;
            while (tempNode != null) {
                parent = tempNode;
                if(redBlackTreeNode.ID < tempNode.ID) {
                    tempNode = tempNode.leftChild;
                } else if (redBlackTreeNode.ID > tempNode.ID) {
                    tempNode = tempNode.rightChild;
                } else {
                    tempNode.count = redBlackTreeNode.count;
                    return;
                }
            }
            redBlackTreeNode.parent = parent;
            if(redBlackTreeNode.ID < parent.ID) {
                parent.leftChild = redBlackTreeNode;
            } else {
                parent.rightChild = redBlackTreeNode;
            }
            redBlackInsertFixUp(redBlackTreeNode);
        }
    }

    /**
     * Restores the red black properties which might have been violated after insertion of a new node.
     * @param redBlackTreeNode The newly inserted node.
     */
    private void redBlackInsertFixUp(RedBlackTreeNode redBlackTreeNode) {
        while(redBlackTreeNode != rootNode && redBlackTreeNode.parent.nodeColor == NodeColor.RED) {
            RedBlackTreeNode uncle = null;
            if(redBlackTreeNode.parent == redBlackTreeNode.parent.parent.leftChild) {

            }
        }
    }
}
