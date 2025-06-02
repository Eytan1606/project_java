package ArielBotos_EytanCabalero2;

import java.util.Scanner;

public class Main1 {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        String collegeName = readNonEmpty("Enter college name: ");
        College college  = new College(collegeName);

        while (true) {
            mainMenu();
            int choice = readInt("Choose an option (0–15): ", 0, 19);
            System.out.println();
            try {
                switch (choice) {
                    case 0  -> { System.out.println("Exiting. Bye!"); return; }
                    case 1  -> flowAddLecturer(college);
                    case 2  -> flowRemoveLecturer(college);
                    case 3  -> flowAddDepartment(college);
                    case 4  -> flowAddCommittee(college);
                    case 5  -> flowRemoveCommittee(college);
                    case 6  -> System.out.printf("Average salary: ₪%.2f%n", college.getAverageSalaryAllLecturers());
                    case 7  -> flowAverageByDepartment(college);
                    case 8  -> flowListAll(college.getLecturers());
                    case 9  -> flowListAll(college.getCommittees());
                    case 10 -> flowAddLecturerToDepartment(college);
                    case 11 -> flowAddLecturerToCommittee(college);
                    case 12 -> flowCompareCommittees(college);
                    case 13 -> flowCompareDepartments(college);
                    case 14 -> flowCloneCommittee(college);
                    case 15 -> flowRemoveCommitteeMember(college);
                    default -> System.out.println("⚠️ Option not implemented.");
                }
            } catch (DuplicateLecturerException |
                     LecturerNotFoundException |
                     DuplicateDepartmentException |
                     DepartmentNotFoundException |
                     DuplicateCommitteeException |
                     CommitteeNotFoundException |
                     AssignmentException |
                     InvalidOperationException ex) {
                System.out.println("⚠️ Error: " + ex.getMessage());
            }
            System.out.println();
        }
    }

    private static void flowAddLecturer(College c) {
        System.out.println("=== Add Lecturer ===");
        String name  = readNonEmpty("Lecturer name: ");
        int    id    = readInt("Lecturer ID (>0): ", 1, Integer.MAX_VALUE);
        String[] validDegrees = { "BA", "MA", "DR", "PROF" };
        String deg = readOption("Degree [BA, MA, DR, PROF]: ", validDegrees);
        String major = readNonEmpty("Major: ");
        double sal   = readDouble("Salary (>=0): ");

        if ("DR".equalsIgnoreCase(deg) || "PROF".equalsIgnoreCase(deg)) {
            int numArt = readInt("Number of articles: ", 0, Integer.MAX_VALUE);
            String[] articles = new String[numArt];
            for (int i = 1; i <= numArt; i++) {
                articles[i - 1] = readNonEmpty("Article " + i + " title: ");
            }

            if ("PROF".equalsIgnoreCase(deg)) {
                String body = readNonEmpty("Granting body: ");
                Professor prof = new Professor(name, id, Degree.PROF, major, sal, body);
                for (String art : articles) {
                    prof.addArticle(art);
                }
                c.addLecturer(prof);
                System.out.println("Lecturer added.");
                return;
            }

            ResearchLecturer rl = new ResearchLecturer(name, id, Degree.DR, major, sal);
            for (String art : articles) {
                rl.addArticle(art);
            }
            c.addLecturer(rl);
            System.out.println("Lecturer added.");
        } else {
            Lecturer l = new Lecturer(name, id, Degree.valueOf(deg.toUpperCase()), major, sal);
            c.addLecturer(l);
            System.out.println("Lecturer added.");
        }
    }

    private static void flowRemoveLecturer(College c) {
        System.out.println("=== Remove Lecturer ===");
        String name = readNonEmpty("Lecturer name to remove: ");
        c.removeLecturer(name);
        System.out.println("Lecturer removed.");
    }

    private static void flowAddDepartment(College c) {
        System.out.println("=== Add Department ===");
        String name = readNonEmpty("Department name: ");
        int ns = readInt("Number of students (>=0): ", 0, Integer.MAX_VALUE);
        Department d = new Department(name, ns);
        c.addDepartment(d);
        System.out.println("Dept added.");
    }

    private static void flowAddCommittee(College c) {
        System.out.println("=== Add Committee ===");
        String cn = readNonEmpty("Committee name: ");
        String ch = readNonEmpty("Chair name: ");
        Lecturer chair = c.findLecturerByName(ch);
        if (chair == null) {
            throw new LecturerNotFoundException("Chair '" + ch.trim() + "' not found.");
        }
        Committee com = new Committee(cn.trim(), chair);
        c.addCommittee(com);
        System.out.println("Committee added.");
    }

    private static void flowRemoveCommittee(College c) {
        System.out.println("=== Remove Committee ===");
        String name = readNonEmpty("Committee to remove: ");
        c.removeCommittee(name);
        System.out.println("Committee removed.");
    }

    private static void flowAverageByDepartment(College c) {
        System.out.println("=== Average Salary by Department ===");
        String d = readNonEmpty("Department name: ");
        double avg = c.getAverageSalaryByDepartment(d);
        System.out.printf("Average in %s: ₪%.2f%n", d.trim(), avg);
    }

    private static void flowListAll(Object[] items) {
        if (items.length == 0) {
            System.out.println("⚠️  has no members.");
            return;
        }
        for (Object item : items) {
            System.out.println(item);
        }
    }

    private static void flowAddLecturerToDepartment(College c) {
        System.out.println("=== Add Lecturer to Department ===");
        String ln = readNonEmpty("Lecturer name: ");
        String dn = readNonEmpty("Department name: ");
        c.addLecturerToDepartment(ln, dn);
        System.out.println("Lecturer added to department.");
    }

    private static void flowAddLecturerToCommittee(College c) {
        System.out.println("=== Add Lecturer to Committee ===");
        String cn = readNonEmpty("Committee name: ");
        String ln = readNonEmpty("Lecturer name: ");
        c.addLecturerToCommittee(ln, cn);
        System.out.println("Lecturer added to committee.");
    }

    private static void flowCompareCommittees(College c) {
        System.out.println("=== Compare Committees ===");
        String c1 = readNonEmpty("First committee: ");
        String c2 = readNonEmpty("Second committee: ");
        String result = c.compareCommittees(c1, c2);
        System.out.println(result);
    }

    private static void flowCompareDepartments(College c) {
        System.out.println("=== Compare Departments ===");
        String d1 = readNonEmpty("First department: ");
        String d2 = readNonEmpty("Second department: ");
        int crit = readInt("Criterion (1=#lecturers, 2=#articles): ", 1, 2);
        String result = c.compareDepartments(d1, d2, crit);
        System.out.println(result);
    }

    private static void flowCloneCommittee(College c) {
        System.out.println("=== Clone Committee ===");
        String orig = readNonEmpty("Committee to clone: ");
        c.cloneCommittee(orig);
        System.out.println("Cloned successfully as '" + orig.trim() + "-new'.");
    }

    private static void flowRemoveCommitteeMember(College c) {
        System.out.println("=== Remove Committee Member ===");
        String cn = readNonEmpty("Committee name: ");
        String ln = readNonEmpty("Lecturer name: ");
        Committee cm = c.findCommitteeByName(cn);
        if (cm == null) {
            throw new CommitteeNotFoundException("Committee '" + cn.trim() + "' not found.");
        }
        Lecturer lec = c.findLecturerByName(ln);
        if (lec == null) {
            throw new LecturerNotFoundException("Lecturer '" + ln.trim() + "' not found.");
        }
        boolean removed = cm.removeMember(lec);
        if (!removed) {
            throw new AssignmentException(
                    "Lecturer '" + ln.trim() + "' is not a member of committee '" + cm.getName() + "'."
            );
        }
        System.out.println("Lecturer removed from committee.");
    }

    private static String readNonEmpty(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            if (!s.isEmpty()) return s;
            System.out.println("⚠️ Cannot be empty, try again.");
        }
    }

    private static int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int v = Integer.parseInt(sc.nextLine().trim());
                if (v < min || v > max) {
                    System.out.println("⚠️ Must be between " + min + " and " + max + ".");
                } else {
                    return v;
                }
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Invalid number, try again.");
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
            System.out.println("⚠️ Invalid option, choose one of " + String.join(", ", opts) + ".");
        }
    }

    private static final String[] MENU = {
            "=== ACADEMIC MANAGER ===",
            "0  - Exit",
            "1  - Add Lecturer",
            "2  - Remove Lecturer",
            "3  - Add Department",
            "4  - Add Committee",
            "5  - Remove Committee",
            "6  - Avg salary (all)",
            "7  - Avg salary by dept",
            "8  - List all lecturers",
            "9  - List all committees",
            "10 - Add lecturer to department",
            "11 - Add lecturer to committee",
            "12 - Compare two committees",
            "13 - Compare two departments",
            "14 - Clone committee",
            "15 - Remove committee member",
            "================================ "
    };

    public static void mainMenu() {
        for (String line : MENU) {
            System.out.println(line);
        }
    }
}
