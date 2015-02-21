package main.gui;

import book.Location;

import java.awt.*;

public class Notification implements Drawable, Destructable {

    private Location loc;
    private String text;
    private long life = System.currentTimeMillis();
    private int millis;

    public Notification(Location loc, String text, int millis) {
        this.loc = loc;
        this.text = text;
        this.millis = millis;
    }

    @Override
    public void draw (Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(loc.x, loc.y, 100, 40);
        g.setColor(Color.WHITE);
        g.drawString(text, loc.x+4, loc.y+38);
    }

    @Override
    public boolean isDestroyed () {
        return System.currentTimeMillis() - life > millis;
    }
}
