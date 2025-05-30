package ArielBotos_EytanCabalero2;

import java.util.Objects;

public class Committee implements Comparable{
    private static final int GROW = 2;

    public String name;
    public Lecturer chair;
    public Lecturer[] members;
    public int memberCount;

    public Committee(String name, Lecturer chair) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name empty");
        this.name = name.trim().toLowerCase();
        setChair(chair);
        this.members = new Lecturer[2];
        this.memberCount = 0;
    }

    public String getName()  { return name; }
    public Lecturer getChair() { return chair; }

    public void setChair(Lecturer c) {
        if (c == null || !c.getDegree().canBeChair())
            throw new InvalidChairException("Chair must be DR or PROF");
        this.chair = c;
    }

    public boolean addMember(Lecturer m) {
        if (m == null) return false;
        for (int i = 0; i < memberCount; i++)
            if (members[i].equals(m)) return false;
        if (memberCount == members.length) {
            Lecturer[] larger = new Lecturer[members.length * GROW];
            for (int i = 0; i < memberCount; i++) larger[i] = members[i];
            members = larger;
        }
        members[memberCount++] = m;
        m.addCommittee(this);
        return true;
    }

    public boolean removeMember(Lecturer m) {
        for (int i = 0; i < memberCount; i++) {
            if (members[i].equals(m)) {
                for (int j = i; j < memberCount - 1; j++)
                    members[j] = members[j + 1];
                memberCount--;
                m.removeCommittee(this);
                return true;
            }
        }
        return false;
    }

    public Lecturer[] getMembers() {
        Lecturer[] copy = new Lecturer[memberCount];
        for (int i = 0; i < memberCount; i++) copy[i] = members[i];
        return copy;
    }
    public boolean containsMember(Lecturer lec) {
        for (int i = 0; i < memberCount; i++) {
            if (members[i].equals(lec)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        return this == o ||
                (o instanceof Committee && name.equals(((Committee)o).name));
    }

    @Override public int hashCode() { return Objects.hash(name); }

    @Override
    public String toString() {
        return capitalize(name)
                + " chaired by " + chair.getName()
                + " [" + memberCount + "]";
    }

    private static String capitalize(String s) {
        if (s == null || s.isEmpty()) return s;
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

    @Override
    public int compareTo(Object o) {
        Committee other = (Committee) o;

        // 1) Sum articles of chair + members
        int sumThis = 0, sumOther = 0;
        if (chair instanceof ResearchLecturer) {
            sumThis += ((ResearchLecturer) chair).getArticleCount();
        }
        if (other.chair instanceof ResearchLecturer) {
            sumOther += ((ResearchLecturer) other.chair).getArticleCount();
        }
        for (int i = 0; i < memberCount; i++) {
            Lecturer m = members[i];
            if (m instanceof ResearchLecturer) {
                sumThis += ((ResearchLecturer) m).getArticleCount();
            }
        }
        for (int i = 0; i < other.memberCount; i++) {
            Lecturer m = other.members[i];
            if (m instanceof ResearchLecturer) {
                sumOther += ((ResearchLecturer) m).getArticleCount();
            }
        }


        int diff = sumThis - sumOther;
        if (diff != 0) {
            return diff;
        }

        // 3) Secondary: compare total lecturers (chair + members)
        int sizeThis  = 1 + memberCount;
        int sizeOther = 1 + other.memberCount;
        return sizeThis - sizeOther;
    }
    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }
}