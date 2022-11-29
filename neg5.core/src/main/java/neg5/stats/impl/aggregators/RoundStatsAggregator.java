package neg5.stats.impl.aggregators;

import neg5.domain.api.AnswersDTO;
import neg5.domain.api.RoundStatDTO;
import neg5.domain.api.TournamentMatchDTO;
import neg5.stats.impl.StatsUtilities;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class RoundStatsAggregator implements StatAggregator<RoundStatDTO> {

    private final Integer round;

    private int numResults;
    private int tossupsHeard;
    private double totalPoints;
    private int totalBouncebackPoints;
    private int totalOvertimeTossups;
    private int numMatches;

    private final Set<AnswersDTO> answers;

    private static final int ROUNDING_SCALE = 2;

    public RoundStatsAggregator(Integer round) {
        this.round = round;

        answers = new HashSet<>();
    }

    @Override
    public void accept(TournamentMatchDTO match) {
        match.getTeams()
                .forEach(team -> {
                    totalPoints += team.getScore();
                    totalBouncebackPoints += team.getBouncebackPoints() == null
                            ? 0
                            : team.getBouncebackPoints();
                    totalOvertimeTossups += team.getOvertimeTossupsGotten() == null
                            ? 0
                            : team.getOvertimeTossupsGotten();
                });

        numResults += match.getTeams().size();
        tossupsHeard += match.getTossupsHeard();
        numMatches++;

        updateAnswers(match);
    }

    @Override
    public RoundStatDTO collect() {
        RoundStatDTO stat = new RoundStatDTO();
        stat.setRound(round);
        stat.setNumMatches(numMatches);
        stat.setAveragePointsPerGame(StatsUtilities.getPointsPerGame(totalPoints, numResults));
        stat.setTossupsHeard((double) tossupsHeard);
        stat.setTossupPoints(StatsUtilities.getTotalPoints(answers));
        stat.setAveragePointsPerBonus(StatsUtilities.calculatePointsPerBonus(answers,
                stat.getAveragePointsPerGame(), totalBouncebackPoints, totalOvertimeTossups, numResults));
        stat.setTossupAnswerCounts(StatsUtilities.aggregateAnswers(answers));
        stat.setTossupPointsPerTossupHeard(calculateTossupPointsPerTossupHeard(stat.getTossupPoints(),
                stat.getTossupsHeard()));
        return stat;
    }

    private void updateAnswers(TournamentMatchDTO match) {
        Set<AnswersDTO> matchAnswers = match.getTeams().stream()
                .flatMap(team -> team.getPlayers().stream())
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

    private BigDecimal calculateTossupPointsPerTossupHeard(double tossupPoints, double tossupsHeard) {
        if (tossupsHeard == 0) {
            return new BigDecimal(0);
        }
        return new BigDecimal(tossupPoints).divide(new BigDecimal(tossupsHeard), ROUNDING_SCALE,
                BigDecimal.ROUND_HALF_EVEN);
    }
}
