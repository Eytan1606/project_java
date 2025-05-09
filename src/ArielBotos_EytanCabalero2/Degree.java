// Degree.java
package ArielBotos_EytanCabalero2;

public enum Degree {
    BA, MA, DR, PROF;

    public boolean canBeChair() {
        return this == DR || this == PROF;
    }

    public static boolean isValid(String s) {
        for (Degree d : values())
            if (d.name().equalsIgnoreCase(s)) return true;
        return false;
    }
}
