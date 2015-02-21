package actions;

import book.Book;

public class NullAction implements CharacterAction {

    private char c;

    public NullAction (char c) {
        this.c = c;
    }

    @Override
    public char getAssociatedCharacter () {
        return c;
    }

    @Override
    public void process (Book book) {

    }
}
