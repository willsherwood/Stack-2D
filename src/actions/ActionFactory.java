package actions;

import book.Book;

public class ActionFactory {

    public static CharacterAction makeAction (final char associatedCharacter, final Action action) {
        return new CharacterAction() {
            @Override
            public char getAssociatedCharacter () {
                return associatedCharacter;
            }

            @Override
            public void process (Book book) {
                action.process(book);
            }
        };
    }
}
