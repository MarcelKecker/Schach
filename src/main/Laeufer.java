package main;

public class Laeufer extends Figur {
    public Laeufer(Position position) {
        this.position = position;
    }

    Position position;
    public boolean istLaeufer(Position position) {
        return this.position.equals(position);
    }
}
