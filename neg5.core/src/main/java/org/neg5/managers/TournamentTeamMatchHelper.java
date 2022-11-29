package org.neg5.managers;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import neg5.api.TournamentMatchApi;
import neg5.api.TournamentTeamApi;
import org.neg5.MatchTeamDTO;
import org.neg5.TournamentMatchDTO;
import org.neg5.TournamentTeamDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TournamentTeamMatchHelper {

    @Inject private TournamentMatchApi tournamentMatchApi;
    @Inject private TournamentTeamApi tournamentTeamApi;

    /**
     * Group matches by teams, where the key is the team's id and the value is the list of matches the team is part of
     * @param tournamentId tournamentId
     * @param phaseId phaseId
     * @return mapping between team -> matches
     */
    public Map<String, List<TournamentMatchDTO>> groupMatchesByTeams(String tournamentId,
                                                                     String phaseId) {
        List<TournamentMatchDTO> matches = tournamentMatchApi.findAllByTournamentAndPhase(tournamentId, phaseId);
        Map<String, List<TournamentMatchDTO>> matchesByTeamId = new HashMap<>();
        matches.forEach(match -> {
            Set<MatchTeamDTO> teams = match.getTeams();
            teams.forEach(team -> {
                matchesByTeamId.computeIfPresent(team.getTeamId(), (id, list) -> {
                    list.add(match);
                    return list;
                });
                matchesByTeamId.computeIfAbsent(team.getTeamId(), teamId -> Lists.newArrayList(match));
            });
        });
        List<TournamentTeamDTO> allTeams = tournamentTeamApi.findAllByTournamentId(tournamentId);
        allTeams.forEach(team -> matchesByTeamId.putIfAbsent(team.getId(), new ArrayList<>()));
        return matchesByTeamId;
    }
}
