package main;

import enums.Farbe;
import enums.FigurEnum;
import figuren.Bauer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Spielfeld extends JPanel {
    private static final int SPIELFELD_BREITE = 8;
    private static final int SPIELFELD_HOEHE = 8;

    Feld[] felder;

    public Spielfeld() {
        this.setLayout(new GridLayout(SPIELFELD_HOEHE, SPIELFELD_BREITE));
        erstelleFelder();
        Bauer bauer = new Bauer(new Position(0, 1), Farbe.WEISS);
        felder[SPIELFELD_BREITE].setFigurAufDiesemFeld(bauer);
    }

    private void erstelleFelder() {
        felder = new Feld[SPIELFELD_BREITE * SPIELFELD_HOEHE];
        for (int i = 0; i < SPIELFELD_BREITE * SPIELFELD_HOEHE; i++) {
            felder[i] = (new Feld(position(i), this));
            this.add(felder[i]);
        }
    }

    public Position position(int feld) {
        return new Position(feld % SPIELFELD_BREITE, feld / SPIELFELD_BREITE);
    }

    public int feld(Position position) {
        return position.getY() * SPIELFELD_BREITE + position.getX();
    }
    public int feld(int x, int y) {
        return y * SPIELFELD_BREITE + x;
    }

}
