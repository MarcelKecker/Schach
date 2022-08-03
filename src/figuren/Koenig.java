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
//        int richtung = switch (farbe) {
//            case WEISS -> -1;
//            case SCHWARZ -> 1;
//        };
//        Position einsEntfernt = position.verschobenUm(0, richtung);
//        Position zweiEntfernt = position.verschobenUm(0, richtung * 2);
//        if (spielfeld.getFigurBei(einsEntfernt) == null) {
//            positionen.add(einsEntfernt);
//            if (position.getY() == startPosition && spielfeld.getFigurBei(zweiEntfernt) == null) {
//                positionen.add(zweiEntfernt);
//            }
//        }
//        probiereSchlagen(spielfeld, positionen, position.verschobenUm(-1, richtung));
//        probiereSchlagen(spielfeld, positionen, position.verschobenUm(1, richtung));

        ArrayList<Position> positionen = new ArrayList<>();
        // TODO richtige Positionen zurückgeben

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

    private void probiereBewegen(Spielfeld spielfeld, ArrayList<Position> positionen, Position ziel) {
        System.out.println(spielfeld.feldKontrolliert(this, ziel));
        if (spielfeld.istImSpielfeld(ziel) && !spielfeld.feldKontrolliert(this, ziel)) {
             if (spielfeld.getFigurBei(ziel) != null && spielfeld.getFigurBei(ziel).getFarbe() == farbe) {
                 return;
             }
            positionen.add(ziel);
        }
    }
}
