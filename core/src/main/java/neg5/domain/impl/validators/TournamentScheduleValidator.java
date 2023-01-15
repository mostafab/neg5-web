package neg5.domain.impl.validators;

import static neg5.validation.FieldValidation.requireCondition;
import static neg5.validation.FieldValidation.requireNotNull;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import neg5.domain.api.FieldValidationErrors;
import neg5.domain.api.TournamentScheduleDTO;
import neg5.domain.api.TournamentScheduledMatchDTO;
import neg5.domain.api.TournamentTeamApi;
import neg5.domain.api.TournamentTeamDTO;

@Singleton
public class TournamentScheduleValidator {

    private final TournamentTeamApi teamApi;

    @Inject
    public TournamentScheduleValidator(TournamentTeamApi teamApi) {
        this.teamApi = teamApi;
    }

    /**
     * Validate the structure of a schedule
     *
     * @param schedule the schedule
     * @return an errors objects. Empty if no errors, populated with errors otherwise
     */
    public FieldValidationErrors validate(TournamentScheduleDTO schedule) {
        FieldValidationErrors errors = new FieldValidationErrors();
        requireNotNull(errors, schedule.getTournamentId(), "tournamentId");
        requireNotNull(errors, schedule.getTournamentPhaseId(), "tournamentPhaseId");
        requireNotNull(errors, schedule.getMatches(), "matches");
        validateMatches(errors, schedule);
        return errors;
    }

    private void validateMatches(FieldValidationErrors errors, TournamentScheduleDTO schedule) {
        if (schedule.getMatches() == null) {
            return;
        }
        List<TournamentScheduledMatchDTO> matches = schedule.getMatches();
        matches.forEach(
                match -> {
                    requireNotNull(errors, match.getRound(), "round");
                    requireCondition(
                            errors,
                            match.getRound() == null || match.getRound() > 0,
                            "round",
                            String.format(
                                    "Round should be a positive integer: %s", match.getRound()));
                    requireCondition(
                            errors,
                            match.getTeam1Id() != null || match.getTeam2Id() != null,
                            "teamIds",
                            String.format(
                                    "%sAt least one team must be selected in each match.",
                                    match.getRound() == null
                                            ? ""
                                            : String.format("Round %d: ", match.getRound())));
                });
        ensureEachTeamOnlyHasOneMatchPerRound(
                errors, schedule.getTournamentId(), schedule.getMatches());
    }

    private void ensureEachTeamOnlyHasOneMatchPerRound(
            FieldValidationErrors errors,
            String tournamentId,
            List<TournamentScheduledMatchDTO> matches) {
        if (tournamentId == null) {
            return;
        }
        Map<String, String> teamNamesById =
                teamApi.findAllByTournamentId(tournamentId).stream()
                        .collect(
                                Collectors.toMap(
                                        TournamentTeamDTO::getId, TournamentTeamDTO::getName));
        Map<String, Set<Integer>> teamRounds = new HashMap<>();
        for (TournamentScheduledMatchDTO match : matches) {
            if (match.getRound() == null) {
                continue;
            }
            String team1Id = match.getTeam1Id();
            String team2Id = match.getTeam2Id();
            if (team1Id != null && team2Id != null) {
                requireCondition(
                        errors,
                        !team1Id.equals(team2Id),
                        "teamId",
                        String.format(
                                "Round %d: %s cannot play itself.",
                                match.getRound(), teamNamesById.get(team1Id)));
            }
            processTeamCheck(errors, teamNamesById, teamRounds, match.getRound(), team1Id);
            processTeamCheck(errors, teamNamesById, teamRounds, match.getRound(), team2Id);
        }
    }

    private void processTeamCheck(
            FieldValidationErrors errors,
            Map<String, String> teamNames,
            Map<String, Set<Integer>> teamRounds,
            Integer round,
            String teamId) {
        if (teamId == null || round == null) {
            return;
        }
        teamRounds.computeIfAbsent(teamId, (id) -> new HashSet<>());
        requireCondition(
                errors,
                !teamRounds.get(teamId).contains(round),
                "round",
                String.format(
                        "%s has multiple matches in round %d.", teamNames.get(teamId), round));
        teamRounds.get(teamId).add(round);
    }
}
