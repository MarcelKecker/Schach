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
        super(getRichtigesIcon(farbe), farbe, spielfeld);
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

        Position schlagPosition1 = position.verschobenUm(-1, richtung);
        if (kannSchlagen(spielfeld, schlagPosition1)) {
            moeglicheZuege.add(schlagPosition1);
        }

        Position schlagPosition2 = position.verschobenUm(1, richtung);
        if (kannSchlagen(spielfeld, schlagPosition2)) {
            moeglicheZuege.add(schlagPosition2);
        }

        return moeglicheZuege;
    }
    @Override
    public ArrayList<Position> getMoeglicheZielPositionen(Position position) {

        ArrayList<Position> positionen = pruefeMoeglicheZuege(position);
        for (int i = 0; i < positionen.size(); i++) {
            if (positionen.get(i) == null) {
                positionen.remove(i);
                i--;
            }
        }
        for (int i = 0; i < positionen.size(); i++) {
            if (spielfeld.setztKoenigSchach(positionen.get(i), position, farbe)) {
                positionen.remove(i);
                i--;
            }
        }
        return positionen;
    }
    @Override
    public boolean kontrolliertFeld(Position position, Position vergleich) {
        ArrayList<Position> positionen = pruefeMoeglicheZuege(position);
        for (int i = 0; i < positionen.size(); i++) {
            if (positionen.get(i) == null) {
                positionen.remove(i);
                i--;
            }
        }
        ArrayList<Position> kontrollierteFelder = positionen;

        for (Position feld : kontrollierteFelder) {
            if (feld != null && feld.equals(vergleich)) {
                return true;
            }
        }
        return false;
    }

    private Position probiereSchlagen(Spielfeld spielfeld, Position ziel) {
        Position position = null;
        if (kannSchlagen(spielfeld, ziel)) {
            position = ziel;
        }
        return position;
    }

    private boolean kannSchlagen(Spielfeld spielfeld, Position ziel) {
        return spielfeld.istImSpielfeld(ziel) && spielfeld.getFigurBei(ziel) != null && spielfeld.getFigurBei(ziel).getFarbe() != farbe;
    }
}
