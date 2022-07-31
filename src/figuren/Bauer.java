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

    public Bauer(Farbe farbe) {
        super(getRichtigesIcon(farbe), farbe);
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
        // TODO richtige Positionen zurückgeben
        positionen.add(new Position(3, 3));
        positionen.add(new Position(4, 3));
        return positionen;
    }
}
