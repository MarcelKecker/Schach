package figuren;

import enums.Farbe;
import main.Figur;
import main.Frame;
import main.Position;
import main.Spielfeld;

import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.util.Objects;

public class Bauer extends Figur {
    private static final ImageIcon bauerSchwarz = new ImageIcon(Objects.requireNonNull(Frame.class.getResource("/resources/BauerSchwarz.png")));
    private static final ImageIcon bauerWeiss = new ImageIcon(Objects.requireNonNull(Frame.class.getResource("/resources/BauerWeiss.png")));
    private final int startPosition;
    private final Spielfeld spielfeld;

    public Bauer(Farbe farbe, Spielfeld spielfeld) {
        super(getRichtigesIcon(farbe), farbe);
        this.spielfeld = spielfeld;
        startPosition = switch (farbe) {
            case WEISS -> 6;
            case SCHWARZ -> 1;
        };
    }

    private static ImageIcon getRichtigesIcon(Farbe farbe) {
        if (farbe == Farbe.SCHWARZ) {
            return bauerSchwarz;
        } else {
            return bauerWeiss;
        }
    }
    private ArrayList<Position> pruefeMoeglicheZuege(Position position) {
        ArrayList<Position> moeglicheZuege = new ArrayList<>();
        int richtung = switch (farbe) {
            case WEISS -> -1;
            case SCHWARZ -> 1;
        };
        Position einsEntfernt = position.verschobenUm(0, richtung);
        Position zweiEntfernt = position.verschobenUm(0, richtung * 2);
        if (spielfeld.getFigurBei(einsEntfernt) == null) {
            moeglicheZuege.add(einsEntfernt);
            if (position.getY() == startPosition && spielfeld.getFigurBei(zweiEntfernt) == null) {
                moeglicheZuege.add(zweiEntfernt);
            }
        }

        moeglicheZuege.add(probiereSchlagen(spielfeld, position.verschobenUm(-1, richtung)));
        moeglicheZuege.add(probiereSchlagen(spielfeld, position.verschobenUm(1, richtung)));
        for (int i = 0; i < moeglicheZuege.size(); i++) {
             if (moeglicheZuege.get(i) == null) {
                 moeglicheZuege.remove(i);
             }
        }
        return moeglicheZuege;
    }
    @Override
    public ArrayList<Position> getMoeglicheZielPositionen(Position position) {

        ArrayList<Position> positionen = pruefeMoeglicheZuege(position);
        for (int i = 0; i < positionen.size(); i++) {
            if (positionen.get(i) == null) {
                positionen.remove(i);
            }
        }
        for (int i = 0; i < positionen.size(); i++) {
            if ((spielfeld.getFigurBei(positionen.get(i)) != null && spielfeld.getFigurBei(positionen.get(i)).getFarbe() == farbe) || setztKoenigSchach(positionen.get(i), position)) {
                positionen.remove(i);
                i--;
            }
        }
        return positionen;
    }
    @Override
    public boolean kontrolliertFeld(Position position, Position vergleich) {
        ArrayList<Position> kontrollierteFelder = pruefeMoeglicheZuege(position);

        for (int i = 0; i < kontrollierteFelder.size(); i++) {
            if (kontrollierteFelder.get(i) == null) {
                kontrollierteFelder.remove(i);
            }
        }

        for (Position feld : kontrollierteFelder) {
            if (feld != null && feld.equals(vergleich)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setztKoenigSchach(Position ziel, Position positionBewegendeFigur) {
        boolean ausgabe;
        Figur figurZiel = spielfeld.getFigurBei(ziel);
        spielfeld.bewegeFigur(ziel, positionBewegendeFigur, false);
        ausgabe = spielfeld.istImSchach(farbe);
        spielfeld.bewegeFigur(positionBewegendeFigur, ziel, false);
        spielfeld.setFigur(ziel, figurZiel);

        return ausgabe;
    }

    private Position probiereSchlagen(Spielfeld spielfeld, Position ziel) {
        Position position = null;
        if (spielfeld.istImSpielfeld(ziel) && spielfeld.getFigurBei(ziel) != null) {
            position = ziel;
        }
        return position;
    }
}
