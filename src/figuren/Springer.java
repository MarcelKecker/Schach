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
    public Springer(Farbe farbe) {
        super(getRichtigesIcon(farbe), farbe);
    }

    private static ImageIcon getRichtigesIcon(Farbe farbe) {
        if (farbe == Farbe.SCHWARZ) {
            return springerSchwarz;
        } else {
            return springerWeiss;
        }
    }

    @Override
    public ArrayList<Position> getMoeglicheZielPositionen(Spielfeld spielfeld, Position position) {

        ArrayList<Position> positionen = new ArrayList<>();
        // TODO richtige Positionen zurückgeben

        probiereBewegen(spielfeld, positionen, position.verschobenUm(-2, 1));
        probiereBewegen(spielfeld, positionen, position.verschobenUm(-2, -1));
        probiereBewegen(spielfeld, positionen, position.verschobenUm(2, 1));
        probiereBewegen(spielfeld, positionen, position.verschobenUm(2, -1));
        probiereBewegen(spielfeld, positionen, position.verschobenUm(1, 2));
        probiereBewegen(spielfeld, positionen, position.verschobenUm(-1, 2));
        probiereBewegen(spielfeld, positionen, position.verschobenUm(1, -2));
        probiereBewegen(spielfeld, positionen, position.verschobenUm(-1, -2));

        return positionen;
    }
    @Override
    public boolean kontrolliertFeld(Position position, Position vergleich) {
        return position.verschobenUm(-2, 1).equals(vergleich)
                || position.verschobenUm(-2, -1).equals(vergleich)
                || position.verschobenUm(2, 1).equals(vergleich)
                || position.verschobenUm(2, -1).equals(vergleich)
                || position.verschobenUm(1, 2).equals(vergleich)
                || position.verschobenUm(-1, 2).equals(vergleich)
                || position.verschobenUm(1, -2).equals(vergleich)
                || position.verschobenUm(-1, -2).equals(vergleich);
    }

    private void probiereBewegen(Spielfeld spielfeld, ArrayList<Position> positionen, Position ziel) {
        System.out.println(spielfeld.feldKontrolliert(this, ziel));
        if (spielfeld.istImSpielfeld(ziel)) {
            if (spielfeld.getFigurBei(ziel) != null && spielfeld.getFigurBei(ziel).getFarbe() == farbe) {
                return;
            }
            positionen.add(ziel);
        }
    }
}