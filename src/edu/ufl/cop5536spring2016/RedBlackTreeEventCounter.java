package edu.ufl.cop5536spring2016;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Sayak Biswas on 3/4/2016.
 * This class uses the Red Black Tree data structure defined in RedBlackTree.java to implement an event counter. The
 * event counter supports the below operations:
 * 1. Build(List)
 * 1. Increase(theID, m)
 * 2. Reduce(theID, m)
 * 3. Count(theID)
 * 4. InRange(ID1, ID2)
 * 5. Next(theID)
 * 6. Previous(theID)
 */
public class RedBlackTreeEventCounter {
    RedBlackTree redBlackTree = new RedBlackTree();

    /**
     * Builds the event counter from the list of Event objects. This runs in O(n) time.
     * @param eventArrayList The list of events
     */
    public void buildEventCounter(ArrayList<Event> eventArrayList) {
        if(eventArrayList != null && eventArrayList.size() > 0) {
            Iterator<Event> iterator = eventArrayList.iterator();
            redBlackTree.buildTreeFromSortedList(eventArrayList.size(), iterator);
        }
    }

    /**
     * Increases the count of the event ID by amount. If ID is not present, inserts it. Print the final count. This
     * runs in O(lg n) time.
     * @param ID The event ID whose count has to be increased.
     * @param amount The amount by which the count has to be increase.
     * @return The final count of the event ID after addition.
     */
    public int increase(int ID, int amount) {
        RedBlackTree.RedBlackTreeNode redBlackTreeNode = redBlackTree.treeSearch(ID);
        if (redBlackTreeNode != null) {
            redBlackTreeNode.setCount(redBlackTreeNode.getCount() + amount);
            return redBlackTreeNode.getCount();
        } else {
            redBlackTree.redBlackInsert(ID, amount);
            return amount;
        }
    }

    /**
     * Decreases the count of the event ID by amount. If the ID's count becomes less than or equal to 0, removes the ID
     * from the counter. Prints the count of the ID after deletion or 0 if the ID is removed or is not present. This
     * runs in O(lg n) time.
     * @param ID The event ID whose count is to be decreased.
     * @param amount The amount by which to decrease.
     * @return The final count or 0.
     */
    public int reduce(int ID, int amount) {
        RedBlackTree.RedBlackTreeNode redBlackTreeNode = redBlackTree.treeSearch(ID);
        if(redBlackTreeNode != null) {
            if(amount >= redBlackTreeNode.getCount()) {
                redBlackTree.redBlackDelete(redBlackTreeNode);
                return 0;
            } else {
                redBlackTreeNode.setCount(redBlackTreeNode.getCount() - amount);
                return redBlackTreeNode.getCount();
            }
        } else {
            return 0;
        }
    }

    /**
     * Searches the counter for the event ID and returns the count. Returns 0, if not present.
     * @param ID The event ID whose count is to be returned.
     * @return The count of the event or 0.
     */
    public int count(int ID) {
        RedBlackTree.RedBlackTreeNode redBlackTreeNode = redBlackTree.treeSearch(ID);
        if(redBlackTreeNode != null) {
            return redBlackTreeNode.getCount();
        } else {
            return 0;
        }
    }

    /**
     * Returns the total count for IDs between ID1 and ID2.
     * @param ID1 The left limit of the range.
     * @param ID2 The right limit of the range.
     * @return The total count.
     */
    public int inRange(int ID1, int ID2) {
        int countInRange = 0;
        RedBlackTree.RedBlackTreeNode rootNode = redBlackTree.getRootNode();
        ArrayList<RedBlackTree.RedBlackTreeNode> nodesInRange = new ArrayList<>();
        nodesInRange = redBlackTree.rangeSearch(rootNode, ID1, ID2, nodesInRange);
        for (RedBlackTree.RedBlackTreeNode node : nodesInRange) {
            countInRange += node.getCount();
        }
        return countInRange;
    }

    /**
     * Returns the event with the lowest ID that is greater than ID.
     * @param ID The event ID whose next is to be found.
     * @return The next event
     */
    public Event next(int ID) {
        RedBlackTree.RedBlackTreeNode redBlackTreeNode = redBlackTree.treeSuccessor(redBlackTree.treeSearch(ID));
        return new Event(redBlackTreeNode.getID(), redBlackTreeNode.getCount());
    }

    /**
     * Returns the event with the greatest ID that is less than ID.
     * @param ID The ID whose previous is to be found.
     * @return The previous event.
     */
    public Event previous(int ID) {
        RedBlackTree.RedBlackTreeNode redBlackTreeNode = redBlackTree.treePredecessor(redBlackTree.treeSearch(ID));
        return new Event(redBlackTreeNode.getID(), redBlackTreeNode.getCount());
    }
}
