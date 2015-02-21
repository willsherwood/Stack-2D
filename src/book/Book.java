package book;

/**
 * A program is called a book
 */
public abstract class Book {

    /**
     * adds the character c to the book at location loc
     */
    public abstract void addCode (Location loc, char c);

    public abstract void run ();

    /**
     * moves the code pointer to a new location
     */
    public abstract void movePointer (Location loc);

    /**
     * switches the direction of movement to direction
     */
    public abstract void switchDirection (Direction direction);

    /**
     * returns the stack proxy
     */
    public abstract StackProxy stack ();

    public abstract void pushOutput (Object pop);

    /**
     * executes the method at location location
     */
    public abstract void gotoMethod (Location location);

    /**
     * finishes the current method
     */
    public abstract void finishMethod ();

    public abstract void inputString ();

    public abstract Location currentPointer ();

    public abstract Direction currentDirection ();
}
