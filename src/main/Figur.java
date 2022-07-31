package main;

import enums.Farbe;

import javax.swing.ImageIcon;
import java.util.ArrayList;

public abstract class Figur {

    private final ImageIcon icon;

    public Figur(ImageIcon icon, Farbe farbe) {
        this.icon = icon;
    }

    public ImageIcon getIcon() {
        return icon;
    }
    public abstract ArrayList<Position> getMoeglicheZielPositionen(Spielfeld spielfeld, Position position);
}
