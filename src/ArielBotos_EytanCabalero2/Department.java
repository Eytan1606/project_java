// Department.java
package ArielBotos_EytanCabalero2;

import java.util.Objects;

public class Department {
    private String name;
    private int numStudents;
    private Lecturer[] lecturers;
    private int lecturerCount;
    private static final int INITIAL_SIZE = 4;

    public Department(String name, int numStudents) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Department name cannot be empty");
        if (numStudents < 0)
            throw new IllegalArgumentException("Number of students cannot be negative");

        this.name = name.trim();
        this.numStudents = numStudents;
        this.lecturers = new Lecturer[INITIAL_SIZE];
        this.lecturerCount = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Department name cannot be empty");
        this.name = name.trim();
    }

    public int getNumStudents() {
        return numStudents;
    }

    public void setNumStudents(int numStudents) {
        if (numStudents < 0)
            throw new IllegalArgumentException("Number of students cannot be negative");
        this.numStudents = numStudents;
    }


    public Lecturer[] getLecturers() {
        if (lecturerCount == 0) {
            return new Lecturer[0];
        }
        Lecturer[] copy = new Lecturer[lecturerCount];
        System.arraycopy(lecturers, 0, copy, 0, lecturerCount);
        return copy;
    }


    public boolean addLecturer(Lecturer l) {
        if (l == null) {
            throw new IllegalArgumentException("Lecturer cannot be null");
        }
        for (int i = 0; i < lecturerCount; i++) {
            if (lecturers[i].equals(l)) {
                return false;
            }
        }
        if (lecturerCount == lecturers.length) {
            Lecturer[] temp = new Lecturer[lecturers.length * 2];
            System.arraycopy(lecturers, 0, temp, 0, lecturers.length);
            lecturers = temp;
        }
        lecturers[lecturerCount++] = l;
        l.setDepartment(this);
        return true;
    }

    public boolean removeLecturer(Lecturer l) {
        if (l == null) {
            throw new IllegalArgumentException("Lecturer cannot be null");
        }
        int idx = -1;
        for (int i = 0; i < lecturerCount; i++) {
            if (lecturers[i].equals(l)) {
                idx = i;
                break;
            }
        }
        if (idx < 0) {
            return false;
        }
        Lecturer removed = lecturers[idx];
        System.arraycopy(lecturers, idx + 1, lecturers, idx, lecturerCount - idx - 1);
        lecturers[--lecturerCount] = null;
        removed.setDepartment(null);
        return true;
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
        return String.format("Department: %s (Students:%d)  LecturersCount:%d",
                name, numStudents, lecturerCount);
    }

    public int getLecturerCount() {
        return lecturerCount;
    }
}
