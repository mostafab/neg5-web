package neg5.domain.impl.matchValidators;

import static neg5.validation.FieldValidation.requireCondition;

import com.google.inject.Singleton;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import neg5.domain.api.AnswersDTO;
import neg5.domain.api.FieldValidationErrors;
import neg5.domain.api.MatchPlayerAnswerDTO;
import neg5.domain.api.MatchTeamDTO;
import neg5.domain.api.TournamentMatchDTO;
import neg5.domain.api.TournamentRulesDTO;
import neg5.domain.api.TournamentTossupValueDTO;
import neg5.domain.api.enums.TossupAnswerType;
import neg5.stats.impl.StatsUtilities;

@Singleton
public class TeamScoreValidator implements TournamentMatchValidator {

    @Nonnull
    @Override
    public FieldValidationErrors getErrors(@Nonnull MatchValidationContext validationContext) {
        FieldValidationErrors errors = new FieldValidationErrors();
        TournamentMatchDTO subject = validationContext.getSubject();
        String tournamentId = validationContext.getSubject().getTournamentId();
        if (tournamentId == null
                || subject.getTeams() == null
                || validationContext.getTeamNamesById() == null
                || validationContext.getRules() == null) {
            return errors;
        }
        TournamentRulesDTO rules = validationContext.getRules();
        subject.getTeams().stream()
                .filter(
                        team ->
                                team.getScore() != null
                                        && validationContext
                                                .getTeamNamesById()
                                                .containsKey(team.getTeamId()))
                .forEach(
                        team -> {
                            String teamName =
                                    validationContext
                                            .getTeamNamesById()
                                            .get(team.getTeamId())
                                            .getName();
                            ensureBouncebacksAreValid(errors, team, rules, teamName);
                            ensureScoreIsPossible(errors, team, rules, teamName);
                        });
        return errors;
    }

    private void ensureBouncebacksAreValid(
            FieldValidationErrors errors,
            MatchTeamDTO team,
            TournamentRulesDTO rules,
            String teamName) {
        if (team.getBouncebackPoints() == null) {
            return;
        }
        if (!Boolean.TRUE.equals(rules.getUsesBouncebacks())) {
            requireCondition(
                    errors,
                    team.getBouncebackPoints() == null || team.getBouncebackPoints() == 0,
                    "team.bouncebackPoints",
                    String.format(
                            "%s should not have bounceback points since the rules don't allow them.",
                            teamName));
        }
        if (Boolean.TRUE.equals(rules.getUsesBouncebacks())) {
            requireCondition(
                    errors,
                    team.getBouncebackPoints() == null || team.getBouncebackPoints() >= 0,
                    "team.bouncebackPoints",
                    String.format(
                            "%s must have a non-negative number of bounceback points", teamName));
        }
    }

    private void ensureScoreIsPossible(
            FieldValidationErrors errors,
            MatchTeamDTO team,
            TournamentRulesDTO rules,
            String teamName) {
        if (rules.getBonusPointValue() == null
                || rules.getBonusPointValue() == 0
                || team.getPlayers() == null) {
            return;
        }
        int score = team.getScore();
        List<MatchPlayerAnswerDTO> playerAnswers =
                team.getPlayers().stream()
                        .filter(player -> player.getAnswers() != null)
                        .flatMap(player -> player.getAnswers().stream())
                        .filter(
                                answer ->
                                        answer.getNumberGotten() != null
                                                && answer.getTossupValue() != null)
                        .collect(Collectors.toList());
        int pointsFromTossups =
                playerAnswers.stream()
                        .mapToInt(answer -> answer.getTossupValue() * answer.getNumberGotten())
                        .sum();
        int pointsFromBonuses = score - pointsFromTossups;
        requireCondition(
                errors,
                pointsFromBonuses >= 0,
                "teams.score",
                String.format(
                        "%s's points from bonuses (%d) cannot be negative.",
                        teamName, pointsFromBonuses));
        requireCondition(
                errors,
                pointsFromBonuses % rules.getBonusPointValue() == 0,
                "teams.score",
                String.format(
                        "%s's points from bonuses (%d) is not a multiple of %d.",
                        teamName, pointsFromBonuses, rules.getBonusPointValue()));

        ensureValidPointsPerBonus(errors, team, rules, teamName);
    }

    private void ensureValidPointsPerBonus(
            FieldValidationErrors errors,
            MatchTeamDTO matchTeam,
            TournamentRulesDTO rules,
            String teamName) {
        if (rules.getPartsPerBonus() == null
                || rules.getBonusPointValue() == null
                || rules.getTossupValues() == null) {
            return;
        }
        Map<Integer, TossupAnswerType> tossupTypes =
                rules.getTossupValues().stream()
                        .collect(
                                Collectors.toMap(
                                        TournamentTossupValueDTO::getValue,
                                        TournamentTossupValueDTO::getAnswerType));
        Set<AnswersDTO> answers = StatsUtilities.getAnswers(matchTeam, tossupTypes);
        BigDecimal pointsPerBonus =
                StatsUtilities.calculatePointsPerBonus(
                        answers,
                        new BigDecimal(matchTeam.getScore()),
                        Optional.ofNullable(matchTeam.getBouncebackPoints()).orElse(0),
                        Optional.ofNullable(matchTeam.getOvertimeTossupsGotten()).orElse(0),
                        1);

        BigDecimal maxPointsPerBonus =
                new BigDecimal(rules.getPartsPerBonus() * rules.getBonusPointValue());
        requireCondition(
                errors,
                pointsPerBonus.compareTo(new BigDecimal(0)) >= 0
                        && pointsPerBonus.compareTo(maxPointsPerBonus) <= 0,
                "team.pointsPerBonus",
                String.format(
                        "%s has an invalid points-per-bonus value (%s).", teamName, pointsPerBonus));
    }
}
