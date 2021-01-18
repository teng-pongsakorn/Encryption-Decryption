package encryptdecrypt;

enum Mode {
    ENC(1), DEC(-1);

    private int direction;

    // constructor
    Mode(int direction) {
        this.direction = direction;
    }

    public int getValue() {
        return this.direction;
    }
}
