package book;

import java.util.Stack;

public class StackState {

    private Location loc;
    private StackProxy proxy;

    public StackState(Location loc, Stack<Object> stack) {
        this.loc = loc;
        this.proxy = new StackProxy();
    }

    public Location getLocation() {
        return loc;
    }

    public StackProxy stack() {
        return proxy;
    }
}
