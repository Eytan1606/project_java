// Main.java
package ArielBotos_EytanCabalero2;

import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        String collegeName = readNonEmpty("Enter college name: ");
        College college  = new College(collegeName);

        while (true) {
            printMenu();
            int choice = readInt("Choose an option (0–16): ", 0, 16);
            System.out.println();
            switch (choice) {
                case 0  -> { System.out.println("Goodbye!"); return; }
                case 1  -> flowAddLecturer(college);
                case 2  -> flowRemoveLecturer(college);
                case 3  -> flowAddDepartment(college);
                case 4  -> flowRemoveDepartment(college);
                case 5  -> flowAddCommittee(college);
                case 6  -> flowRemoveCommittee(college);
                case 7  -> System.out.printf("Average salary: ₪%.2f%n",
                        college.getAverageSalaryAllLecturers());
                case 8  -> {
                    String d = readNonEmpty("Department name: ");
                    System.out.printf("Average in %s: ₪%.2f%n",
                            d, college.getAverageSalaryByDepartment(d));
                }
                case 9  -> {
                    for (Lecturer l : college.getLecturers())
                        System.out.println(l);
                }
                case 10 -> {
                    for (Department dpt : college.getDepartments())
                        System.out.println(dpt);
                }
                case 11 -> {
                    for (Committee c : college.getCommittees())
                        System.out.println(c);
                }
                case 14 -> {
                    String n1 = readNonEmpty("First DR/Prof name: ");
                    String n2 = readNonEmpty("Second DR/Prof name: ");
                    System.out.println(college.compareByArticles(n1, n2));
                }
                case 15 -> {
                    String d1 = readNonEmpty("First department: ");
                    String d2 = readNonEmpty("Second department: ");
                    int crit = readInt("Criterion (1=#lecturers, 2=#articles): ", 1, 2);
                    System.out.println(college.compareDepartments(d1, d2, crit));
                }
                case 16 -> {
                    String orig = readNonEmpty("Committee to clone: ");
                    boolean ok = college.cloneCommittee(orig);
                    System.out.println(ok ? "Cloned successfully." : "Committee not found.");
                }
                default -> System.out.println("Option not implemented or invalid.");
            }
            System.out.println();
        }
    }

    // ————————— Flows —————————

    private static void flowAddLecturer(College c) {
        String name  = readNonEmpty("Lecturer name: ");
        int    id    = readInt("Lecturer ID (>0): ", 1, Integer.MAX_VALUE);

        // היפטרנו מ-List, משתמשים במערך רגיל
        String[] validDegrees = { "BA", "MA", "DR", "PROF" };
        String deg = readOption("Degree [BA, MA, DR, PROF]: ", validDegrees);

        String major = readNonEmpty("Major: ");
        double sal   = readDouble("Salary (>=0): ");

        Lecturer lec;
        if ("PROF".equals(deg)) {
            String body = readNonEmpty("Granting body: ");
            lec = new Professor(name, id, Degree.PROF, major, sal, body);
        } else if ("DR".equals(deg)) {
            lec = new ResearchLecturer(name, id, Degree.DR, major, sal);
        } else {
            lec = new Lecturer(name, id, Degree.valueOf(deg), major, sal);
        }
        System.out.println(c.addLecturer(lec) ? "Lecturer added." : "Already exists.");
    }

    private static void flowRemoveLecturer(College c) {
        String name = readNonEmpty("Lecturer name to remove: ");
        System.out.println(c.removeLecturer(name) ? "Removed." : "Not found.");
    }

    private static void flowAddDepartment(College c) {
        String name = readNonEmpty("Department name: ");
        int ns = readInt("Number of students (>=0): ", 0, Integer.MAX_VALUE);
        Department d = new Department(name, ns);
        System.out.println(c.addDepartment(d) ? "Dept added." : "Already exists.");
    }

    private static void flowRemoveDepartment(College c) {
        String name = readNonEmpty("Department to remove: ");
        System.out.println(c.removeDepartment(name) ? "Removed." : "Not found.");
    }

    private static void flowAddCommittee(College c) {
        String cn = readNonEmpty("Committee name: ");
        String ch = readNonEmpty("Chair name: ");
        Lecturer chair = c.findLecturerByName(ch);
        if (chair == null) {
            System.out.println("Chair not found.");
            return;
        }
        try {
            Committee cm = new Committee(cn, chair);
            System.out.println(c.addCommittee(cm) ? "Committee added." : "Already exists.");
        } catch (InvalidChairException e) {
            System.out.println("Cannot create committee: " + e.getMessage());
        }
    }

    private static void flowRemoveCommittee(College c) {
        String name = readNonEmpty("Committee to remove: ");
        System.out.println(c.removeCommittee(name) ? "Removed." : "Not found.");
    }

    // ————————— Input Helpers —————————

    private static String readNonEmpty(String prompt) {
        String s;
        do {
            System.out.print(prompt);
            s = sc.nextLine().trim();
            if (s.isEmpty()) System.out.println("⚠️ Input cannot be blank.");
        } while (s.isEmpty());
        return s;
    }

    private static int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int v = Integer.parseInt(sc.nextLine().trim());
                if (v < min || v > max) {
                    System.out.printf("⚠️ Enter a number between %d and %d.%n", min, max);
                } else {
                    return v;
                }
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Invalid integer, try again.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double v = Double.parseDouble(sc.nextLine().trim());
                if (v < 0) {
                    System.out.println("⚠️ Must be non-negative.");
                } else {
                    return v;
                }
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Invalid number, try again.");
            }
        }
    }

    private static String readOption(String prompt, String[] opts) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim().toUpperCase();
            for (String o : opts) {
                if (o.equals(s)) {
                    return s;
                }
            }
            System.out.println("⚠️ Please enter one of: " + String.join(", ", opts));
        }
    }

    private static void printMenu() {
        System.out.println("=== ACADEMIC MANAGER ===");
        System.out.println("0  – Exit");
        System.out.println("1  – Add Lecturer");
        System.out.println("2  – Remove Lecturer");
        System.out.println("3  – Add Department");
        System.out.println("4  – Remove Department");
        System.out.println("5  – Add Committee");
        System.out.println("6  – Remove Committee");
        System.out.println("7  – Avg salary (all)");
        System.out.println("8  – Avg salary by dept");
        System.out.println("9  – List all lecturers");
        System.out.println("10 – List all departments");
        System.out.println("11 – List all committees");
        System.out.println("14 – Compare DR/Prof by # articles");
        System.out.println("15 – Compare two departments");
        System.out.println("16 – Clone committee");
        System.out.print("> ");
    }
}
