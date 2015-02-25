package main.gui;

import book.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.Queue;

public class BookComponent extends JComponent {

    public final int CELL_SIZE = 20;
    private int cellHeight, cellWidth;

    private char[][] map;

    private Location pointer;
    private Direction direction;

    private Book book;
    private Queue<Drawable> drawables;

    public StackProxy stack() {
        return book.stack();
    }

    private static Map<Integer, Direction> associatedDirections;

    public BookComponent () {
        recalculateCellSizes();
        pointer = new Location(cellWidth / 2, cellHeight / 2);
        direction = Direction.RIGHT;
        book = new BoundedBook(1, 1);
        map = new char[1][1];
        drawables = new LinkedList<>();
    }

    static {
        associatedDirections = new HashMap<>();
        associatedDirections.put(KeyEvent.VK_UP, Direction.UP);
        associatedDirections.put(KeyEvent.VK_RIGHT, Direction.RIGHT);
        associatedDirections.put(KeyEvent.VK_DOWN, Direction.DOWN);
        associatedDirections.put(KeyEvent.VK_LEFT, Direction.LEFT);
    }

    public void sendKey (int keyCode) {
        System.out.println(keyCode);
        if (keyCode == KeyEvent.VK_BACK_SPACE) {
            shiftPointer(-1);
            map[pointer.y][pointer.x] = 0;
            paint(getGraphics());
            return;
        } else if (keyCode == KeyEvent.VK_F5) {
            addAll();
            SwingUtilities.invokeLater(()-> {
                book.start();
                JFrame frame = new JFrame();
                JPanel panel = new JPanel();
                frame.getContentPane().add(panel);
                StackVisualizationComponent stackVisualizationComponent = new StackVisualizationComponent();
                panel.add(stackVisualizationComponent);
                frame.pack();
                frame.setLocation(0, 0);
                frame.setVisible(true);
                while(book.step()) {
                    pointer = book.currentPointer();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    paint(getGraphics());
                    stackVisualizationComponent.paint(frame.getGraphics(), book.stack().contents());
                }
                frame.dispose();
            });
            return;
        } else if (keyCode == KeyEvent.VK_CONTROL) {
            drawables.add(new Notification(new Location(pointer.x * CELL_SIZE - 2 * CELL_SIZE, pointer.y * CELL_SIZE - 2 * CELL_SIZE), "Coordinate: " + pointer, 4000));
            paint(getGraphics());
            return;
        }
        if (keyCode < 32)
            return;

        if (associatedDirections.containsKey(keyCode)) {
            direction = associatedDirections.get(keyCode);
            shiftPointer(1);
        } else {
            map[pointer.y][pointer.x] = (char) keyCode;
            shiftPointer(1);
        }
        paint(getGraphics());
    }

    public void shiftPointer(int amt) {
        pointer = pointer.over(amt, direction);
        if (pointer.x<0 || pointer.y<0 || pointer.x >= cellWidth || pointer.y >= cellHeight)
            pointer = pointer.over(-amt, direction);
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
        for (int y = 0; y < cellHeight; y++)
            for (int x = 0; x < cellWidth; x++)
                if (map[y][x] != 0)
                    g.drawString(map[y][x] + "", x * CELL_SIZE + 4, y * (CELL_SIZE) + CELL_SIZE - 4);
        Iterator<Drawable> p = drawables.iterator();
        while (p.hasNext()) {
            Drawable t = p.next();
            t.draw(g);
            if (t instanceof Destructable) {
                if (((Destructable)t).isDestroyed())
                    p.remove();
            }
        }
    }

    @Override
    public Dimension getPreferredSize () {
        return new Dimension(600, 600);
    }

    private void recalculateCellSizes () {
        int cellHeight = getHeight() / CELL_SIZE;
        int cellWidth = getWidth() / CELL_SIZE;
        if (cellHeight != this.cellHeight || cellWidth != this.cellWidth) {
            map = new char[cellHeight][cellWidth];
            System.out.println(cellWidth);
            this.cellWidth = cellWidth;
            this.cellHeight = cellHeight;
        }
    }

    private void addAll () {
        book = new BoundedBook(cellWidth, cellHeight);
        for (int y = 0; y < cellHeight; y++)
            for (int x = 0; x < cellWidth; x++)
                book.addCode(new Location(x, y), map[y][x]);
    }
}
