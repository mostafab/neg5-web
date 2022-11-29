package neg5.stats.impl;

import neg5.domain.api.AnswersDTO;
import neg5.domain.api.MatchTeamDTO;
import neg5.domain.api.TournamentMatchDTO;
import neg5.domain.api.enums.TossupAnswerType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Common utility stats functions
 */
public final class StatsUtilities {

    private static final int ROUNDING_SCALE = 2;

    private StatsUtilities() {}

    /**
     * Calculates points per game
     * @param totalPoints total points scored
     * @param numMatches number of matches
     * @return points per game
     */
    public static BigDecimal getPointsPerGame(double totalPoints,
                                              double numMatches) {
        if (numMatches == 0) {
            return new BigDecimal(0);
        }
        return new BigDecimal(totalPoints).divide(new BigDecimal(numMatches),
                ROUNDING_SCALE, RoundingMode.HALF_UP);
    }

    /**
     * Calculate points per tossup heard
     * @param tossupsHeard tossups heard
     * @param numMatches number of matches
     * @param pointsPerGame points per game
     * @return a BigDecimal representation of points per tossup heard, rounded to 2 decimal places.
     */
    public static BigDecimal calculatePointsPerTossupsHeard(int tossupsHeard,
                                                            double numMatches,
                                                            BigDecimal pointsPerGame) {
        if (tossupsHeard == 0) {
            return new BigDecimal(0);
        }
        return pointsPerGame
                .multiply(new BigDecimal(numMatches))
                .divide(new BigDecimal(tossupsHeard), ROUNDING_SCALE, RoundingMode.HALF_UP);
    }

    /**
     * Calculate points per bonus
     * @param answers set of answers
     * @param pointsPerGame points per game
     * @param bouncebackPoints the amount of points gotten from bouncebacks
     * @param overtimeTossupsGotten number of overtime tossups gotten
     * @param numMatches number of matches
     * @return points per bonus
     */
    public static BigDecimal calculatePointsPerBonus(Set<AnswersDTO> answers,
                                                     BigDecimal pointsPerGame,
                                                     int bouncebackPoints,
                                                     int overtimeTossupsGotten,
                                                     int numMatches) {
        BigDecimal totalGets = new BigDecimal(
                answers.stream()
                        .filter(answer -> !TossupAnswerType.NEG.equals(answer.getAnswerType()))
                        .mapToInt(AnswersDTO::getTotal)
                        .sum()
        ).subtract(new BigDecimal(overtimeTossupsGotten));
        if (totalGets.equals(new BigDecimal(0))) {
            return new BigDecimal(0);
        }
        BigDecimal totalPoints = pointsPerGame.multiply(new BigDecimal(numMatches))
                .subtract(new BigDecimal(bouncebackPoints));

        BigDecimal pointsFromTossups = new BigDecimal(
                answers.stream()
                        .mapToDouble(answer -> answer.getTotal() * answer.getValue())
                        .sum()
        );
        return totalPoints.subtract(pointsFromTossups).divide(totalGets, ROUNDING_SCALE, RoundingMode.HALF_UP);
    }

    /**
     * Calculate tossup powers to negs ratio
     * @param answers list of answers to consider
     * @return ratio, or 0 if no negs or no powers
     */
    public static BigDecimal calculatePowerToNegRatio(Set<AnswersDTO> answers) {
        Map<TossupAnswerType, Integer> answerTypeCounts = buildAnswerTypeCountsMap(answers);
        int negs = answerTypeCounts.getOrDefault(TossupAnswerType.NEG, 0);
        int powers = answerTypeCounts.getOrDefault(TossupAnswerType.POWER, 0);
        if (negs == 0 || powers == 0) {
            return new BigDecimal(0);
        }
        return new BigDecimal(powers).divide(new BigDecimal(negs), ROUNDING_SCALE,  RoundingMode.HALF_UP);
    }

    /**
     * Calculate get to neg ratio
     * @param answers list of answers to consider
     * @return ratio, or 0 if no negs or gets.
     */
    public static BigDecimal calculateGetsToNegRatio(Set<AnswersDTO> answers) {
        Map<TossupAnswerType, Integer> answerTypeCounts = buildAnswerTypeCountsMap(answers);
        int negs = answerTypeCounts.getOrDefault(TossupAnswerType.NEG, 0);
        int gets = answerTypeCounts.getOrDefault(TossupAnswerType.POWER, 0)
                + answerTypeCounts.getOrDefault(TossupAnswerType.BASE, 0);
        if (negs == 0 || gets == 0) {
            return new BigDecimal(0);
        }
        return new BigDecimal(gets).divide(new BigDecimal(negs), ROUNDING_SCALE, RoundingMode.HALF_UP);
    }

    /**
     * Calculate the percentage of a match a player played
     * @param tossupsHeard number of tossups the player heard
     * @param totalTossups number of tossups the match had overall
     * @return percentage of match played
     */
    public static BigDecimal calculatePercentGamePlayed(int tossupsHeard, int totalTossups) {
        if (totalTossups == 0) {
            return new BigDecimal(0);
        }
        return new BigDecimal(tossupsHeard).divide(new BigDecimal(totalTossups), ROUNDING_SCALE,
                RoundingMode.HALF_EVEN);
    }

    /**
     * Get total number of points from a set of answers
     * @param answers answers
     * @return total points
     */
    public static Double getTotalPoints(Set<AnswersDTO> answers) {
        return answers.stream()
                .mapToDouble(answer -> answer.getTotal() * answer.getValue())
                .sum();
    }

    /**
     * Get total bonus points
     * @param totalPoints total number of points
     * @param bouncebackPoints number of bounceback points gotten
     * @param answers player answers
     * @return a double representation of bonus points
     */
    public static Double getBonusPoints(Double totalPoints,
                                        Double bouncebackPoints,
                                        Set<AnswersDTO> answers) {
        return totalPoints - getTotalPoints(answers) - (bouncebackPoints == null ? 0 : bouncebackPoints);
    }

    /**
     * Convert a map of tossup values and their counts to an array of answers
     * @param tossupTotalCounts map of tossup value -> count
     * @return set of answers
     */
    public static Set<AnswersDTO> convertAnswersCounts(Map<Integer, Integer> tossupTotalCounts) {
        return tossupTotalCounts.entrySet().stream()
                .map(entry -> {
                    AnswersDTO answers = new AnswersDTO();
                    answers.setTotal(entry.getValue());
                    answers.setValue(entry.getKey());
                    return answers;
                })
                .collect(Collectors.toSet());
    }

    public static Set<AnswersDTO> getAnswers(MatchTeamDTO team) {
        return team
                .getPlayers()
                .stream()
                .flatMap(player -> player.getAnswers().stream())
                .map(answer -> {
                    AnswersDTO answers = new AnswersDTO();
                    answers.setAnswerType(answer.getAnswerType());
                    answers.setTotal(answer.getNumberGotten());
                    answers.setValue(answer.getTossupValue());
                    return answers;
                })
                .collect(Collectors.toSet());
    }

    public static Set<AnswersDTO> getAnswers(String teamId, TournamentMatchDTO match) {
        MatchTeamDTO team = match.getTeams().stream()
                .filter(t -> t.getTeamId().equals(teamId))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("Cannot find team " + teamId + " in match " + match.getId()));
        return getAnswers(team);
    }

    /**
     * Sum totals of answers that are the same value and return a new array
     * with an element for each tossup value and the total number
     * @param answers set of answers
     * @return array with an element for each tossup value and the total number of that value
     */
    public static Set<AnswersDTO> aggregateAnswers(Set<AnswersDTO> answers) {
        Map<Integer, AnswersDTO> tossupValueCounts = new HashMap<>();
        answers.forEach(answer -> {
            tossupValueCounts.computeIfPresent(answer.getValue(), (value, dto) -> {
                dto.setTotal(dto.getTotal() + answer.getTotal());
                return dto;
            });
            tossupValueCounts.computeIfAbsent(answer.getValue(), value -> {
                AnswersDTO dto = new AnswersDTO();
                dto.setValue(value);
                dto.setAnswerType(answer.getAnswerType());
                dto.setTotal(answer.getTotal());
                return dto;
            });
        });
        return new HashSet<>(tossupValueCounts.values());
    }

    private static Map<TossupAnswerType, Integer> buildAnswerTypeCountsMap(Set<AnswersDTO> answers) {
        Map<TossupAnswerType, Integer> counts = new HashMap<>();
        answers.stream()
                .filter(answer -> answer.getAnswerType() != null)
                .forEach(answer -> {
                    counts.computeIfPresent(answer.getAnswerType(), (answerType, count) -> count + answer.getTotal());
                    counts.putIfAbsent(answer.getAnswerType(), answer.getTotal());
                });
        return counts;
    }
}
