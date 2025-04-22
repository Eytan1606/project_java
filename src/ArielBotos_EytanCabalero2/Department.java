package ArielBotos_EytanCabalero2;

public class Department {
    private static final int ARRAY_GROWTH = 2;

    private String name;
    private int studentCount;
    private Lecturer[] lecturers;
    private int lecturerCount;

    public Department(String name, int studentCount) {
        this.name = name.toLowerCase();
        this.studentCount = studentCount;
        this.lecturers = new Lecturer[2];
        this.lecturerCount = 0;
    }

    public String getName() {
        return capitalize(name);
    }

    public int getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(int studentCount) {
        this.studentCount = studentCount;
    }

    public Lecturer[] getLecturers() {
        return lecturers;
    }

    public int getLecturerCount() {
        return lecturerCount;
    }

    public void addLecturer(Lecturer lecturer) {
        if (!isInDepartment(lecturer)) {
            if (lecturerCount == lecturers.length) expandLecturers();
            lecturers[lecturerCount++] = lecturer;
        }
    }

    public void removeLecturer(Lecturer lecturer) {
        for (int i = 0; i < lecturerCount; i++) {
            if (lecturers[i] == lecturer) {
                lecturers[i] = lecturers[--lecturerCount];
                lecturers[lecturerCount] = null;
                break;
            }
        }
    }

    private boolean isInDepartment(Lecturer lecturer) {
        for (int i = 0; i < lecturerCount; i++) {
            if (lecturers[i] == lecturer) return true;
        }
        return false;
    }

    private void expandLecturers() {
        Lecturer[] newArray = new Lecturer[lecturers.length * ARRAY_GROWTH];
        System.arraycopy(lecturers, 0, newArray, 0, lecturers.length);
        lecturers = newArray;
    }

    public double averageSalary() {
        if (lecturerCount == 0) return 0;
        double sum = 0;
        for (int i = 0; i < lecturerCount; i++) {
            sum += lecturers[i].getSalary();
        }
        return sum / lecturerCount;
    }

    @Override
    public String toString() {
        return "Department: " + getName() + ", Students: " + studentCount + ", Lecturers: " + lecturerCount;
    }

    private String capitalize(String input) {
        if (input == null || input.isEmpty()) return input;
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
}
