import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Sayak Biswas on 3/4/2016.
 * This class defines the Red Black Tree data structure to be used for the event counter and exposes the below
 * operations:
 * 1. Insert
 * 2. Delete
 * 3. Search
 * 4. Minimum
 * 5. Maximum
 * 6. Successor
 * 7. Predecessor
 * 8. BuildTree
 * 9. RangeSearch
 * @author Sayak Biswas
 */
public class RedBlackTree {

    /**
     * This inner class used to represent the nodes in a Red Black Tree. A node stores the below information:
     * 1. ID
     * 2. count
     * 3. References to left and right children
     * 4. Color
     */
    static class RedBlackTreeNode {

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

        /**
         * Returns the ID of a node.
         * @return The ID
         */
        public int getID() {
            return ID;
        }

        /**
         * Returns the count value stored in the node.
         * @return The value of the node
         */
        public int getCount() {
            return count;
        }

        /**
         * Saves a count value in the node.
         * @param count The count value to be stored in the node.
         */
        public void setCount(int count) {
            this.count = count;
        }
    }

    /**
     * Holds the root node of the tree.
     */
    private RedBlackTreeNode rootNode = null;

    /**
     * Returns a reference to the root node.
     * @return The reference to the root node.
     */
    public RedBlackTreeNode getRootNode() {
        return rootNode;
    }

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
            if(parentOf(redBlackTreeNode) == leftChildOf(parentOf(parentOf(redBlackTreeNode)))) {
                RedBlackTreeNode uncle = rightChildOf(parentOf(parentOf(redBlackTreeNode)));
                if(colorOf(uncle) == NodeColor.RED) {
                    setColor(parentOf(redBlackTreeNode), NodeColor.BLACK);
                    setColor(uncle, NodeColor.BLACK);
                    setColor(parentOf(parentOf(redBlackTreeNode)), NodeColor.RED);
                    redBlackTreeNode = parentOf(parentOf(redBlackTreeNode));
                } else {
                    if(redBlackTreeNode == rightChildOf(parentOf(redBlackTreeNode))) {
                        redBlackTreeNode = parentOf(redBlackTreeNode);
                        leftRotate(redBlackTreeNode);
                    }
                    setColor(parentOf(redBlackTreeNode), NodeColor.BLACK);
                    setColor(parentOf(parentOf(redBlackTreeNode)), NodeColor.RED);
                    rightRotate(parentOf(parentOf(redBlackTreeNode)));
                }
            } else {
                RedBlackTreeNode uncle = leftChildOf(parentOf(parentOf(redBlackTreeNode)));
                if(colorOf(uncle) == NodeColor.RED) {
                    setColor(parentOf(redBlackTreeNode), NodeColor.BLACK);
                    setColor(uncle, NodeColor.BLACK);
                    setColor(parentOf(parentOf(redBlackTreeNode)), NodeColor.RED);
                    redBlackTreeNode = parentOf(parentOf(redBlackTreeNode));
                } else {
                    if(redBlackTreeNode == leftChildOf(parentOf(redBlackTreeNode))) {
                        redBlackTreeNode = parentOf(redBlackTreeNode);
                        rightRotate(redBlackTreeNode);
                    }
                    setColor(parentOf(redBlackTreeNode), NodeColor.BLACK);
                    setColor(parentOf(parentOf(redBlackTreeNode)), NodeColor.RED);
                    leftRotate(parentOf(parentOf(redBlackTreeNode)));
                }
            }
        }
        rootNode.nodeColor = NodeColor.BLACK;
    }

    /**
     * Performs left rotation on the subtree to restore balance.
     * @param redBlackTreeNode The node on which left rotation has to be performed.
     */
    private void leftRotate(RedBlackTreeNode redBlackTreeNode) {
        if(redBlackTreeNode != null) {
            RedBlackTreeNode right = redBlackTreeNode.rightChild;
            redBlackTreeNode.rightChild = right.leftChild; //Turn right's left subtree into redBlackTreeNode's right subtree.
            if(right.leftChild != null) {
                right.leftChild.parent = redBlackTreeNode;
            }
            right.parent = redBlackTreeNode.parent; //Link redBlackTreeNode's parent to right
            if(redBlackTreeNode.parent == null) {
                rootNode = right;
            } else if(redBlackTreeNode == redBlackTreeNode.parent.leftChild) {
                redBlackTreeNode.parent.leftChild = right;
            } else {
                redBlackTreeNode.parent.rightChild = right;
            }
            right.leftChild = redBlackTreeNode; //Put redBlackTreeNode on right's left
            redBlackTreeNode.parent = right;
        }
    }

    /**
     * Performs right rotation on the subtree to restore balance.
     * @param redBlackTreeNode The node on which left rotation has to be performed.
     */
    private  void rightRotate(RedBlackTreeNode redBlackTreeNode) {
        if(redBlackTreeNode != null) {
            RedBlackTreeNode left = redBlackTreeNode.leftChild;
            redBlackTreeNode.leftChild = left.rightChild; //Turn left's right subtree into redBlackTreeNode's left subtree.
            if(left.rightChild != null) {
                left.rightChild.parent = redBlackTreeNode;
            }
            left.parent = redBlackTreeNode.parent; //Link redBlackTreeNode's parent to left
            if(redBlackTreeNode.parent == null) {
                rootNode = left;
            } else if(redBlackTreeNode == redBlackTreeNode.parent.rightChild) {
                redBlackTreeNode.parent.rightChild = left;
            } else {
                redBlackTreeNode.parent.rightChild = left;
            }
            left.rightChild = redBlackTreeNode; //Put redBlackTreeNode on left's right
            redBlackTreeNode.parent = left;
        }
    }

    /**
     * Deletes a node from the tree and makes a call to an auxiliary method to re-balance the tree.
     * @param nodeToDelete The node to be deleted
     */
    public void redBlackDelete(RedBlackTreeNode nodeToDelete) {
        RedBlackTreeNode replacementNode = nodeToDelete;
        RedBlackTreeNode replacementSuccessor;
        NodeColor replacementNodeOriginalColor = replacementNode.nodeColor;
        if(nodeToDelete.leftChild == null) {
            replacementSuccessor = nodeToDelete.rightChild;
            redBlackTransplant(nodeToDelete, nodeToDelete.rightChild);
        } else if(nodeToDelete.rightChild == null) {
            replacementSuccessor = nodeToDelete.leftChild;
            redBlackTransplant(nodeToDelete, nodeToDelete.leftChild);
        } else {
            replacementNode = treeMinimum(nodeToDelete.rightChild);
            replacementNodeOriginalColor = replacementNode.nodeColor;
            replacementSuccessor = replacementNode.rightChild;
            if(replacementNode.parent == nodeToDelete) {
                if(replacementSuccessor != null) {
                    replacementSuccessor.parent = replacementNode;
                }
            } else {
                redBlackTransplant(replacementNode, replacementNode.rightChild);
                replacementNode.rightChild = nodeToDelete.rightChild;
                replacementNode.rightChild.parent = replacementNode;
            }
            redBlackTransplant(nodeToDelete, replacementNode);
            replacementNode.leftChild = nodeToDelete.leftChild;
            replacementNode.leftChild.parent = replacementNode;
            replacementNode.nodeColor = nodeToDelete.nodeColor;
        }
        if(replacementNodeOriginalColor == NodeColor.BLACK) {
            redBlackDeleteFixUp(replacementSuccessor);
        }
    }

    /**
     * Replaces one subtree as a child of its parent with another subtree.
     * @param nodeToReplace The root node of the subtree to be replaced.
     * @param nodeToReplaceWith The root node of the subtree which replaces the original node.
     */
    private void redBlackTransplant(RedBlackTreeNode nodeToReplace, RedBlackTreeNode nodeToReplaceWith) {
        if(nodeToReplace.parent == null) {
            rootNode = nodeToReplaceWith;
        } else if(nodeToReplace == nodeToReplace.parent.leftChild) {
            nodeToReplace.parent.leftChild = nodeToReplaceWith;
        } else {
            nodeToReplace.parent.rightChild = nodeToReplaceWith;
        }
        nodeToReplaceWith.parent = nodeToReplace.parent;
    }

    /**
     * Finds element in a tree whose key is a minimum.
     * @param redBlackTreeNode The node which is a root of the subtree which needs to be searched for minimum.
     * @return The node containing the minimum element in the subtree rooted at redBlackTreeNode.
     */
    public RedBlackTreeNode treeMinimum(RedBlackTreeNode redBlackTreeNode) {
        while (redBlackTreeNode.leftChild != null) {
            redBlackTreeNode = redBlackTreeNode.leftChild;
        }
        return redBlackTreeNode;
    }

    /**
     * Finds element in a tree whose key is a maximum.
     * @param redBlackTreeNode The node which is a root of the subtree which needs to be searched for maximum.
     * @return The node containing the maximum element in the subtree rooted at redBlackTreeNode.
     */
    public RedBlackTreeNode treeMaximum(RedBlackTreeNode redBlackTreeNode) {
        while (redBlackTreeNode.rightChild != null) {
            redBlackTreeNode = redBlackTreeNode.rightChild;
        }
        return redBlackTreeNode;
    }

    /**
     * Restores the red black properties that might have been violated after deletion of a node.
     * @param redBlackTreeNode The root node of the subtree which needs to be re-balanced.
     */
    private void redBlackDeleteFixUp(RedBlackTreeNode redBlackTreeNode) {
        while (redBlackTreeNode != rootNode && colorOf(redBlackTreeNode) == NodeColor.BLACK) {
            if(redBlackTreeNode == leftChildOf(parentOf(redBlackTreeNode))) {
                RedBlackTreeNode siblingNode = rightChildOf(parentOf(redBlackTreeNode));
                if(colorOf(siblingNode) == NodeColor.RED) {
                    setColor(siblingNode, NodeColor.BLACK);
                    setColor(parentOf(redBlackTreeNode), NodeColor.RED);
                    leftRotate(parentOf(redBlackTreeNode));
                    siblingNode = rightChildOf(parentOf(redBlackTreeNode));
                }
                if(colorOf(leftChildOf(siblingNode)) == NodeColor.BLACK
                        && colorOf(rightChildOf(siblingNode)) == NodeColor.BLACK) {
                    setColor(siblingNode, NodeColor.RED);
                    redBlackTreeNode = parentOf(redBlackTreeNode);
                } else {
                    if(colorOf(rightChildOf(siblingNode)) == NodeColor.BLACK) {
                        setColor(leftChildOf(siblingNode), NodeColor.BLACK);
                        setColor(siblingNode, NodeColor.RED);
                        rightRotate(siblingNode);
                        siblingNode = rightChildOf(parentOf(redBlackTreeNode));
                    }
                    setColor(siblingNode, colorOf(parentOf(redBlackTreeNode)));
                    setColor(parentOf(redBlackTreeNode), NodeColor.BLACK);
                    setColor(rightChildOf(siblingNode),  NodeColor.BLACK);
                    leftRotate(parentOf(redBlackTreeNode));
                    redBlackTreeNode = rootNode;
                }
            } else {
                RedBlackTreeNode siblingNode = leftChildOf(parentOf(redBlackTreeNode));
                if(colorOf(siblingNode) == NodeColor.RED) {
                    setColor(siblingNode, NodeColor.BLACK);
                    setColor(parentOf(redBlackTreeNode), NodeColor.RED);
                    rightRotate(parentOf(redBlackTreeNode));
                    siblingNode = leftChildOf(parentOf(redBlackTreeNode));
                }
                if(colorOf(rightChildOf(siblingNode)) == NodeColor.BLACK
                        && colorOf(leftChildOf(siblingNode)) == NodeColor.BLACK) {
                    setColor(siblingNode, NodeColor.RED);
                    redBlackTreeNode = parentOf(redBlackTreeNode);
                } else {
                    if(colorOf(leftChildOf(siblingNode)) == NodeColor.BLACK) {
                        setColor(rightChildOf(siblingNode), NodeColor.BLACK);
                        setColor(siblingNode, NodeColor.RED);
                        leftRotate(siblingNode);
                        siblingNode = leftChildOf(parentOf(redBlackTreeNode));
                    }
                    setColor(siblingNode, colorOf(parentOf(redBlackTreeNode)));
                    setColor(parentOf(redBlackTreeNode), NodeColor.BLACK);
                    setColor(leftChildOf(siblingNode), NodeColor.BLACK);
                    rightRotate(parentOf(redBlackTreeNode));
                    redBlackTreeNode = rootNode;
                }
            }
        }
        setColor(redBlackTreeNode, NodeColor.BLACK);
    }

    /**
     * Searches for a node with the input ID in the tree.
     * @param theID The ID to look for in the tree.
     * @return The node containing the ID.
     */
    public RedBlackTreeNode treeSearch(int theID) {
        if(rootNode == null) {
            return null;
        }
        RedBlackTreeNode nodeToSearch = rootNode;
        while (nodeToSearch != null) {
            if(theID < nodeToSearch.ID) {
                nodeToSearch = nodeToSearch.leftChild;
            } else if(theID > nodeToSearch.ID) {
                nodeToSearch = nodeToSearch.rightChild;
            } else {
                return nodeToSearch;
            }
        }
        return null;
    }

    /**
     * Finds the successor of the input ID in the sorted order determined by an inorder traversal.
     * @param redBlackTreeNode The subtree node to be searched for successor.
     * @param ID The ID whose successor is to be determined.
     * @return The node with the smallest ID greater than the input ID.
     */
    public RedBlackTreeNode treeSuccessor(RedBlackTreeNode redBlackTreeNode, int ID) {
        if(redBlackTreeNode == null) {
            return null;
        }
        if(redBlackTreeNode.ID == ID) {
            if(redBlackTreeNode.rightChild != null) {
                return treeMinimum(redBlackTreeNode.rightChild);
            } else {
                RedBlackTreeNode parentNode = redBlackTreeNode.parent;
                RedBlackTreeNode childNode = redBlackTreeNode;
                while (parentNode != null && childNode == parentNode.rightChild) {
                    childNode = parentNode;
                    parentNode = parentNode.parent;
                }
                return parentNode;
            }
        }
        if(ID < redBlackTreeNode.ID) {
            RedBlackTreeNode temp = treeSuccessor(redBlackTreeNode.leftChild, ID);
            if (temp != null) {
                return temp;
            } else {
                return redBlackTreeNode;
            }
        }
        return treeSuccessor(redBlackTreeNode.rightChild, ID);
    }

    /**
     * Finds the predecessor of the input ID in the sorted order determined by an inorder traversal.
     * @param redBlackTreeNode The subtree node to be searched for predecessor.
     * @param ID The ID whose predecessor is to be determined.
     * @return The node with the greatest ID smaller than the input ID.
     */
    public RedBlackTreeNode treePredecessor(RedBlackTreeNode redBlackTreeNode, int ID) {
        if(redBlackTreeNode == null) {
            return null;
        }
        if(redBlackTreeNode.ID == ID) {
            if(redBlackTreeNode.leftChild != null) {
                return treeMaximum(redBlackTreeNode.leftChild);
            } else {
                RedBlackTreeNode parentNode = redBlackTreeNode.parent;
                RedBlackTreeNode childNode = redBlackTreeNode;
                while (parentNode != null && childNode == parentNode.leftChild) {
                    childNode = parentNode;
                    parentNode = parentNode.parent;
                }
                return parentNode;
            }
        }
        if(ID < redBlackTreeNode.ID) {
            return treePredecessor(redBlackTreeNode.leftChild, ID);
        }
        RedBlackTreeNode temp = treePredecessor(redBlackTreeNode.rightChild, ID);
        if(temp != null) {
            return temp;
        } else {
            return redBlackTreeNode;
        }
    }

    /**
     * Builds tree from sorted ArrayList of RedBlackTreeNode objects. This runs in O(n) time.
     * @param size Number of events to be added to the tree.
     * @param iterator This iterator has the Event objects to be read.
     */
    public void buildTreeFromSortedList(int size, Iterator<RedBlackTreeNode> iterator) {
        int redLevel = 0;
        for (int i = size - 1; i >= 0; i = i / 2 - 1) {
            redLevel++;
        }
        rootNode = buildTree(0, 0, size - 1, redLevel, iterator);
    }

    /**
     * Builds the tree by placing the middle element to current position. Places [begin; middle) elements to left
     * subtree and (middle, end) elements to right subtree.
     * @param currentLevel The current level of the tree.
     * @param begin The starting index of the subtree.
     * @param end The ending index of the subtree.
     * @param redLevel The level at which nodes should be colored red.
     * @param iterator The Event objects are read from this ArrayList Iterator.
     * @return The root of the subtree.
     */
    private RedBlackTreeNode buildTree(int currentLevel, int begin, int end, int redLevel,
                                       Iterator<RedBlackTreeNode> iterator) {
        if(end < begin) {
            return null;
        }
        int mid = (begin + end) / 2;
        RedBlackTreeNode left = null;
        if(begin < mid) {
            left = buildTree(currentLevel + 1, begin, mid - 1, redLevel, iterator);
        }

        RedBlackTreeNode middle = iterator.next();

        if(currentLevel == redLevel) {
            middle.nodeColor = NodeColor.RED;
        }

        if(left != null) {
            middle.leftChild = left;
            left.parent = middle;
        }

        if(mid < end) {
            RedBlackTreeNode right = buildTree(currentLevel + 1, mid + 1, end, redLevel, iterator);
            if(right != null) {
                middle.rightChild = right;
                right.parent = middle;
            }
        }

        return middle;
    }

    /**
     * Stores all nodes in the tree whose IDs are in the range between ID1 and ID2.
     * @param rootNode The root node of the tree.
     * @param ID1 The left limit of the range.
     * @param ID2 The right limit of the range.
     * @param nodesInRange The ArrayList which stores the found nodes.
     * @return The ArrayList containing nodes between the range of ID1 and ID2.
     */
    public ArrayList<RedBlackTreeNode> rangeSearch(RedBlackTreeNode rootNode, int ID1, int ID2,
                                                   ArrayList<RedBlackTreeNode> nodesInRange) {
        if(rootNode != null) {
            if(rootNode.ID > ID1) {
                nodesInRange = rangeSearch(leftChildOf(rootNode), ID1, ID2, nodesInRange);
            }

            if(rootNode.ID >= ID1 && rootNode.ID <= ID2) {
                nodesInRange.add(rootNode);
            }

            if(rootNode.ID < ID2) {
                nodesInRange = rangeSearch(rightChildOf(rootNode), ID1, ID2, nodesInRange);
            }
        }

        return nodesInRange;
    }

    /**
     * Returns the color of a node. This method handles null nodes.
     * @param redBlackTreeNode The node whose color needs to be checked.
     * @return The color of redBlackTreeNode.
     */
    private static NodeColor colorOf(RedBlackTreeNode redBlackTreeNode) {
        if(redBlackTreeNode == null) {
            return NodeColor.BLACK;
        } else {
            return redBlackTreeNode.nodeColor;
        }
    }

    /**
     * Returns the parent of a node. This method handles null nodes as well.
     * @param redBlackTreeNode The node whose parent needs to be returned.
     * @return The parent node of the redBlackTreeNode.
     */
    private static RedBlackTreeNode parentOf(RedBlackTreeNode redBlackTreeNode) {
        if(redBlackTreeNode == null) {
            return null;
        } else {
            return redBlackTreeNode.parent;
        }
    }

    /**
     * Sets the color of a node. This handles null nodes as well.
     * @param redBlackTreeNode The node whose color has to be set.
     * @param color The color that needs to be set.
     */
    private static void setColor(RedBlackTreeNode redBlackTreeNode, NodeColor color) {
        if(redBlackTreeNode != null) {
            redBlackTreeNode.nodeColor = color;
        }
    }

    /**
     * Returns the left child of a node. Returns null if the input node is null.
     * @param redBlackTreeNode The node whose left child is needed.
     * @return The left child of redBlackTreeNode.
     */
    private static RedBlackTreeNode leftChildOf(RedBlackTreeNode redBlackTreeNode) {
        if(redBlackTreeNode == null) {
            return null;
        } else {
            return redBlackTreeNode.leftChild;
        }
    }

    /**
     * Returns the right child of a node. Returns null if the input node is null.
     * @param redBlackTreeNode The node whose right child is requested.
     * @return The right child of redBlackTreeNode.
     */
    private static RedBlackTreeNode rightChildOf(RedBlackTreeNode redBlackTreeNode) {
        if(redBlackTreeNode == null) {
            return null;
        } else {
            return redBlackTreeNode.rightChild;
        }
    }
}
