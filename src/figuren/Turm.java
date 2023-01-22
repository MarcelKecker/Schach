package figuren;

import enums.Farbe;
import main.*;

import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.util.Objects;

public class Turm extends Figur {
    private static final ImageIcon turmSchwarz = new ImageIcon(Objects.requireNonNull(Frame.class.getResource("/resources/TurmSchwarz.png")));
    private static final ImageIcon turmWeiss = new ImageIcon(Objects.requireNonNull(Frame.class.getResource("/resources/TurmWeiss.png")));
    private final Spielfeld spielfeld;

    private boolean wurdeBewegt;

    public Turm(Farbe farbe, Spielfeld spielfeld) {
        super(getRichtigesIcon(farbe), farbe, spielfeld);
        this.spielfeld = spielfeld;
    }

    private static ImageIcon getRichtigesIcon(Farbe farbe) {
        if (farbe == Farbe.SCHWARZ) {
            return turmSchwarz;
        } else {
            return turmWeiss;
        }
    }

    @Override
    public ArrayList<Position> getMoeglicheZielPositionen(Position position) {
        ArrayList<Position> positionen = new ArrayList<>();
        pruefeMoeglicheZuege(position, positionen);
        for (int i = 0; i < positionen.size(); i++) {
            if ((spielfeld.getFigurBei(positionen.get(i)) != null && spielfeld.getFigurBei(positionen.get(i)).getFarbe() == farbe) || spielfeld.setztKoenigSchach(positionen.get(i), position, farbe)) {
                positionen.remove(i);
                i--;
            }
        }
        return positionen;
    }

    private void pruefeMoeglicheZuege(Position position, ArrayList<Position> moeglicheZuege) {
        int xRichtung = -1;
        int yRichtung = 0;
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
            if (bewegenVerboten(ziel) || pruefeFigurTreffen(ziel, xRichtung, yRichtung)) {
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

    private boolean bewegenVerboten(Position ziel) {
        return !spielfeld.istImSpielfeld(ziel);
    }

    public void setWurdeBewegt(boolean wurdeBewegt) {
        this.wurdeBewegt = wurdeBewegt;
    }

    public boolean wurdeBewegt() {
        return wurdeBewegt;
    }
}