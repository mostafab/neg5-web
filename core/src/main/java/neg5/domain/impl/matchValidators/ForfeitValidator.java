package neg5.domain.impl.matchValidators;

import static neg5.validation.FieldValidation.requireCondition;

import com.google.inject.Singleton;
import java.util.Optional;
import javax.annotation.Nonnull;
import neg5.domain.api.FieldValidationErrors;
import neg5.domain.api.MatchTeamDTO;
import neg5.domain.api.TournamentMatchDTO;
import org.apache.commons.collections.CollectionUtils;

@Singleton
public class ForfeitValidator implements TournamentMatchValidator {

    @Nonnull
    @Override
    public FieldValidationErrors getErrors(@Nonnull MatchValidationContext validationContext) {
        FieldValidationErrors errors = new FieldValidationErrors();
        if (!validationContext.isForfeit()) {
            return errors;
        }
        TournamentMatchDTO match = validationContext.getSubject();
        requireCondition(
                errors,
                match.getTossupsHeard() == null,
                "match.tossupsHeard",
                "A forfeited match should not have any tossups heard.");
        boolean noInfoIsAdded =
                Optional.ofNullable(match.getTeams())
                        .map(teams -> teams.stream().allMatch(this::allTeamDataIsBlank))
                        .orElse(false);
        requireCondition(
                errors,
                noInfoIsAdded,
                "teams.forfeit",
                "A forfeited match should contain no team-specific information. Please clear out the score and remove all players.");
        return errors;
    }

    private boolean allTeamDataIsBlank(MatchTeamDTO team) {
        return team.getScore() == null
                && team.getOvertimeTossupsGotten() == null
                && team.getBouncebackPoints() == null
                && CollectionUtils.isEmpty(team.getPlayers());
    }
}
