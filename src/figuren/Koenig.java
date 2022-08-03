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
    public Koenig(Farbe farbe) {
        super(getRichtigesIcon(farbe), farbe);
    }

    private static ImageIcon getRichtigesIcon(Farbe farbe) {
        if (farbe == Farbe.SCHWARZ) {
            return koenigSchwarz;
        } else {
            return koenigWeiss;
        }
    }

    @Override
    public ArrayList<Position> getMoeglicheZielPositionen(Spielfeld spielfeld, Position position) {
        ArrayList<Position> positionen = new ArrayList<>();

        probiereBewegen(spielfeld, positionen, position.verschobenUm(1, 1));
        probiereBewegen(spielfeld, positionen, position.verschobenUm(1, 0));
        probiereBewegen(spielfeld, positionen, position.verschobenUm(1, -1));
        probiereBewegen(spielfeld, positionen, position.verschobenUm(0, 1));
        probiereBewegen(spielfeld, positionen, position.verschobenUm(0, -1));
        probiereBewegen(spielfeld, positionen, position.verschobenUm(-1, 1));
        probiereBewegen(spielfeld, positionen, position.verschobenUm(-1, 0));
        probiereBewegen(spielfeld, positionen, position.verschobenUm(-1, -1));

        return positionen;
    }

    @Override
    public boolean kontrolliertFeld(Position position, Position vergleich) {
        return position.verschobenUm(1, 1).equals(vergleich)
                || position.verschobenUm(1, 0).equals(vergleich)
                || position.verschobenUm(1, -1).equals(vergleich)
                || position.verschobenUm(0, 1).equals(vergleich)
                || position.verschobenUm(0, -1).equals(vergleich)
                || position.verschobenUm(-1, 1).equals(vergleich)
                || position.verschobenUm(-1, 0).equals(vergleich)
                || position.verschobenUm(-1, -1).equals(vergleich);
    }

    @Override
    public boolean setztKoenigSchach(Position ziel, Position positionBewegendeFigur, Spielfeld spielfeld) {
        return false;
    }

    private void probiereBewegen(Spielfeld spielfeld, ArrayList<Position> positionen, Position ziel) {
        if (spielfeld.istImSpielfeld(ziel) && !spielfeld.feldKontrolliert(this, ziel)) {
             if (spielfeld.getFigurBei(ziel) != null && spielfeld.getFigurBei(ziel).getFarbe() == farbe) {
                 return;
             }
            positionen.add(ziel);
        }
    }
}
