package book;

import java.util.*;

public class StackProxy {

    private Stack<Object> stack;

    public StackProxy() {
        stack = new Stack<>();
    }

    public <K> K pop() {
        return (K) stack.pop();
    }

    public <K> void push(K k) {
        stack.push(k);
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }
}
