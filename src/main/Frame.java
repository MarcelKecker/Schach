package main;

import javax.swing.*;

public class Frame extends JFrame {

    Frame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(new Spielfeld());
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
