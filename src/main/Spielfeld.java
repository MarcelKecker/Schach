package main;

import enums.Farbe;
import figuren.Koenig;
import figuren.Laeufer;
import figuren.Springer;
import figuren.Turm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Spielfeld extends JPanel implements ActionListener {
    private static final int SPIELFELD_BREITE = 8;
    private static final int SPIELFELD_HOEHE = 8;
    private Position positionAusgewaehlteFigur;
    private Feld[] felder;

    public Spielfeld() {
        this.setLayout(new GridLayout(SPIELFELD_HOEHE, SPIELFELD_BREITE));
        erstelleFelder();
//        for (int i = 0; i < SPIELFELD_BREITE; i++) {
//            felder[feld(i, 6)].setFigurAufDiesemFeld(new Koenig(Farbe.WEISS));
//            felder[feld(i, 1)].setFigurAufDiesemFeld(new Bauer(Farbe.SCHWARZ));
//        }
        felder[feld(0, 0)].setFigurAufDiesemFeld(new Springer(Farbe.SCHWARZ));
        felder[feld(1, 0)].setFigurAufDiesemFeld(new Springer(Farbe.WEISS));
        felder[feld(2, 0)].setFigurAufDiesemFeld(new Koenig(Farbe.SCHWARZ));
        felder[feld(3, 0)].setFigurAufDiesemFeld(new Koenig(Farbe.WEISS));
        felder[feld(7, 7)].setFigurAufDiesemFeld(new Turm(Farbe.WEISS, this));
        felder[feld(0, 7)].setFigurAufDiesemFeld(new Laeufer(Farbe.WEISS, this));

    }

    private void erstelleFelder() {
        felder = new Feld[SPIELFELD_BREITE * SPIELFELD_HOEHE];
        for (int i = 0; i < SPIELFELD_BREITE * SPIELFELD_HOEHE; i++) {
            felder[i] = (new Feld(position(i), this));
            this.add(felder[i]);
            felder[i].addActionListener(this);
        }
    }

    private boolean istFigurAusgewaehlt() {
        for (Feld feld : felder) {
            if (feld.istZielPosition()) {
                return true;
            }
        }
        return false;
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

    public boolean istImSpielfeld(Position position) {
        return position.getX() < SPIELFELD_BREITE && position.getX() >= 0 && position.getY() < SPIELFELD_HOEHE && position.getY() >= 0;
    }

    public boolean feldKontrolliert(Figur ausfuehrendeFigur, Position position) {
        for (int i = 0; i < SPIELFELD_BREITE * SPIELFELD_HOEHE; i++) {
            if (felder[i].getFigurAufDiesemFeld() != null && felder[i].getFigurAufDiesemFeld().kontrolliertFeld(position(i), position) && felder[i].getFigurAufDiesemFeld().getFarbe() !=ausfuehrendeFigur.getFarbe()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //TODO nur abwechselnd ziehen
        Feld ausgewaehltesFeld = (Feld) e.getSource();

        if (istFigurAusgewaehlt()) {
            if (ausgewaehltesFeld.istZielPosition()) {
                bewegeFigur(ausgewaehltesFeld.getPosition());
            }
            for (Feld feld : felder) {
                feld.setIstZielPosition(false);
            }
            positionAusgewaehlteFigur = null;
        } else if (ausgewaehltesFeld.istFigurAufDiesemFeld()) {
            ArrayList<Position> moeglicheZielPositionen = ausgewaehltesFeld.getMoeglicheZielPositionen();
            for (Position position : moeglicheZielPositionen) {
                Feld zielFeld = getFeldBei(position);
                zielFeld.setIstZielPosition(true);
            }
            positionAusgewaehlteFigur = ausgewaehltesFeld.getPosition();
        }
    }

    private void bewegeFigur(Position ziel) {
        Feld ausgewaehltesFeld = getFeldBei(positionAusgewaehlteFigur);
        getFeldBei(ziel).setFigurAufDiesemFeld(ausgewaehltesFeld.getFigurAufDiesemFeld());
        ausgewaehltesFeld.setFigurAufDiesemFeld(null);
    }

    public Feld getFeldBei(Position ziel) {
        return felder[feld(ziel)];
    }

    public Figur getFigurBei(Position ziel) {
        return getFeldBei(ziel).getFigurAufDiesemFeld();
    }
}
