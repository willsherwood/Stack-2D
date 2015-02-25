package book;

import actions.defaultActions.DefaultActions;
import exceptions.NoStartingPointException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private List<Location> previousLocations;

    public BoundedBook (int width, int height) {
        this.width = width;
        this.height = height;
        stackProxy = new StackProxy();
        codes = new char[height][width];
        previousLocations = new ArrayList<>();
        methods = new HashMap<>();
    }

    @Override
    public void start () {
        movePointer(startingPoint());
        switchDirection(Direction.RIGHT);
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                if (codes[y][x] == '.') // method found
                    addMethod(x, y);
    }

    @Override
    public void executeMethod(int identifier) {
        System.out.println(String.format("Tried to execute %d in direction %s.%n", identifier, direction));
    }

    private void addMethod(int x, int y) {
        for (Direction d : Direction.values()) {
            Location p = new Location(x, y).over(1, d);
            if (!Character.isDigit(codes[p.y][p.x]))
                continue;
            int identifier = 0;
            while (Character.isDigit(codes[p.y][p.x])) {
                identifier = identifier * 10 + codes[p.y][p.x];
            }
            p = new Location(p.x, p.y).over(1, d);
            int arity = 0;
            if (!Character.isDigit(codes[p.y][p.x]))
                continue;
            while (Character.isDigit(codes[p.y][p.x])) {
                arity = arity * 10 + codes[p.y][p.x];
            }
            Method method = new Method(arity, new Location(x, y));
            methods.putIfAbsent(identifier, new HashMap<>());
            methods.get(identifier).put(d, method);
        }
    }

    @Override
    public void pushOutput (Object pop) {
        System.out.println(pop);
    }

    public boolean step () {
        process(codes[pointer.y][pointer.x]);
        movePointer(pointer.over(1, direction));
        return locationInBounds(pointer);
    }

    private boolean locationInBounds (Location loc) {
        return loc.x >= 0 && loc.x < width && loc.y >= 0 && loc.y < height;
    }

    private void process (char c) {
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

    private void getNumber (char c) {
        if (gettingNumber) {
            int current = stackProxy.pop();
            stackProxy.push(current * 10 + (c - 48));
            return;
        }
        gettingNumber = true;
        stackProxy.push(c - 48);
    }

    private Location startingPoint () {
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                if (codes[y][x] == 's')
                    return new Location(x, y);
        throw new NoStartingPointException();
    }

    @Override
    public void addCode (Location loc, char c) {
        codes[loc.y][loc.x] = c;
    }

    @Override
    public void movePointer (Location loc) {
        this.pointer = loc;
    }

    @Override
    public void switchDirection (Direction direction) {
        this.direction = direction;
    }

    @Override
    public StackProxy stack () {
        return stackProxy;
    }

    @Override
    public void gotoMethod (Location location) {
        previousLocations.add(pointer);
        movePointer(location.over(-1, direction));
    }

    @Override
    public void finishMethod () {
        movePointer(previousLocations.remove(previousLocations.size() - 1));
    }

    @Override
    public void inputString () {
        gettingString = true;
    }

    @Override
    public Location currentPointer () {
        return pointer;
    }

    @Override
    public Direction currentDirection () {
        return direction;
    }
}
