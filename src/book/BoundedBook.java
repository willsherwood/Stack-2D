package book;

import actions.defaultActions.DefaultActions;
import exceptions.NoStartingPointException;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * A book bound by a width and a height
 */
public class BoundedBook extends Book {

    private int height, width;

    private char[][] codes;
    private Location pointer;
    private Direction direction;
    private StackProxy stackProxy;

    private Map<Integer, Map<Direction, Method>> methods;

    private boolean gettingNumber;
    private boolean gettingString;
    private Stack<StackState> stackFrame;

    public BoundedBook(int width, int height) {
        this.width = width;
        this.height = height;
        stackProxy = new StackProxy();
        codes = new char[height][width];
        methods = new HashMap<>();
    }

    @Override
    public void start() {
        stackFrame = new Stack<>();
        movePointer(startingPoint());
        switchDirection(Direction.RIGHT);
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                if (codes[y][x] == '.') // method found
                    addMethod(x, y);
    }

    @Override
    public void executeMethod(int identifier) {
        Stack<Object> stack = new Stack<>();
        Method m = methods.get(identifier).get(direction); //TODO: NullPointerException for invalid identifiers
        if (m == null)
            m = methods.entrySet().iterator().next().getValue().entrySet().iterator().next().getValue();
        int i = m.getArity();
        while (i-- > 0)
            stack.push(stack().pop());
        System.out.println(stack);
        stackFrame.push(new StackState(m.getLocation(), stack));
        System.out.println(stack());
        pointer = m.getLocation();
    }

    private void addMethod(int x, int y) {
        main:
        for (Direction d : Direction.values()) {
            Location p = new Location(x, y).over(1, d);
            if (!locationInBounds(p))
                continue main;
            if (!Character.isDigit(codes[p.y][p.x]))
                continue;
            int identifier = 0;
            while (Character.isDigit(codes[p.y][p.x])) {
                identifier = identifier * 10 + (codes[p.y][p.x] - 48);
                p = p.over(1, d);
                if (!locationInBounds(p))
                    continue main;
            }
            p = new Location(p.x, p.y).over(1, d);
            if (!locationInBounds(p))
                continue main;
            int arity = 0;
            if (!Character.isDigit(codes[p.y][p.x]))
                continue;
            while (Character.isDigit(codes[p.y][p.x])) {
                arity = arity * 10 + (codes[p.y][p.x] - 48);
                p = p.over(1, d);
                if (!locationInBounds(p))
                    continue main;
            }
            Method method = new Method(arity, new Location(x, y));
            methods.putIfAbsent(identifier, new HashMap<>());
            methods.get(identifier).put(d, method);
            System.out.println("added " + identifier + " with arity " + arity);
        }
    }

    @Override
    public void pushOutput(Object pop) {
        System.out.println(pop);
    }

    public boolean step() {
        process(codes[pointer.y][pointer.x]);
        movePointer(pointer.over(1, direction));
        return locationInBounds(pointer);
    }

    private boolean locationInBounds(Location loc) {
        return loc.x >= 0 && loc.x < width && loc.y >= 0 && loc.y < height;
    }

    private void process(char c) {
        if (gettingString) {
            if (c == '"')
                gettingString = false;
            else {
                if (stack().isEmpty())
                    stack().push(c);
                else
                    stack().push(stack().pop().toString() + c);
            }
            return;
        }
        if (c >= '0' && c <= '9') {
            getNumber(c);
            return;
        }
        gettingNumber = false;
        DefaultActions.getAction(c).process(this);
    }

    private void getNumber(char c) {
        if (gettingNumber) {
            int current = stackProxy.pop();
            stackProxy.push(current * 10 + (c - 48));
            return;
        }
        gettingNumber = true;
        stackProxy.push(c - 48);
    }

    private Location startingPoint() {
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                if (codes[y][x] == 's')
                    return new Location(x, y);
        throw new NoStartingPointException();
    }

    @Override
    public void addCode(Location loc, char c) {
        codes[loc.y][loc.x] = c;
    }

    @Override
    public void movePointer(Location loc) {
        this.pointer = loc;
    }

    @Override
    public void switchDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public StackProxy stack() {
        if (stackFrame.isEmpty())
            return stackProxy;
        return stackFrame.peek().stack();
    }

    @Override
    public void finishMethod() {
        pointer = stackFrame.peek().getLocation();
        stack().addAll(stackFrame.pop().stack()); // unconsumed arguments will be restored to stack
    }

    @Override
    public void inputString() {
        gettingString = true;
    }

    @Override
    public Location currentPointer() {
        return pointer;
    }

    @Override
    public Direction currentDirection() {
        return direction;
    }
}
