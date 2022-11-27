package org.neg5.matchValidators;

import org.neg5.FieldValidationErrors;
import org.neg5.MatchPlayerAnswerDTO;
import org.neg5.TournamentMatchDTO;

import javax.annotation.Nonnull;
import java.util.List;

public class TotalTossupsValidator implements EnhancedMatchValidator {

  @Override
  public FieldValidationErrors getErrors(
      @Nonnull List<TournamentMatchDTO> allMatches, @Nonnull TournamentMatchDTO subject) {
    Integer tossupsHeard = subject.getTossupsHeard();
    FieldValidationErrors errors = new FieldValidationErrors();
    if (tossupsHeard == null) {
      return errors;
    }
    int numberOfTossupsAnswered =
        subject.getTeams().stream()
            .flatMap(team -> team.getPlayers().stream())
            .flatMap(player -> player.getAnswers().stream())
            .mapToInt(MatchPlayerAnswerDTO::getNumberGotten)
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
