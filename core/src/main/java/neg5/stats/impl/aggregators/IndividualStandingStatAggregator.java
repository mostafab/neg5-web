package neg5.stats.impl.aggregators;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import neg5.domain.api.AnswersDTO;
import neg5.domain.api.MatchPlayerDTO;
import neg5.domain.api.TournamentMatchDTO;
import neg5.stats.api.IndividualStandingStatDTO;
import neg5.stats.impl.StatsUtilities;

public class IndividualStandingStatAggregator implements StatAggregator<IndividualStandingStatDTO> {

    private final String playerId;

    private final Map<Integer, Integer> tossupTotalCounts;
    private final Set<AnswersDTO> answers;
    private int tossupsHeard;

    private BigDecimal gamesPlayed;

    public IndividualStandingStatAggregator(String playerId) {
        this.playerId = playerId;

        tossupTotalCounts = new HashMap<>();
        answers = new HashSet<>();

        gamesPlayed = new BigDecimal(0);
    }

    @Override
    public void accept(TournamentMatchDTO match) {
        MatchPlayerDTO player = MatchUtil.getPlayer(playerId, match);

        updateAnswers(player);
        updateGamesPlayed(match, player);

        tossupsHeard += player.getTossupsHeard() == null ? 0 : player.getTossupsHeard();
    }

    @Override
    public IndividualStandingStatDTO collect() {
        IndividualStandingStatDTO standing = new IndividualStandingStatDTO();
        standing.setPlayerId(playerId);
        standing.setTossupsHeard(tossupsHeard);
        standing.setGamesPlayed(gamesPlayed);
        standing.setTossupAnswerCounts(StatsUtilities.convertAnswersCounts(tossupTotalCounts));
        standing.setTotalPoints(StatsUtilities.getTotalPoints(answers));

        standing.setPointsPerGame(
                StatsUtilities.getPointsPerGame(
                        standing.getTotalPoints(), gamesPlayed.doubleValue()));
        standing.setPointsPerTossupHeard(
                StatsUtilities.calculatePointsPerTossupsHeard(
                        tossupsHeard, gamesPlayed.doubleValue(), standing.getPointsPerGame()));

        standing.setPowersToNegRatio(StatsUtilities.calculatePowerToNegRatio(answers));
        standing.setGetsToNegRatio(StatsUtilities.calculateGetsToNegRatio(answers));

        return standing;
    }

    private void updateAnswers(MatchPlayerDTO player) {
        Set<AnswersDTO> matchAnswers =
                player.getAnswers().stream()
                        .map(
                                playerAnswer -> {
                                    AnswersDTO answers = new AnswersDTO();
                                    answers.setValue(playerAnswer.getTossupValue());
                                    answers.setTotal(playerAnswer.getNumberGotten());
                                    answers.setAnswerType(playerAnswer.getAnswerType());
                                    return answers;
                                })
                        .collect(Collectors.toSet());
        answers.addAll(matchAnswers);
        matchAnswers.forEach(
                answer -> {
                    tossupTotalCounts.computeIfPresent(
                            answer.getValue(), (tossupValue, count) -> count + answer.getTotal());
                    tossupTotalCounts.putIfAbsent(answer.getValue(), answer.getTotal());
                });
    }

    private void updateGamesPlayed(TournamentMatchDTO match, MatchPlayerDTO player) {
        int tuh = player.getTossupsHeard() == null ? 0 : player.getTossupsHeard();
        int totalTossups = match.getTossupsHeard() == null ? 0 : match.getTossupsHeard();
        gamesPlayed = gamesPlayed.add(StatsUtilities.calculatePercentGamePlayed(tuh, totalTossups));
    }
}
