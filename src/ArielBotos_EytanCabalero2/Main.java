// Main.java
package ArielBotos_EytanCabalero2;

import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        String collegeName = readNonEmpty("Enter college name: ");
        College college  = new College(collegeName);

        while (true) {
            mainMenu();
            int choice = readInt("Choose an option (0–15): ", 0, 19);
            System.out.println();
            switch (choice) {
                case 0  -> { System.out.println("Goodbye!"); return; }
                case 1  -> flowAddLecturer(college);
                case 2  -> flowRemoveLecturer(college);
                case 3  -> flowAddDepartment(college);
                case 4  -> flowAddCommittee(college);
                case 5  -> flowRemoveCommittee(college);
                case 6  -> System.out.printf("Average salary: ₪%.2f%n",
                        college.getAverageSalaryAllLecturers());
                case 7  -> flowAverageByDepartment(college);
                case 8  -> flowListAll(college.getLecturers());
                case 9 -> flowListAll(college.getCommittees());
                case 10 -> flowAddLecturerToDepartment(college);
                case 11-> flowAddLecturerToCommittee(college);
                case 12 -> flowCompareCommittees(college);
                case 13 -> flowCompareDepartments(college);
                case 14 -> {
                    flowCloneCommittee(college);
                }
                case 15-> flowRemoveCommitteeMember(college);

                default -> System.out.println("⚠️ Option not implemented.");
            }
            System.out.println();
        }
    }

    private static void flowAddLecturer(College c) {
        System.out.println("=== Add Lecturer ===");
        String name  = readNonEmpty("Lecturer name: ");
        int    id    = readInt("Lecturer ID (>0): ", 1, Integer.MAX_VALUE);
        String[] validDegrees = {"BA","MA","DR","PROF"};
        String deg = readOption("Degree [BA, MA, DR, PROF]: ", validDegrees);
        String major = readNonEmpty("Major: ");
        double sal   = readDouble("Salary (>=0): ");

        if ("DR".equals(deg) || "PROF".equals(deg)) {

            int numArt = readInt("Number of articles: ", 0, Integer.MAX_VALUE);
            String[] articles = new String[numArt];
            for (int i = 1; i <= numArt; i++) {
                articles[i-1] = readNonEmpty("Article " + i + " title: ");
            }

            if ("PROF".equals(deg)) {
                String body = readNonEmpty("Granting body: ");
                Professor prof = new Professor(name, id, Degree.PROF, major, sal, body);

                for (String title : articles) {
                    prof.addArticle(title);
                }
                System.out.println(c.addLecturer(prof)
                        ? "Professor added with articles."
                        : "Already exists.");
            } else {
                ResearchLecturer rl = new ResearchLecturer(name, id, Degree.DR, major, sal);
                // הוספת המאמרים ל־ResearchLecturer
                for (String title : articles) {
                    rl.addArticle(title);
                }
                System.out.println(c.addLecturer(rl)
                        ? "Research Lecturer added with articles."
                        : "Already exists.");
            }
        } else {
            Lecturer l = new Lecturer(name, id, Degree.valueOf(deg), major, sal);
            System.out.println(c.addLecturer(l)
                    ? "Lecturer added."
                    : "Already exists.");
        }
    }


    private static void flowRemoveLecturer(College c) {
        String name = readNonEmpty("Lecturer name to remove: ");
        System.out.println(c.removeLecturer(name) ? "Removed." : "Not found.");
    }

    private static void flowAddDepartment(College c) {
        System.out.println("=== Add Department ===");
        String name = readNonEmpty("Department name: ");
        int ns = readInt("Number of students (>=0): ", 0, Integer.MAX_VALUE);
        Department d = new Department(name, ns);
        System.out.println(c.addDepartment(d) ? "Dept added." : "Already exists.");
    }



    private static void flowAddCommittee(College c) {
        System.out.println("=== Add Committee ===");
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
    private static void flowCompareCommittees(College college) {
        String first = readNonEmpty("Enter first committee name: ");
        String second = readNonEmpty("Enter second committee name: ");
        System.out.println(college.compareCommittees(first, second));
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


    private static void flowAddLecturerToDepartment(College c) {
        String dn = readNonEmpty("Department name: ");
        Department dept = c.findDepartmentByName(dn);
        if (dept == null) {
            System.out.println("⚠️ Department not found."); return;
        }
        String ln = readNonEmpty("Lecturer name: ");
        Lecturer lec = c.findLecturerByName(ln);
        if (lec == null) {
            System.out.println("⚠️ Lecturer not found."); return;
        }
        boolean ok = dept.addLecturer(lec);
        System.out.println(ok
                ? "Lecturer added to department."
                : "⚠️ Lecturer already in department.");
    }

    private static void flowAddLecturerToCommittee(College c) {
        String cn = readNonEmpty("Committee name: ");
        Committee cm = c.findCommitteeByName(cn);
        if (cm == null) {
            System.out.println("⚠️ Committee not found."); return;
        }
        String ln = readNonEmpty("Lecturer name: ");
        Lecturer lec = c.findLecturerByName(ln);
        if (lec == null) {
            System.out.println("⚠️ Lecturer not found."); return;
        }
        boolean ok = cm.addMember(lec);
        System.out.println(ok
                ? "Lecturer added to committee."
                : "⚠️ Lecturer already in committee.");
    }


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
                int x = max -4;
                if (v < min || v > max) System.out.println("⚠️ Must be between " + min + "–" + x + ".");
                else return v;
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Invalid integer, try again.");
            }
        }
    }

    private static String readOption(String prompt, String[] opts) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim().toUpperCase();
            for (String o : opts) if (o.equals(s)) return s;
            System.out.println("⚠️ Invalid option, choose one of " + String.join(", ", opts) + ".");
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
    private static void flowListAll(Object[] items) {
        if (items.length == 0) {
            System.out.println("⚠️  " + "has no members.");
            return;
        }
        for (Object item : items) {
            System.out.println(item);
        }
    }


    private static final String[] MENU ={
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
            "9 - List all committees",
            "10 - Add lecturer to department",
            "11 - Add lecturer to committee",
            "12 - Compare two committees by articles and membersCount",
            "13 - Compare two departments",
            "14 - Clone committee",
            "15 - Remove committee member",
            "================================ "
    };
    public static void mainMenu() {
        for (int i = 0; i < MENU.length; i++) {
            System.out.println(MENU[i]);
        }
    }
}
