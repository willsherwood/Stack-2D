package book;

import java.util.Stack;

public class StackProxy {

    private Stack<Object> stack;
    private StackMode stackMode;

    public StackProxy () {
        stack = new Stack<>();
    }

    public <K> K pop () {
        return stackMode == StackMode.PEEK ? (K) stack.peek() : (K) stack.pop();
    }

    public Stack<Object> contents () {
        return (Stack<Object>) stack.clone();
    }

    public void addAll(StackProxy stack) {
        while (stack.isEmpty())
            this.stack.push(stack.pop());
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

    @Override
    public String toString() {
        return "StackProxy{" +
                "stack=" + stack +
                ", stackMode=" + stackMode +
                '}';
    }
}
