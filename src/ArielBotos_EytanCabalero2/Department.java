// Department.java
package ArielBotos_EytanCabalero2;

import java.util.Objects;

public class Department {
    private static final int GROW = 2;

    private String name;
    private int numStudents;
    private Lecturer[] lecturers;
    private int lecturerCount;


    public Department(String name, int numStudents) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Dept name empty");
        if (numStudents < 0)            throw new IllegalArgumentException("Students <0");
        this.name = name.trim().toLowerCase();
        this.numStudents = numStudents;
        this.lecturers = new Lecturer[2];
        this.lecturerCount = 0;
    }

    public String getName() { return name; }
    public int getNumStudents() { return numStudents; }

    public boolean addLecturer(Lecturer l) {
        if (l == null) return false;
        for (int i = 0; i < lecturerCount; i++)
            if (lecturers[i].equals(l)) return false;
        if (lecturerCount == lecturers.length) {
            Lecturer[] larger = new Lecturer[lecturers.length * GROW];
            for (int i = 0; i < lecturerCount; i++) larger[i] = lecturers[i];
            lecturers = larger;
        }
        lecturers[lecturerCount++] = l;
        l.setDepartment(this);
        return true;
    }


    public boolean removeLecturer(Lecturer l) {
        for (int i = 0; i < lecturerCount; i++) {
            if (lecturers[i].equals(l)) {
                for (int j = i; j < lecturerCount - 1; j++)
                    lecturers[j] = lecturers[j + 1];
                lecturerCount--;
                l.removeDepartment();
                return true;
            }
        }
        return false;
    }

    public Lecturer[] getLecturers() {
        Lecturer[] copy = new Lecturer[lecturerCount];
        for (int i = 0; i < lecturerCount; i++) copy[i] = lecturers[i];
        return copy;
    }

    public int getLecturerCount() { return lecturerCount; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Department)) return false;
        Department d = (Department) o;
        return name.equals(d.name);
    }

    @Override public int hashCode() { return Objects.hash(name); }

    @Override
    public String toString() {
        return capitalize(name)
                + " (Students: " + numStudents
                + ", Lecturers: " + lecturerCount
                + ")";
    }

    private static String capitalize(String s) {
        if (s == null || s.isEmpty()) return s;
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

    public void addMember(Lecturer lec) {
        if (lecturerCount == lecturers.length) {
            Lecturer[] newArr = new Lecturer[lecturers.length * 2];
            for (int i = 0; i < lecturers.length; i++) {
                newArr[i] = lecturers[i];
            }
            lecturers = newArr;
        }
        lecturers[lecturerCount++] = lec;
    }
    public boolean removeMember(Lecturer lec) {
        for (int i = 0; i < lecturerCount; i++) {
            if (lecturers[i].equals(lec)) {
                // shift left
                for (int j = i; j < lecturerCount - 1; j++) {
                    lecturers[j] = lecturers[j + 1];
                }
                lecturers[--lecturerCount] = null;
                return true;
            }
        }
        return false;
    }
}