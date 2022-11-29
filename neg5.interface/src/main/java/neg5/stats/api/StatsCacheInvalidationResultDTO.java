package neg5.stats.api;

public class StatsCacheInvalidationResultDTO {

    private int keysInvalidated;
    private String tournamentId;

    public String getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(String tournamentId) {
        this.tournamentId = tournamentId;
    }

    public int getKeysInvalidated() {
        return keysInvalidated;
    }

    public void setKeysInvalidated(int keysInvalidated) {
        this.keysInvalidated = keysInvalidated;
    }
}
