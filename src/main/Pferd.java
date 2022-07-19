package main;

public class Pferd extends Figur {
    public Pferd(Position position) {
        this.position = position;
    }

    Position position;
    public boolean istPferd(Position position) {
        return this.position.equals(position);
    }
}
