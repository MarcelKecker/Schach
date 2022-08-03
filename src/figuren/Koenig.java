package figuren;

import enums.Farbe;
import main.Figur;
import main.Frame;
import main.Position;
import main.Spielfeld;

import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.util.Objects;

public class Koenig extends Figur {
    private static final ImageIcon koenigSchwarz = new ImageIcon(Objects.requireNonNull(Frame.class.getResource("/resources/KoenigSchwarz.png")));
    private static final ImageIcon koenigWeiss = new ImageIcon(Objects.requireNonNull(Frame.class.getResource("/resources/KoenigWeiss.png")));
    private final Spielfeld spielfeld;
    public Koenig(Farbe farbe, Spielfeld spielfeld) {
        super(getRichtigesIcon(farbe), farbe);
        this.spielfeld = spielfeld;
        getMoeglicheZielPositionen(new Position(0, 0));
    }

    private static ImageIcon getRichtigesIcon(Farbe farbe) {
        if (farbe == Farbe.SCHWARZ) {
            return koenigSchwarz;
        } else {
            return koenigWeiss;
        }
    }

    private void pruefeMoeglicheZuege(Position position, ArrayList<Position> moeglicheZuege) {
        versucheHinzufuegenMoeglicherZuege(moeglicheZuege, position.verschobenUm(1, 1));
        versucheHinzufuegenMoeglicherZuege(moeglicheZuege, position.verschobenUm(1, 0));
        versucheHinzufuegenMoeglicherZuege(moeglicheZuege, position.verschobenUm(1, -1));
        versucheHinzufuegenMoeglicherZuege(moeglicheZuege, position.verschobenUm(0, 1));
        versucheHinzufuegenMoeglicherZuege(moeglicheZuege, position.verschobenUm(0, -1));
        versucheHinzufuegenMoeglicherZuege(moeglicheZuege, position.verschobenUm(-1, 1));
        versucheHinzufuegenMoeglicherZuege(moeglicheZuege, position.verschobenUm(-1, 0));
        versucheHinzufuegenMoeglicherZuege(moeglicheZuege, position.verschobenUm(-1, -1));
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

