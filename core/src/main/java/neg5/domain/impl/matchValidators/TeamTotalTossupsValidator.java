package neg5.domain.impl.matchValidators;

import com.google.inject.Singleton;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import neg5.domain.api.FieldValidationErrors;
import neg5.domain.api.TournamentMatchDTO;
import neg5.domain.api.TournamentTossupValueDTO;
import neg5.domain.api.enums.TossupAnswerType;

@Singleton
public class TeamTotalTossupsValidator implements TournamentMatchValidator {

    @Override
    @Nonnull
    public FieldValidationErrors getErrors(@Nonnull MatchValidationContext validationContext) {
        FieldValidationErrors errors = new FieldValidationErrors();
        TournamentMatchDTO subject = validationContext.getSubject();
        Integer tossupsHeard = subject.getTossupsHeard();
        if (tossupsHeard == null
                || subject.getTeams() == null
                || validationContext.isForfeit()
                || validationContext.getRules() == null) {
            return errors;
        }
        Set<Integer> negAnswerValues =
                validationContext.getRules().getTossupValues().stream()
                        .filter(tv -> tv.getAnswerType() == TossupAnswerType.NEG)
                        .map(TournamentTossupValueDTO::getValue)
                        .collect(Collectors.toSet());
        int numberOfTossupsAnswered =
                subject.getTeams().stream()
                        .filter(team -> team.getPlayers() != null)
                        .flatMap(team -> team.getPlayers().stream())
                        .filter(player -> player.getAnswers() != null)
                        .flatMap(player -> player.getAnswers().stream())
                        .filter(answer -> !negAnswerValues.contains(answer.getTossupValue()))
                        .mapToInt(
                                answers ->
                                        answers.getNumberGotten() == null
                                                ? 0
                                                : answers.getNumberGotten())
                        .sum();
        if (numberOfTossupsAnswered > tossupsHeard) {
            errors.add(
                    "team.totalTossupsAnswered",
                    String.format(
                            "The number of total tossups answered across teams (%d) is greater than the number of tossups heard (%d)",
                            numberOfTossupsAnswered, tossupsHeard));
        }
        return errors;
    }
}
