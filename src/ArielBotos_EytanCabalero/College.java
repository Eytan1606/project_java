package ArielBotos_EytanCabalero;

import java.util.Objects;
import java.util.Scanner;

public class College {
    private static final Scanner sc = new Scanner(System.in);
    private static final int GROW = 2;

    private final String collegeName;
    private Lecturer[] lecturers;
    private int lecturerCount;
    private Department[] departments;
    private int departmentCount;


    private Committee[] committees;
    private int committeeCount;

    public College(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new NullInputException();
        }
        this.collegeName    = name.trim();
        this.lecturers      = new Lecturer[2];
        this.lecturerCount  = 0;
        this.departments    = new Department[2];
        this.departmentCount = 0;
        this.committees     = new Committee[2];
        this.committeeCount = 0;
    }

    public void addLecturer(Lecturer l) {
        if (l == null) {
            throw new IllegalArgumentException("Lecturer cannot be null");
        }
        for (int i = 0; i < lecturerCount; i++) {
            if (lecturers[i].equals(l)) {
                throw new DuplicateEntityException("Lecturer", String.valueOf(l.getId()));
            }
        }
        if (lecturerCount == lecturers.length) {
            Lecturer[] temp = new Lecturer[lecturers.length * GROW];
            System.arraycopy(lecturers, 0, temp, 0, lecturers.length);
            lecturers = temp;
        }
        lecturers[lecturerCount++] = l;
    }

    public Lecturer findLecturerByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }
        String key = name.trim();
        for (int i = 0; i < lecturerCount; i++) {
            if (lecturers[i].getName().equalsIgnoreCase(key)) {
                return lecturers[i];
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
        int idx = -1;
        for (int i = 0; i < lecturerCount; i++) {
            if (lecturers[i].equals(l)) {
                idx = i;
                break;
            }
        }
        if (idx < 0) {
            throw new EntityNotFoundException("Lecturer", String.valueOf(l.getId()));
        }
        l.removeFromDepartment();

        Committee[] joined = l.getCommittees();
        for (Committee c : joined) {
            l.removeFromCommittee(c);
        }

        System.arraycopy(lecturers, idx + 1, lecturers, idx, lecturerCount - idx - 1);
        lecturers[--lecturerCount] = null;
    }



    public Lecturer[] getLecturers() {
        if (lecturerCount == 0) {
            return new Lecturer[0];
        }
        Lecturer[] copy = new Lecturer[lecturerCount];
        System.arraycopy(lecturers, 0, copy, 0, lecturerCount);
        return copy;
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
        boolean added = c.addMember(lec);
        if (!added) {
            throw new ValidationException(
                    "Lecturer '" + lec.getName() +
                            "' is already a member of committee '" + c.getName() + "'."
            );
        }
    }




    public void addDepartment(Department d) {
        if (d == null) {
            throw new ValidationException("Department cannot be null");
        }
        for (int i = 0; i < departmentCount; i++) {
            if (departments[i].equals(d)) {
                throw new DuplicateEntityException("Department", d.getName());
            }
        }
        if (departmentCount == departments.length) {
            Department[] temp = new Department[departments.length * GROW];
            System.arraycopy(departments, 0, temp, 0, departments.length);
            departments = temp;
        }
        departments[departmentCount++] = d;
    }


    public Department[] getDepartments() {
        if (departmentCount == 0) {
            return new Department[0];
        }
        Department[] copy = new Department[departmentCount];
        System.arraycopy(departments, 0, copy, 0, departmentCount);
        return copy;
    }


    public Department findDepartmentByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }
        String key = name.trim();
        for (int i = 0; i < departmentCount; i++) {
            if (departments[i].getName().equalsIgnoreCase(key)) {
                return departments[i];
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
        int idx = -1;
        for (int i = 0; i < departmentCount; i++) {
            if (departments[i].equals(d)) {
                idx = i;
                break;
            }
        }
        if (idx < 0) {
            throw new EntityNotFoundException("Department", d.getName());
        }

        Lecturer[] lects = d.getLecturers();
        for (Lecturer l : lects) {
            l.removeFromDepartment();
        }
        System.arraycopy(departments, idx + 1, departments, idx, departmentCount - idx - 1);
        departments[--departmentCount] = null;
    }

    public void addCommittee(Committee c) {
        if (c == null) {
            throw new ValidationException("Committee cannot be null.");
        }
        for (int i = 0; i < committeeCount; i++) {
            if (committees[i].equals(c)) {
                throw new DuplicateEntityException("Committee", c.getName());
            }
        }
        if (committeeCount == committees.length) {
            Committee[] temp = new Committee[committees.length * GROW];
            System.arraycopy(committees, 0, temp, 0, committees.length);
            committees = temp;
        }
        committees[committeeCount++] = c;
    }


    public Committee[] getCommittees() {
        if (committeeCount == 0) {
            return new Committee[0];
        }
        Committee[] copy = new Committee[committeeCount];
        System.arraycopy(committees, 0, copy, 0, committeeCount);
        return copy;
    }


    public Committee findCommitteeByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }
        String key = name.trim();
        for (int i = 0; i < committeeCount; i++) {
            if (committees[i].getName().equalsIgnoreCase(key)) {
                return committees[i];
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
            throw new IllegalArgumentException("Committee cannot be null.");
        }
        int idx = -1;
        for (int i = 0; i < committeeCount; i++) {
            if (committees[i].equals(c)) {
                idx = i;
                break;
            }
        }
        if (idx < 0) {
            throw new EntityNotFoundException("Committee", c.getName());
        }
        Lecturer[] members = c.getMembers();
        for (Lecturer l : members) {
            c.removeMember(l);
        }
        if (c.getChair() != null) {
            c.setChair(null);
        }
        System.arraycopy(committees, idx + 1, committees, idx, committeeCount - idx - 1);
        committees[--committeeCount] = null;
    }

    public double getAverageSalaryAllLecturers() {
        if (lecturerCount == 0) {
            return 0.0;
        }
        double sum = 0.0;
        for (int i = 0; i < lecturerCount; i++) {
            sum += lecturers[i].getSalary();
        }
        return sum / lecturerCount;
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

    public String compareCommittees(String name1, String name2) {
        Committee c1 = findCommitteeByName(name1);
        if (c1 == null) {
            throw new EntityNotFoundException("Committee", name1.trim());
        }

        Committee c2 = findCommitteeByName(name2);
        if (c2 == null) {
            throw new EntityNotFoundException("Committee", name2.trim());
        }

        int cmp = c1.compareTo(c2);

        int articles1 = 0;
        if (c1.getChair() instanceof Doctor) {
            articles1 = ((Doctor) c1.getChair()).getArticleCount();
        }
        int articles2 = 0;
        if (c2.getChair() instanceof Doctor) {
            articles2 = ((Doctor) c2.getChair()).getArticleCount();
        }
        int size1 = c1.getTotalSize();
        int size2 = c2.getTotalSize();

        if (cmp == 0) {
            return String.format(
                    "Committees \"%s\" and \"%s\" are tied: %d articles, %d members.",
                    c1.getName(), c2.getName(), articles1, size1
            );
        } else if (cmp < 0) {
            return String.format(
                    "Committee \"%s\" wins with %d articles vs %d articles.",
                    c2.getName(), articles2, articles1
            );
        } else {
            return String.format(
                    "Committee \"%s\" wins with %d articles vs %d articles.",
                    c1.getName(), articles1, articles2
            );
        }
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
            throw new IllegalArgumentException("Original name cannot be empty");
        }
        Committee o = findCommitteeByName(origName);
        if (o == null) {
            throw new EntityNotFoundException("Committee", origName.trim());
        }

        String newName = origName.trim() + "-new";

        if (findCommitteeByName(newName) != null) {
            throw new DuplicateEntityException("Committee", newName);
        }

        Committee copy = new Committee(newName, o.getChair());
        Lecturer[] mems = o.getMembers();
        for (Lecturer l : mems) {
            copy.addMember(l);
        }
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
                collegeName, lecturerCount, departmentCount, committeeCount
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
            13 - Compare two Committees(by memberCount or articleCount)
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