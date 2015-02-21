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
}
