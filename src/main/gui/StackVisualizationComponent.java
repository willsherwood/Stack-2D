package main.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

public class StackVisualizationComponent extends JComponent {

    public void paint (Graphics g, Stack<Object> stack) {
        BufferedImage b = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        draw(b.getGraphics(), stack);
        g.drawImage(b, 0, 0, null);
    }

    private void draw (Graphics g, Stack<Object> stack) {
        int y = 20;
        while (!stack.isEmpty()) {
            y += 20;
            g.drawString(stack.pop().toString(), 20, y);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(400, 800);
    }
}
