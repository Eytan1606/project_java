package ArielBotos_EytanCabalero;

import java.util.Objects;


public class Committee implements Comparable {
    private String name;
    private Lecturer chair;
    private Lecturer[] members;
    private int memberCount;
    private static final int INITIAL_SIZE = 4;

    public Committee(String name, Lecturer chair) {
        if (name == null || name.trim().isEmpty())
            throw new ValidationException("Committee name cannot be empty");
        if (chair == null)
            throw new ValidationException("Chair cannot be null");
        if (!chair.getDegree().canBeChair())
            throw new ValidationException("Lecturer " + chair.getName() + " cannot be chair");

        this.name = name.trim();
        this.chair = chair;
        this.members = new Lecturer[INITIAL_SIZE];
        this.memberCount = 0;

        chair.addToCommittee(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Committee name cannot be empty");
        this.name = name.trim();
    }

    public Lecturer getChair() {
        return chair;
    }


    public void setChair(Lecturer newChair) {
        if (newChair == null)
            throw new IllegalArgumentException("Chair cannot be null");
        if (!newChair.getDegree().canBeChair())
            throw new ValidationException("Lecturer " + newChair.getName() + " cannot be chair");

        for (int i = 0; i < memberCount; i++) {
            if (members[i].equals(newChair)) {

                System.arraycopy(members, i + 1, members, i, memberCount - i - 1);
                members[--memberCount] = null;
                break;
            }
        }

        if (this.chair != null) {
            this.chair.removeFromCommittee(this);
        }
        this.chair = newChair;
        newChair.addToCommittee(this);
    }


    public Lecturer[] getMembers() {
        if (memberCount == 0) {
            return new Lecturer[0];
        }
        Lecturer[] copy = new Lecturer[memberCount];
        System.arraycopy(members, 0, copy, 0, memberCount);
        return copy;
    }


    public boolean addMember(Lecturer l) {
        if (l == null) {
            throw new IllegalArgumentException("Lecturer cannot be null");
        }
        if (l.equals(chair)) {
            return false;
        }
        for (int i = 0; i < memberCount; i++) {
            if (members[i].equals(l)) {
                return false;
            }
        }
        if (memberCount == members.length) {
            Lecturer[] temp = new Lecturer[members.length * 2];
            System.arraycopy(members, 0, temp, 0, members.length);
            members = temp;
        }
        members[memberCount++] = l;
        return l.addToCommittee(this);
    }


    public boolean removeMember(Lecturer l) {
        if (l == null) {
            throw new IllegalArgumentException("Lecturer cannot be null");
        }
        int idx = -1;
        for (int i = 0; i < memberCount; i++) {
            if (members[i].equals(l)) {
                idx = i;
                break;
            }
        }
        if (idx < 0) {
            return false;
        }
        Lecturer removed = members[idx];
        System.arraycopy(members, idx + 1, members, idx, memberCount - idx - 1);
        members[--memberCount] = null;
        return removed.removeFromCommittee(this);
    }


    public int getTotalSize() {
        return 1 + memberCount;
    }

    @Override
    public int compareTo(Object o) {
        if (!(o instanceof Committee)) {
            throw new IllegalArgumentException("Cannot compare Committee with " + o.getClass());
        }
        Committee other = (Committee) o;

        int thisArticles = 0;
        if (this.chair instanceof Researcher) {
            thisArticles = ((Researcher) this.chair).getArticleCount();
        }
        int otherArticles = 0;
        if (other.chair instanceof Researcher) {
            otherArticles = ((Researcher) other.chair).getArticleCount();
        }

        int cmp = Integer.compare(thisArticles, otherArticles);
        if (cmp != 0) return cmp;
        return Integer.compare(this.getTotalSize(), other.getTotalSize());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Committee)) return false;
        Committee other = (Committee) o;
        return this.name.equalsIgnoreCase(other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name.toLowerCase());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Committee: %s  Chair: %s  MembersCount: %d",
                name,
                chair.getName(),
                memberCount));
        if (memberCount > 0) {
            sb.append(" (Members: ");
            for (int i = 0; i < memberCount; i++) {
                sb.append(members[i].getName());
                if (i < memberCount - 1) sb.append(", ");
            }
            sb.append(")");
        }
        return sb.toString();
    }
}
