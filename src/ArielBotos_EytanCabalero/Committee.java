package ArielBotos_EytanCabalero;

import java.util.Objects;


public class Committee implements Comparable<Committee> {
    private String name;
    private Lecturer chair;
    private Degree memberDegree;
    private CustomArray<Lecturer> members = new CustomArray<>();

    public Committee(String name, Lecturer chair, Degree memberDegree) {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Committee name cannot be empty");
        }
        Objects.requireNonNull(chair, "Chair cannot be null");
        Objects.requireNonNull(memberDegree, "Member degree cannot be null");
        if (!chair.getDegree().canBeChair()) {
            throw new ValidationException(
                    "Lecturer " + chair.getName() + " cannot be chair"
            );
        }
        this.name = name.trim();
        this.chair = chair;
        this.memberDegree = memberDegree;
        chair.addToCommittee(this);
    }

    public String getName() {
        return name;
    }

    public Lecturer getChair() {
        return chair;
    }

    public Degree getMemberDegree() {
        return memberDegree;
    }

    public void setChair(Lecturer newChair) {
        Objects.requireNonNull(newChair, "Chair cannot be null");
        if (!newChair.getDegree().canBeChair()) {
            throw new ValidationException(
                    "Lecturer " + newChair.getName() + " cannot be chair"
            );
        }
        // remove old chair's link
        if (this.chair != null) {
            this.chair.removeFromCommittee(this);
        }
        this.chair = newChair;
        newChair.addToCommittee(this);
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

    public void removeMember(Lecturer l) {
        Objects.requireNonNull(l, "Member cannot be null");
        if (!members.remove(l)) {
            throw new EntityNotFoundException("Member", l.getName());
        }
        l.removeFromCommittee(this);
    }

    public Lecturer[] getMembers() {
        return members.toArray(new Lecturer[0]);
    }

    public int memberCount() {
        return members.size();
    }

    public int getTotalSize() {
        return 1 + memberCount();
    }

    @Override
    public int compareTo(Committee other) {
        if (other == null) {
            throw new IllegalArgumentException("Cannot compare Committee with null");
        }
        return this.name.compareTo(other.name);
    }

    @Override
    public String toString() {
        return String.format(
                "Committee: %s, Chair: %s, Members: %d (Degree: %s)",
                name,
                chair.getName(),
                memberCount(),
                memberDegree
        );
    }
}

