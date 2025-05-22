// College.java
package ArielBotos_EytanCabalero2;

public class College {
    private static final int GROW = 2;

    private String collegeName;
    private Lecturer[]   lecturers;
    private int          lecturerCount;
    private Department[] departments;
    private int          departmentCount;
    private Committee[]  committees;
    private int          committeeCount;

    public College(String name) {
        this.collegeName = name.trim();
        this.lecturers   = new Lecturer[2];
        this.lecturerCount = 0;
        this.departments = new Department[2];
        this.departmentCount = 0;
        this.committees  = new Committee[2];
        this.committeeCount = 0;
    }

    // --- Lecturers ---
    public boolean addLecturer(Lecturer l) {
        if (l == null) return false;
        for (Lecturer ex : getLecturers())
            if (ex.equals(l)) return false;
        if (lecturerCount == lecturers.length) {
            Lecturer[] large = new Lecturer[lecturers.length * GROW];
            for (int i = 0; i < lecturerCount; i++) large[i] = lecturers[i];
            lecturers = large;
        }
        lecturers[lecturerCount++] = l;
        return true;
    }

    public boolean removeLecturer(String name) {
        for (int i = 0; i < lecturerCount; i++) {
            if (lecturers[i].getName().equalsIgnoreCase(name)) {
                for (int j = i; j < lecturerCount - 1; j++)
                    lecturers[j] = lecturers[j + 1];
                lecturerCount--;
                return true;
            }
        }
        return false;
    }

    public Lecturer findLecturerByName(String name) {
        for (Lecturer l : getLecturers())
            if (l.getName().equalsIgnoreCase(name))
                return l;
        return null;
    }

    public Lecturer[] getLecturers() {
        Lecturer[] copy = new Lecturer[lecturerCount];
        for (int i = 0; i < lecturerCount; i++) copy[i] = lecturers[i];
        return copy;
    }

    public int getLecturerCount() { return lecturerCount; }

    public double getAverageSalaryAllLecturers() {
        if (lecturerCount == 0) return 0;
        double sum = 0;
        for (Lecturer l : getLecturers()) sum += l.getSalary();
        return sum / lecturerCount;
    }

    // --- Departments ---
    public boolean addDepartment(Department d) {
        if (d == null) return false;
        for (Department ex : getDepartments())
            if (ex.equals(d)) return false;
        if (departmentCount == departments.length) {
            Department[] large = new Department[departments.length * GROW];
            for (int i = 0; i < departmentCount; i++) large[i] = departments[i];
            departments = large;
        }
        departments[departmentCount++] = d;
        return true;
    }

    public boolean removeDepartment(String name) {
        for (int i = 0; i < departmentCount; i++) {
            if (departments[i].getName().equalsIgnoreCase(name)) {
                for (int j = i; j < departmentCount - 1; j++)
                    departments[j] = departments[j + 1];
                departmentCount--;
                return true;
            }
        }
        return false;
    }

    public Department findDepartmentByName(String name) {
        for (Department d : getDepartments())
            if (d.getName().equalsIgnoreCase(name))
                return d;
        return null;
    }

    public Department[] getDepartments() {
        Department[] copy = new Department[departmentCount];
        for (int i = 0; i < departmentCount; i++) copy[i] = departments[i];
        return copy;
    }

    public int getDepartmentCount() { return departmentCount; }

    public double getAverageSalaryByDepartment(String name) {
        Department d = findDepartmentByName(name);
        if (d == null || d.getLecturerCount() == 0) return 0;
        double sum = 0;
        for (Lecturer l : d.getLecturers()) sum += l.getSalary();
        return sum / d.getLecturerCount();
    }

    // --- Committees ---
    public boolean addCommittee(Committee c) {
        if (c == null) return false;
        for (Committee ex : getCommittees())
            if (ex.equals(c)) return false;
        if (committeeCount == committees.length) {
            Committee[] large = new Committee[committees.length * GROW];
            for (int i = 0; i < committeeCount; i++) large[i] = committees[i];
            committees = large;
        }
        committees[committeeCount++] = c;
        return true;
    }

    public boolean removeCommittee(String name) {
        for (int i = 0; i < committeeCount; i++) {
            if (committees[i].getName().equalsIgnoreCase(name)) {
                for (int j = i; j < committeeCount - 1; j++)
                    committees[j] = committees[j + 1];
                committeeCount--;
                return true;
            }
        }
        return false;
    }

    public Committee findCommitteeByName(String name) {
        for (Committee c : getCommittees())
            if (c.getName().equalsIgnoreCase(name))
                return c;
        return null;
    }

    public Committee[] getCommittees() {
        Committee[] copy = new Committee[committeeCount];
        for (int i = 0; i < committeeCount; i++) copy[i] = committees[i];
        return copy;
    }

    public int getCommitteeCount() { return committeeCount; }

    // --- Advanced operations (compare & clone) ---
    public String compareByArticles(String n1, String n2) {
        Lecturer l1 = findLecturerByName(n1), l2 = findLecturerByName(n2);
        if (!(l1 instanceof ResearchLecturer) || !(l2 instanceof ResearchLecturer))
            return "Both must be DR or PROF.";
        int a1 = ((ResearchLecturer)l1).getArticleCount();
        int a2 = ((ResearchLecturer)l2).getArticleCount();
        return String.format("%s has %d; %s has %d", l1.getName(), a1, l2.getName(), a2);
    }

    public String compareDepartments(String d1, String d2, int crit) {
        Department dept1 = findDepartmentByName(d1), dept2 = findDepartmentByName(d2);
        if (dept1 == null || dept2 == null) return "Dept not found.";
        int v1 = (crit == 1)
                ? dept1.getLecturerCount()
                : sumArticles(dept1);
        int v2 = (crit == 1)
                ? dept2.getLecturerCount()
                : sumArticles(dept2);
        return String.format("%s: %d vs %s: %d", dept1.getName(), v1, dept2.getName(), v2);
    }

    private int sumArticles(Department d) {
        int sum = 0;
        for (Lecturer l : d.getLecturers())
            if (l instanceof ResearchLecturer)
                sum += ((ResearchLecturer)l).getArticleCount();
        return sum;
    }

    public boolean cloneCommittee(String origName) {
        Committee o = findCommitteeByName(origName);
        if (o == null) return false;
        Committee copy = new Committee(origName + "-new", o.getChair());
        for (Lecturer m : o.getMembers()) copy.addMember(m);
        return addCommittee(copy);
    }

    }