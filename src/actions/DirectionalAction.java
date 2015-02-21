package actions;

import book.Book;
import book.Direction;

public class DirectionalAction implements CharacterAction {

    private char associatedCharacter;
    private Direction direction;

    public DirectionalAction (char associatedCharacter, Direction direction) {
        this.associatedCharacter = associatedCharacter;
        this.direction = direction;
    }

    @Override
    public char getAssociatedCharacter () {
        return associatedCharacter;
    }

    @Override
    public void process (Book book) {
        book.switchDirection(direction);
    }
}
