// Professor.java
package ArielBotos_EytanCabalero2;

public class Professor extends ResearchLecturer {
    private String grantingBody;

    public Professor(String name, int id, Degree deg, String major, double salary, String body) {
        super(name, id, deg, major, salary);
        setGrantingBody(body);
    }

    public void setGrantingBody(String b) {
        if (b == null || b.isBlank())
            throw new IllegalArgumentException("Granting body empty");
        this.grantingBody = b.trim();
    }

    public String getGrantingBody() { return grantingBody; }

    @Override
    public String toString() {
        return super.toString() + " - Granted by: " + grantingBody;
    }
}