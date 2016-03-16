package edu.ufl.cop5536spring2016;

/**
 * Created by Sayak Biswas on 3/15/2016.
 * This class holds the events read from the input file.
 */
public class Event {

    /**
     * Holds the ID of the event.
     */
    private int ID;

    /**
     * Holds the count of the event.
     */
    private int count;

    /**
     * The constructor
     * @param ID The ID of the event
     * @param count The count of the event
     */
    public Event(int ID, int count) {
        this.ID = ID;
        this.count = count;
    }

    /**
     * Returns the ID of the event.
     * @return The ID of the event.
     */
    public int getID() {
        return ID;
    }

    /**
     * Sets the ID of the event.
     * @param ID The ID of the event.
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * Returns the count of the event.
     * @return The count of the event.
     */
    public int getCount() {
        return count;
    }

    /**
     * Sets the count of the event.
     * @param count The count of the event.
     */
    public void setCount(int count) {
        this.count = count;
    }
}
