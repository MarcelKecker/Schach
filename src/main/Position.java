package main;

public class Position {
    private final int x;
    private final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object vergleich) {

        if (vergleich instanceof Position) {
            Position positionVergleich = (Position) vergleich;
            return this.getX() == positionVergleich.getX() && this.getY() == positionVergleich.getY();
        } else {
            return false;
        }
    }
}
