package book;

public class Location {

    public final int x, y;

    public Location (int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        if (x != location.x) return false;
        if (y != location.y) return false;

        return true;
    }

    @Override
    public int hashCode () {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString () {
        return String.format("{%d, %d}", x, y);
    }

    /**
     *  returns a location "over" amount spaces in the direction direction.
     */
    public Location over(int amount, Direction direction) {
        return new Location(x + amount * direction.dx, y + amount * direction.dy);
    }
}
