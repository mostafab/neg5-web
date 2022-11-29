package neg5.stats.impl.aggregators;

import neg5.domain.api.AnswersDTO;
import neg5.stats.api.IndividualMatchStatsDTO;
import neg5.domain.api.MatchPlayerDTO;
import neg5.domain.api.MatchTeamDTO;
import neg5.domain.api.TournamentMatchDTO;
import neg5.stats.impl.StatsUtilities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class IndividualMatchesStatAggregator implements StatAggregator<List<IndividualMatchStatsDTO>> {

    private final String playerId;

    private final List<IndividualMatchStatsDTO> matches;

    public IndividualMatchesStatAggregator(String playerId) {
        this.playerId = playerId;

        matches = new ArrayList<>();
    }

    @Override
    public void accept(TournamentMatchDTO match) {
        matches.add(calculateIndividualMatchStat(match));
    }

    @Override
    public List<IndividualMatchStatsDTO> collect() {
        return new ArrayList<>(matches);
    }

    private IndividualMatchStatsDTO calculateIndividualMatchStat(TournamentMatchDTO match) {
        IndividualMatchStatsDTO  stat = new IndividualMatchStatsDTO();

        PlayerWrapper wrapper = getPlayer(match);
        MatchPlayerDTO thisPlayer = wrapper.thisPlayer;

        stat.setOpponentTeamId(wrapper.opponentTeamId);
        stat.setRound(match.getRound() == null ? null : match.getRound().intValue());
        stat.setPercentGamePlayed(StatsUtilities.calculatePercentGamePlayed(
                thisPlayer.getTossupsHeard() == null ? 0 : thisPlayer.getTossupsHeard(),
                match.getTossupsHeard() == null ? 0 : match.getTossupsHeard()));

        stat.setTossupsHeard(thisPlayer.getTossupsHeard() == null ? 0 : thisPlayer.getTossupsHeard().doubleValue());
        stat.setTossupAnswerCounts(getAnswers(thisPlayer));
        stat.setPoints(StatsUtilities.getTotalPoints(stat.getTossupAnswerCounts()));
        stat.setPointsPerTossup(StatsUtilities.calculatePointsPerTossupsHeard(stat.getTossupsHeard().intValue(),
                1, new BigDecimal(stat.getPoints())));

        stat.setPowersToNegRatio(StatsUtilities.calculatePowerToNegRatio(stat.getTossupAnswerCounts()));
        stat.setGetsToNegRatio(StatsUtilities.calculateGetsToNegRatio(stat.getTossupAnswerCounts()));

        return stat;
    }

    private PlayerWrapper getPlayer(TournamentMatchDTO match) {
        MatchPlayerDTO thisPlayer = MatchUtil.getPlayer(playerId, match);

        String teamId = match.getTeams().stream()
                .filter(team -> team.getPlayers().stream().anyMatch(p -> p.getPlayerId().equals(playerId)))
                .findFirst()
                .map(MatchTeamDTO::getTeamId)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find player "
                        + playerId + " in match " + match.getId()));
        String opponentTeamId = match.getTeams().stream()
                .filter(team -> team.getPlayers().stream().noneMatch(p -> p.getPlayerId().equals(playerId)))
                .findFirst()
                .map(MatchTeamDTO::getTeamId)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find opponent team for player "
                        + playerId + " in match " + match.getId()));

        return new PlayerWrapper(thisPlayer, teamId, opponentTeamId);
    }

    private Set<AnswersDTO> getAnswers(MatchPlayerDTO player) {
        return player.getAnswers().stream()
                .map(answer -> {
                    AnswersDTO answers = new AnswersDTO();
                    answers.setTotal(answer.getNumberGotten());
                    answers.setValue(answer.getTossupValue());
                    answers.setAnswerType(answer.getAnswerType());
                    return answers;
                })
                .collect(Collectors.toSet());
    }

    private final class PlayerWrapper {
        private final MatchPlayerDTO thisPlayer;
        private final String teamId;
        private final String opponentTeamId;

        private PlayerWrapper(MatchPlayerDTO thisPlayer,
                              String teamId,
                              String opponentTeamId) {
            this.thisPlayer = thisPlayer;
            this.teamId = teamId;
            this.opponentTeamId = opponentTeamId;
        }
    }
}
