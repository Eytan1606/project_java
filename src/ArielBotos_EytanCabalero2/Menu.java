package ArielBotos_EytanCabalero2;

public class Menu {
    private final CollegeManager manager;

    public Menu(CollegeManager manager) {
        this.manager = manager;
    }
// this part has to be change
    public String addLecturer(String name, String id, Degree degree, String degreeName, double salary) {
        if (manager.findLecturerByName(name) != null) {
            return "Lecturer already exists.";
        }
        Lecturer lecturer = new Lecturer(name, id, degree, degreeName, salary);
        manager.addLecturer(lecturer);
        return "Lecturer added successfully.";
    }

    public String addDepartment(String name, int students) {
        if (manager.findDepartmentByName(name) != null) {
            return "Department already exists.";
        }
        manager.addDepartment(new Department(name, students));
        return "Department added successfully.";
    }

    public String addCommittee(String name, String chairName) {
        Lecturer chair = manager.findLecturerByName(chairName);
        if (chair == null || !chair.getDegree().canBeChair()) {
            return "Invalid chair.";
        }
        if (manager.findCommitteeByName(name) != null) {
            return "Committee already exists.";
        }
        manager.addCommittee(new Committee(name, chair));
        return "Committee added successfully.";
    }

    public String assignLecturerToDepartment(String lecturerName, String deptName) {
        Lecturer lecturer = manager.findLecturerByName(lecturerName);
        Department dept = manager.findDepartmentByName(deptName);
        if (lecturer == null || dept == null) return "Lecturer or Department not found.";
        dept.addLecturer(lecturer);
        lecturer.setDepartment(dept);
        return "Lecturer assigned to department.";
    }

    public String assignLecturerToCommittee(String committeeName, String lecturerName) {
        Committee committee = manager.findCommitteeByName(committeeName);
        Lecturer lecturer = manager.findLecturerByName(lecturerName);
        if (committee == null || lecturer == null) return "Committee or Lecturer not found.";
        return committee.addMember(lecturer) ? "Lecturer assigned to committee." : "Lecturer already in committee.";
    }

    public String updateCommitteeChair(String committeeName, String chairName) {
        Committee committee = manager.findCommitteeByName(committeeName);
        Lecturer newChair = manager.findLecturerByName(chairName);
        if (committee == null || newChair == null || !newChair.getDegree().canBeChair()) {
            return "Invalid committee or chair.";
        }
        committee.setChair(newChair);
        return "Committee chair updated.";
    }

    public String removeMemberFromCommittee(String committeeName, String memberName) {
        Committee committee = manager.findCommitteeByName(committeeName);
        Lecturer lecturer = manager.findLecturerByName(memberName);
        if (committee == null || lecturer == null) return "Committee or Lecturer not found.";
        committee.removeMember(lecturer);
        return "Member removed from committee.";
    }
    public String removeLecturerFromDepartment(String deptName, String lecturerName) {
        Department dept = manager.findDepartmentByName(deptName);
        Lecturer lecturer = manager.findLecturerByName(lecturerName);
        if (dept == null || lecturer == null) return "Department or Lecturer not found.";
        dept.removeLecturer(lecturer);
        lecturer.setDepartment(null);
        return "Lecturer removed from department.";
    }

    public String displayAllDepartments() {
        manager.printAllDepartments();
        return "Displayed all departments.";
    }

}
