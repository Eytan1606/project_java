
package ArielBotos_EytanCabalero;

import java.io.Serializable;

public class Professor extends Doctor implements Serializable {
    private String grantingBody;

    private static final long serialVersionUID = 1L;

    public Professor(String name, int id, Degree degree, String major, double salary, String grantingBody) {
        super(name, id, degree, major, salary);
        setGrantingBody(grantingBody);
    }


    public void setGrantingBody(String grantingBody) {
        if (grantingBody == null || grantingBody.trim().isEmpty())
            throw new IllegalArgumentException("Granting body cannot be empty.");
        this.grantingBody = grantingBody.trim();
    }

    @Override
    public String toString() {
        return String.format("%s , Granted by: %s", super.toString(), grantingBody);
    }
}