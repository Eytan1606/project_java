package ArielBotos_EytanCabalero.part2;

public class Menu {
    private final College college;

    public Menu(College college) {
        this.college = college;
    }

    public void displayMenu() {
        boolean running = true;
        while (running) {
            printMenu();
            int choice = inputValidator.readPositiveInt("Select an option: ");
            switch (choice) {
                case 0 -> running = false;
                case 1 -> addLecturer();
                case 2 -> addCommittee();
                case 3 -> addMemberToCommittee();
                case 4 -> updateCommitteeChair();
                case 5 -> removeMemberFromCommittee();
                case 6 -> addDepartment();
                case 7 -> printAverageSalaryAll();
                case 8 -> averageSalaryByDepartment();
                case 9 -> college.printLecturers();
                case 10 -> college.printCommittees();
                default -> System.out.println("Invalid option. Please choose between 0 and 10.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n=== College Management Menu ===");
        System.out.println("0  - Exit");
        System.out.println("1  - Add Lecturer");
        System.out.println("2  - Add Committee");
        System.out.println("3  - Add Member to Committee");
        System.out.println("4  - Update Committee Chair");
        System.out.println("5  - Remove Member from Committee");
        System.out.println("6  - Add Department");
        System.out.println("7  - Average Salary (All Lecturers)");
        System.out.println("8  - Average Salary by Department");
        System.out.println("9  - Display All Lecturers");
        System.out.println("10 - Display All Committees");
    }

    private void addLecturer() {
        String id = readTrimmed("Enter ID: ");
        if (college.getLecturerById(id) != null) {
            System.out.println("Lecturer already exists.");
            return;
        }

        String name = readTrimmed("Enter name: ");
        String title = inputValidator.readValidDegree("Enter degree title (BA/MA/Dr/Prof): ").trim().toLowerCase();
        String degree = readTrimmed("Enter degree name: ");
        double salary = inputValidator.readPositiveDouble("Enter salary: ");

        college.addLecturer(new Lecturer(id, name, degree, title, salary));
        System.out.println("Lecturer has been added successfully.");
    }

    private void addCommittee() {
        String name = readTrimmed("Enter committee name: ");
        Lecturer chair = getLecturerById("Enter chair ID: ");

        if (chair == null) {
            System.out.println("Lecturer not found.");
        } else if (!college.AddCommittee(name, chair)) {
            System.out.println("Error creating committee.");
        } else {
            System.out.println("Committee created successfully.");
        }
    }

    private void addMemberToCommittee() {
        String committeeName = readTrimmed("Enter committee name: ");
        Lecturer lecturer = getLecturerById("Enter lecturer ID: ");

        if (lecturer == null || !college.addMemberToCommittee(committeeName, lecturer)) {
            System.out.println("Error adding member.");
        } else {
            System.out.println("Member added successfully.");
        }
    }

    private void updateCommitteeChair() {
        String committeeName = readTrimmed("Enter committee name: ");
        Lecturer newChair = getLecturerById("Enter new chair ID: ");

        if (newChair == null || !college.updateCommitteeChair(committeeName, newChair)) {
            System.out.println("Error updating chair.");
        } else {
            System.out.println("Chair updated successfully.");
        }
    }

    private void removeMemberFromCommittee() {
        String committeeName = readTrimmed("Enter committee name: ");
        Lecturer lecturer = getLecturerById("Enter lecturer ID: ");

        if (lecturer == null || !college.removeMemberFromCommittee(committeeName, lecturer)) {
            System.out.println("Error removing member.");
        } else {
            System.out.println("Member removed successfully.");
        }
    }

    private void addDepartment() {
        String name = readTrimmed("Enter department name: ");
        int numStudents = inputValidator.readPositiveInt("Enter number of students: ");

        if (!college.addDepartment(new Department(numStudents, name))) {
            System.out.println("Department already exists. Try again.");
        } else {
            System.out.println("Department added successfully.");
        }
    }

    private void averageSalaryByDepartment() {
        String name = readTrimmed("Enter department name: ");
        double avg = college.averageSalaryByDepartment(name);
        System.out.println("Average salary in " + name + ": " + avg);
    }

    private void printAverageSalaryAll() {
        System.out.println("Average salary: " + college.averageSalary());
    }


    private String readTrimmed(String prompt) {
        return inputValidator.readNonEmptyLine(prompt).trim();
    }

    private Lecturer getLecturerById(String prompt) {
        String id = readTrimmed(prompt);
        return college.getLecturerById(id);
    }
}
