// Degree.java
package ArielBotos_EytanCabalero2;

public enum Degree {
    BA(false) ,
    MA(false),
    DR(true),
    PROF(true);

    private final boolean canBeChair;

    Degree(boolean canBeChair){
        this.canBeChair = canBeChair;
    }
    public boolean canBeChair(){
        return canBeChair;
    }

    public static boolean isValid(String s){
        if (s == null) return false;
        for (Degree d : values()){
            if  (d.name().equalsIgnoreCase(s.trim())){
                return true;
            }
        }
        return false;
    }
}