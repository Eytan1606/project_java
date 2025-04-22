package ArielBotos_EytanCabalero2;

import java.util.Scanner;

public class Menu {
    private final CollegeManager manager;
    private final Scanner scanner;

    public Menu(CollegeManager manager, Scanner scanner) {
        this.manager = manager;
        this.scanner = scanner;
    }

    public void start() {
        while (true) {
            displayMenu();
            System.out.print("Enter your choice: ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.isBlank()) {
                System.out.println("Choice cannot be empty.");
                continue;
            }

            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 0 and 11.");
                continue;
            }

            switch (choice) {
                case 0 -> {
                    System.out.println("Program terminated.");
                    System.exit(0);
                }
                case 1 -> addLecturer();
                case 2 -> addCommittee();
                case 3 -> assignLecturerToCommittee();
                case 4 -> updateCommitteeChair();
                case 5 -> removeMemberFromCommittee();
                case 6 -> addDepartment();
                case 7 -> System.out.println("Average Salary (All Lecturers): " + manager.averageCollegeSalary());
                case 8 -> {
                    System.out.print("Enter department name: ");
                    String deptName = scanner.nextLine().toLowerCase().trim();
                    if (deptName.isBlank()) {
                        System.out.println("Department name cannot be empty.");
                        break;
                    }
                    System.out.println("Average Salary: " + manager.averageDepartmentSalary(deptName));
                }
                case 9 -> manager.printAllLecturers();
                case 10 -> manager.printAllCommittees();
                case 11 -> assignLecturerToDepartment();
                default -> System.out.println("Invalid choice.");
            }

            if (choice != 0) {
                System.out.println("Press Enter to return to menu.");
                scanner.nextLine();
            }
        }
    }

    private void displayMenu() {
        System.out.println("\n--- MENU ---");
        System.out.println("0 - Exit");
        System.out.println("1 - Add Lecturer");
        System.out.println("2 - Add Committee");
        System.out.println("3 - Assign Lecturer to Committee");
        System.out.println("4 - Update Committee Chair");
        System.out.println("5 - Remove Member from Committee");
        System.out.println("6 - Add Department");
        System.out.println("7 - Average Salary (All Lecturers)");
        System.out.println("8 - Average Salary by Department");
        System.out.println("9 - Display All Lecturers");
        System.out.println("10 - Display All Committees");
        System.out.println("11 - Assign Lecturer to Department");
    }

    private void addLecturer() {
        String name;
        while (true) {
            System.out.print("Enter lecturer name: ");
            name = scanner.nextLine().toLowerCase().trim();

            if (name.isBlank()) {
                System.out.println("Name cannot be empty.");
                continue;
            }

            if (manager.findLecturerByName(name) != null) {
                System.out.println("Lecturer already exists. Please enter a new name or type 'back' to return to the menu:");
                continue;
            }

            break;
        }

        System.out.print("Enter ID: ");
        String id = scanner.nextLine().trim();
        if (id.isBlank()) {
            System.out.println("ID cannot be empty.");
            return;
        }

        System.out.print("Enter Degree (BA/MA/DR/PROF): ");
        Degree degree;
        try {
            String degInput = scanner.nextLine().trim().toUpperCase();
            if (degInput.isBlank()) {
                System.out.println("Degree cannot be empty.");
                return;
            }
            degree = Degree.valueOf(degInput);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid degree.");
            return;
        }

        System.out.print("Enter Degree Name: ");
        String degreeName = scanner.nextLine().toLowerCase().trim();
        if (degreeName.isBlank()) {
            System.out.println("Degree name cannot be empty.");
            return;
        }

        System.out.print("Enter salary: ");
        double salary;
        try {
            String salaryInput = scanner.nextLine().trim();
            if (salaryInput.isBlank()) {
                System.out.println("Salary cannot be empty.");
                return;
            }
            salary = Double.parseDouble(salaryInput);
            if (salary <= 0) {
                System.out.println("Salary must be a positive number.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid salary.");
            return;
        }

        Lecturer lecturer = new Lecturer(name, id, degree, degreeName, salary);
        manager.addLecturer(lecturer);
        System.out.println("Lecturer added successfully.");

        if (manager.getDepartmentCount() > 0) {
            System.out.print("Assign lecturer to a department? (yes/no): ");
            String response = scanner.nextLine().trim();
            if (response.equalsIgnoreCase("yes")) {
                System.out.print("Enter department name: ");
                String deptName = scanner.nextLine().toLowerCase().trim();
                Department dept = manager.findDepartmentByName(deptName);
                if (dept != null) {
                    dept.addLecturer(lecturer);
                    lecturer.setDepartment(dept);
                    System.out.println("Lecturer assigned to department successfully.");
                } else {
                    System.out.println("Department not found.");
                }
            }
        }
    }

    private void assignLecturerToDepartment() {
        System.out.print("Enter lecturer name: ");
        String lecturerName = scanner.nextLine().toLowerCase().trim();
        if (lecturerName.isBlank()) {
            System.out.println("Name cannot be empty.");
            return;
        }

        Lecturer lecturer = manager.findLecturerByName(lecturerName);
        if (lecturer == null) {
            System.out.println("Lecturer not found.");
            return;
        }

        System.out.print("Enter department name: ");
        String deptName = scanner.nextLine().toLowerCase().trim();
        if (deptName.isBlank()) {
            System.out.println("Name cannot be empty.");
            return;
        }

        Department newDept = manager.findDepartmentByName(deptName);
        if (newDept == null) {
            System.out.println("Department not found.");
            return;
        }

        Department currentDept = lecturer.getDepartment();
        if (currentDept != null && currentDept != newDept) {
            currentDept.removeLecturer(lecturer);
        }

        newDept.addLecturer(lecturer);
        lecturer.setDepartment(newDept);
        System.out.println("Lecturer assigned to department successfully.");
    }

    private void assignLecturerToCommittee() {
        System.out.print("Enter committee name: ");
        String committeeName = scanner.nextLine().toLowerCase().trim();
        if (committeeName.isBlank()) {
            System.out.println("Committee name cannot be empty.");
            return;
        }

        Committee committee = manager.findCommitteeByName(committeeName);
        if (committee == null) {
            System.out.println("Committee not found.");
            return;
        }

        System.out.print("Enter lecturer name: ");
        String name = scanner.nextLine().toLowerCase().trim();
        if (name.isBlank()) {
            System.out.println("Lecturer name cannot be empty.");
            return;
        }

        Lecturer lecturer = manager.findLecturerByName(name);
        if (lecturer == null) {
            System.out.println("Lecturer not found.");
            return;
        }

        boolean added = committee.addMember(lecturer);
        if (added) {
            System.out.println("Lecturer assigned to committee successfully.");
        } else {
            System.out.println("Lecturer is already in the committee or is the chair.");
        }
    }

    private void updateCommitteeChair() {
        System.out.print("Enter committee name: ");
        String committeeName = scanner.nextLine().toLowerCase().trim();
        if (committeeName.isBlank()) {
            System.out.println("Committee name cannot be empty.");
            return;
        }

        Committee committee = manager.findCommitteeByName(committeeName);
        if (committee == null) {
            System.out.println("Committee not found.");
            return;
        }

        System.out.print("Enter new chair name: ");
        String name = scanner.nextLine().toLowerCase().trim();
        if (name.isBlank()) {
            System.out.println("Chair name cannot be empty.");
            return;
        }

        Lecturer newChair = manager.findLecturerByName(name);
        if (newChair == null || !newChair.getDegree().canBeChair()) {
            System.out.println("Invalid chair (must be DR or PROF).");
            return;
        }

        committee.setChair(newChair);
        System.out.println("Committee chair updated successfully.");
    }

    private void removeMemberFromCommittee() {
        System.out.print("Enter committee name: ");
        String committeeName = scanner.nextLine().toLowerCase().trim();
        if (committeeName.isBlank()) {
            System.out.println("Committee name cannot be empty.");
            return;
        }

        Committee committee = manager.findCommitteeByName(committeeName);
        if (committee == null) {
            System.out.println("Committee not found.");
            return;
        }

        System.out.print("Enter member name to remove: ");
        String name = scanner.nextLine().toLowerCase().trim();
        if (name.isBlank()) {
            System.out.println("Lecturer name cannot be empty.");
            return;
        }

        Lecturer lecturer = manager.findLecturerByName(name);
        if (lecturer == null) {
            System.out.println("Lecturer not found.");
            return;
        }

        committee.removeMember(lecturer);
        System.out.println("Member removed successfully.");
    }

    private void addCommittee() {
        System.out.print("Enter committee name: ");
        String name = scanner.nextLine().toLowerCase().trim();
        if (name.isBlank()) {
            System.out.println("Committee name cannot be empty.");
            return;
        }

        if (manager.findCommitteeByName(name) != null) {
            System.out.println("Committee already exists.");
            return;
        }

        System.out.print("Enter chair lecturer name: ");
        String chairName = scanner.nextLine().toLowerCase().trim();
        if (chairName.isBlank()) {
            System.out.println("Chair name cannot be empty.");
            return;
        }

        Lecturer chair = manager.findLecturerByName(chairName);
        if (chair == null || !chair.getDegree().canBeChair()) {
            System.out.println("Invalid chair (must be DR or PROF).");
            return;
        }

        Committee committee = new Committee(name, chair);
        manager.addCommittee(committee);
        System.out.println("Committee added successfully.");
    }

    private void addDepartment() {
        System.out.print("Enter department name: ");
        String name = scanner.nextLine().toLowerCase().trim();
        if (name.isBlank()) {
            System.out.println("Department name cannot be empty.");
            return;
        }

        if (manager.findDepartmentByName(name) != null) {
            System.out.println("Department already exists.");
            return;
        }

        System.out.print("Enter number of students: ");
        String input = scanner.nextLine().trim();
        int students;
        try {
            students = Integer.parseInt(input);
            if (students <= 0) {
                System.out.println("Number of students must be positive.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number.");
            return;
        }

        Department dept = new Department(name, students);
        manager.addDepartment(dept);
        System.out.println("Department added successfully.");
    }
}
