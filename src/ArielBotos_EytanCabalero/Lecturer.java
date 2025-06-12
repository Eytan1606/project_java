package ArielBotos_EytanCabalero;

import java.util.Objects;

public class Lecturer extends Person {

    private Department department;
    private CustomArray<Committee> committees = new CustomArray<>();

    public Lecturer(String name, int id, Degree degree, String major, double salary){
        super(name, id, degree, major, salary);
        this.department = null;
    }

    void setDepartment(Department department){
        this.department = department;
    }

    public Committee[] getCommittees() {
        return committees.toArray(new Committee[0]);
    }



    public boolean assignToDepartment(Department d) {
        if (d == null) {
            throw new IllegalArgumentException("Department cannot be null");
        }
        if (this.department == d) {
            return false;
        }
        if (this.department != null) {
            this.department.removeLecturer(this);
        }
        this.department = d;
         return d.addLecturer(this);

    }


    public boolean addToCommittee(Committee c) {
        Objects.requireNonNull(c, "Committee cannot be null");

        if (committees.contains(c)) {
            throw new ValidationException(
                    "Lecturer '" + getName() +
                            "' is already a member of committee '" + c.getName() + "'."
            );
        }

        committees.add(c);
        return true;
    }

    public boolean removeFromCommittee(Committee c) {
        Objects.requireNonNull(c, "Committee cannot be null");
        if (committees.remove(c)) {
            return c.removeMember(this);
        }
        return false;
    }
    public boolean removeFromDepartment() {
        if (department == null) {
            return false;
        }
        Department old = department;
        department = null;
        return old.removeLecturer(this);
    }

    @Override
    public boolean equals(Object o){
            return super.equals(o);
        }


        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder(super.toString())
                    .append(" → Dept: ")
                    .append(department == null ? "NoDept" : department.getName())
                    .append(" , Committees: [");
            Committee[] arr = getCommittees();
            for (int i = 0; i < arr.length; i++) {
                sb.append(arr[i].getName());
                if (i < arr.length - 1) sb.append(", ");
            }
            sb.append("]");
            return sb.toString();
        }



}


