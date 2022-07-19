package main;

public class Dame extends Figur {
    public Dame(Position position) {
        this.position = position;
    }

    Position position;
    public boolean istDame(Position position) {
        return this.position.equals(position);
    }
}
