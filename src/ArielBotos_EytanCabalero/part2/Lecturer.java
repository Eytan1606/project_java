package ArielBotos_EytanCabalero.part2;

import java.util.Arrays;

public class Lecturer {
    private String name;
    private String id;
    private String degreeName;
    private String degreeTitle;
    private double salary;
    private Department department;

    private Committee[] committees = new Committee[2];
    private int committeeCount = 0;

    public Lecturer(String name, String id, String degreeName, String degreeTitle, double salary) {
        this.name = name;
        this.id = id;
        this.degreeName = degreeName;
        this.degreeTitle = degreeTitle;
        this.salary = salary;
    }

    // Getters
    public String getId() { return id; }

    public double getSalary() { return salary; }

    public String getName() { return name; }

    public String getDegreeTitle() { return degreeTitle; }

    public String getDegreeName() { return degreeName; }

    public Department getDepartment() { return department; }


    public void setDepartment(Department department) {
        this.department = department;
    }

    public void joinTheCommittee(Committee committee) {
        for (int i = 0; i < committeeCount; i++) {
            if (committees[i] == committee) return; // כבר חבר
        }

        if (committeeCount == committees.length) {
            committees = Arrays.copyOf(committees, committees.length * 2);
        }

        committees[committeeCount++] = committee;
    }

    public void leaveCommittee(Committee committee) {
        for (int i = 0; i < committeeCount; i++) {
            if (committees[i] == committee) {

                for (int j = i; j < committeeCount - 1; j++) {
                    committees[j] = committees[j + 1];
                }
                committees[--committeeCount] = null;
                return;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder info = new StringBuilder("Lecturer\n");
        info.append("ID: ").append(id)
                .append(", Name: ").append(name)
                .append(", Degree: ").append(degreeTitle).append(" (").append(degreeName).append(")")
                .append(", Salary: ").append(salary);

        if (department != null) {
            info.append(", Department: ").append(department.getName());
        }

        if (committeeCount > 0) {
            info.append(", Committees: ");
            for (int i = 0; i < committeeCount; i++) {
                info.append(committees[i].getName());
                if (i < committeeCount - 1) info.append(", ");
            }
        }

        return info.toString();
    }
}









