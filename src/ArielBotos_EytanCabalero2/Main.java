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
            int choice = readInt("Choose an option (0–17): ", 0, 17);
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
                case 8  -> flowAverageByDepartment(college);
                case 9  -> flowListAll(college.getLecturers());
                case 10 -> flowListAll(college.getDepartments());
                case 11 -> flowListAll(college.getCommittees());
                case 14 -> flowCompareArticles(college);
                case 15 -> flowCompareDepartments(college);
                case 16 -> flowCloneCommittee(college);
                case 17 -> flowRemoveCommitteeMember(college);
                default -> System.out.println("Option not implemented or invalid.");
            }
            System.out.println();
        }
    }

    // — Flows —
    private static void flowAddLecturer(College c) {
        String name  = readNonEmpty("Lecturer name: ");
        int    id    = readInt("Lecturer ID (>0): ", 1, Integer.MAX_VALUE);
        String[] validDegrees = {"BA","MA","DR","PROF"};
        String deg = readOption("Degree [BA, MA, DR, PROF]: ", validDegrees);
        String major = readNonEmpty("Major: ");
        double sal   = readDouble("Salary (>=0): ");

        // Instantiate lecturer according to degree
        if ("DR".equals(deg) || "PROF".equals(deg)) {
            // ResearchLecturer or Professor
            ResearchLecturer rl;
            if ("PROF".equals(deg)) {
                String body = readNonEmpty("Granting body: ");
                rl = new Professor(name, id, Degree.PROF, major, sal, body);
            } else {
                rl = new ResearchLecturer(name, id, Degree.DR, major, sal);
            }
            // Prompt for articles
            int n = readInt("Number of articles: ", 0, Integer.MAX_VALUE);
            for (int i = 1; i <= n; i++) {
                String title = readNonEmpty("  Article " + i + " title: ");
                rl.addArticle(title);
            }
            System.out.println(c.addLecturer(rl) ? "Lecturer added with articles." : "Already exists.");
        } else {
            // Regular lecturer
            Lecturer l = new Lecturer(name, id, Degree.valueOf(deg), major, sal);
            System.out.println(c.addLecturer(l) ? "Lecturer added." : "Already exists.");
        }
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
            System.out.println("⚠️ Chair not found."); return;
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

    private static void flowAverageByDepartment(College c) {
        String d = readNonEmpty("Department name: ");
        Department dept = c.findDepartmentByName(d);
        if (dept == null) {
            System.out.println("⚠️ Department '" + d + "' not found.");
        } else {
            System.out.printf("Average in %s: ₪%.2f%n", d,
                    c.getAverageSalaryByDepartment(d));
        }
    }

    private static void flowListAll(Object[] arr) {
        for (Object o : arr) System.out.println(o);
    }

    private static void flowCompareArticles(College c) {
        String n1 = readNonEmpty("First DR/Prof name: ");
        String n2 = readNonEmpty("Second DR/Prof name: ");
        System.out.println(c.compareByArticles(n1, n2));
    }

    private static void flowCompareDepartments(College c) {
        String d1 = readNonEmpty("First department: ");
        String d2 = readNonEmpty("Second department: ");
        int crit = readInt("Criterion (1=#lecturers, 2=#articles): ", 1, 2);
        System.out.println(c.compareDepartments(d1, d2, crit));
    }

    private static void flowCloneCommittee(College c) {
        String orig = readNonEmpty("Committee to clone: ");
        String newName = orig + "-new";
        if (c.cloneCommittee(orig))
            System.out.println("Cloned successfully as '" + newName + "'.");
        else
            System.out.println("⚠️ Committee '" + orig + "' not found.");
    }

    private static void flowRemoveCommitteeMember(College c) {
        String cn = readNonEmpty("Committee name: ");
        Committee cm = c.findCommitteeByName(cn);
        if (cm == null) {
            System.out.println("⚠️ Committee not found."); return;
        }
        String mn = readNonEmpty("Member name to remove: ");
        Lecturer ml = c.findLecturerByName(mn);
        if (ml == null) {
            System.out.println("⚠️ Lecturer not found."); return;
        }
        System.out.println(cm.removeMember(ml)
                ? "Member removed." : "⚠️ That lecturer is not in committee.");
    }

    // — Input Helpers —
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
                if (v < min || v > max)
                    System.out.printf("⚠️ Enter a number between %d and %d.%n", min, max);
                else return v;
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
                if (v < 0) System.out.println("⚠️ Must be non-negative.");
                else return v;
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Invalid number, try again.");
            }
        }
    }

    private static String readOption(String prompt, String[] opts) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim().toUpperCase();
            for (String o : opts) if (o.equals(s)) return s;
            System.out.print("⚠️ Please enter one of:");
            for (String o : opts) System.out.print(" " + o);
            System.out.println();
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
        System.out.println("17 – Remove committee member");
        System.out.print("> ");
    }
}
