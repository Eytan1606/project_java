// Degree.java
package ArielBotos_EytanCabalero;

public enum Degree {
    BA(false) ,
    MA(false),
    DR(true),
    PROF(true);

    private final boolean canBeChair;
    private String name;

    Degree(boolean canBeChair){
        this.canBeChair = canBeChair;
    }
    public boolean canBeChair(){
        return canBeChair;
    }

    public String getName() {
        return name;
    }

}