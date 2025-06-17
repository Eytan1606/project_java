// Degree.java
package ArielBotos_EytanCabalero;

import java.io.Serializable;

public enum Degree implements Serializable {
    BA(false) ,
    MA(false),
    DR(true),
    PROF(true);

    // פה אני פותח נתיב לשמור קובץ
    private static final long serialVersionUID = 1L;

    private final boolean canBeChair;
    private String name;

    Degree(boolean canBeChair){
        this.canBeChair = canBeChair;
    }
    public boolean canBeChair(){
        return canBeChair;
    }

}