package actions.defaultActions;

import actions.Action;
import actions.ActionFactory;
import actions.CharacterAction;
import book.Direction;
import book.Location;
import book.StackProxy;
import exceptions.CodeNotSupportedException;

import java.util.*;

public class DefaultActions {

    private static Map<Character, Action> actions;

    static {
        actions = new HashMap<>();

        addAction(ActionFactory.makeAction('+', b -> {
            StackProxy s = b.stack();
            int A = s.pop();
            int B = s.pop();
            s.push(A + B);
        }));

        addAction(ActionFactory.makeAction('-', b -> {
            StackProxy s = b.stack();
            int A = s.pop();
            int B = s.pop();
            s.push(-A + B);
        }));

        addAction(ActionFactory.makeAction('|', b -> {
            StackProxy s = b.stack();
            Object p = s.pop();
            boolean out = false;
            if (p instanceof Boolean)
                out = (boolean) p;
            if (p instanceof Integer)
                out = ((int) p) > 0;
            if (p instanceof String)
                out = !((String) p).isEmpty();
            b.switchDirection(out ? Direction.UP : Direction.DOWN);
        }));

        addAction(ActionFactory.makeAction('$', b -> {
            Object o = b.stack().pop();
            for (int i = 0; i < 2; i++)
                b.stack().push(o);
        }));

        addAction(ActionFactory.makeAction('p', b -> {
            b.pushOutput(b.stack().pop());
        }));

        addAction(ActionFactory.makeAction('P', b -> {
            StringBuilder s = new StringBuilder();
            while (!b.stack().isEmpty())
                s.append(b.stack().pop().toString()+", ");
            b.pushOutput(s.toString());
        }));

        addAction(new NullAction('s'));
        addAction(new NullAction(' '));
        addAction(new NullAction((char) 0));

        addAction(ActionFactory.makeAction('t', b -> {
            int y = b.stack().pop();
            int x = b.stack().pop();
            b.movePointer(new Location(x, y));
        }));

        addAction(ActionFactory.makeAction('=', b -> {
            b.stack().push(b.stack().pop().equals(b.stack().pop()) ? 1 : 0);
        }));

        addAction(ActionFactory.makeAction('.', b -> {
            int y = b.stack().pop();
            int x = b.stack().pop();
            b.gotoMethod(new Location(x , y));
        }));

        addAction(ActionFactory.makeAction(',', b -> {
            b.finishMethod();
        }));

        addAction(ActionFactory.makeAction('"', b -> {
            b.inputString();
            b.stack().push("");
        }));

        addAction(new DirectionalAction('v', Direction.DOWN));
        addAction(new DirectionalAction('^', Direction.UP));
        addAction(new DirectionalAction('<', Direction.LEFT));
        addAction(new DirectionalAction('>', Direction.RIGHT));
    }

    public static void addAction (CharacterAction action) {
        actions.put(action.getAssociatedCharacter(), action);
    }

    public static Action getAction (char c) {
        if (!actions.containsKey(c))
            throw new CodeNotSupportedException(c);
        return actions.get(c);
    }
}
