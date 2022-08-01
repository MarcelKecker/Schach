package main;

import enums.Farbe;

import javax.swing.ImageIcon;
import java.util.ArrayList;

public abstract class Figur {

    private final ImageIcon icon;

    protected final Farbe farbe;

    public Figur(ImageIcon icon, Farbe farbe) {
        this.icon = icon;
        this.farbe = farbe;
    }

    public ImageIcon getIcon() {
        return icon;
    }
    public abstract ArrayList<Position> getMoeglicheZielPositionen(Spielfeld spielfeld, Position position);
    public Farbe getFarbe() {
        return farbe;
    }
}
