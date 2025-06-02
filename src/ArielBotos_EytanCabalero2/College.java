package ArielBotos_EytanCabalero2;

import java.util.Objects;

public class College {
    private static final int GROW = 2;

    private String collegeName;


    private Lecturer[] lecturers;
    private int lecturerCount;


    private Department[] departments;
    private int departmentCount;


    private Committee[] committees;
    private int committeeCount;

    public College(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("College name cannot be empty");
        }
        this.collegeName    = name.trim();
        this.lecturers      = new Lecturer[2];
        this.lecturerCount  = 0;
        this.departments    = new Department[2];
        this.departmentCount = 0;
        this.committees     = new Committee[2];
        this.committeeCount = 0;
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
            Lecturer[] temp = new Lecturer[lecturers.length * GROW];
            System.arraycopy(lecturers, 0, temp, 0, lecturers.length);
            lecturers = temp;
        }
        lecturers[lecturerCount++] = l;
        return true;
    }

    public Lecturer findLecturerByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }
        String key = name.trim();
        for (int i = 0; i < lecturerCount; i++) {
            if (lecturers[i].getName().equalsIgnoreCase(key)) {
                return lecturers[i];
            }
        }
        return null;
    }

    public boolean removeLecturer(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Lecturer name cannot be empty");
        }
        Lecturer toRemove = findLecturerByName(name);
        if (toRemove == null) {
            return false;
        }
        return removeLecturer(toRemove);
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

        l.removeFromDepartment();

        Committee[] joined = l.getCommittees();
        for (int i = 0; i < joined.length; i++) {
            l.removeFromCommittee(joined[i]);
        }

        System.arraycopy(lecturers, idx + 1, lecturers, idx, lecturerCount - idx - 1);
        lecturers[--lecturerCount] = null;
        return true;
    }

    public Lecturer[] getLecturers() {
        if (lecturerCount == 0) {
            return new Lecturer[0];
        }
        Lecturer[] copy = new Lecturer[lecturerCount];
        System.arraycopy(lecturers, 0, copy, 0, lecturerCount);
        return copy;
    }

    public boolean addDepartment(Department d) {
        if (d == null) {
            throw new IllegalArgumentException("Department cannot be null");
        }
        for (int i = 0; i < departmentCount; i++) {
            if (departments[i].equals(d)) {
                return false;
            }
        }
        if (departmentCount == departments.length) {
            Department[] temp = new Department[departments.length * GROW];
            System.arraycopy(departments, 0, temp, 0, departments.length);
            departments = temp;
        }
        departments[departmentCount++] = d;
        return true;
    }


    public Department[] getDepartments() {
        if (departmentCount == 0) {
            return new Department[0];
        }
        Department[] copy = new Department[departmentCount];
        System.arraycopy(departments, 0, copy, 0, departmentCount);
        return copy;
    }


    public Department findDepartmentByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }
        String key = name.trim();
        for (int i = 0; i < departmentCount; i++) {
            if (departments[i].getName().equalsIgnoreCase(key)) {
                return departments[i];
            }
        }
        return null;
    }


    public boolean removeDepartment(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Department name cannot be empty");
        }
        Department toRemove = findDepartmentByName(name);
        if (toRemove == null) {
            return false;
        }
        return removeDepartment(toRemove);
    }

    public boolean removeDepartment(Department d) {
        if (d == null) {
            throw new IllegalArgumentException("Department cannot be null");
        }
        int idx = -1;
        for (int i = 0; i < departmentCount; i++) {
            if (departments[i].equals(d)) {
                idx = i;
                break;
            }
        }
        if (idx < 0) {
            return false;
        }
        Lecturer[] lects = d.getLecturers();
        for (int i = 0; i < lects.length; i++) {
            lects[i].removeFromDepartment();
        }
        System.arraycopy(departments, idx + 1, departments, idx, departmentCount - idx - 1);
        departments[--departmentCount] = null;
        return true;
    }

    public boolean addCommittee(Committee c) {
        if (c == null) {
            throw new IllegalArgumentException("Committee cannot be null");
        }
        for (int i = 0; i < committeeCount; i++) {
            if (committees[i].equals(c)) {
                return false;
            }
        }
        if (committeeCount == committees.length) {
            Committee[] temp = new Committee[committees.length * GROW];
            System.arraycopy(committees, 0, temp, 0, committees.length);
            committees = temp;
        }
        committees[committeeCount++] = c;
        return true;
    }


    public Committee[] getCommittees() {
        if (committeeCount == 0) {
            return new Committee[0];
        }
        Committee[] copy = new Committee[committeeCount];
        System.arraycopy(committees, 0, copy, 0, committeeCount);
        return copy;
    }


    public Committee findCommitteeByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }
        String key = name.trim();
        for (int i = 0; i < committeeCount; i++) {
            if (committees[i].getName().equalsIgnoreCase(key)) {
                return committees[i];
            }
        }
        return null;
    }

    public boolean removeCommittee(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Committee name cannot be empty");
        }
        Committee toRemove = findCommitteeByName(name);
        if (toRemove == null) {
            return false;
        }
        return removeCommittee(toRemove);
    }

    public boolean removeCommittee(Committee c) {
        if (c == null) {
            throw new IllegalArgumentException("Committee cannot be null");
        }
        int idx = -1;
        for (int i = 0; i < committeeCount; i++) {
            if (committees[i].equals(c)) {
                idx = i;
                break;
            }
        }
        if (idx < 0) {
            return false;
        }
        Lecturer[] members = c.getMembers();
        for (int i = 0; i < members.length; i++) {
            c.removeMember(members[i]);
        }
        if (c.getChair() != null) {
            c.setChair(null);
        }
        System.arraycopy(committees, idx + 1, committees, idx, committeeCount - idx - 1);
        committees[--committeeCount] = null;
        return true;
    }

    public double getAverageSalaryAllLecturers() {
        if (lecturerCount == 0) {
            return 0.0;
        }
        double sum = 0.0;
        for (int i = 0; i < lecturerCount; i++) {
            sum += lecturers[i].getSalary();
        }
        return sum / lecturerCount;
    }

    public double getAverageSalaryByDepartment(String deptName) {
        if (deptName == null || deptName.trim().isEmpty()) {
            throw new IllegalArgumentException("Department name cannot be empty");
        }
        Department d = findDepartmentByName(deptName);
        if (d == null) {
            throw new IllegalArgumentException("Department '" + deptName + "' not found");
        }
        Lecturer[] lects = d.getLecturers();
        if (lects.length == 0) {
            return 0.0;
        }
        double sum = 0.0;
        for (int i = 0; i < lects.length; i++) {
            sum += lects[i].getSalary();
        }
        return sum / lects.length;
    }

    public String compareCommittees(String name1, String name2) {
        Committee c1 = findCommitteeByName(name1);
        Committee c2 = findCommitteeByName(name2);
        if (c1 == null || c2 == null) {
            return "⚠️ One or both committees not found.";
        }
        int cmp = c1.compareTo(c2);
        int articles1 = 0;
        if (c1.getChair() instanceof ResearchLecturer) {
            articles1 = ((ResearchLecturer) c1.getChair()).getArticleCount();
        }
        int articles2 = 0;
        if (c2.getChair() instanceof ResearchLecturer) {
            articles2 = ((ResearchLecturer) c2.getChair()).getArticleCount();
        }
        int size1 = c1.getTotalSize();
        int size2 = c2.getTotalSize();
        if (cmp == 0) {
            return String.format(
                    "Committees \"%s\" and \"%s\" are tied: %d articles, %d lecturers.",
                    c1.getName(), c2.getName(), articles1, size1
            );
        } else if (cmp < 0) {
            return String.format(
                    "Committee \"%s\" wins with %d articles vs %d articles.",
                    c2.getName(), articles2, articles1
            );
        } else {
            return String.format(
                    "Committee \"%s\" wins with %d articles vs %d articles.",
                    c1.getName(), articles1, articles2
            );
        }
    }

    public String compareDepartments(String d1, String d2, int crit) {
        Department dept1 = findDepartmentByName(d1);
        Department dept2 = findDepartmentByName(d2);
        if (dept1 == null || dept2 == null) {
            return "Dept not found.";
        }
        int v1 = (crit == 1) ? dept1.getLecturerCount() : sumArticles(dept1);
        int v2 = (crit == 1) ? dept2.getLecturerCount() : sumArticles(dept2);
        return String.format("%s: %d vs %s: %d", dept1.getName(), v1, dept2.getName(), v2);
    }

    private int sumArticles(Department d) {
        int sum = 0;
        Lecturer[] lects = d.getLecturers();
        for (int i = 0; i < lects.length; i++) {
            if (lects[i] instanceof ResearchLecturer) {
                sum += ((ResearchLecturer) lects[i]).getArticleCount();
            }
        }
        return sum;
    }

    public boolean cloneCommittee(String origName) {
        if (origName == null || origName.trim().isEmpty()) {
            throw new IllegalArgumentException("Original name cannot be empty");
        }
        Committee o = findCommitteeByName(origName);
        if (o == null) {
            return false;
        }
        Committee copy = new Committee(origName + "-new", o.getChair());
        Lecturer[] mems = o.getMembers();
        for (int i = 0; i < mems.length; i++) {
            copy.addMember(mems[i]);
        }
        return addCommittee(copy);
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof College)) return false;
        College other = (College) o;
        return this.collegeName.equalsIgnoreCase(other.collegeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(collegeName.toLowerCase());
    }

    @Override
    public String toString() {
        return String.format(
                "College: %s, Lecturers: %d, Departments: %d, Committees: %d",
                collegeName, lecturerCount, departmentCount, committeeCount
        );
    }
}
