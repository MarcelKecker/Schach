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
    public Bauer(Farbe farbe) {
        super(getRichtigesIcon(farbe), farbe);
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

    @Override
    public ArrayList<Position> getMoeglicheZielPositionen(Spielfeld spielfeld, Position position) {
        ArrayList<Position> positionen = new ArrayList<>();
        int richtung = switch (farbe) {
            case WEISS -> -1;
            case SCHWARZ -> 1;
        };
        Position einsEntfernt = position.verschobenUm(0, richtung);
        Position zweiEntfernt = position.verschobenUm(0, richtung * 2);
        if (spielfeld.getFigurBei(einsEntfernt) == null) {
            positionen.add(einsEntfernt);
            if (position.getY() == startPosition && spielfeld.getFigurBei(zweiEntfernt) == null) {
                positionen.add(zweiEntfernt);
            }
        }
        probiereSchlagen(spielfeld, positionen, position.verschobenUm(-1, richtung));
        probiereSchlagen(spielfeld, positionen, position.verschobenUm(1, richtung));

        return positionen;
    }

    @Override
    public boolean kontrolliertFeld(Position position,Position vergleich) {
        return (farbe == Farbe.WEISS && (position.verschobenUm(-1, 1).equals(vergleich) || position.verschobenUm(1, 1).equals(vergleich))) || (farbe == Farbe.SCHWARZ && (position.verschobenUm(-1, -1).equals(vergleich) || position.verschobenUm(1, -1).equals(vergleich)));
    }

    private void probiereSchlagen(Spielfeld spielfeld, ArrayList<Position> positionen, Position ziel) {
        if (spielfeld.istImSpielfeld(ziel) && spielfeld.getFigurBei(ziel) != null && spielfeld.getFigurBei(ziel).getFarbe() != farbe) {
            positionen.add(ziel);
        }
    }
}
