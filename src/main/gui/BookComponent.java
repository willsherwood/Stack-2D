package main.gui;

import book.Direction;
import book.Location;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.*;

public class BookComponent extends JComponent {

    public final int CELL_SIZE = 20;
    private int cellHeight, cellWidth;

    private Location pointer;
    private Direction direction;

    private static Map<Integer, Direction> associatedDirections;

    public BookComponent () {
        recalculateCellSizes();
        pointer = new Location(cellWidth / 2, cellHeight / 2);
        direction = Direction.RIGHT;
    }

    static {
        associatedDirections = new HashMap<>();
        associatedDirections.put(KeyEvent.VK_UP, Direction.UP);
        associatedDirections.put(KeyEvent.VK_RIGHT, Direction.RIGHT);
        associatedDirections.put(KeyEvent.VK_DOWN, Direction.DOWN);
        associatedDirections.put(KeyEvent.VK_LEFT, Direction.LEFT);
    }

    public void sendKey (int keyCode) {
        if (keyCode < 32)
            return;
        if (associatedDirections.containsKey(keyCode)) {
            pointer = pointer.over(1, associatedDirections.get(keyCode));
            direction = Direction.RIGHT;
        } else {
            System.out.println(keyCode);
            pointer = pointer.over(1, direction);
        }
        paint(getGraphics());
    }

    @Override
    public void paint (Graphics g) {
        recalculateCellSizes();
        BufferedImage buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        draw(buffer.getGraphics());
        g.drawImage(buffer, 0, 0, null);
    }

    public void draw (Graphics g) {
        g.setColor(Color.DARK_GRAY);
        for (int y = 0; y < cellHeight; y++)
            g.drawLine(0, y * CELL_SIZE, getWidth(), y * CELL_SIZE);
        for (int x = 0; x < cellWidth; x++)
            g.drawLine(x * CELL_SIZE, 0, x * CELL_SIZE, getHeight());
        g.setColor(Color.GREEN);
        g.drawRect(pointer.x * CELL_SIZE, pointer.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
    }

    @Override
    public Dimension getPreferredSize () {
        return new Dimension(600, 600);
    }

    private void recalculateCellSizes () {
        cellHeight = getHeight() / CELL_SIZE;
        cellWidth = getWidth() / CELL_SIZE;
    }
}
