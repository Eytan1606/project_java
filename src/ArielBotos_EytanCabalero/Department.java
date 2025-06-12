// Department.java
package ArielBotos_EytanCabalero;

import java.util.Objects;

public class Department {
    private String name;
    private int numStudents;
    private CustomArray<Lecturer> lecturers = new CustomArray<>();


    public Department(String name, int numStudents) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Department name cannot be empty");
        if (numStudents < 0)
            throw new IllegalArgumentException("Number of students cannot be negative");

        this.name = name.trim();
        this.numStudents = numStudents;
    }

    public String getName() {
        return name;
    }


    public void setNumStudents(int numStudents) {
        if (numStudents < 0)
            throw new IllegalArgumentException("Number of students cannot be negative");
        this.numStudents = numStudents;
    }


    public Lecturer[] getLecturers() {
        return lecturers.toArray(new Lecturer[0]);
    }


    public boolean addLecturer(Lecturer l) {
        if (!lecturers.contains(l)) {
            lecturers.add(l);
            return true;
        }
        return false;
    }

    public boolean removeLecturer(Lecturer l) {
        if (l == null) {
            throw new IllegalArgumentException("Lecturer cannot be null");
        }

        boolean removed = lecturers.remove(l);
        if (removed){
            l.setDepartment(null);
        }
        return removed;

    }
    public int getLecturerCount() {
        return lecturers.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Department)) return false;
        Department other = (Department) o;
        return this.name.equalsIgnoreCase(other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name.toLowerCase());
    }

    @Override
    public String toString() {
        return String.format(
                "Department: %s (Students: %d) Lecturers: %d",
                name, numStudents, getLecturerCount()
        );
    }

}
