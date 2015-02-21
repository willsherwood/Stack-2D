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

    public BoundedBook (int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void addCode (Location loc, char c) {
        codes[loc.y][loc.x] = c;
    }

    @Override
    public void run () {
        pointer = startingPoint();
        direction = Direction.RIGHT;
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
        // TODO
    }

    private Location startingPoint() {
        for (int y=0; y<height; y++)
            for (int x=0; x<width; x++)
                if (codes[y][x] == 'S')
                    return new Location(x, y);
        throw new NoStartingPointException();
    }
}
