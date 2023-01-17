package neg5.domain.api.enums;

/** Represents the levels of access a person can have for a tournament */
public enum TournamentAccessLevel {
    NONE(Integer.MIN_VALUE),
    COLLABORATOR(1),
    ADMIN(2),
    OWNER(Integer.MAX_VALUE);

    private final int level;

    TournamentAccessLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public boolean isAtLeast(TournamentAccessLevel other) {
        return this.level >= other.level;
    }
}
