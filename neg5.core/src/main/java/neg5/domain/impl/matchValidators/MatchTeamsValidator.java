package neg5.domain.impl.matchValidators;

import static neg5.validation.FieldValidation.requireCondition;
import static neg5.validation.FieldValidation.requireNotNull;

import com.google.inject.Singleton;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import neg5.domain.api.FieldValidationErrors;
import neg5.domain.api.MatchTeamDTO;
import neg5.domain.api.TournamentMatchDTO;

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

        return errors;
    }
}
