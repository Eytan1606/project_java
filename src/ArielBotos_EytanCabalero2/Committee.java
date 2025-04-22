package ArielBotos_EytanCabalero2;

public class Committee {
    private static final int ARRAY_GROWTH = 2;

    private String name;
    private Lecturer chair;
    private Lecturer[] members;
    private int memberCount;

    public Committee(String name, Lecturer chair) {
        if (!chair.getDegree().canBeChair()) {
            throw new IllegalArgumentException("Chair must be DR or PROF.");
        }
        this.name = name.toLowerCase();
        this.chair = chair;
        this.members = new Lecturer[2];
        this.memberCount = 0;
    }

    public String getName() {
        return capitalize(name);
    }

    public Lecturer getChair() {
        return chair;
    }

    public boolean addMember(Lecturer lecturer) {
        if (lecturer == chair || isMember(lecturer)) return false;
        if (memberCount == members.length) expandMembers();
        members[memberCount++] = lecturer;
        lecturer.addCommittee(this);
        return true;
    }

    public void removeMember(Lecturer lecturer) {
        for (int i = 0; i < memberCount; i++) {
            if (members[i] == lecturer) {
                members[i] = members[--memberCount];
                members[memberCount] = null;
                lecturer.removeCommittee(this);
                return;
            }
        }
    }

    public void setChair(Lecturer newChair) {
        if (!newChair.getDegree().canBeChair()) return;
        if (isMember(newChair)) removeMember(newChair);
        this.chair = newChair;
    }

    private boolean isMember(Lecturer lecturer) {
        for (int i = 0; i < memberCount; i++) {
            if (members[i] == lecturer) return true;
        }
        return false;
    }

    private void expandMembers() {
        Lecturer[] newArray = new Lecturer[members.length * ARRAY_GROWTH];
        System.arraycopy(members, 0, newArray, 0, members.length);
        members = newArray;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Committee: ").append(getName())
                .append(" | Chair: ").append(chair.getName())
                .append(" | Members: ");

        if (memberCount == 0) {
            result.append("No Members");
        } else {
            result.append("[");
            for (int i = 0; i < memberCount; i++) {
                result.append(members[i].getName());
                if (i < memberCount - 1) result.append(", ");
            }
            result.append("]");
        }

        return result.toString();
    }

    private String capitalize(String input) {
        if (input == null || input.isEmpty()) return input;
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
}
