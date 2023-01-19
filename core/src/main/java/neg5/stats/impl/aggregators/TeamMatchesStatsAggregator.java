package neg5.stats.impl.aggregators;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import neg5.domain.api.AnswersDTO;
import neg5.domain.api.MatchTeamDTO;
import neg5.domain.api.TeamMatchStatsDTO;
import neg5.domain.api.TournamentMatchDTO;
import neg5.domain.api.enums.MatchResult;
import neg5.domain.api.enums.TossupAnswerType;
import neg5.stats.impl.StatsUtilities;

public class TeamMatchesStatsAggregator implements StatAggregator<List<TeamMatchStatsDTO>> {

    private final String teamId;

    private final List<TeamMatchStatsDTO> matches;

    public TeamMatchesStatsAggregator(String teamId) {
        this.teamId = teamId;

        matches = new ArrayList<>();
    }

    @Override
    public void accept(TournamentMatchDTO match) {
        matches.add(calculateTeamMatchStats(match));
    }

    @Override
    public List<TeamMatchStatsDTO> collect() {
        return new ArrayList<>(matches);
    }

    private TeamMatchStatsDTO calculateTeamMatchStats(TournamentMatchDTO match) {
        MatchUtil.TeamsWrapper teams = MatchUtil.getTeams(teamId, match);

        TeamMatchStatsDTO stats = new TeamMatchStatsDTO();
        stats.setRound(match.getRound() == null ? null : match.getRound().intValue());
        stats.setOpponentTeamId(teams.getOtherTeam().getTeamId());
        stats.setOpponentPoints(
                Optional.ofNullable(teams.getOtherTeam().getScore())
                        .map(Integer::doubleValue)
                        .orElse(null));
        stats.setResult(getResult(teams));

        MatchTeamDTO thisTeam = teams.getThisTeam();
        stats.setPoints(
                Optional.ofNullable(thisTeam.getScore()).map(Integer::doubleValue).orElse(null));

        Set<AnswersDTO> answers =
                StatsUtilities.aggregateAnswers(StatsUtilities.getAnswers(thisTeam));
        stats.setTossupAnswerCounts(answers);
        stats.setPowersToNegRatio(StatsUtilities.calculatePowerToNegRatio(answers));
        stats.setGetsToNegRatio(StatsUtilities.calculateGetsToNegRatio(answers));

        stats.setTossupsHeard(
                match.getTossupsHeard() == null ? 0 : match.getTossupsHeard().doubleValue());
        if (stats.getTossupsHeard() != null && stats.getPoints() != null) {
            stats.setPointsPerTossupHeard(
                    StatsUtilities.calculatePointsPerTossupsHeard(
                            stats.getTossupsHeard().intValue(),
                            1,
                            new BigDecimal(stats.getPoints())));
        }

        stats.setBouncebackPoints(
                thisTeam.getBouncebackPoints() == null
                        ? null
                        : thisTeam.getBouncebackPoints().doubleValue());
        if (stats.getPoints() != null) {
            stats.setBonusPoints(
                    StatsUtilities.getBonusPoints(
                            stats.getPoints(), stats.getBouncebackPoints(), answers));
        }
        stats.setBonusesHeard(getBonusesHeard(answers, thisTeam.getOvertimeTossupsGotten()));
        stats.setPointsPerBonus(getPointsPerBonus(thisTeam, stats.getPoints(), answers));

        return stats;
    }

    private Integer getBonusesHeard(Set<AnswersDTO> answers, Integer overtimeTossups) {
        return answers.stream()
                        .filter(answer -> TossupAnswerType.NEG != answer.getAnswerType())
                        .mapToInt(AnswersDTO::getTotal)
                        .sum()
                - (overtimeTossups == null ? 0 : overtimeTossups);
    }

    private BigDecimal getPointsPerBonus(
            MatchTeamDTO thisTeam, Double points, Set<AnswersDTO> answers) {
        if (points == null) {
            return null;
        }
        return StatsUtilities.calculatePointsPerBonus(
                answers,
                new BigDecimal(points),
                thisTeam.getBouncebackPoints() == null ? 0 : thisTeam.getBouncebackPoints(),
                thisTeam.getOvertimeTossupsGotten() == null
                        ? 0
                        : thisTeam.getOvertimeTossupsGotten(),
                1);
    }

    private MatchResult getResult(MatchUtil.TeamsWrapper teams) {
        MatchTeamDTO thisTeam = teams.getThisTeam();
        MatchTeamDTO otherTeam = teams.getOtherTeam();
        if (Boolean.TRUE.equals(thisTeam.getForfeit())) {
            return MatchResult.FORFEIT;
        } else if (Boolean.TRUE.equals(otherTeam.getForfeit())) {
            return MatchResult.WIN;
        }

        if (thisTeam.getScore() == null || otherTeam.getScore() == null) {
            return null;
        }
        Integer thisScore = thisTeam.getScore();
        Integer otherScore = otherTeam.getScore();
        if (thisScore > otherScore) {
            return MatchResult.WIN;
        }
        if (thisScore < otherScore) {
            return MatchResult.LOSS;
        }
        return MatchResult.TIE;
    }
}
