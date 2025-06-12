package ArielBotos_EytanCabalero;

import java.util.Objects;


public class Committee implements Comparable<Committee> {
    private String name;
    private Lecturer chair;
    private CustomArray<Lecturer> members = new CustomArray<>();
    private Degree memberDegree;

    public Committee(String name, Lecturer chair, Degree memberDegree) {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Committee name cannot be empty");
        }
        Objects.requireNonNull(chair, "Chair cannot be null");
        Objects.requireNonNull(memberDegree, "Member degree cannot be null");
        if (!chair.getDegree().canBeChair()) {
            throw new ValidationException("Lecturer " + chair.getName() + " cannot be chair");
        }
        this.name = name.trim();
        this.chair = chair;
        this.memberDegree = memberDegree;
        chair.addToCommittee(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Committee name cannot be empty.");
        this.name = name.trim();
    }

    public Lecturer getChair() {
        return chair;
    }
    public Degree getMemberDegree() {
        return memberDegree;
    }


    public void setChair(Lecturer newChair) {
        Objects.requireNonNull(newChair, "Chair cannot be null.");
        if (!newChair.getDegree().canBeChair()) {
            throw new ValidationException(
                    "Lecturer " + newChair.getName() + " cannot be chair"
            );
        }

        members.remove(newChair);

        if (this.chair != null) {
            this.chair.removeFromCommittee(this);
        }

        this.chair = newChair;
        newChair.addToCommittee(this);
    }


    public Lecturer[] getMembers() {
       return members.toArray(new Lecturer[0]);
    }


    public void addMember(Lecturer l) {
        Objects.requireNonNull(l, "Member cannot be null");
        if (!l.getDegree().equals(memberDegree)) {
            throw new ValidationException(
                    "Lecturer " + l.getName() +
                            " must have degree " + memberDegree
            );
        }
        if (members.contains(l)) {
            throw new DuplicateEntityException("Member", l.getName());
        }
        members.add(l);
        l.addToCommittee(this);
    }


    public boolean removeMember(Lecturer l) {
        if (l == null) {
            throw new IllegalArgumentException("Lecturer cannot be null.");
        }
        if (members.remove(l)){
            return l.removeFromCommittee(this);
        }
        return false;
    }


    private int getTotalSize() {
        return 1 + members.size();
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
        int count = members.size();
        sb.append(String.format(
                "Committee: %s  Chair: %s  MemberCount: %d",
                name, chair.getName(), count
        ));

        if (count > 0) {
            sb.append(" (Members: ");
            Lecturer[] array = getMembers();
            for (int i = 0; i < array.length; i++) {
                sb.append(array[i].getName());
                if (i < array.length - 1) sb.append(", ");
            }
            sb.append(")");
        }
        return sb.toString();
    }


    @Override
    public int compareTo(Committee other) {
        if (other == null) {
            throw new IllegalArgumentException("Cannot compare Committee with null.");
        }

        int mine = (chair instanceof Researcher)
                ? ((Researcher) chair).getArticleCount()
                : 0;
        int theirs = (other.chair instanceof Researcher)
                ? ((Researcher) other.chair).getArticleCount()
                : 0;

        int cmp = Integer.compare(mine, theirs);
        if (cmp != 0) return cmp;

        return Integer.compare(this.getTotalSize(), other.getTotalSize());
    }
}
