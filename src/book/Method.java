package book;

public class Method {

    private int arity;

    private Location loc;

    /**
     * constructs a method that uses arity arguments
     */
    public Method(int arity, Location loc) {
        this.arity = arity;
        this.loc = loc;
    }

    public Location getLocation() {
        return loc;
    }

    public int getArity() {
        return arity;
    }
}
