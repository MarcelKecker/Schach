package enums;

public enum Farbe {
    SCHWARZ, WEISS;

    public Farbe getAndereFarbe() {
        if (this == SCHWARZ) {
            return WEISS;
        } else {
            return SCHWARZ;
        }
    }
}
