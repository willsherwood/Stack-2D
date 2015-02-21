package book;

import exceptions.NoStartingPointException;

/**
 * A book bound by a width and a height
 */
public class BoundedBook extends Book {

    private int height, width;

    private char[][] codes;
    private Location pointer;
    private Direction direction;
    private StackProxy stackProxy;

    public BoundedBook (int width, int height) {
        this.width = width;
        this.height = height;
        stackProxy = new StackProxy();
    }

    @Override
    public void run () {
        movePointer(startingPoint());
        switchDirection(Direction.RIGHT);
        while(step());
    }

    /**
     * @return whether or not the program has exited
     */
    private boolean step() {
        process(codes[pointer.y][pointer.x]);
        pointer = pointer.over(1, direction);
        return !locationInBounds(pointer);
    }

    private boolean locationInBounds (Location loc) {
        return loc.x >= 0 && loc.x < width && loc.y >= 0 && loc.y < height;
    }

    private void process (char c) {

    }

    private Location startingPoint() {
        for (int y=0; y<height; y++)
            for (int x=0; x<width; x++)
                if (codes[y][x] == 'S')
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
}
