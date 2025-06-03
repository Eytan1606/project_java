package ArielBotos_EytanCabalero;

public class Lecturer extends Person {
    private static final int GROW = 4;

    private Department department;
    private Committee[] committees;
    private int committeeCount;

    public Lecturer(String name, int id, Degree degree, String major, double salary){
        super(name, id, degree, major, salary);
        this.department = null;
        this.committees = new Committee[GROW];
        this.committeeCount = 0;

    }
    public Department getDepartment(){
        return department;
    }

    void setDepartment(Department department){
        this.department = department;
    }

    public boolean assignToDepartment(Department d ){
        if (d == null){
            throw new IllegalArgumentException("Department cannot be null");
        }
        if (this.department == d){
            return false;
        }
        if (this.department != null){
            this.department.removeLecturer(this);
        }
        this.department = d;
        return d.addLecturer(this);
        }

        public boolean removeFromDepartment(){
        if (department == null) return false;
        Department old = department;
        department = null;
        return old.removeLecturer(this);
        }

        public Committee[] getCommittees(){
        if (committeeCount == 0){
            return new Committee[0];
        }
        Committee[] copy = new Committee[committeeCount];
        System.arraycopy(committees , 0 , copy ,0 , committeeCount);
        return copy;
        }


        public boolean addToCommittee(Committee c){
        if (c == null){
            throw new IllegalArgumentException("Committee cannot be null");
        }
        for (int i =0; i <committeeCount; i++){
            if  (committees[i].equals(c)){
                return false;
            }
        }
        if (committeeCount == committees.length){
            Committee[] temp = new Committee[committees.length * 2];
            System.arraycopy(committees, 0 , temp, 0 ,committees.length);
            committees = temp;
        }
        committees[committeeCount ++] = c;
        return c.addMember(this);
        }


        public  boolean removeFromCommittee(Committee c) {
            if (c == null){
                throw new IllegalArgumentException("Committee cannot be null");
            }
            int idx = -1;
            for (int i =0; i <committeeCount; i++){
                if (committees[i].equals(c)) {
                    idx = i;
                    break;
                }
            }
            if (idx <0){
                return false;
            }
            Committee removed = committees[idx];
            System.arraycopy(committees, idx +1 , committees, idx, committeeCount - idx - 1);
            committees[--committeeCount] = null;
            return removed.removeMember(this);
        }

        @Override
        public boolean equals(Object o){
            return super.equals(o);
        }

        @Override
        public int hashCode(){
            return super.hashCode();
        }

        @Override
        public String toString() {
           String deptName = (department == null) ? "NoDept" : department.getName();
           StringBuilder sb = new StringBuilder();
           sb.append(super.toString())
                .append(" → Dept: ")
                .append(deptName)
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


