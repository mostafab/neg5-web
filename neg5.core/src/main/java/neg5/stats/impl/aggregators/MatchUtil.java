package neg5.stats.impl.aggregators;

import neg5.domain.api.MatchPlayerDTO;
import neg5.domain.api.MatchTeamDTO;
import neg5.domain.api.TournamentMatchDTO;

/**
 * Utility class for matches
 */
final class MatchUtil {

    private MatchUtil() {}

    static TeamsWrapper getTeams(String teamId, TournamentMatchDTO match) {
        MatchTeamDTO thisTeam = match.getTeams().stream()
                .filter(team -> teamId.equals(team.getTeamId()))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("Cannot find team " + teamId + " in match " + match.getId()));
        MatchTeamDTO otherTeam = match.getTeams().stream()
                .filter(team -> !teamId.equals(team.getTeamId()))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("Cannot find non-team " + teamId + " in match " + match.getId()));

        return new TeamsWrapper(thisTeam, otherTeam);
    }

    public static MatchPlayerDTO getPlayer(String playerId, TournamentMatchDTO match) {
        return match.getTeams().stream()
                .flatMap(t -> t.getPlayers().stream())
                .filter(player -> playerId.equals(player.getPlayerId()))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("Cannot find player " + playerId + " in match " + match.getId()));
    }

    final static class TeamsWrapper {

        private final MatchTeamDTO thisTeam;
        private final MatchTeamDTO otherTeam;

        TeamsWrapper(MatchTeamDTO thisTeam, MatchTeamDTO otherTeam) {
            this.thisTeam = thisTeam;
            this.otherTeam = otherTeam;
        }

        MatchTeamDTO getThisTeam() {
            return thisTeam;
        }

        MatchTeamDTO getOtherTeam() {
            return otherTeam;
        }
    }
}
