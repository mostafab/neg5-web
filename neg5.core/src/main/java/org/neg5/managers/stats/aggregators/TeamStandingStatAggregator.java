package org.neg5.managers.stats.aggregators;

import org.neg5.AnswersDTO;
import org.neg5.MatchPlayerAnswerDTO;
import org.neg5.TeamRecordDTO;
import org.neg5.TeamStandingStatsDTO;
import org.neg5.TournamentMatchDTO;
import org.neg5.managers.stats.StatsUtilities;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

/**
 * Aggregator for a {@link TeamStandingStatsDTO}
 */
public class TeamStandingStatAggregator implements StatAggregator<TeamStandingStatsDTO> {

    private final String teamId;

    private final DoubleStream.Builder pointsPerGameBuilder;
    private final DoubleStream.Builder pointsAgainstPerGameBuilder;

    private int numMatches;
    private int tossupsHeard;
    private int bouncebackPoints;
    private int overtimeTossupsGotten;

    private final TeamRecordDTO teamRecord;
    private final Map<Integer, Integer> tossupTotalCounts;
    private final Set<AnswersDTO> answers;

    private static final int ROUNDING_SCALE = 2;
    private static final int WIN_RECORD_ROUNDING_SCALE = 3;

    private boolean aggregated;

    public TeamStandingStatAggregator(String teamId) {
        this.teamId = teamId;

        pointsPerGameBuilder = DoubleStream.builder();
        pointsAgainstPerGameBuilder = DoubleStream.builder();

        teamRecord = new TeamRecordDTO();
        tossupTotalCounts = new HashMap<>();
        answers = new HashSet<>();
    }

    @Override
    public void accept(TournamentMatchDTO match) {
        if (aggregated) {
            throw new IllegalStateException("Already called aggregate on team " + teamId);
        }

        MatchUtil.TeamsWrapper teams = getTeams(match);
        pointsPerGameBuilder.accept(teams.getThisTeam().getScore());
        pointsAgainstPerGameBuilder.accept(teams.getOtherTeam().getScore());

        if (match.getTossupsHeard() != null) {
            tossupsHeard += match.getTossupsHeard();
        }
        pointsAgainstPerGameBuilder.accept(
                teams.getOtherTeam().isPresent()
                        ? teams.getOtherTeam().get().getScore()
                        : 0
        );

        if (match.getTossupsHeard() != null) {
            tossupsHeard += match.getTossupsHeard();
        }
        numMatches++;

        updateTeamRecord(teams);
        updateTossupTotalCounts(teams);
        updateAnswers(teams);
    }

    @Override
    public TeamStandingStatsDTO collect() {
        if (aggregated) {
            throw new IllegalStateException("Already called aggregate on team " + teamId);
        }

        finalizeRecord();
        TeamStandingStatsDTO stats = new TeamStandingStatsDTO();
        stats.setRecord(teamRecord);
        stats.setTossupsHeard(tossupsHeard);
        stats.setTeamId(teamId);

        double ppg = pointsPerGameBuilder.build().average().orElse(0);
        stats.setPointsPerGame(new BigDecimal(ppg).setScale(ROUNDING_SCALE, BigDecimal.ROUND_HALF_UP));

        double papg = pointsAgainstPerGameBuilder.build().average().orElse(0);
        stats.setPointsAgainstPerGame(new BigDecimal(papg).setScale(ROUNDING_SCALE, BigDecimal.ROUND_HALF_UP));

        stats.setMarginOfVictory(stats.getPointsPerGame().subtract(stats.getPointsAgainstPerGame()));
        stats.setPointsPerBonus(calculatePointsPerBonus(stats.getPointsPerGame()));
        stats.setPointsPerTossupHeard(calculatePointsPerTossupHeard(stats.getPointsPerGame()));
        stats.setTossupAnswerCounts(StatsUtilities.convertAnswersCounts(tossupTotalCounts));

        stats.setPowersToNegRatio(StatsUtilities.calculatePowerToNegRatio(answers));
        stats.setGetsToNegRatio(StatsUtilities.calculateGetsToNegRatio(answers));

        aggregated = true;

        return stats;
    }

    private void updateTeamRecord(MatchUtil.TeamsWrapper wrapper) {
        if (wrapper.getThisTeam().getScore() > wrapper.getOtherTeam().getScore()) {
            teamRecord.setWins(teamRecord.getWins() + 1);
        } else if (wrapper.getThisTeam().getScore() < wrapper.getOtherTeam().getScore()) {
            teamRecord.setLosses(teamRecord.getLosses() + 1);
        } else {
            teamRecord.setTies(teamRecord.getTies() + 1);
        }
    }

    private void updateTossupTotalCounts(MatchUtil.TeamsWrapper wrapper) {
        Set<MatchPlayerAnswerDTO> answers = wrapper.getThisTeam().getPlayers().stream()
                .flatMap(player -> player.getAnswers().stream())
                .collect(Collectors.toSet());

        answers.forEach(answer -> {
            tossupTotalCounts.computeIfPresent(answer.getTossupValue(),
                    (tossupValue, count) -> count + answer.getNumberGotten());
            tossupTotalCounts.putIfAbsent(answer.getTossupValue(), answer.getNumberGotten());
        });

        if (wrapper.getThisTeam().getBouncebackPoints() != null) {
            bouncebackPoints += wrapper.getThisTeam().getBouncebackPoints();
        }
        if (wrapper.getThisTeam().getOvertimeTossupsGotten() != null) {
            overtimeTossupsGotten += wrapper.getThisTeam().getOvertimeTossupsGotten();
        }
    }

    private void updateAnswers(MatchUtil.TeamsWrapper wrapper) {
        Set<AnswersDTO> matchAnswers = wrapper.getThisTeam().getPlayers().stream()
                .flatMap(player -> player.getAnswers().stream())
                .map(playerAnswer -> {
                    AnswersDTO answers = new AnswersDTO();
                    answers.setValue(playerAnswer.getTossupValue());
                    answers.setTotal(playerAnswer.getNumberGotten());
                    answers.setAnswerType(playerAnswer.getAnswerType());
                    return answers;
                })
                .collect(Collectors.toSet());
        answers.addAll(matchAnswers);
    }

    private void finalizeRecord() {
        if (numMatches == 0) {
            teamRecord.setWinPercentage(new BigDecimal(0));
        } else {
            double wins = teamRecord.getWins();
            teamRecord.setWinPercentage(new BigDecimal(wins / numMatches).setScale(WIN_RECORD_ROUNDING_SCALE,
                    BigDecimal.ROUND_HALF_EVEN));
        }
    }

    private MatchUtil.TeamsWrapper getTeams(TournamentMatchDTO match) {
        return MatchUtil.getTeams(teamId, match);
    }

    private BigDecimal calculatePointsPerTossupHeard(BigDecimal pointsPerGame) {
        return StatsUtilities.calculatePointsPerTossupsHeard(tossupsHeard, numMatches, pointsPerGame);
    }

    private BigDecimal calculatePointsPerBonus(BigDecimal pointsPerGame) {
        return StatsUtilities.calculatePointsPerBonus(answers, pointsPerGame,
                bouncebackPoints, overtimeTossupsGotten, numMatches);
    }
}
