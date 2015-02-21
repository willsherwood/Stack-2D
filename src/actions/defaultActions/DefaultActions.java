package actions.defaultActions;

import actions.Action;
import actions.ActionFactory;
import actions.CharacterAction;
import book.Direction;
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
                out = ((int) p) != 0;
            if (p instanceof String)
                out = !((String) p).isEmpty();
            b.switchDirection(out ? Direction.UP : Direction.DOWN);
        }));

        addAction(ActionFactory.makeAction('$', b -> {
            Object o = b.stack().pop();
            for (int i=0; i<2; i++)
                b.stack().push(o);
        }));

        addAction(ActionFactory.makeAction('p', b -> {
            b.pushOutput(b.stack().pop());
        }));

        addAction(ActionFactory.makeAction('P', b -> {
            StringBuilder s = new StringBuilder();
            while (!b.stack().isEmpty())
                s.append(b.stack().pop().toString());
            b.pushOutput(s.toString());
        }));

        addAction(ActionFactory.makeAction('s', b-> {

        }));

        addAction(ActionFactory.makeAction(' ', b-> {

        }));
    }

    public static void addAction(CharacterAction action) {
        actions.put(action.getAssociatedCharacter(), action);
    }

    public static Action getAction (char c) {
        if (!actions.containsKey(c))
            throw new CodeNotSupportedException(c);
        return actions.get(c);
    }
}
