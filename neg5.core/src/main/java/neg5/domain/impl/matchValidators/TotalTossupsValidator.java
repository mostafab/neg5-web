package neg5.domain.impl.matchValidators;

import com.google.inject.Singleton;
import java.util.List;
import javax.annotation.Nonnull;
import neg5.domain.api.FieldValidationErrors;
import neg5.domain.api.TournamentMatchDTO;

@Singleton
public class TotalTossupsValidator implements TournamentMatchValidator {

    @Override
    public FieldValidationErrors getErrors(
            @Nonnull List<TournamentMatchDTO> allMatches, @Nonnull TournamentMatchDTO subject) {
        FieldValidationErrors errors = new FieldValidationErrors();
        Integer tossupsHeard = subject.getTossupsHeard();
        if (tossupsHeard == null || subject.getTeams() == null) {
            return errors;
        }
        int numberOfTossupsAnswered =
                subject.getTeams().stream()
                        .filter(team -> team.getPlayers() != null)
                        .flatMap(team -> team.getPlayers().stream())
                        .filter(player -> player.getAnswers() != null)
                        .flatMap(player -> player.getAnswers().stream())
                        .mapToInt(
                                answers ->
                                        answers.getNumberGotten() == null
                                                ? 0
                                                : answers.getNumberGotten())
                        .sum();
        if (numberOfTossupsAnswered > tossupsHeard) {
            errors.add(
                    "totalTossupsAnswered",
                    String.format(
                            "The number of total tossups answered by all players (%d) is greater than the number of tossups heard (%d)",
                            tossupsHeard, numberOfTossupsAnswered));
        }
        return errors;
    }
}
