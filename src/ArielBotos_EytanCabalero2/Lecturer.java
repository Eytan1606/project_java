package ArielBotos_EytanCabalero2;

public class Lecturer {
    private static final int ARRAY_GROWTH = 2;
    //dsv

    public String name;
    private String id;
    private Degree degree;
    private String degreeName;
    private double salary;
    private Department department;
    private Committee[] committees;
    private int committeeCount;

    public Lecturer(String name, String id, Degree degree, String degreeName, double salary) {
        this.name = name.toLowerCase();
        this.id = id;
        this.degree = degree;
        this.degreeName = degreeName.toLowerCase();
        this.salary = salary;
        this.department = null;
        this.committees = new Committee[2];
        this.committeeCount = 0;
    }

    public String getName() {
        return capitalizeEachWord(name);
    }

    public String getId() {
        return id;
    }

    public Degree getDegree() {
        return degree;
    }

    public void setDegree(Degree degree) {
        this.degree = degree;
    }

    public String getDegreeName() {
        return capitalize(degreeName);
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Department getDepartment() {
        return department;
    }

    void setDepartment(Department department) {
        this.department = department;
    }

    public Committee[] getCommittees() {
        return committees;
    }

    public int getCommitteeCount() {
        return committeeCount;
    }

    public void addCommittee(Committee committee) {
        if (!isInCommittee(committee)) {
            if (committeeCount == committees.length) expandCommittees();
            committees[committeeCount++] = committee;
        }
    }

    public void removeCommittee(Committee committee) {
        for (int i = 0; i < committeeCount; i++) {
            if (committees[i] == committee) {
                committees[i] = committees[--committeeCount];
                committees[committeeCount] = null;
                break;
            }
        }
    }

    private boolean isInCommittee(Committee committee) {
        for (int i = 0; i < committeeCount; i++) {
            if (committees[i] == committee) return true;
        }
        return false;
    }

    private void expandCommittees() {
        Committee[] newArray = new Committee[committees.length * ARRAY_GROWTH];
        System.arraycopy(committees, 0, newArray, 0, committees.length);
        committees = newArray;
    }

    @Override
    public String toString() {
        String departmentText = (department != null) ? department.getName() : "No department";
        StringBuilder committeesText = new StringBuilder();

        if (committeeCount == 0) {
            committeesText.append("No Committees");
        } else {
            committeesText.append("[");
            for (int i = 0; i < committeeCount; i++) {
                committeesText.append(committees[i].getName());
                if (i < committeeCount - 1) committeesText.append(", ");
            }
            committeesText.append("]");
        }

        return getName() + " (" + degree + ", " + getDegreeName() + "), ID: " + id +
                ", Salary: " + salary + ", Department: " + departmentText + ", Committees: " + committeesText;
    }

    private String capitalize(String input) {
        if (input == null || input.isEmpty()) return input;
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    private String capitalizeEachWord(String input) {
        if (input == null || input.isEmpty()) return input;
        String[] words = input.split(" ");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1))
                        .append(" ");
            }
        }
        return result.toString().trim();
    }
}
