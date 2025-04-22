package ArielBotos_EytanCabalero2;

public enum Degree {
    BA,
    MA,
    DR,
    PROF;

    public boolean canBeChair() {
        return this == DR || this == PROF;
    }
}
