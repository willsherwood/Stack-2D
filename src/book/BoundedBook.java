package book;

import actions.defaultActions.DefaultActions;
import exceptions.NoStartingPointException;

import java.util.*;

/**
 * A book bound by a width and a height
 */
public class BoundedBook extends Book {

    private int height, width;

    private char[][] codes;
    private Location pointer;
    private Direction direction;
    private StackProxy stackProxy;

    private boolean gettingNumber;
    private boolean gettingString;

    private List<Location> previousLocations;

    public BoundedBook (int width, int height) {
        this.width = width;
        this.height = height;
        stackProxy = new StackProxy();
        codes = new char[height][width];
        previousLocations = new ArrayList<>();
    }

    @Override
    public void run () {
        movePointer(startingPoint());
        switchDirection(Direction.RIGHT);
        while (step()) ;
    }

    @Override
    public void pushOutput (Object pop) {
        System.out.println(pop);
    }

    /**
     * @return whether or not the program has exited
     */
    private boolean step () {
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
        // EXPERIMENT !!
        movePointer(previousLocations.remove(previousLocations.size() - 1));
    }

    @Override
    public void inputString () {
        gettingString = true;
    }
}
