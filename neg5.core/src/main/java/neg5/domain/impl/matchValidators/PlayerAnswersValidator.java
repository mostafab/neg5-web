package neg5.domain.impl.matchValidators;

import static neg5.validation.FieldValidation.requireCondition;
import static neg5.validation.FieldValidation.requireConditionsInSequence;
import static neg5.validation.FieldValidation.requireNotNull;

import com.google.inject.Singleton;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nonnull;
import neg5.domain.api.FieldValidationErrors;
import neg5.domain.api.MatchPlayerAnswerDTO;
import neg5.domain.api.MatchPlayerDTO;
import neg5.domain.api.TournamentMatchDTO;
import neg5.domain.api.TournamentPlayerDTO;

@Singleton
public class PlayerAnswersValidator implements TournamentMatchValidator {

    @Nonnull
    @Override
    public FieldValidationErrors getErrors(@Nonnull MatchValidationContext validationContext) {
        FieldValidationErrors errors = new FieldValidationErrors();
        Map<String, TournamentPlayerDTO> playersById = validationContext.getPlayerNamesById();
        if (playersById == null) {
            return errors;
        }

        TournamentMatchDTO subject = validationContext.getSubject();
        Optional.ofNullable(subject.getTeams())
                .ifPresent(
                        teams -> {
                            teams.stream()
                                    .filter(team -> team.getPlayers() != null)
                                    .flatMap(team -> team.getPlayers().stream())
                                    .forEach(
                                            player ->
                                                    errors.add(
                                                            getPlayerAnswerErrors(
                                                                    subject, player, playersById)));
                        });

        return errors;
    }

    private FieldValidationErrors getPlayerAnswerErrors(
            TournamentMatchDTO subject,
            MatchPlayerDTO matchPlayer,
            Map<String, TournamentPlayerDTO> playersById) {
        FieldValidationErrors errors = new FieldValidationErrors();
        requireNotNull(errors, matchPlayer.getPlayerId(), "players.playerId");
        requireCondition(
                errors,
                playersById.containsKey(matchPlayer.getPlayerId()),
                "players.playerId",
                String.format("Invalid player id: %s", matchPlayer.getPlayerId()));
        requireNotNull(errors, matchPlayer.getAnswers(), "players.answers");
        if (!errors.isEmpty()) {
            return errors;
        }
        final String playerName = playersById.get(matchPlayer.getPlayerId()).getName();
        if (subject.getTossupsHeard() == null || subject.getTossupsHeard() == 0) {
            requireCondition(
                    errors,
                    matchPlayer.getTossupsHeard() == null || matchPlayer.getTossupsHeard() == 0,
                    "players.tossupsHeard",
                    String.format(
                            "%s should have 0 tossups heard since this match didn't record tossups heard",
                            playerName));
        }
        requireCondition(
                errors,
                matchPlayer.getTossupsHeard() == null || matchPlayer.getTossupsHeard() >= 0,
                "players.tossupsHeard",
                String.format("%s has a negative number of tossups heard.", playerName));

        Integer playerTossupsHeard = matchPlayer.getTossupsHeard();
        if (playerTossupsHeard == null || playerTossupsHeard == 0) {
            validateNoTossupsHeardCase(errors, matchPlayer, playerName);
            return errors;
        }
        requireCondition(
                errors,
                subject.getTossupsHeard() == null
                        || subject.getTossupsHeard() >= playerTossupsHeard,
                "players.tossupsHeard",
                String.format(
                        "%s has a greater number of tossups heard (%d) than the match (%d)",
                        playerName, playerTossupsHeard, subject.getTossupsHeard()));
        int numberOfTossupsAnswered = 0;
        for (MatchPlayerAnswerDTO answer : matchPlayer.getAnswers()) {
            requireConditionsInSequence(
                    errors,
                    err ->
                            requireNotNull(
                                    err, answer.getTossupValue(), "players.answer.tossupValue"),
                    err ->
                            requireCondition(
                                    err,
                                    answer.getNumberGotten() == null
                                            || answer.getNumberGotten() >= 0,
                                    "players.answer.numberGotten",
                                    String.format(
                                            "%s must have a non-negative number of tossups worth %d",
                                            playerName, answer.getTossupValue())));
            numberOfTossupsAnswered +=
                    answer.getNumberGotten() == null ? 0 : answer.getNumberGotten();
        }
        requireCondition(
                errors,
                playerTossupsHeard >= numberOfTossupsAnswered,
                "players.answer.tossupsHeard",
                String.format(
                        "%s's number of answered tossups (%d) exceeds their total tossups heard (%d)",
                        playerName, numberOfTossupsAnswered, playerTossupsHeard));
        return errors;
    }

    private void validateNoTossupsHeardCase(
            FieldValidationErrors errors, MatchPlayerDTO player, String playerName) {
        Optional.ofNullable(player.getAnswers())
                .ifPresent(
                        answers -> {
                            requireCondition(
                                    errors,
                                    answers.stream()
                                            .allMatch(
                                                    answer ->
                                                            answer.getNumberGotten() == null
                                                                    || answer.getNumberGotten()
                                                                            == 0),
                                    "answers",
                                    String.format(
                                            "%s has 0 tossups heard but a non-zero number of answers.",
                                            playerName));
                        });
    }
}
