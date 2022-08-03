package figuren;

import enums.Farbe;
import main.*;

import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.util.Objects;

public class Dame extends Figur {
    private static final ImageIcon dameSchwarz = new ImageIcon(Objects.requireNonNull(Frame.class.getResource("/resources/DameSchwarz.png")));
    private static final ImageIcon dameWeiss = new ImageIcon(Objects.requireNonNull(Frame.class.getResource("/resources/DameWeiss.png")));
    private Spielfeld spielfeld;

    public Dame(Farbe farbe, Spielfeld spielfeld) {
        super(getRichtigesIcon(farbe), farbe, spielfeld);
        getMoeglicheZielPositionen(spielfeld, new Position(0, 0));
    }

    private static ImageIcon getRichtigesIcon(Farbe farbe) {
        if (farbe == Farbe.SCHWARZ) {
            return dameSchwarz;
        } else {
            return dameWeiss;
        }
    }

    @Override
    public ArrayList<Position> getMoeglicheZielPositionen(Spielfeld spielfeld, Position position) {
        this.spielfeld = spielfeld;

        ArrayList<Position> positionen = new ArrayList<>();
        pruefeMoeglicheZuege(position, positionen);
        for (int i = 0; i < positionen.size(); i++) {
            if ((spielfeld.getFigurBei(positionen.get(i)) != null && spielfeld.getFigurBei(positionen.get(i)).getFarbe() == farbe) || setztKoenigSchach(positionen.get(i), position, spielfeld)) {
                positionen.remove(i);
                i--;
            }
        }
        return positionen;
    }

    private void pruefeMoeglicheZuege(Position position, ArrayList<Position> moeglicheZuege) {
        int xRichtung = -1;
        int yRichtung = -1;
        versucheHinzufuegenMoeglicherZuege(position, moeglicheZuege, xRichtung, yRichtung);

        yRichtung = 1;
        versucheHinzufuegenMoeglicherZuege(position, moeglicheZuege, xRichtung, yRichtung);

        xRichtung = 1;
        yRichtung = -1;
        versucheHinzufuegenMoeglicherZuege(position, moeglicheZuege, xRichtung, yRichtung);

        yRichtung = 1;
        versucheHinzufuegenMoeglicherZuege(position, moeglicheZuege, xRichtung, yRichtung);

        xRichtung = -1;
        yRichtung = 0;
        versucheHinzufuegenMoeglicherZuege(position, moeglicheZuege, xRichtung, yRichtung);

        xRichtung = 1;
        versucheHinzufuegenMoeglicherZuege(position, moeglicheZuege, xRichtung, yRichtung);

        xRichtung = 0;
        yRichtung = -1;
        versucheHinzufuegenMoeglicherZuege(position, moeglicheZuege, xRichtung, yRichtung);

        yRichtung = 1;
        versucheHinzufuegenMoeglicherZuege(position, moeglicheZuege, xRichtung, yRichtung);
    }

    private void versucheHinzufuegenMoeglicherZuege(Position position, ArrayList<Position> moeglicheZuege, int xRichtung, int yRichtung) {
        for (int i = 0; i < 8; i++) {
            Position ziel = position.verschobenUm(xRichtung * (i + 1), yRichtung * (i + 1));
            if (bewegenVerboten(position.verschobenUm(xRichtung * (i + 1), yRichtung * (i + 1))) || pruefeFigurTreffen(ziel, xRichtung, yRichtung)) {
                break;
            }
            moeglicheZuege.add(ziel);
        }
    }
    private boolean pruefeFigurTreffen(Position ziel, int xRichtung, int yRichtung) {
        if (spielfeld.getFigurBei(ziel.verschobenUm(-xRichtung, -yRichtung)) != null) {
            return spielfeld.getFigurBei(ziel.verschobenUm(-xRichtung, -yRichtung)) != this;
        } else {
            return false;
        }
    }
    @Override
    public boolean kontrolliertFeld(Position position, Position vergleich) {
        ArrayList<Position> kontrollierteFelder = new ArrayList<>();

        pruefeMoeglicheZuege(position, kontrollierteFelder);

        for (Position feld : kontrollierteFelder) {
            if (feld.equals(vergleich)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setztKoenigSchach(Position ziel, Position positionBewegendeFigur, Spielfeld spielfeld) {
        boolean ausgabe;
        Figur figurZiel = spielfeld.getFigurBei(ziel);
        spielfeld.bewegeFigur(ziel, positionBewegendeFigur, false);
        ausgabe = spielfeld.istImSchach(farbe);
        spielfeld.bewegeFigur(positionBewegendeFigur, ziel, false);
        spielfeld.setFigur(ziel, figurZiel);

        return ausgabe;
    }

    private boolean bewegenVerboten(Position ziel) {
        return !spielfeld.istImSpielfeld(ziel);
    }
}
