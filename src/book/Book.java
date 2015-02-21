package book;

/**
 * A program is called a book
 */
public abstract class Book {

    /**
     *  adds the character c to the book at location loc
     */
    public abstract void addCode(Location loc, char c);

    public abstract void run();

    /**
     *  moves the code pointer to a new location
     */
    public abstract void movePointer(Location loc);

    /**
     *  switches the direction of movement to direction
     */
    public abstract void switchDirection(Direction direction);

    /**
     *  returns the stack proxy
     */
    public abstract StackProxy stack();
}
