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
                    case 13 -> flowCompareCommitteesNew(college);
                    case 14 -> flowCompareProfessorsByArticles(college);
                    case 15 -> flowCloneCommittee(college);
                    default -> System.out.println("⚠ Option not implemented.");
                }
            }  catch (ValidationException |
                DuplicateEntityException |
                EntityNotFoundException ex) {
            System.out.println("Error: " + ex.getMessage());
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
            7  - Remove Lecturer from Department 
            8  - Remove Lecturer from Committee
            9  - Average salary (All Lecturers)
            10 - Average salary by Department
            11 - Display All Lecturers
            12 - Display All Committees
            13 - Compare two Committees(by memberCount or articleCount)
            14 - Compare two professors 
            15 - Clone Committee\s""";

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
            System.out.println("Error: Lecturer '" + ch.trim() + "' not found.");
            return;
        }

        try {
            Committee com = new Committee(cn.trim(), chair);
            c.addCommittee(com);
            System.out.println("Committee added successfully.");
        } catch (ValidationException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
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

    private static void flowCompareCommitteesNew(College c) {
        System.out.println("--- Compare Committees (choose criterion) ---");

        // 1. קלט שמות שתי הוועדות
        String name1 = readNonEmpty("First Committee name: ", "Committee name");
        Committee com1 = c.findCommitteeByName(name1);
        if (com1 == null) {
            System.out.printf("⚠ Committee \"%s\" not found.%n", name1);
            return;
        }

        String name2 = readNonEmpty("Second Committee name: ", "Committee name");
        Committee com2 = c.findCommitteeByName(name2);
        if (com2 == null) {
            System.out.printf("⚠ Committee \"%s\" not found.%n", name2);
            return;
        }

        System.out.println("Choose comparison criterion:");
        System.out.println("  1. Number of Articles (all members)");
        System.out.println("  2. Number of Members (excluding chair)");
        int criterion = readInt("Enter 1 or 2: ", 1, 2);

        if (criterion == 1) {
            int totalArticles1 = 0;
            // פה זה החישוב של היושב ראש. תראי אם את רוצה לשנות
            if (com1.getChair() instanceof Researcher) {
                totalArticles1 += ((Researcher) com1.getChair()).getArticleCount();
            }
            // פה זה החישוב של סהכ כל המאמשים של חברי הוועדה
            for (Lecturer member : com1.getMembers()) {
                if (member instanceof Researcher) {
                    totalArticles1 += ((Researcher) member).getArticleCount();
                }
            }

            int totalArticles2 = 0;
            if (com2.getChair() instanceof Researcher) {
                totalArticles2 += ((Researcher) com2.getChair()).getArticleCount();
            }
            for (Lecturer member : com2.getMembers()) {
                if (member instanceof Researcher) {
                    totalArticles2 += ((Researcher) member).getArticleCount();
                }
            }

            System.out.printf("» \"%s\" total articles: %d.%n", name1, totalArticles1);
            System.out.printf("» \"%s\" total articles: %d.%n", name2, totalArticles2);

            if (totalArticles1 == totalArticles2) {
                System.out.printf("Result: Tie – both committees have %d article(s).%n", totalArticles1);
            } else if (totalArticles1 > totalArticles2) {
                System.out.printf("Result: \"%s\" wins with %d vs %d articles.%n",
                        name1, totalArticles1, totalArticles2);
            } else {
                System.out.printf("Result: \"%s\" wins with %d vs %d articles.%n",
                        name2, totalArticles2, totalArticles1);
            }

        } else {

            int membersCount1 = com1.getMembers().length;
            int membersCount2 = com2.getMembers().length;
            System.out.printf("» \"%s\" has %d member(s).%n", name1, membersCount1);
            System.out.printf("» \"%s\" has %d member(s).%n", name2, membersCount2);

            if (membersCount1 == membersCount2) {
                System.out.printf("Result: Tie – both have %d member(s).%n", membersCount1);
            } else if (membersCount1 > membersCount2) {
                System.out.printf("Result: \"%s\" wins with %d vs %d members.%n",
                        name1, membersCount1, membersCount2);
            } else {
                System.out.printf("Result: \"%s\" wins with %d vs %d members.%n",
                        name2, membersCount2, membersCount1);
            }
        }
    }
    private static void flowCompareProfessorsByArticles(College c) {
        System.out.println("--- Compare Professors by Number of Articles ---");

        String name1 = readNonEmpty("First Professor/ResearchLecturer name: ", "Lecturer name");
        Lecturer lec1 = c.findLecturerByName(name1);
        if (lec1 == null) {
            System.out.printf("⚠ Lecturer \"%s\" not found.%n", name1);
            return;
        }
        if (!(lec1 instanceof Researcher)) {
            System.out.printf("⚠ Lecturer \"%s\" is not a researcher/Professor.%n", name1);
            return;
        }

        String name2 = readNonEmpty("Second Professor/ResearchLecturer name: ", "Lecturer name");
        Lecturer lec2 = c.findLecturerByName(name2);
        if (lec2 == null) {
            System.out.printf("⚠ Lecturer \"%s\" not found.%n", name2);
            return;
        }
        if (!(lec2 instanceof Researcher)) {
            System.out.printf("⚠ Lecturer \"%s\" is not a researcher/Professor.%n", name2);
            return;
        }

        Researcher r1 = (Researcher) lec1;
        Researcher r2 = (Researcher) lec2;
        int count1 = r1.getArticleCount();
        int count2 = r2.getArticleCount();

        System.out.printf("» %s has %d article(s).%n", name1, count1);
        System.out.printf("» %s has %d article(s).%n", name2, count2);

        if (count1 == count2) {
            System.out.printf("Result: Tie – both have %d article(s).%n", count1);
        } else if (count1 > count2) {
            System.out.printf("Result: \"%s\" wins with %d vs %d articles.%n", name1, count1, count2);
        } else {
            System.out.printf("Result: \"%s\" wins with %d vs %d articles.%n", name2, count2, count1);
        }
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
            throw new EntityNotFoundException("Committee", cn.trim());
        }
        Lecturer lec = c.findLecturerByName(ln);
        if (lec == null) {
            throw new EntityNotFoundException("Lecturer", ln.trim());
        }
        boolean removed = cm.removeMember(lec);
        if (!removed) {
            throw new ValidationException(
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
