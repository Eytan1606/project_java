package ArielBotos_EytanCabalero;

import java.util.Objects;
import java.util.Scanner;

public class College {
    private static final Scanner sc = new Scanner(System.in);
    private final String collegeName;

    private CustomArray<Lecturer> lecturers = new CustomArray<>();
    private CustomArray<Department> departments = new CustomArray<>();
    private CustomArray<Committee> committees = new CustomArray<>();

    public College(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new NullInputException();
        }
        this.collegeName    = name.trim();
    }

    public boolean addLecturer(Lecturer l) {
        Objects.requireNonNull(l, "Lecturer cannot be null");
        if (!lecturers.contains(l)) {
            lecturers.add(l);
            return true;
        }
        return false;
    }

    public Lecturer findLecturerByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }
        String key = name.trim();
        // במקום for על המערך הישן – לולאה על המערך שמחזיר getLecturers()
        for (Lecturer l : getLecturers()) {
            if (l.getName().equalsIgnoreCase(key)) {
                return l;
            }
        }
        return null;
    }

    public void removeLecturer(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Lecturer name cannot be empty");
        }
        Lecturer toRemove = findLecturerByName(name);
        if (toRemove == null) {
            throw new EntityNotFoundException("Lecturer '",name.trim());
        }
        removeLecturer(toRemove);
    }

    private void removeLecturer(Lecturer l) {
        if (l == null) {
            throw new ValidationException("Lecturer cannot be null");
        }
        if (!lecturers.contains(l)) {
            throw new EntityNotFoundException("Lecturer", String.valueOf(l.getId()));
        }

        l.removeFromDepartment();

        for (Committee c : l.getCommittees()) {
            l.removeFromCommittee(c);
        }

        lecturers.remove(l);
    }



    public Lecturer[] getLecturers() {
        return lecturers.toArray(new Lecturer[0]);
    }

    public void addLecturerToDepartment(String lecturerName, String deptName) {
        Lecturer lec = findLecturerByName(lecturerName);
        if (lec == null) {
            throw new EntityNotFoundException("Lecturer", lecturerName.trim());
        }
        Department dept = findDepartmentByName(deptName);
        if (dept == null) {
            throw new EntityNotFoundException("Department", deptName.trim());
        }
        boolean added = lec.assignToDepartment(dept);
        if (!added) {
            throw new ValidationException(
                    "Lecturer '" + lec.getName() +
                            "' is already in department '" + dept.getName() + "'."
            );
        }
    }

    public void addLecturerToCommittee(String lecturerName, String committeeName) {
        Committee c = findCommitteeByName(committeeName);
        if (c == null) {
            throw new EntityNotFoundException("Committee", committeeName.trim());
        }

        Lecturer lec = findLecturerByName(lecturerName);
        if (lec == null) {
            throw new EntityNotFoundException("Lecturer", lecturerName.trim());
        }

        try {
            c.addMember(lec);
        } catch (DuplicateEntityException ex) {
            throw new ValidationException(
                    "Lecturer '" + lec.getName() +
                            "' is already a member of committee '" + c.getName() + "'."
            );
        }
    }




    public void addDepartment(Department d) {
        Objects.requireNonNull(d, "Department cannot be null.");

        if (departments.contains(d)) {
            throw new DuplicateEntityException("Department", d.getName());
        }
        departments.add(d);
    }


    public Department[] getDepartments() {
        return departments.toArray(new Department[0]);
    }


    public Department findDepartmentByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }
        String key = name.trim();
        for (Department d : getDepartments()) {
            if (d.getName().equalsIgnoreCase(key)) {
                return d;
            }
        }
        return null;
    }
    public Degree findDegreeByName(String name) {
        if (name == null || name.trim().isEmpty()) return null;
        String key = name.trim();
        for (Degree d : Degree.values()) {
            if (d.name().equalsIgnoreCase(key)) {
                return d;
            }
        }
        return null;
    }



    public void removeDepartment(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Department name cannot be empty");
        }
        Department toRemove = findDepartmentByName(name);
        if (toRemove == null) {
            throw new EntityNotFoundException("Department", name.trim());
        }
        removeDepartment(toRemove);
    }

    private void removeDepartment(Department d) {
        if (d == null) {
            throw new ValidationException("Department cannot be null");
        }
        if (!departments.contains(d)) {
            throw new EntityNotFoundException("Department", d.getName());
        }

        for (Lecturer l : d.getLecturers()) {
            l.removeFromDepartment();
        }
        departments.remove(d);
    }

    public boolean addCommittee(Committee c) {
        Objects.requireNonNull(c, "Committee cannot be null");
        if (committees.contains(c)) {
            throw new DuplicateEntityException("Committee", c.getName());
        }
        committees.add(c);
        return true;
    }


    public Committee[] getCommittees() {
        return committees.toArray(new Committee[0]);
    }


    public Committee findCommitteeByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }
        String key = name.trim();
        for (Committee c : getCommittees()) {
            if (c.getName().equalsIgnoreCase(key)) {
                return c;
            }
        }
        return null;
    }

    public void removeCommittee(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Committee name cannot be empty.");
        }
        Committee toRemove = findCommitteeByName(name);
        if (toRemove == null) {
            throw new EntityNotFoundException("Committee", name.trim());
        }
        removeCommittee(toRemove);
    }

    private void removeCommittee(Committee c) {
        if (c == null) {
            throw new IllegalArgumentException("Committee cannot be null");
        }

        if (!committees.contains(c)) {
            throw new EntityNotFoundException("Committee", c.getName());
        }

        for (Lecturer l : c.getMembers()) {
            l.removeFromCommittee(c);
        }

        Lecturer chair = c.getChair();
        if (chair != null) {
            chair.removeFromCommittee(c);
        }
        committees.remove(c);
    }


    public double getAverageSalaryByDepartment(String deptName) {
        if (deptName == null || deptName.trim().isEmpty()) {
            throw new IllegalArgumentException("Department name cannot be empty");
        }
        Department d = findDepartmentByName(deptName);
        if (d == null) {
            throw new IllegalArgumentException("Department '" + deptName + "' not found");
        }
        Lecturer[] lects = d.getLecturers();
        if (lects.length == 0) {
            return 0.0;
        }
        double sum = 0.0;
        for (int i = 0; i < lects.length; i++) {
            sum += lects[i].getSalary();
        }
        return sum / lects.length;
    }

    public String compareDepartments(String d1, String d2, int crit) {
        Department dept1 = findDepartmentByName(d1);
        if (dept1 == null) {
            throw new EntityNotFoundException("Department", d1.trim());
        }
        Department dept2 = findDepartmentByName(d2);
        if (dept2 == null) {
            throw new EntityNotFoundException("Department", d2.trim());
        }

        int v1 = (crit == 1) ? dept1.getLecturerCount() : sumArticles(dept1);
        int v2 = (crit == 1) ? dept2.getLecturerCount() : sumArticles(dept2);
        return String.format("%s: %d vs %s: %d", dept1.getName(), v1, dept2.getName(), v2);
    }

    private int sumArticles(Department d) {
        int sum = 0;
        Lecturer[] lects = d.getLecturers();
        for (int i = 0; i < lects.length; i++) {
            if (lects[i] instanceof Doctor) {
                sum += ((Doctor) lects[i]).getArticleCount();
            }
        }
        return sum;
    }

    public void cloneCommittee(String origName) {
        if (origName == null || origName.trim().isEmpty()) {
            throw new ValidationException("Original name cannot be empty");
        }
        String key = origName.trim();
        Committee o = findCommitteeByName(key);
        if (o == null) {
            throw new EntityNotFoundException("Committee", key);
        }

        String newName = key + "-new";
        if (findCommitteeByName(newName) != null) {
            throw new DuplicateEntityException("Committee", newName);
        }

        // Use the new 3-arg constructor: name, chair, memberDegree
        Committee copy = new Committee(
                newName,
                o.getChair(),
                o.getMemberDegree()
        );

        // Copy over all members
        for (Lecturer l : o.getMembers()) {
            try {
                copy.addMember(l);
            } catch (DuplicateEntityException ex) {
                // shouldn’t happen—original had no duplicates
            }
        }

        // Finally add it to the college
        addCommittee(copy);
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof College)) return false;
        College other = (College) o;
        return this.collegeName.equalsIgnoreCase(other.collegeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(collegeName.toLowerCase());
    }

    @Override
    public String toString() {
        return String.format(
                "College: %s, Lecturers: %d, Departments: %d, Committees: %d",
                collegeName,
                lecturers.size(),
                departments.size(),
                committees.size()
        );
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
            13 - Compare two Committees(by member Count or article Count)
            14 - Compare two professors 
            15 - Clone Committee\s""";

    public static void flowAddLecturer(College c) {
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

            Doctor rl = new Doctor(name, id, Degree.DR, major, sal);
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
    public double getAverageSalaryAllLecturers() {
        Lecturer[] all = getLecturers();
        if (all.length == 0) {
            return 0.0;
        }
        double sum = 0.0;
        for (Lecturer l : all) {
            sum += l.getSalary();
        }
        return sum / all.length;
    }

    public static void flowRemoveLecturer(College c) {
        System.out.println("--- Remove Lecturer from Department ---");
        String name = readNonEmpty("Lecturer name to remove: ", "Lecturer name");
        c.removeLecturer(name);
        System.out.println("Lecturer removed successfully.");
    }

    public static void flowAddDepartment(College c) {
        System.out.println("--- Add Department ---");
        String name = readNonEmpty("Department name: ", "Department name");
        int ns = readInt("Number of students (>=0): ", 0, Integer.MAX_VALUE);
        Department d = new Department(name, ns);
        c.addDepartment(d);
        System.out.println("Department added successfully.");
    }

    public static void flowAddCommittee(College c) {
        System.out.println("---- Add Committee ----");
        String cn = readNonEmpty("Committee name: ", "Committee name");
        String ch = readNonEmpty("Chair name: ",     "Chair name");
        String md = readNonEmpty("Member degree: ",  "Member degree");

        Lecturer chair = c.findLecturerByName(ch);
        if (chair == null) {
            System.out.println("Error: Lecturer '" + ch + "' not found.");
            return;
        }
        Degree memberDegree = c.findDegreeByName(md);
        if (memberDegree == null) {
            System.out.println("Error: Degree '" + md + "' not found.");
            return;
        }

        try {
            Committee com = new Committee(
                    cn.trim(),
                    chair,
                    memberDegree
            );
            c.addCommittee(com);
            System.out.println("Committee added successfully.");
        } catch (ValidationException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }


    public static void flowRemoveCommittee(College c) {
        System.out.println("--- Remove Committee ---");
        String name = readNonEmpty("Committee to remove: ", "Committee name");
        c.removeCommittee(name);
        System.out.println("Committee removed successfully.");
    }

    public static void flowAverageByDepartment(College c) {
        System.out.println("--- Average Salary by Department ---");
        String d = readNonEmpty("Department name: ", "Department name");
        double avg = c.getAverageSalaryByDepartment(d);
        System.out.printf("Average in %s: ₪%.2f%n", d.trim(), avg);
    }


    public static void flowListAll(Object[] items) {
        if (items.length == 0) {
            System.out.println("⚠ has no members.");
            return;
        }
        for (Object item : items) {
            System.out.println(item);
        }
    }

    public static void flowAddLecturerToDepartment(College c) {
        System.out.println("--- Assign Lecturer to Department ---");
        String ln = readNonEmpty("Lecturer name: ", "Lecturer name");
        String dn = readNonEmpty("Department name: ", "Department name");
        c.addLecturerToDepartment(ln, dn);
        System.out.println("Lecturer added to Department successfully.");
    }


    public static void flowAddLecturerToCommittee(College c) {
        System.out.println("--- Assign Lecturer to Committee ---");
        String cn = readNonEmpty("Committee name: ", "Committee name");
        String ln = readNonEmpty("Lecturer name: ", "Lecturer name");
        c.addLecturerToCommittee(ln, cn);
        System.out.println("Lecturer added to Committee successfully.");
    }


    public static void flowCompareCommitteesNew(College c) {
        System.out.println("--- Compare Committees (choose criterion) ---");

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
            if (com1.getChair() instanceof Researcher) {
                totalArticles1 += ((Researcher) com1.getChair()).getArticleCount();
            }
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

    public static void flowCompareProfessorsByArticles(College c) {
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

    public static void flowCloneCommittee(College c) {
        System.out.println("--- Clone Committee ---");
        String orig = readNonEmpty("Committee to clone: ", "Committee name");
        c.cloneCommittee(orig);
        System.out.println("Cloned successfully as '" + orig.trim() + "-new'.");
    }

    public static void flowRemoveCommitteeMember(College c) {
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

    public static int readInt(String prompt, int min, int max) {
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