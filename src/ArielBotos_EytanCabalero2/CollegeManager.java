package ArielBotos_EytanCabalero2;

public class CollegeManager {
    private static final int ARRAY_GROWTH = 2;

    private String collegeName;
    private Lecturer[] lecturers;
    private Department[] departments;
    private Committee[] committees;
    private int lecturerCount;
    private int departmentCount;
    private int committeeCount;

    public CollegeManager(String collegeName) {
        this.collegeName = collegeName;
        this.lecturers = new Lecturer[2];
        this.departments = new Department[2];
        this.committees = new Committee[2];
        this.lecturerCount = 0;
        this.departmentCount = 0;
        this.committeeCount = 0;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public int getDepartmentCount() {
        return departmentCount;
    }

    public void addLecturer(Lecturer lecturer) {
        if (lecturerCount == lecturers.length) expandLecturers();
        lecturers[lecturerCount++] = lecturer;
    }

    public void addDepartment(Department department) {
        if (departmentCount == departments.length) expandDepartments();
        departments[departmentCount++] = department;
    }

    public void addCommittee(Committee committee) {
        if (committeeCount == committees.length) expandCommittees();
        committees[committeeCount++] = committee;
    }

    private void expandLecturers() {
        Lecturer[] newArray = new Lecturer[lecturers.length * ARRAY_GROWTH];
        System.arraycopy(lecturers, 0, newArray, 0, lecturers.length);
        lecturers = newArray;
    }

    private void expandDepartments() {
        Department[] newArray = new Department[departments.length * ARRAY_GROWTH];
        System.arraycopy(departments, 0, newArray, 0, departments.length);
        departments = newArray;
    }

    private void expandCommittees() {
        Committee[] newArray = new Committee[committees.length * ARRAY_GROWTH];
        System.arraycopy(committees, 0, newArray, 0, committees.length);
        committees = newArray;
    }

    public Lecturer findLecturerByName(String name) {
        String search = name.toLowerCase();
        for (int i = 0; i < lecturerCount; i++) {
            if (lecturers[i].name.equals(search)) {
                return lecturers[i];
            }
        }
        return null;
    }
    public Department findDepartmentByName(String name) {
        String search = name.toLowerCase();
        for (int i = 0; i < departmentCount; i++) {
            if (departments[i].getName().toLowerCase().equals(search)) {
                return departments[i];
            }
        }
        return null;
    }

    public Committee findCommitteeByName(String name) {
        String search = name.toLowerCase();
        for (int i = 0; i < committeeCount; i++) {
            if (committees[i].getName().toLowerCase().equals(search)) {
                return committees[i];
            }
        }
        return null;
    }

    public double averageCollegeSalary() {
        if (lecturerCount == 0) return 0;
        double sum = 0;
        for (int i = 0; i < lecturerCount; i++) {
            sum += lecturers[i].getSalary();
        }
        return sum / lecturerCount;
    }

    public double averageDepartmentSalary(String deptName) {
        Department d = findDepartmentByName(deptName);
        return (d == null) ? 0 : d.averageSalary();
    }

    public void printAllLecturers() {
        for (int i = 0; i < lecturerCount; i++) {
            System.out.println(lecturers[i]);
        }
    }

    public void printAllCommittees() {
        for (int i = 0; i < committeeCount; i++) {
            System.out.println(committees[i]);
        }
    }
}
