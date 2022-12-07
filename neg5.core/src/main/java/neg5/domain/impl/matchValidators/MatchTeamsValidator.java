package neg5.domain.impl.matchValidators;

import static neg5.validation.FieldValidation.requireCondition;
import static neg5.validation.FieldValidation.requireNotNull;

import com.google.inject.Singleton;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import neg5.domain.api.FieldValidationErrors;
import neg5.domain.api.MatchTeamDTO;
import neg5.domain.api.TournamentMatchDTO;
import neg5.domain.api.TournamentPlayerDTO;
import neg5.domain.api.TournamentTeamDTO;

@Singleton
public class MatchTeamsValidator implements TournamentMatchValidator {

    @Nonnull
    @Override
    public FieldValidationErrors getErrors(@Nonnull MatchValidationContext validationContext) {
        TournamentMatchDTO subject = validationContext.getSubject();
        FieldValidationErrors errors = new FieldValidationErrors();
        requireNotNull(errors, subject.getTeams(), "teams");
        requireCondition(
                errors,
                subject.getTeams() == null || subject.getTeams().size() == 2,
                "teams",
                "Exactly two teams must be included in this match.");
        requireCondition(
                errors,
                subject.getTeams() == null
                        || subject.getTeams().stream().allMatch(team -> team.getTeamId() != null),
                "teams.teamId",
                "All teams must have a teamId");
        if (!errors.isEmpty()) {
            return errors;
        }
        Set<String> teamIds =
                subject.getTeams().stream()
                        .map(MatchTeamDTO::getTeamId)
                        .collect(Collectors.toSet());
        requireCondition(
                errors, teamIds.size() == 2, "teams", "A team cannot play against itself.");
        // Skip rest of the validation if match is a forfeit
        if (validationContext.isForfeit()) {
            return errors;
        }
        subject.getTeams()
                .forEach(
                        team -> {
                            requireCondition(
                                    errors,
                                    team.getScore() != null,
                                    "teams.score",
                                    String.format(
                                            "%s must have a recorded score.",
                                            Optional.ofNullable(
                                                            validationContext.getTeamNamesById())
                                                    .map(kv -> kv.get(team.getTeamId()))
                                                    .map(TournamentTeamDTO::getName)
                                                    .orElse("Team")));
                            ensureTeamHasAllCorrectPlayers(
                                    errors,
                                    team,
                                    validationContext.getTeamNamesById(),
                                    validationContext.getPlayerNamesById());
                        });
        return errors;
    }

    private void ensureTeamHasAllCorrectPlayers(
            FieldValidationErrors errors,
            MatchTeamDTO matchTeam,
            Map<String, TournamentTeamDTO> teamsById,
            Map<String, TournamentPlayerDTO> playersById) {
        // This violation should never happen on the front-end, but we'll check just in case.
        if (matchTeam.getPlayers() != null
                && teamsById != null
                && playersById != null
                && teamsById.containsKey(matchTeam.getTeamId())) {
            matchTeam.getPlayers().stream()
                    .filter(player -> playersById.containsKey(player.getPlayerId()))
                    .forEach(
                            player -> {
                                TournamentPlayerDTO tournamentPlayer =
                                        playersById.get(player.getPlayerId());
                                requireCondition(
                                        errors,
                                        matchTeam.getTeamId().equals(tournamentPlayer.getTeamId()),
                                        "matchTeam.players",
                                        String.format(
                                                "%s assigned to incorrect team (%s).",
                                                tournamentPlayer.getName(),
                                                teamsById.get(matchTeam.getTeamId()).getName()));
                            });
        }
    }
}
