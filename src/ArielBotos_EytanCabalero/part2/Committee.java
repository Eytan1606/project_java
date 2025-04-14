package ArielBotos_EytanCabalero.part2;

import java.util.Arrays;

public class Committee {
    private String name;
    private Lecturer chair;
    private Lecturer[] members = new Lecturer[2];
    public int membersCount = 0;

    public Committee(String name, Lecturer chair){
        this.name = name;
        this.chair = chair;

    }
    public String getName(){ return name;}

    public boolean addMember(Lecturer lecturer){
        if (lecturer == chair) return false;
        for (int i=0; i<membersCount; i++){
            if (members[i] == lecturer) return false;
        }
        if (membersCount == members.length){
            members = Arrays.copyOf(members, members.length*2);
        }
        members[membersCount++] = lecturer;
        lecturer.joinTheCommittee(this);
        return true;
    }
    public boolean removeMembers(Lecturer lecturer){
        int i =0;
        while (i<membersCount) { i++;
            if (members[i] == lecturer){
                for (int j = i; j < membersCount -1; j++){
                    members[j] = members[j+1];
                }
            }membersCount --;
            lecturer.leaveCommittee(this);
            return true;

        }
        return false;
    }
    public boolean setChair(Lecturer lecturer){
        if(lecturer.getDegreeTitle().equals("Dr") || lecturer.getDegreeTitle().equals("Prof")) {
            this.chair = lecturer;
            return true;
        }
        return false;
    }
    @Override
    public String toString() {
        String info = "Committee: " + name + ", Chair: " + chair.getName() + " ";
        if (membersCount > 0) {
            info += "Members: ";
            for (int i = 0; i < membersCount; i++) {
                info += members[i].getName() + " ";
            }
        }
        return info;
    }
}
