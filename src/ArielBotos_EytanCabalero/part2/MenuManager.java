package ArielBotos_EytanCabalero.part2;

public class MenuManager {
    private final College college;

    public MenuManager(College college) {
        this.college = college;
    }

    public void displayMenu() {
        boolean running = true;
        while (running) {
            printMenu();
            int choice = inputValidator.readPositiveInt("Select an option: ");
            switch (choice) {
                case 0 -> running = false;
                case 1 -> addLecturer();//צריך לטפל באותיות גדולות
                case 2 -> addCommittee();//
                case 3 -> addMemberToCommittee();
                case 4 -> updateCommitteeChair();
                case 5 -> removeMemberFromCommittee();
                case 6 -> addDepartment();
                case 7 -> System.out.println("Average salary: " + college.averageSalary());
                case 8 -> averageSalaryByDepartment();
                case 9 -> college.printLecturers();
                case 10 -> college.printCommittees();
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\nMenu:");
        System.out.println("0 - Exit");
        System.out.println("1 - Add Lecturer");
        System.out.println("2 - Add Committee");
        System.out.println("3 - Add Member to Committee");
        System.out.println("4 - Update Committee Chair");
        System.out.println("5 - Remove Member from Committee");
        System.out.println("6 - Add Department");
        System.out.println("7 - Average Salary (All Lecturers)");
        System.out.println("8 - Average Salary by Department");
        System.out.println("9 - Display All Lecturers");
        System.out.println("10 - Display All Committees");
    }

    private void addLecturer() {
        String id = inputValidator.readNonEmptyLine("Enter ID: ");
        if (college.getLecturerById(id) != null ) {
            System.out.println("Lecturer already exists."); return;
        }
        String name = inputValidator.readNonEmptyLine("Enter name: ");
        String title = inputValidator.readValidDegree("Enter degree title (BA/MA/Dr/Prof): ");
        String degree = inputValidator.readNonEmptyLine("Enter degree name: ");
        double salary = inputValidator.readPositiveDouble("Enter salary: ");
        college.addLecturer(new Lecturer(id, name, title, degree, salary));
        System.out.println("Lecturer has been added successfully.");
    }

    private void addCommittee() {
        String name = inputValidator.readNonEmptyLine("Enter committee name: ");
        Lecturer chairMan = college.getLecturerById(inputValidator.readNonEmptyLine("Enter chair name: "));

        if (chairMan == null) {
            System.out.println("Lecturer not found.");
        } else if (!college.AddCommittee(name, chairMan)) {
            System.out.println("Error creating committee.");
        } else {
            System.out.println("Committee created successfully.");
        }
    }

    private void addMemberToCommittee() {
        String cname = inputValidator.readNonEmptyLine("Enter committee name: ");
        Lecturer l = college.getLecturerById(inputValidator.readNonEmptyLine("Enter lecturer ID: "));
        if (l == null || !college.addMemberToCommittee(cname, l))
            System.out.println("Error adding member.");
    }

    private void updateCommitteeChair() {
        String name = inputValidator.readNonEmptyLine("Enter committee name: ");
        Lecturer l = college.getLecturerById(inputValidator.readNonEmptyLine("Enter new chair ID: "));
        if (l == null || !college.updateCommitteeChair(name, l))
            System.out.println("Error updating chair.");
    }

    private void removeMemberFromCommittee() {
        String name = inputValidator.readNonEmptyLine("Enter committee name: ");
        Lecturer l = college.getLecturerById(inputValidator.readNonEmptyLine("Enter lecturer ID: "));
        if (l == null || !college.removeMemberFromCommittee(name, l))
            System.out.println("Error removing member.");
    }

    private void addDepartment() {
        String name = inputValidator.readNonEmptyLine("Enter department name: ");
        int count = inputValidator.readPositiveInt("Enter number of students: ");
        if (!college.addDepartment(new Department(count, name))) {
            System.out.println("Department already exists. try again");
        }
    }

    private void averageSalaryByDepartment() {
        String dname = inputValidator.readNonEmptyLine("Enter department name: ");
        System.out.println("Average salary: " + college.averageSalaryByDepartment(dname));
    }
}
