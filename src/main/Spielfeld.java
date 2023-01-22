package main;

import enums.Farbe;
import enums.RochadenSeite;
import figuren.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static enums.Farbe.SCHWARZ;
import static enums.Farbe.WEISS;

public class Spielfeld extends JPanel implements ActionListener {
    private static final int SPIELFELD_BREITE = 8;
    private static final int SPIELFELD_HOEHE = 8;
    private Position positionAusgewaehlteFigur;
    private Feld[] felder;
    private Farbe amZug = WEISS;
    Koenig koenigWeiss;
    Turm turmLinksWeiss;
    Turm turmRechtsWeiss;
    Koenig koenigSchwarz;
    Turm turmLinksSchwarz;
    Turm turmRechtsSchwarz;
    private JFrame frame;

    public Spielfeld(JFrame frame) {
        this.frame = frame;
        this.setLayout(new GridLayout(SPIELFELD_HOEHE, SPIELFELD_BREITE));
        erstelleFelder();
        erstelleFiguren();

    }

    private void erstelleFiguren() {
        for (int i = 0; i < SPIELFELD_BREITE; i++) {
            felder[feld(i, 6)].setFigurAufDiesemFeld(new Bauer(Farbe.WEISS, this));
            felder[feld(i, 1)].setFigurAufDiesemFeld(new Bauer(SCHWARZ, this));
        }
        koenigWeiss = new Koenig(Farbe.WEISS, this);
        turmLinksWeiss = new Turm(Farbe.WEISS, this);
        turmRechtsWeiss = new Turm(Farbe.WEISS, this);
        koenigSchwarz = new Koenig(SCHWARZ, this);
        turmLinksSchwarz = new Turm(SCHWARZ, this);
        turmRechtsSchwarz = new Turm(SCHWARZ, this);
        felder[feld(1, 7)].setFigurAufDiesemFeld(new Springer(Farbe.WEISS, this));
        felder[feld(6, 7)].setFigurAufDiesemFeld(new Springer(Farbe.WEISS, this));
        felder[feld(1, 0)].setFigurAufDiesemFeld(new Springer(SCHWARZ, this));
        felder[feld(6, 0)].setFigurAufDiesemFeld(new Springer(SCHWARZ, this));
        felder[feld(4, 7)].setFigurAufDiesemFeld(koenigWeiss);
        felder[feld(4, 0)].setFigurAufDiesemFeld(koenigSchwarz);
        felder[feld(0, 7)].setFigurAufDiesemFeld(turmLinksWeiss);
        felder[feld(7, 7)].setFigurAufDiesemFeld(turmRechtsWeiss);
        felder[feld(0, 0)].setFigurAufDiesemFeld(turmLinksSchwarz);
        felder[feld(7, 0)].setFigurAufDiesemFeld(turmRechtsSchwarz);
        felder[feld(2, 7)].setFigurAufDiesemFeld(new Laeufer(Farbe.WEISS, this));
        felder[feld(5, 7)].setFigurAufDiesemFeld(new Laeufer(Farbe.WEISS, this));
        felder[feld(2, 0)].setFigurAufDiesemFeld(new Laeufer(SCHWARZ, this));
        felder[feld(5, 0)].setFigurAufDiesemFeld(new Laeufer(SCHWARZ, this));
        felder[feld(3, 7)].setFigurAufDiesemFeld(new Dame(Farbe.WEISS, this));
        felder[feld(3, 0)].setFigurAufDiesemFeld(new Dame(SCHWARZ, this));
    }

    private void resetFiguren() {
        for (Feld feld : felder) {
            feld.setFigurAufDiesemFeld(null);
        }
    }

    private void erstelleFelder() {
        felder = new Feld[SPIELFELD_BREITE * SPIELFELD_HOEHE];
        for (int i = 0; i < SPIELFELD_BREITE * SPIELFELD_HOEHE; i++) {
            felder[i] = (new Feld(position(i)));
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

    public static int feld(Position position) {
        return position.getY() * SPIELFELD_BREITE + position.getX();
    }

    public static int feld(int x, int y) {
        return y * SPIELFELD_BREITE + x;
    }

    public boolean istImSpielfeld(Position position) {
        return position.getX() < SPIELFELD_BREITE && position.getX() >= 0 && position.getY() < SPIELFELD_HOEHE && position.getY() >= 0;
    }

    public boolean feldKontrolliert(Farbe koenigFarbe, Position position) {
        for (int i = 0; i < felder.length; i++) {
            if (felder[i].getFigurAufDiesemFeld() != null && felder[i].getFigurAufDiesemFeld().getFarbe() != koenigFarbe && felder[i].getFigurAufDiesemFeld().kontrolliertFeld(position(i), position)) {
                return true;
            }
        }
        return false;
    }

    public boolean istImSchach(Farbe koenigFarbe) {
        Position position = null;
        if (koenigFarbe == Farbe.WEISS) {
            for (Feld feld : felder) {
                if (feld.getFigurAufDiesemFeld() == koenigWeiss) {
                    position = feld.getPosition();
                }
            }
        } else {
            for (Feld feld : felder) {
                if (feld.getFigurAufDiesemFeld() == koenigSchwarz) {
                    position = feld.getPosition();
                }
            }
        }
        return feldKontrolliert(koenigFarbe, position);
    }

    public void pruefeNachMatt(Farbe verliereFarbe) {
        Koenig verliererKoenig;
        if (verliereFarbe == WEISS) {
            verliererKoenig = koenigWeiss;
        } else {
            verliererKoenig = koenigSchwarz;
        }
        for (Feld feld : felder) {
            feld.setIstZielPosition(false);
        }
        ArrayList<Position> moeglicheZielPositionenKoenig = getFigurFeld(verliererKoenig).getMoeglicheZielPositionen();
        if (istImSchach(verliereFarbe) && moeglicheZielPositionenKoenig.size() == 0) {
            verarbeiteMatt(verliereFarbe.getAndereFarbe());
        }
    }

    public void verarbeiteMatt(Farbe gewinnerFarbe) {
        int nochEinSpiel = JOptionPane.showOptionDialog(frame, gewinnerFarbe + " hat gewonnen! \n Noch ein Spiel?", "Noch ein Spiel?",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE, null,
                new String[]{"Ja", "Nein"}, "Ja");
        if (nochEinSpiel == 0) {
            resetSpiel();
        } else {
            System.exit(0);
        }
    }

    public void resetSpiel() {
        amZug = WEISS;
        resetFiguren();
        erstelleFiguren();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Feld ausgewaehltesFeld = (Feld) e.getSource();
        if (istFigurAusgewaehlt()) {
            if (ausgewaehltesFeld.istZielPosition()) {
                amZug = getFigurBei(positionAusgewaehlteFigur).getFarbe().getAndereFarbe();
                updateWurdeBewegt();
                bewegeFigur(ausgewaehltesFeld.getPosition(), positionAusgewaehlteFigur);
                pruefeNachMatt(ausgewaehltesFeld.getFigurAufDiesemFeld().getFarbe().getAndereFarbe());
            }
            for (Feld feld : felder) {
                feld.setIstZielPosition(false);
            }
            positionAusgewaehlteFigur = null;
        } else if (ausgewaehltesFeld.istFigurAufDiesemFeld() && ausgewaehltesFeld.getFigurAufDiesemFeld().getFarbe() == amZug) {
            ArrayList<Position> moeglicheZielPositionen = ausgewaehltesFeld.getMoeglicheZielPositionen();
            for (Position position : moeglicheZielPositionen) {
                Feld zielFeld = getFeldBei(position);
                zielFeld.setIstZielPosition(true);
            }
            positionAusgewaehlteFigur = ausgewaehltesFeld.getPosition();
        }
    }

    private void updateWurdeBewegt() {
        if (felder[feld(positionAusgewaehlteFigur)].getFigurAufDiesemFeld() == koenigWeiss) {
            koenigWeiss.setWurdeBewegt(true);
        } else if (felder[feld(positionAusgewaehlteFigur)].getFigurAufDiesemFeld() == koenigSchwarz) {
            koenigSchwarz.setWurdeBewegt(true);
        } else if (felder[feld(positionAusgewaehlteFigur)].getFigurAufDiesemFeld() == turmLinksSchwarz) {
            turmLinksSchwarz.setWurdeBewegt(true);
        } else if (felder[feld(positionAusgewaehlteFigur)].getFigurAufDiesemFeld() == turmRechtsSchwarz) {
            turmRechtsSchwarz.setWurdeBewegt(true);
        } else if (felder[feld(positionAusgewaehlteFigur)].getFigurAufDiesemFeld() == turmLinksWeiss) {
            turmLinksWeiss.setWurdeBewegt(true);
        } else if (felder[feld(positionAusgewaehlteFigur)].getFigurAufDiesemFeld() == turmRechtsWeiss) {
            turmRechtsWeiss.setWurdeBewegt(true);
        }
    }

    public void bewegeFigur(Position ziel, Position start) {
        Feld startFeld = getFeldBei(start);
        getFeldBei(ziel).setFigurAufDiesemFeld(startFeld.getFigurAufDiesemFeld());
        startFeld.setFigurAufDiesemFeld(null);
    }

    public void setFigur(Position position, Figur figur) {
        getFeldBei(position).setFigurAufDiesemFeld(figur);
    }

    public boolean setztKoenigSchach(Position ziel, Position positionBewegendeFigur, Farbe farbe) {
        boolean ausgabe;
        Figur figurZiel = getFigurBei(ziel);
        bewegeFigur(ziel, positionBewegendeFigur);
        ausgabe = istImSchach(farbe);
        bewegeFigur(positionBewegendeFigur, ziel);
        setFigur(ziel, figurZiel);

        return ausgabe;
    }

    public boolean rochadeMoeglich(Farbe farbe, RochadenSeite rochadenSeite) {
        Koenig beteiligterKoenig;
        Turm beteiligterTurm;
        if (farbe == WEISS) {
            beteiligterKoenig = koenigWeiss;
            beteiligterTurm = rochadenSeite == RochadenSeite.KURZ ? turmRechtsWeiss : turmLinksWeiss;
        } else {
            beteiligterKoenig = koenigSchwarz;
            beteiligterTurm = rochadenSeite == RochadenSeite.KURZ ? turmRechtsSchwarz : turmLinksSchwarz;
        }
        int vorzeichenRichtung = switch (rochadenSeite) {
            case KURZ -> +1;
            case LANG -> -1;
        };
        Position koenigPosition = getFigurFeld(beteiligterKoenig).getPosition();
        Position uebersprungenesFeld = koenigPosition.verschobenUm(vorzeichenRichtung, 0);
        Position koenigZielFeld = koenigPosition.verschobenUm(2 * vorzeichenRichtung, 0);
        Position nebenKoenigZiel = koenigPosition.verschobenUm(3 * vorzeichenRichtung, 0);

        return !beteiligterTurm.wurdeBewegt()
                && !beteiligterKoenig.wurdeBewegt()
                && getFigurBei(uebersprungenesFeld) == null
                && !setztKoenigSchach(uebersprungenesFeld, koenigPosition, farbe)
                && getFigurBei(koenigZielFeld) == null
                && !setztKoenigSchach(koenigZielFeld, koenigPosition, farbe)
                && (rochadenSeite == RochadenSeite.KURZ || getFigurBei(nebenKoenigZiel) == null);

    }

    private Feld getFigurFeld(Figur figur) {
        for (Feld feld : felder) {
            if (feld.getFigurAufDiesemFeld() == figur) {
                return feld;
            }
        }
        throw new RuntimeException("Figur " + figur + " existiert nicht");
    }

    public Feld getFeldBei(Position ziel) {
        return felder[feld(ziel)];
    }

    public Figur getFigurBei(Position ziel) {
        return getFeldBei(ziel).getFigurAufDiesemFeld();
    }
}
