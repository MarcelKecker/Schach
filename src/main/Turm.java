package main;

public class Turm extends Figur {
    public Turm(Position position) {
        this.position = position;
    }

    Position position;
    public boolean istTurm(Position position) {
        return this.position.equals(position);
    }
}
