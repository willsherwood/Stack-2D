package actions;

import book.Book;

import java.util.function.*;

public class IntegralAction implements CharacterAction {

    private char associatedCharacter;
    private Action action;

    public IntegralAction (char associatedCharacter, BiFunction<Integer, Integer, Object> func) {
        this.associatedCharacter = associatedCharacter;
        this.action = b -> b.stack().push(func.apply(b.stack().pop(), b.stack().pop()));
    }

    @Override
    public char getAssociatedCharacter () {
        return associatedCharacter;
    }

    @Override
    public void process (Book book) {
        action.process(book);
    }
}
