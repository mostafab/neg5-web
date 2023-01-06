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
import neg5.domain.api.ScoresheetDTO;
import neg5.domain.api.TournamentMatchDTO;
import neg5.domain.api.TournamentPlayerApi;
import neg5.domain.api.TournamentPlayerDTO;
import neg5.domain.api.TournamentTeamApi;
import neg5.domain.api.TournamentTossupValueApi;
import neg5.domain.api.TournamentTossupValueDTO;

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
        match.setTossupsHeard(scoresheet.getCycles().size());

        String tournamentId = teamApi.get(scoresheet.getTeam1Id()).getTournamentId();
        match.setTournamentId(tournamentId);

        List<TournamentTossupValueDTO> tossupValues =
                tossupValueApi.findAllByTournamentId(tournamentId);
        match.setTeams(getMatchTeams(scoresheet, tossupValues));

        return match;
    }

    private Set<MatchTeamDTO> getMatchTeams(
            ScoresheetDTO scoresheet, List<TournamentTossupValueDTO> tossupValues) {
        return Lists.newArrayList(scoresheet.getTeam1Id(), scoresheet.getTeam2Id()).stream()
                .map(teamId -> buildMatchTeam(teamId, scoresheet, tossupValues))
                .collect(Collectors.toSet());
    }

    private MatchTeamDTO buildMatchTeam(
            String teamId, ScoresheetDTO scoresheet, List<TournamentTossupValueDTO> tossupValues) {
        MatchTeamDTO matchTeam = new MatchTeamDTO();
        matchTeam.setScore(0);
        matchTeam.setTeamId(teamId);
        // TODO add tossups without bonuses

        Set<String> playersOnTeam =
                playerApi.findByTeamId(teamId).stream()
                        .map(TournamentPlayerDTO::getId)
                        .collect(Collectors.toSet());
        Map<String, MatchPlayerDTO> playerToMatchPlayers =
                playersOnTeam.stream()
                        .collect(
                                Collectors.toMap(
                                        Function.identity(),
                                        playerId -> buildStubMatchPlayer(playerId, tossupValues)));

        scoresheet
                .getCycles()
                .forEach(
                        cycle -> {
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
