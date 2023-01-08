package neg5.domain.impl.scoresheet;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import neg5.domain.api.MatchPlayerAnswerDTO;
import neg5.domain.api.MatchPlayerDTO;
import neg5.domain.api.MatchTeamDTO;
import neg5.domain.api.ScoresheetCycleAnswerDTO;
import neg5.domain.api.ScoresheetDTO;
import neg5.domain.api.TournamentMatchDTO;
import neg5.domain.api.TournamentPlayerApi;
import neg5.domain.api.TournamentPlayerDTO;
import neg5.domain.api.TournamentTeamApi;
import neg5.domain.api.TournamentTossupValueApi;
import neg5.domain.api.TournamentTossupValueDTO;
import neg5.domain.api.enums.TossupAnswerType;

@Singleton
public class ScoresheetToMatchConverter {

    private final TournamentTeamApi teamApi;
    private final TournamentPlayerApi playerApi;
    private final TournamentTossupValueApi tossupValueApi;

    @Inject
    public ScoresheetToMatchConverter(
            TournamentTeamApi teamApi,
            TournamentPlayerApi playerApi,
            TournamentTossupValueApi tossupValueApi) {
        this.teamApi = teamApi;
        this.playerApi = playerApi;
        this.tossupValueApi = tossupValueApi;
    }

    public TournamentMatchDTO convert(ScoresheetDTO scoresheet) {
        TournamentMatchDTO match = new TournamentMatchDTO();

        match.setRound(scoresheet.getRound().longValue());
        match.setNotes(scoresheet.getNotes());
        match.setRoom(scoresheet.getRoom());
        match.setPacket(scoresheet.getPacket());
        match.setModerator(scoresheet.getModerator());
        match.setPhases(scoresheet.getPhases());
        match.setIsTiebreaker(scoresheet.getIsTiebreaker());
        match.setTossupsHeard(scoresheet.getCycles().size());

        String tournamentId = teamApi.get(scoresheet.getTeam1Id()).getTournamentId();
        match.setTournamentId(tournamentId);

        List<TournamentTossupValueDTO> tossupValues =
                tossupValueApi.findAllByTournamentId(tournamentId);
        match.setTeams(convertMatchTeams(scoresheet, tossupValues));

        return match;
    }

    private Set<MatchTeamDTO> convertMatchTeams(
            ScoresheetDTO scoresheet, List<TournamentTossupValueDTO> tossupValues) {
        return Lists.newArrayList(scoresheet.getTeam1Id(), scoresheet.getTeam2Id()).stream()
                .map(teamId -> convertMatchTeam(teamId, scoresheet, tossupValues))
                .collect(Collectors.toSet());
    }

    private MatchTeamDTO convertMatchTeam(
            String teamId, ScoresheetDTO scoresheet, List<TournamentTossupValueDTO> tossupValues) {
        MatchTeamDTO matchTeam = new MatchTeamDTO();
        matchTeam.setScore(0);
        matchTeam.setTeamId(teamId);
        // TODO add tossups without bonuses

        Set<String> playersOnThisTeam =
                playerApi.findByTeamId(teamId).stream()
                        .map(TournamentPlayerDTO::getId)
                        .collect(Collectors.toSet());
        Map<String, MatchPlayerDTO> playerToMatchPlayers =
                playersOnThisTeam.stream()
                        .collect(
                                Collectors.toMap(
                                        Function.identity(),
                                        playerId -> buildStubMatchPlayer(playerId, tossupValues)));

        Map<Integer, TossupAnswerType> answerValuesToType =
                tossupValues.stream()
                        .collect(
                                Collectors.toMap(
                                        TournamentTossupValueDTO::getValue,
                                        TournamentTossupValueDTO::getAnswerType));
        scoresheet
                .getCycles()
                .forEach(
                        cycle -> {
                            boolean rewardedToThisTeam =
                                    cycle.getAnswers().stream()
                                            .filter(
                                                    answer ->
                                                            answerValuesToType.get(
                                                                            answer.getValue())
                                                                    != TossupAnswerType.NEG)
                                            .findFirst()
                                            .map(ScoresheetCycleAnswerDTO::getPlayerId)
                                            .map(playersOnThisTeam::contains)
                                            .orElse(false);
                            cycle.getActivePlayers()
                                    .forEach(
                                            activePlayerId -> {
                                                MatchPlayerDTO matchPlayer =
                                                        playerToMatchPlayers.get(activePlayerId);
                                                // player is on the other team
                                                if (matchPlayer == null) {
                                                    return;
                                                }
                                                matchPlayer.setTossupsHeard(
                                                        matchPlayer.getTossupsHeard() + 1);
                                            });
                            cycle.getAnswers()
                                    .forEach(
                                            answer -> {
                                                if (playersOnThisTeam.contains(
                                                        answer.getPlayerId())) {
                                                    matchTeam.setScore(
                                                            matchTeam.getScore()
                                                                    + answer.getValue());

                                                    MatchPlayerDTO matchPlayer =
                                                            playerToMatchPlayers.get(
                                                                    answer.getPlayerId());
                                                    matchPlayer.getAnswers().stream()
                                                            .filter(
                                                                    playerAnswerCounts ->
                                                                            playerAnswerCounts
                                                                                    .getTossupValue()
                                                                                    .equals(
                                                                                            answer
                                                                                                    .getValue()))
                                                            .findFirst()
                                                            .ifPresent(
                                                                    playerAnswer -> {
                                                                        playerAnswer
                                                                                .setNumberGotten(
                                                                                        playerAnswer
                                                                                                        .getNumberGotten()
                                                                                                + 1);
                                                                    });
                                                }
                                            });
                            cycle.getBonuses()
                                    .forEach(
                                            bonus -> {
                                                if (teamId.equals(bonus.getAnsweringTeamId())) {
                                                    matchTeam.setScore(
                                                            matchTeam.getScore()
                                                                    + bonus.getValue());
                                                    // If this team got the bonus but wasn't the
                                                    // original recipient, award them bounceback
                                                    // points
                                                    if (!rewardedToThisTeam) {
                                                        matchTeam.setBouncebackPoints(
                                                                matchTeam.getBouncebackPoints()
                                                                                == null
                                                                        ? bonus.getValue()
                                                                        : matchTeam
                                                                                        .getBouncebackPoints()
                                                                                + bonus.getValue());
                                                    }
                                                }
                                            });
                        });

        matchTeam.setPlayers(new ArrayList<>(playerToMatchPlayers.values()));
        return matchTeam;
    }

    private MatchPlayerDTO buildStubMatchPlayer(
            String playerId, List<TournamentTossupValueDTO> tossupValues) {
        MatchPlayerDTO matchPlayer = new MatchPlayerDTO();
        matchPlayer.setPlayerId(playerId);
        matchPlayer.setTossupsHeard(0);
        matchPlayer.setAnswers(
                tossupValues.stream()
                        .map(
                                tv -> {
                                    MatchPlayerAnswerDTO matchPlayerAnswer =
                                            new MatchPlayerAnswerDTO();
                                    matchPlayerAnswer.setTossupValue(tv.getValue());
                                    matchPlayerAnswer.setNumberGotten(0);

                                    return matchPlayerAnswer;
                                })
                        .collect(Collectors.toSet()));

        return matchPlayer;
    }
}
