package figuren;

import enums.Farbe;
import main.Figur;
import main.Frame;
import main.Position;
import main.Spielfeld;

import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.util.Objects;

public class Springer extends Figur {
    private static final ImageIcon springerSchwarz = new ImageIcon(Objects.requireNonNull(Frame.class.getResource("/resources/PferdSchwarz.png")));
    private static final ImageIcon springerWeiss = new ImageIcon(Objects.requireNonNull(Frame.class.getResource("/resources/PferdWeiss.png")));
    private final Spielfeld spielfeld;

    public Springer(Farbe farbe, Spielfeld spielfeld) {
        super(getRichtigesIcon(farbe), farbe);
        this.spielfeld = spielfeld;
        getMoeglicheZielPositionen(new Position(0, 0));
    }

    private static ImageIcon getRichtigesIcon(Farbe farbe) {
        if (farbe == Farbe.SCHWARZ) {
            return springerSchwarz;
        } else {
            return springerWeiss;
        }
    }
    private void pruefeMoeglicheZuege(Position position, ArrayList<Position> moeglicheZuege) {
        versucheHinzufuegenMoeglicherZuege(moeglicheZuege, position.verschobenUm(-2, 1));
        versucheHinzufuegenMoeglicherZuege(moeglicheZuege, position.verschobenUm(-2, 1));
        versucheHinzufuegenMoeglicherZuege(moeglicheZuege, position.verschobenUm(2, 1));
        versucheHinzufuegenMoeglicherZuege(moeglicheZuege, position.verschobenUm(2, -1));
        versucheHinzufuegenMoeglicherZuege(moeglicheZuege, position.verschobenUm(1, 2));
        versucheHinzufuegenMoeglicherZuege(moeglicheZuege, position.verschobenUm(-1, 2));
        versucheHinzufuegenMoeglicherZuege(moeglicheZuege, position.verschobenUm(1, -2));
        versucheHinzufuegenMoeglicherZuege(moeglicheZuege, position.verschobenUm(-1, -2));
    }
    @Override
    public ArrayList<Position> getMoeglicheZielPositionen(Position position) {
        ArrayList<Position> positionen = new ArrayList<>();
        pruefeMoeglicheZuege(position, positionen);
        for (int i = 0; i < positionen.size(); i++) {
            if ((spielfeld.getFigurBei(positionen.get(i)) != null && spielfeld.getFigurBei(positionen.get(i)).getFarbe() == farbe) || setztKoenigSchach(positionen.get(i), position)) {
                positionen.remove(i);
                i--;
            }
        }
        return positionen;
    }

    private void versucheHinzufuegenMoeglicherZuege(ArrayList<Position> moeglicheZuege, Position ziel) {
        if (!bewegenVerboten(ziel)) {
            moeglicheZuege.add(ziel);
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
    public boolean setztKoenigSchach(Position ziel, Position positionBewegendeFigur) {
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