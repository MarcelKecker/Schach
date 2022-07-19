package main;

public class Bauer extends Figur {
    public Bauer(Position position) {
        this.position = position;
    }

    Position position;
    public boolean istBauer(Position position) {
        return this.position.equals(position);
    }
}
