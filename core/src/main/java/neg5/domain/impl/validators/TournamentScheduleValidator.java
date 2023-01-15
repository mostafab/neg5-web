package neg5.domain.impl.validators;

import static neg5.validation.FieldValidation.requireCondition;
import static neg5.validation.FieldValidation.requireNotNull;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.List;
import java.util.Map;
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
                    requireNotNull(errors, match.getScheduleId(), "scheduleId");
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
    }
}
