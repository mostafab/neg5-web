package neg5.accessManager.api;

/**
 * Exception thrown when someone tries to access a tournament for which they do not have sufficient privileges
 */
public class TournamentAccessException extends RuntimeException {

    private final String tournamentId;

    public TournamentAccessException(String tournamentId, String message) {
        super(message);
        this.tournamentId = tournamentId;
    }

    public String getTournamentId() {
        return tournamentId;
    }
}
