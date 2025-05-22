package ArielBotos_EytanCabalero2;

import java.util.Objects;

public class Lecturer implements Comparable {
    private static final int GROW = 2;

    private String name;
    private int id;
    private Degree degree;
    private String major;
    private double salary;
    private Department department;
    private Committee[] committees;
    private int committeeCount;

    public Lecturer(String name, int id, Degree degree, String major, double salary) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name empty");
        if (id <= 0)                  throw new IllegalArgumentException("ID must be >0");
        if (salary < 0)               throw new IllegalArgumentException("Salary <0");
        this.name = name.trim().toLowerCase();
        this.id   = id;
        this.degree = degree;
        this.major  = major.trim().toLowerCase();
        this.salary = salary;
        this.department = null;
        this.committees = new Committee[2];
        this.committeeCount = 0;
    }

    public String getName()     { return name; }
    public int    getId()       { return id; }
    public Degree getDegree()   { return degree; }
    public String getMajor()    { return major; }
    public double getSalary()   { return salary; }
    public Department getDepartment() { return department; }
    public Committee[] getCommittees() {
        Committee[] copy = new Committee[committeeCount];
        for (int i = 0; i < committeeCount; i++) copy[i] = committees[i];
        return copy;
    }
    public int getCommitteeCount() { return committeeCount; }

    public void setDepartment(Department d) { this.department = d; }
    public void removeDepartment()         { this.department = null; }

    public boolean addCommittee(Committee c) {
        if (c == null) return false;
        for (int i = 0; i < committeeCount; i++)
            if (committees[i].equals(c)) return false;
        if (committeeCount == committees.length) expandCommittees();
        committees[committeeCount++] = c;
        return true;
    }
    public boolean removeCommittee(Committee c) {
        for (int i = 0; i < committeeCount; i++) {
            if (committees[i].equals(c)) {
                // shift left
                for (int j = i; j < committeeCount - 1; j++)
                    committees[j] = committees[j + 1];
                committeeCount--;
                return true;
            }
        }
        return false;
    }
    private void expandCommittees() {
        Committee[] larger = new Committee[committees.length * GROW];
        for (int i = 0; i < committeeCount; i++) larger[i] = committees[i];
        committees = larger;
    }

    @Override
    public boolean equals(Object o) {
        return this == o ||
                (o instanceof Lecturer && id == ((Lecturer)o).id);
    }
    @Override public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        String dept = department == null ? "None" : capitalize(department.getName());
        StringBuilder sb = new StringBuilder();
        sb.append(capitalize(name))
                .append(" (ID ").append(id).append(") - ")
                .append(degree).append(" - ")
                .append(capitalize(major)).append(" - ₪")
                .append(String.format("%.2f", salary))
                .append(" - Dept: ").append(dept)
                .append(" - Committees:");
        if (committeeCount == 0) sb.append(" None");
        else {
            sb.append(" [");
            for (int i = 0; i < committeeCount; i++) {
                sb.append(capitalize(committees[i].getName()));
                if (i < committeeCount - 1) sb.append(", ");
            }
            sb.append("]");
        }
        return sb.toString();
    }

    @Override
    public int compareTo(Object o) {
        Lecturer other = (Lecturer)o;
        return Integer.compare(id, other.id);
    }

    private static String capitalize(String s) {
        if (s == null || s.isEmpty()) return s;
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }
}