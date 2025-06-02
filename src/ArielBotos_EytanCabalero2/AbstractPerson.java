package ArielBotos_EytanCabalero2;

import java.util.Objects;



public abstract class AbstractPerson implements IEmployable {
    private String name;
    private final int id;
    private Degree degree;
    private String major;
    private double salary;

    protected AbstractPerson(String name, int id, Degree degree, String major , double salary ){
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Name cannot be empty");
        if (id <=0)
            throw new IllegalArgumentException("ID must be positive");
        if (degree == null)
            throw new IllegalArgumentException("Degree cannot be null");
        if (major == null || major.trim().isEmpty())
            throw new IllegalArgumentException("Major cannot be empty");
        if (salary < 0)
            throw new IllegalArgumentException("Salary cannot be negative");

        this.name = capitalize(name.trim());
        this.id = id;
        this.degree = degree;
        this.major = major;
        this.salary = salary;

    }
    @Override
    public String getName(){
        return name;
    }
    protected void setName(String name){
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Name cannot be empty");
        this.name = capitalize(name.trim());
    }
    @Override
    public int getId(){return id;}

    public Degree getDegree(){return degree;}

    public void setDegree(Degree degree){
        if (degree == null)
            throw new IllegalArgumentException("Degree cannot be null");
        this.degree =degree;
    }
    public String getMajor(){return major;}

    public void setMajor(String major){
        if (major == null || major.trim().isEmpty())
            throw new IllegalArgumentException("Major cannot be empty");
        this.major = major.trim();
    }
    @Override
    public double getSalary(){
        return salary;
    }
    public void setSalary(double salary){
        if (salary < 0)
            throw new IllegalArgumentException("Salary has to be positive");
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof AbstractPerson)) return false;
        AbstractPerson other = (AbstractPerson) o;
        return this.id == other.id;
    }
    @Override
     public int hashCode(){
        return Objects.hash(id);
    }
    @Override
    public String toString(){
        return String.format("%s (ID:%d), %s, Major:%s, Salary:₪%.2f",
                name, id, degree, major, salary);
    }
    private static String capitalize(String s){
        if (s == null || s.isEmpty()) return s;
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }
}
