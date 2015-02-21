package book;

import java.util.*;

public class StackProxy {

    private Stack<Object> stack;
    private StackMode stackMode;

    public StackProxy () {
        stack = new Stack<>();
    }

    public <K> K pop () {
        return stackMode == StackMode.PEEK ? (K) stack.peek() : (K) stack.pop();
    }

    public <K> void push (K k) {
        stack.push(k);
    }

    public void setMode (StackMode stackMode) {
        this.stackMode = stackMode;
    }

    public boolean isEmpty () {
        return stack.isEmpty();
    }
}
