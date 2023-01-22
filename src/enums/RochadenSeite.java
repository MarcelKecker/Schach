package enums;

public enum RochadenSeite {
    LANG(-1), KURZ(1);

    private final int vorzeichenRichtung;

    RochadenSeite(int vorzeichenRichtung) {
        this.vorzeichenRichtung = vorzeichenRichtung;
    }

    public int getVorzeichenRichtung() {
        return vorzeichenRichtung;
    }
}
