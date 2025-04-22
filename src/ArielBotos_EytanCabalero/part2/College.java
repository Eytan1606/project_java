package ArielBotos_EytanCabalero.part2;

import java.util.Arrays;

public class College {
    private  final String name;
    private Lecturer[] lecturers = new Lecturer[2];
    private int lecturerCount = 0;
    private Department[] departments = new Department[2];
    private int departmentCount = 0;
    private Committee[] committees = new Committee[2];
    private int committeeCount = 0;

    public College(String name) {
        this.name = name;

    }

    private void ensureLecturerCapacity() {
        if (lecturerCount == lecturers.length) {
            lecturers = Arrays.copyOf(lecturers, lecturers.length * 2);
        }
    }

    private void ensureDepartmentCapacity() {
        if (departmentCount == departments.length) {
            departments = Arrays.copyOf(departments, departments.length * 2);
        }
    }

    private void ensureCommitteeCapacity() {
        if (committeeCount == committees.length) {
            committees = Arrays.copyOf(committees, committees.length * 2);
        }
    }

    public void addLecturer(Lecturer lecturer) {
        for (int i = 0; i < lecturerCount; i++) {
            if (lecturers[i].getId().equals(lecturer.getId())) return;
        }
        ensureLecturerCapacity();
        lecturers[lecturerCount++] = lecturer;
    }

    public boolean addDepartment(Department department) {
        for (int i = 0; i < departmentCount; i++) {
            if (departments[i].getname().equals(department.getname())) return false;
        }
        ensureDepartmentCapacity();
        departments[departmentCount++] = department;
        return true;
    }

    public boolean AddCommittee(String name, Lecturer chairman) {
        for (int i = 0; i < committeeCount; i++) {
            if (committees[i].getName().equals(name)) return false;
        }
        if (!chairman.getDegreeTitle().equals("Dr") && !chairman.getDegreeTitle().equals("Prof")) {return false;}
        ensureCommitteeCapacity();
        committees[committeeCount++] = new Committee(name, chairman);
        return true;
    }

    public boolean addMemberToCommittee(String committeeName, Lecturer lecturer) {
        for (int i = 0; i < committeeCount; i++) {
            if (committees[i].getName().equals(committeeName)) {
                return committees[i].addMember(lecturer);
            }
        }
        return false;
    }

    public boolean removeMemberFromCommittee(String committeeName, Lecturer lecturer) {
        for (int i = 0; i < committeeCount; i++) {
            if (committees[i].getName().equals(committeeName)) {
                return committees[i].removeMembers(lecturer);
            }
        }
        return false;
    }

    public boolean updateCommitteeChair(String committeeName, Lecturer chairman) {
        for (int i = 0; i < committeeCount; i++) {
            if (committees[i].getName().equals(committeeName)) {
                return committees[i].setChair(chairman);
            }
        }
        return false;
    }

    public double averageSalary() {
        if (lecturerCount == 0) return 0;
        double sum = 0;
        for (int i = 0; i < lecturerCount; i++) {
            sum += lecturers[i].getSalary();
        }
        return sum / lecturerCount;
    }

    public double averageSalaryByDepartment(String deptName) {
        for (int i = 0; i < departmentCount; i++) {
            if (departments[i].getname().equals(deptName)) {
                Lecturer[] list = departments[i].getLecturer();
                if (list.length == 0) return 0;
                double sum = 0;
                for (Lecturer l : list) sum += l.getSalary();
                return sum / list.length;
            }
        }
        return 0;
    }

    public void printLecturers() {
        for (int i = 0; i < lecturerCount; i++) {
            System.out.println(lecturers[i]);
        }
    }

    public void printCommittees() {
        for (int i = 0; i < committeeCount; i++) {
            System.out.println(committees[i]);
        }
    }

    public Lecturer getLecturerById(String id) {
        for (int i = 0; i < lecturerCount; i++) {
            if (lecturers[i].getId().equals(id)) return lecturers[i];
        }
        return null;
    }
}
