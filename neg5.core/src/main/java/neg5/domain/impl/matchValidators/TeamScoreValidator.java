package neg5.domain.impl.matchValidators;

import static neg5.validation.FieldValidation.requireCondition;

import com.google.inject.Inject;
import javax.annotation.Nonnull;
import neg5.domain.api.FieldValidationErrors;
import neg5.domain.api.MatchTeamDTO;
import neg5.domain.api.TournamentMatchDTO;
import neg5.domain.api.TournamentRulesApi;
import neg5.domain.api.TournamentRulesDTO;

public class TeamScoreValidator implements TournamentMatchValidator {

    private final TournamentRulesApi rulesApi;

    @Inject
    public TeamScoreValidator(TournamentRulesApi rulesApi) {
        this.rulesApi = rulesApi;
    }

    @Nonnull
    @Override
    public FieldValidationErrors getErrors(@Nonnull MatchValidationContext validationContext) {
        FieldValidationErrors errors = new FieldValidationErrors();
        TournamentMatchDTO subject = validationContext.getSubject();
        String tournamentId = validationContext.getSubject().getTournamentId();
        if (tournamentId == null
                || subject.getTeams() == null
                || validationContext.getTeamNamesById() == null) {
            return errors;
        }
        TournamentRulesDTO rules = rulesApi.getForTournament(tournamentId);
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
        int pointsFromTossups =
                team.getPlayers().stream()
                        .filter(player -> player.getAnswers() != null)
                        .flatMap(player -> player.getAnswers().stream())
                        .filter(
                                answer ->
                                        answer.getNumberGotten() != null
                                                && answer.getTossupValue() != null)
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
    }
}
