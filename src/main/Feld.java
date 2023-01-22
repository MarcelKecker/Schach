package main;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Feld extends JButton{

    private final Position position;
    private boolean istZielPosition;

    private Figur figurAufDiesemFeld;

    Feld(Position position) {
        this.position = position;
        this.setPreferredSize(new Dimension(100, 100));
        updateGraphics();
    }

    private boolean istWeissesFeld() {
        return (position.getX() % 2 == 0 && position.getY() % 2 == 0) || (position.getX() % 2 == 1 && position.getY() % 2 == 1);
    }

    private void updateGraphics() {
        if (istZielPosition) {
            if (istWeissesFeld()) {
                setBackground(Color.RED.brighter());
            } else {
                setBackground(Color.RED.darker());
            }
        } else {
            if (istWeissesFeld()) {
                setBackground(Color.WHITE);
            } else {
                setBackground(Color.GREEN.darker().darker().darker());
            }
        }
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
    public ArrayList<Position> getMoeglicheZielPositionen() {
        return figurAufDiesemFeld.getMoeglicheZielPositionen(position);
    }
    public boolean istFigurAufDiesemFeld() {
        return figurAufDiesemFeld != null;
    }

    public void setIstZielPosition(boolean istZielPosition) {
        this.istZielPosition = istZielPosition;
        updateGraphics();
    }

    public boolean istZielPosition() {
        return istZielPosition;
    }

    public Position getPosition() {
        return position;
    }

    public Figur getFigurAufDiesemFeld() {
        return figurAufDiesemFeld;
    }
}
