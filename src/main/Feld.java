package main;

import enums.FigurEnum;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Feld extends JButton {

    private Position position;
    private Spielfeld spielfeld;

    private Figur figurAufDiesemFeld;

    Feld(Position position, Spielfeld spielfeld) {
        this.position = position;
        this.spielfeld = spielfeld;
        this.setPreferredSize(new Dimension(100, 100));
        if ((position.getX() % 2 == 0 && position.getY() % 2 == 0) || (position.getX() % 2 == 1 && position.getY() % 2 == 1)) {
            setBackground(Color.WHITE);
        } else {
            setBackground(Color.GREEN.darker().darker().darker());
        }
        updateGraphics();
    }

    private void updateGraphics() {
        if (this.figurAufDiesemFeld != null) {
            setIcon(this.figurAufDiesemFeld.getIcon());
        } else {
            setIcon(null);
        }
    }

    public void setFigurAufDiesemFeld(Figur figurAufDiesemFeld) {
        this.figurAufDiesemFeld = figurAufDiesemFeld;
        updateGraphics();
    }
}
