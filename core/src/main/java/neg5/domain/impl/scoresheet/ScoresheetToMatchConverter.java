package neg5.domain.impl.scoresheet;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.Set;
import java.util.stream.Collectors;
import neg5.domain.api.MatchTeamDTO;
import neg5.domain.api.ScoresheetDTO;
import neg5.domain.api.TournamentMatchDTO;
import neg5.domain.api.TournamentPlayerApi;
import neg5.domain.api.TournamentPlayerDTO;
import neg5.domain.api.TournamentTeamApi;

@Singleton
public class ScoresheetToMatchConverter {

    private final TournamentTeamApi teamApi;
    private final TournamentPlayerApi playerApi;

    @Inject
    public ScoresheetToMatchConverter(TournamentTeamApi teamApi, TournamentPlayerApi playerApi) {
        this.teamApi = teamApi;
        this.playerApi = playerApi;
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
        match.setTeams(getMatchTeams(scoresheet));

        return match;
    }

    private Set<MatchTeamDTO> getMatchTeams(ScoresheetDTO scoresheet) {
        return Lists.newArrayList(scoresheet.getTeam1Id(), scoresheet.getTeam2Id()).stream()
                .map(
                        teamId -> {
                            Set<String> playerIds =
                                    playerApi.findByTeamId(teamId).stream()
                                            .map(TournamentPlayerDTO::getId)
                                            .collect(Collectors.toSet());
                            MatchTeamDTO matchTeam = new MatchTeamDTO();
                            matchTeam.setTeamId(teamId);

                            return matchTeam;
                        })
                .collect(Collectors.toSet());
    }
}
