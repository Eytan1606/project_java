package ArielBotos_EytanCabalero2;

import java.util.Scanner;

public class Main1 {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        String collegeName = readNonEmpty("Enter college name: ", "College name");
        College college  = new College(collegeName);

        while (true) {
            mainMenu();
            int choice = readInt("\nChoose an option (0–15): ", 0, 15);
            System.out.println();
            try {
                switch (choice) {
                    case 0  -> { System.out.println("Exiting. Bye!"); return; }
                    case 1  -> flowAddLecturer(college);
                    case 2  -> flowAddDepartment(college);
                    case 3  -> flowAddCommittee(college);
                    case 4  -> flowRemoveCommittee(college);
                    case 5  -> flowAddLecturerToDepartment(college);
                    case 6  -> flowAddLecturerToCommittee(college);
                    case 7  -> flowRemoveLecturer(college);
                    case 8  -> flowRemoveCommitteeMember(college);
                    case 9  -> System.out.printf("Average salary: ₪%.2f%n", college.getAverageSalaryAllLecturers());
                    case 10 -> flowAverageByDepartment(college);
                    case 11 -> flowListAll(college.getLecturers());
                    case 12 -> flowListAll(college.getCommittees());
                    case 13 -> flowCompareCommittees(college);
                    case 14 -> flowCompareDepartments(college);
                    case 15 -> flowCloneCommittee(college);
                    default -> System.out.println("⚠ Option not implemented.");
                }
            } catch (NullInputException |
                     DuplicateLecturerException |
                     LecturerNotFoundException |
                     DuplicateDepartmentException |
                     DepartmentNotFoundException |
                     DuplicateCommitteeException |
                     CommitteeNotFoundException |
                     AssignmentException |
                     InvalidOperationException ex) {
                System.out.println("⚠ Error: " + ex.getMessage());
            }
            System.out.println();
        }
    }

    private static final String MENU = """
            \n--- ACADEMIC MANAGER ---
            0  - Exit
            1  - Add Lecturer
            2  - Add Department
            3  - Add Committee
            4  - Remove Committee
            5  - Assign Lecturer to Department
            6  - Assign Lecturer to Committee
            7  - Remove Lecturer from Department // לא יודעת למה התכוונת כי היה רשום רק remove lecturer אבל המתודה עושה את זה ->>
            8  - Remove Lecturer from Committee
            9  - Average salary (All Lecturers)
            10 - Average salary by Department
            11 - Display All Lecturers
            12 - Display All Committees
            13 - Compare two Committees // ביקשו לתת אפשרות להשוואה לשני קריטריונים - כלומר אחרי שבוחרים 13 צריך עוד שאלה של על פי איזה קריטריון רוצים להשוות
            // צריך להוסיף השוואה בין ד"ר לפרופסור על פי מספר המאמרים
            14 - Compare two Departments // לא ביקשו להשוות
            15 - Clone Committee\s""";
            // אני פתאום חושבת על זה ואין בכלל אפשרות להסיר מרצה וועדה מcollege

    private static void flowAddLecturer(College c) {
        System.out.println("--- Add Lecturer ---");
        String name  = readNonEmpty("Lecturer name: ", "Lecturer name");
        int    id    = readInt("Lecturer ID (>0): ", 1, Integer.MAX_VALUE);
        String[] validDegrees = { "BA", "MA", "DR", "PROF" };
        String deg = readOption("Degree [BA, MA, DR, PROF]: ", validDegrees);
        String major = readNonEmpty("Major: ", "Major");
        double sal   = readDouble("Salary (>=0): ");

        if ("DR".equalsIgnoreCase(deg) || "PROF".equalsIgnoreCase(deg)) {
            int numArt = readInt("Number of articles: ", 0, Integer.MAX_VALUE);
            String[] articles = new String[numArt];
            for (int i = 1; i <= numArt; i++) {
                articles[i - 1] = readNonEmpty("Article " + i + " title: ", "Article " + i + " title");
            }

            if ("PROF".equalsIgnoreCase(deg)) {
                String body = readNonEmpty("Granting body: ", "Granting body");
                Professor prof = new Professor(name, id, Degree.PROF, major, sal, body);
                for (String art : articles) {
                    prof.addArticle(art);
                }
                c.addLecturer(prof);
                System.out.println("Lecturer added successfully.");
                return;
            }

            ResearchLecturer rl = new ResearchLecturer(name, id, Degree.DR, major, sal);
            for (String art : articles) {
                rl.addArticle(art);
            }
            c.addLecturer(rl);
            System.out.println("Lecturer added successfully.");
        } else {
            Lecturer l = new Lecturer(name, id, Degree.valueOf(deg.toUpperCase()), major, sal);
            c.addLecturer(l);
            System.out.println("Lecturer added successfully.");
        }
    }

    private static void flowRemoveLecturer(College c) {
        System.out.println("--- Remove Lecturer from Department ---");
        String name = readNonEmpty("Lecturer name to remove: ", "Lecturer name");
        c.removeLecturer(name);
        System.out.println("Lecturer removed successfully.");
    }

    private static void flowAddDepartment(College c) {
        System.out.println("--- Add Department ---");
        String name = readNonEmpty("Department name: ", "Department name");
        int ns = readInt("Number of students (>=0): ", 0, Integer.MAX_VALUE);
        Department d = new Department(name, ns);
        c.addDepartment(d);
        System.out.println("Department added successfully.");
    }

    private static void flowAddCommittee(College c) {
        System.out.println("--- Add Committee ---");
        String cn = readNonEmpty("Committee name: ", "Committee name");
        String ch = readNonEmpty("Chair name: ", "Chair name");
        Lecturer chair = c.findLecturerByName(ch);
        if (chair == null) {
            throw new LecturerNotFoundException("Chair '" + ch.trim() + "' not found.");
        }
        Committee com = new Committee(cn.trim(), chair);
        c.addCommittee(com);
        System.out.println("Committee added successfully.");
    }

    private static void flowRemoveCommittee(College c) {
        System.out.println("--- Remove Committee ---");
        String name = readNonEmpty("Committee to remove: ", "Committee name");
        c.removeCommittee(name);
        System.out.println("Committee removed successfully.");
    }

    private static void flowAverageByDepartment(College c) {
        System.out.println("--- Average Salary by Department ---");
        String d = readNonEmpty("Department name: ", "Department name");
        double avg = c.getAverageSalaryByDepartment(d);
        System.out.printf("Average in %s: ₪%.2f%n", d.trim(), avg);
    }

    private static void flowListAll(Object[] items) {
        if (items.length == 0) {
            System.out.println("⚠ has no members.");
            return;
        }
        for (Object item : items) {
            System.out.println(item);
        }
    }

    private static void flowAddLecturerToDepartment(College c) {
        System.out.println("--- Assign Lecturer to Department ---");
        String ln = readNonEmpty("Lecturer name: ", "Lecturer name");
        String dn = readNonEmpty("Department name: ", "Department name");
        c.addLecturerToDepartment(ln, dn);
        System.out.println("Lecturer added to Department successfully.");
    }

    private static void flowAddLecturerToCommittee(College c) {
        System.out.println("--- Assign Lecturer to Committee ---");
        String cn = readNonEmpty("Committee name: ", "Committee name");
        String ln = readNonEmpty("Lecturer name: ", "Lecturer name");
        c.addLecturerToCommittee(ln, cn);
        System.out.println("Lecturer added to Committee successfully.");
    }

    private static void flowCompareCommittees(College c) {
        System.out.println("--- Compare Committees ---");
        String c1 = readNonEmpty("First committee: ", "Committee name");
        String c2 = readNonEmpty("Second committee: ", "Committee name");
        String result = c.compareCommittees(c1, c2);
        System.out.println(result);
    }

    private static void flowCompareDepartments(College c) {
        System.out.println("--- Compare Departments ---");
        String d1 = readNonEmpty("First department: ", "Department name");
        String d2 = readNonEmpty("Second department: ", "Department name");
        int crit = readInt("Criterion (1=#lecturers, 2=#articles): ", 1, 2);
        String result = c.compareDepartments(d1, d2, crit);
        System.out.println(result);
    }

    private static void flowCloneCommittee(College c) {
        System.out.println("--- Clone Committee ---");
        String orig = readNonEmpty("Committee to clone: ", "Committee name");
        c.cloneCommittee(orig);
        System.out.println("Cloned successfully as '" + orig.trim() + "-new'.");
    }

    private static void flowRemoveCommitteeMember(College c) {
        System.out.println("--- Remove Committee Member ---");
        String cn = readNonEmpty("Committee name: ", "Committee name");
        String ln = readNonEmpty("Lecturer name: ", "Lecturer name");
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
                    "Lecturer '" + ln.trim() + "' is not a member of Committee '" + cm.getName() + "'."
            );
        }
        System.out.println("Lecturer removed from Committee successfully.");
    }

    public static String readNonEmpty(String prompt, String label) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println(label + " cannot be empty, please try again.");
        }
    }

    private static int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int v = Integer.parseInt(sc.nextLine().trim());
                if (v < min || v > max) {
                    System.out.println("⚠ Must be between " + min + " and " + max + ".");
                } else {
                    return v;
                }
            } catch (NumberFormatException e) {
                System.out.println("⚠ Invalid number, please try again.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double v = Double.parseDouble(sc.nextLine().trim());
                if (v < 0) {
                    System.out.println("⚠ Must be non-negative.");
                } else {
                    return v;
                }
            } catch (NumberFormatException e) {
                System.out.println("⚠ Invalid number, please try again.");
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
            System.out.println("⚠ Invalid option, choose one of " + String.join(", ", opts) + ".");
        }
    }

    public static void mainMenu() {
        System.out.println(MENU);
    }
}
