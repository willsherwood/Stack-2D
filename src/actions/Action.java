package actions;

import book.Book;

@FunctionalInterface
public interface Action {

    /**
     * processes the given action with the given book
     */
    public void process (Book book);
}
