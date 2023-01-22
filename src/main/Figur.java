package main;

import enums.Farbe;

import javax.swing.ImageIcon;
import java.util.ArrayList;

public abstract class Figur {

    private final ImageIcon icon;

    protected final Farbe farbe;
    private final Spielfeld spielfeld;

    public Figur(ImageIcon icon, Farbe farbe, Spielfeld spielfeld) {
        this.icon = icon;
        this.farbe = farbe;
        this.spielfeld = spielfeld;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public abstract ArrayList<Position> getMoeglicheZielPositionen(Position position);

    public Farbe getFarbe() {
        return farbe;
    }

    public abstract boolean kontrolliertFeld(Position position, Position vergleich);

//    public boolean setztKoenigSchach(Position ziel, Position positionBewegendeFigur) {
//        boolean ausgabe;
//        Figur figurZiel = spielfeld.getFigurBei(ziel);
//        spielfeld.bewegeFigur(ziel, positionBewegendeFigur);
//        ausgabe = spielfeld.istImSchach(farbe);
//        spielfeld.bewegeFigur(positionBewegendeFigur, ziel);
//        spielfeld.setFigur(ziel, figurZiel);
//
//        return ausgabe;
//    }
}
