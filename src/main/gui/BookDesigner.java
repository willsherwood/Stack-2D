package main.gui;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class BookDesigner extends JFrame {

    private JPanel panel;
    private BookComponent bookComponent;

    public BookDesigner() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        bookComponent = new BookComponent();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed (KeyEvent e) {
                if (e.getKeyChar() != Character.MAX_VALUE)
                    bookComponent.sendKey(e.getKeyChar());
                else
                    bookComponent.sendKey(e.getKeyCode());
            }
        });

        addWindowStateListener(new WindowAdapter() {
            @Override
            public void windowStateChanged (WindowEvent e) {
                System.out.println(e);
            }
        });

        panel.add(bookComponent);
        getContentPane().add(panel);

        pack();

        setVisible(true);
    }
}
