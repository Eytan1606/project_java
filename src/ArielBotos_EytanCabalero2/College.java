package ArielBotos_EytanCabalero2;

import java.util.Objects;

public class College {
    private static final int GROW = 2;

    private final String collegeName;


    private Lecturer[] lecturers;
    private int lecturerCount;


    private Department[] departments;
    private int departmentCount;


    private Committee[] committees;
    private int committeeCount;

    public College(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new NullInputException();
        }
        this.collegeName    = name.trim();
        this.lecturers      = new Lecturer[2];
        this.lecturerCount  = 0;
        this.departments    = new Department[2];
        this.departmentCount = 0;
        this.committees     = new Committee[2];
        this.committeeCount = 0;
    }

    public void addLecturer(Lecturer l) {
        if (l == null) {
            throw new IllegalArgumentException("Lecturer cannot be null");
        }
        for (int i = 0; i < lecturerCount; i++) {
            if (lecturers[i].equals(l)) {
                throw new DuplicateEntityException("Lecturer", String.valueOf(l.getId()));
            }
        }
        if (lecturerCount == lecturers.length) {
            Lecturer[] temp = new Lecturer[lecturers.length * GROW];
            System.arraycopy(lecturers, 0, temp, 0, lecturers.length);
            lecturers = temp;
        }
        lecturers[lecturerCount++] = l;
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

    public void removeLecturer(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Lecturer name cannot be empty");
        }
        Lecturer toRemove = findLecturerByName(name);
        if (toRemove == null) {
            throw new EntityNotFoundException("Lecturer '",name.trim());
        }
        removeLecturer(toRemove);
    }

    private void removeLecturer(Lecturer l) {
        if (l == null) {
            throw new ValidationException("Lecturer cannot be null");
        }
        int idx = -1;
        for (int i = 0; i < lecturerCount; i++) {
            if (lecturers[i].equals(l)) {
                idx = i;
                break;
            }
        }
        if (idx < 0) {
            throw new EntityNotFoundException("Lecturer", String.valueOf(l.getId()));
        }
        l.removeFromDepartment();

        Committee[] joined = l.getCommittees();
        for (Committee c : joined) {
            l.removeFromCommittee(c);
        }

        System.arraycopy(lecturers, idx + 1, lecturers, idx, lecturerCount - idx - 1);
        lecturers[--lecturerCount] = null;
    }



    public Lecturer[] getLecturers() {
        if (lecturerCount == 0) {
            return new Lecturer[0];
        }
        Lecturer[] copy = new Lecturer[lecturerCount];
        System.arraycopy(lecturers, 0, copy, 0, lecturerCount);
        return copy;
    }

    public void addLecturerToDepartment(String lecturerName, String deptName) {
        Lecturer lec = findLecturerByName(lecturerName);
        if (lec == null) {
            throw new EntityNotFoundException("Lecturer", lecturerName.trim());
        }
        Department dept = findDepartmentByName(deptName);
        if (dept == null) {
            throw new EntityNotFoundException("Department", deptName.trim());
        }
        boolean added = lec.assignToDepartment(dept);
        if (!added) {
            throw new ValidationException(
                    "Lecturer '" + lec.getName() +
                            "' is already in department '" + dept.getName() + "'."
            );
        }
    }

    public void addLecturerToCommittee(String lecturerName, String committeeName) {
        Committee c = findCommitteeByName(committeeName);
        if (c == null) {
            throw new EntityNotFoundException("Committee", committeeName.trim());
        }
        Lecturer lec = findLecturerByName(lecturerName);
        if (lec == null) {
            throw new EntityNotFoundException("Lecturer", lecturerName.trim());
        }
        boolean added = c.addMember(lec);
        if (!added) {
            throw new ValidationException(
                    "Lecturer '" + lec.getName() +
                            "' is already a member of committee '" + c.getName() + "'."
            );
        }
    }




    public void addDepartment(Department d) {
        if (d == null) {
            throw new ValidationException("Department cannot be null");
        }
        for (int i = 0; i < departmentCount; i++) {
            if (departments[i].equals(d)) {
                throw new DuplicateEntityException("Department", d.getName());
            }
        }
        if (departmentCount == departments.length) {
            Department[] temp = new Department[departments.length * GROW];
            System.arraycopy(departments, 0, temp, 0, departments.length);
            departments = temp;
        }
        departments[departmentCount++] = d;
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


    public void removeDepartment(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Department name cannot be empty");
        }
        Department toRemove = findDepartmentByName(name);
        if (toRemove == null) {
            throw new EntityNotFoundException("Department", name.trim());
        }
        removeDepartment(toRemove);
    }

    private void removeDepartment(Department d) {
        if (d == null) {
            throw new ValidationException("Department cannot be null");
        }
        int idx = -1;
        for (int i = 0; i < departmentCount; i++) {
            if (departments[i].equals(d)) {
                idx = i;
                break;
            }
        }
        if (idx < 0) {
            throw new EntityNotFoundException("Department", d.getName());
        }

        Lecturer[] lects = d.getLecturers();
        for (Lecturer l : lects) {
            l.removeFromDepartment();
        }
        System.arraycopy(departments, idx + 1, departments, idx, departmentCount - idx - 1);
        departments[--departmentCount] = null;
    }

    public void addCommittee(Committee c) {
        if (c == null) {
            throw new ValidationException("Committee cannot be null.");
        }
        for (int i = 0; i < committeeCount; i++) {
            if (committees[i].equals(c)) {
                throw new DuplicateEntityException("Committee", c.getName());
            }
        }
        if (committeeCount == committees.length) {
            Committee[] temp = new Committee[committees.length * GROW];
            System.arraycopy(committees, 0, temp, 0, committees.length);
            committees = temp;
        }
        committees[committeeCount++] = c;
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

    public void removeCommittee(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Committee name cannot be empty.");
        }
        Committee toRemove = findCommitteeByName(name);
        if (toRemove == null) {
            throw new EntityNotFoundException("Committee", name.trim());
        }
        removeCommittee(toRemove);
    }

    private void removeCommittee(Committee c) {
        if (c == null) {
            throw new IllegalArgumentException("Committee cannot be null.");
        }
        int idx = -1;
        for (int i = 0; i < committeeCount; i++) {
            if (committees[i].equals(c)) {
                idx = i;
                break;
            }
        }
        if (idx < 0) {
            throw new EntityNotFoundException("Committee", c.getName());
        }
        Lecturer[] members = c.getMembers();
        for (Lecturer l : members) {
            c.removeMember(l);
        }
        if (c.getChair() != null) {
            c.setChair(null);
        }
        System.arraycopy(committees, idx + 1, committees, idx, committeeCount - idx - 1);
        committees[--committeeCount] = null;
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
        if (c1 == null) {
            throw new EntityNotFoundException("Committee", name1.trim());
        }

        Committee c2 = findCommitteeByName(name2);
        if (c2 == null) {
            throw new EntityNotFoundException("Committee", name2.trim());
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
                    "Committees \"%s\" and \"%s\" are tied: %d articles, %d members.",
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
        if (dept1 == null) {
            throw new EntityNotFoundException("Department", d1.trim());
        }
        Department dept2 = findDepartmentByName(d2);
        if (dept2 == null) {
            throw new EntityNotFoundException("Department", d2.trim());
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

    public void cloneCommittee(String origName) {
        if (origName == null || origName.trim().isEmpty()) {
            throw new IllegalArgumentException("Original name cannot be empty");
        }
        Committee o = findCommitteeByName(origName);
        if (o == null) {
            throw new EntityNotFoundException("Committee", origName.trim());
        }

        String newName = origName.trim() + "-new";

        if (findCommitteeByName(newName) != null) {
            throw new DuplicateEntityException("Committee", newName);
        }

        Committee copy = new Committee(newName, o.getChair());
        Lecturer[] mems = o.getMembers();
        for (Lecturer l : mems) {
            copy.addMember(l);
        }
        addCommittee(copy);
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
