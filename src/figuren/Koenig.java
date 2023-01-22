package figuren;

import enums.Farbe;
import enums.RochadenSeite;
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
    private boolean wurdeBewegt;
    public Koenig(Farbe farbe, Spielfeld spielfeld) {
        super(getRichtigesIcon(farbe), farbe, spielfeld);
        this.spielfeld = spielfeld;
    }

    private static ImageIcon getRichtigesIcon(Farbe farbe) {
        if (farbe == Farbe.SCHWARZ) {
            return koenigSchwarz;
        } else {
            return koenigWeiss;
        }
    }

    private void pruefeEinfacheZuege(Position position, ArrayList<Position> moeglicheZuege) {
        versucheHinzufuegenMoeglicherZuege(moeglicheZuege, position.verschobenUm(1, 1));
        versucheHinzufuegenMoeglicherZuege(moeglicheZuege, position.verschobenUm(1, 0));
        versucheHinzufuegenMoeglicherZuege(moeglicheZuege, position.verschobenUm(1, -1));
        versucheHinzufuegenMoeglicherZuege(moeglicheZuege, position.verschobenUm(0, 1));
        versucheHinzufuegenMoeglicherZuege(moeglicheZuege, position.verschobenUm(0, -1));
        versucheHinzufuegenMoeglicherZuege(moeglicheZuege, position.verschobenUm(-1, 1));
        versucheHinzufuegenMoeglicherZuege(moeglicheZuege, position.verschobenUm(-1, 0));
        versucheHinzufuegenMoeglicherZuege(moeglicheZuege, position.verschobenUm(-1, -1));
    }

    private void pruefeMoeglicheZuege(Position position, ArrayList<Position> moeglicheZuege) {
        pruefeEinfacheZuege(position, moeglicheZuege);
        if (spielfeld.istImSchach(farbe)) {
            return;
        }
        if (spielfeld.rochadeMoeglich(farbe, RochadenSeite.KURZ)) {
            versucheHinzufuegenMoeglicherZuege(moeglicheZuege, position.verschobenUm(2, 0));
        }
        if (spielfeld.rochadeMoeglich(farbe, RochadenSeite.LANG)) {
            versucheHinzufuegenMoeglicherZuege(moeglicheZuege, position.verschobenUm(-2, 0));
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

    private void versucheHinzufuegenMoeglicherZuege(ArrayList<Position> moeglicheZuege, Position ziel) {
        if (!bewegenVerboten(ziel)) {
            moeglicheZuege.add(ziel);
        }
    }
    @Override
    public boolean kontrolliertFeld(Position position, Position vergleich) {
        ArrayList<Position> kontrollierteFelder = new ArrayList<>();

        pruefeEinfacheZuege(position, kontrollierteFelder);

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

