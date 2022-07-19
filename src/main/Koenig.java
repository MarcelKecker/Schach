package main;

public class Koenig extends Figur {
    public Koenig(Position position) {
        this.position = position;
    }

    Position position;
    public boolean istKoenig(Position position) {
        return this.position.equals(position);
    }
}
