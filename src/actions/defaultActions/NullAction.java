package actions.defaultActions;

import actions.CharacterAction;
import book.Book;

public class NullAction implements CharacterAction {

    private char c;

    @Override
    public char getAssociatedCharacter () {
        return c;
    }

    public NullAction(char c) {
        this.c = c;
    }

    @Override
    public void process (Book book) {

    }
}
