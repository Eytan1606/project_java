package ArielBotos_EytanCabalero2;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the college name: ");
        String collegeName = scanner.nextLine();

        CollegeManager manager = new CollegeManager(collegeName);

        while (true) {
            System.out.println("\n========= COLLEGE MANAGEMENT MENU =========");
            System.out.println("0  - Exit");
            System.out.println("1  - Add Lecturer");
            System.out.println("2  - Add Department");
            System.out.println("3  - Add Committee");
            System.out.println("4  - Assign Lecturer to Department");
            System.out.println("5  - Assign Lecturer to Committee");
            System.out.println("6  - Update Committee Chair");
            System.out.println("7  - Remove Member from Committee");
            System.out.println("8  - Average Salary (All Lecturers)");
            System.out.println("9  - Average Salary by Department");
            System.out.println("10 - Display All Lecturers");
            System.out.println("11 - Display All Committees");
            System.out.println("===========================================");

            System.out.print("Enter your choice: ");
            String input = scanner.nextLine().trim();

            if (!input.matches("\\d+")) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            int choice = Integer.parseInt(input);

            switch (choice) {
                case 0 -> {
                    System.out.println("Program terminated.");
                    return;
                }
                case 1 -> {
                    System.out.println("\n--- Add Lecturer ---");
                    System.out.print("Enter Lecturer Name: ");
                    String name = scanner.nextLine().trim();
                    System.out.print("Enter ID: ");
                    String id = scanner.nextLine().trim();
                    System.out.print("Enter Degree (BA/MA/DR/PROF): ");
                    Degree degree = Degree.valueOf(scanner.nextLine().trim().toUpperCase());
                    System.out.print("Enter Degree Name: ");
                    String degreeName = scanner.nextLine().trim();
                    System.out.print("Enter Salary: ");
                    double salary = Double.parseDouble(scanner.nextLine().trim());

                    if (manager.findLecturerByName(name) != null) {
                        System.out.println("Lecturer already exists.");
                    } else {
                        Lecturer lecturer = new Lecturer(name, id, degree, degreeName, salary);
                        manager.addLecturer(lecturer);
                        System.out.println("Lecturer added successfully.");
                    }
                }
                case 2 -> {
                    System.out.println("\n--- Add Department ---");
                    System.out.print("Enter Department Name: ");
                    String deptName = scanner.nextLine().trim();
                    System.out.print("Enter Number of Students: ");
                    int students = Integer.parseInt(scanner.nextLine().trim());

                    if (manager.findDepartmentByName(deptName) != null) {
                        System.out.println("Department already exists.");
                    } else {
                        manager.addDepartment(new Department(deptName, students));
                        System.out.println("Department added successfully.");
                    }
                }
                case 3 -> {
                    System.out.println("\n--- Add Committee ---");
                    System.out.print("Enter Committee Name: ");
                    String committeeName = scanner.nextLine().trim();
                    System.out.print("Enter Chair Lecturer Name: ");
                    String chairName = scanner.nextLine().trim();
                    Lecturer chair = manager.findLecturerByName(chairName);

                    if (chair == null || !chair.getDegree().canBeChair()) {
                        System.out.println("Invalid chair.");
                    } else if (manager.findCommitteeByName(committeeName) != null) {
                        System.out.println("Committee already exists.");
                    } else {
                        manager.addCommittee(new Committee(committeeName, chair));
                        System.out.println("Committee added successfully.");
                    }
                }
                case 4 -> {
                    System.out.println("\n--- Assign Lecturer to Department ---");
                    System.out.print("Enter Lecturer Name: ");
                    String lecturerName = scanner.nextLine().trim();
                    System.out.print("Enter Department Name: ");
                    String deptName = scanner.nextLine().trim();

                    Lecturer lecturer = manager.findLecturerByName(lecturerName);
                    Department dept = manager.findDepartmentByName(deptName);

                    if (lecturer == null || dept == null) {
                        System.out.println("Lecturer or Department not found.");
                    } else {
                        dept.addLecturer(lecturer);
                        lecturer.setDepartment(dept);
                        System.out.println("Lecturer assigned to department.");
                    }
                }
                case 5 -> {
                    System.out.println("\n--- Assign Lecturer to Committee ---");
                    System.out.print("Enter Committee Name: ");
                    String comName = scanner.nextLine().trim();
                    System.out.print("Enter Lecturer Name: ");
                    String lecName = scanner.nextLine().trim();

                    Committee committee = manager.findCommitteeByName(comName);
                    Lecturer lecturer = manager.findLecturerByName(lecName);

                    if (committee == null || lecturer == null) {
                        System.out.println("Committee or Lecturer not found.");
                    } else {
                        boolean added = committee.addMember(lecturer);
                        System.out.println(added ? "Lecturer assigned to committee." : "Lecturer already in committee.");
                    }
                }
                case 6 -> {
                    System.out.println("\n--- Update Committee Chair ---");
                    System.out.print("Enter Committee Name: ");
                    String comName = scanner.nextLine().trim();
                    System.out.print("Enter New Chair Name: ");
                    String chairName = scanner.nextLine().trim();

                    Committee committee = manager.findCommitteeByName(comName);
                    Lecturer newChair = manager.findLecturerByName(chairName);

                    if (committee == null || newChair == null || !newChair.getDegree().canBeChair()) {
                        System.out.println("Invalid committee or chair.");
                    } else {
                        committee.setChair(newChair);
                        System.out.println("Committee chair updated.");
                    }
                }
                case 7 -> {
                    System.out.println("\n--- Remove Member from Committee ---");
                    System.out.print("Enter Committee Name: ");
                    String comName = scanner.nextLine().trim();
                    System.out.print("Enter Member Name to Remove: ");
                    String memberName = scanner.nextLine().trim();

                    Committee committee = manager.findCommitteeByName(comName);
                    Lecturer lecturer = manager.findLecturerByName(memberName);

                    if (committee == null || lecturer == null) {
                        System.out.println("Committee or Lecturer not found.");
                    } else {
                        committee.removeMember(lecturer);
                        System.out.println("Member removed from committee.");
                    }
                }
                case 8 -> System.out.println("\nAverage Salary (All Lecturers): " + manager.averageCollegeSalary());
                case 9 -> {
                    System.out.println("\n--- Average Salary by Department ---");
                    System.out.print("Enter Department Name: ");
                    String deptName = scanner.nextLine().trim();
                    System.out.println("Average Salary: " + manager.averageDepartmentSalary(deptName));
                }
                case 10 -> {
                    System.out.println("\n--- All Lecturers ---");
                    manager.printAllLecturers();
                }
                case 11 -> {
                    System.out.println("\n--- All Committees ---");
                    manager.printAllCommittees();
                }
            }
        }
    }
}
