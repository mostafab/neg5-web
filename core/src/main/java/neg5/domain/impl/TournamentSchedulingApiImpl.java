package neg5.domain.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import neg5.domain.api.ScheduleGenerationRequestDTO;
import neg5.domain.api.TournamentPoolDTO;
import neg5.domain.api.TournamentScheduleDTO;
import neg5.domain.api.TournamentScheduledMatchDTO;
import neg5.domain.api.TournamentSchedulingApi;
import neg5.domain.api.TournamentTeamApi;
import neg5.domain.api.TournamentTeamDTO;
import neg5.domain.impl.scheduling.RoundRobinScheduler;

@Singleton
public class TournamentSchedulingApiImpl implements TournamentSchedulingApi {

    private final TournamentTeamApi teamApi;
    private final RoundRobinScheduler roundRobinScheduler;

    @Inject
    public TournamentSchedulingApiImpl(
            TournamentTeamApi teamApi, RoundRobinScheduler roundRobinScheduler) {
        this.teamApi = teamApi;
        this.roundRobinScheduler = roundRobinScheduler;
    }

    @Override
    @Nonnull
    public TournamentScheduleDTO generateSchedule(@Nonnull ScheduleGenerationRequestDTO request) {
        Map<String, Set<String>> teamsByPools =
                splitTeamsByPools(request.getTournamentId(), request.getPhaseId());

        TournamentScheduleDTO schedule = new TournamentScheduleDTO();
        schedule.setMatches(new ArrayList<>());
        teamsByPools
                .values()
                .forEach(
                        teams -> {
                            List<TournamentScheduledMatchDTO> matches =
                                    roundRobinScheduler.generateRoundRobinMatches(teams);
                            schedule.getMatches().addAll(matches);
                        });
        return schedule;
    }

    private Map<String, Set<String>> splitTeamsByPools(String tournamentId, String phaseId) {
        List<TournamentTeamDTO> teams = teamApi.findAllByTournamentId(tournamentId);
        return teams.stream()
                .map(
                        team -> {
                            String poolId =
                                    team.getDivisions().stream()
                                            .filter(pool -> phaseId.equals(pool.getPhaseId()))
                                            .findFirst()
                                            .map(TournamentPoolDTO::getId)
                                            .orElse(null);
                            TeamPool teamPool = new TeamPool();
                            teamPool.poolId = poolId;
                            teamPool.teamId = team.getId();
                            return teamPool;
                        })
                .filter(tp -> tp.poolId != null)
                .collect(Collectors.groupingBy(team -> team.poolId))
                .entrySet()
                .stream()
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                entry ->
                                        entry.getValue().stream()
                                                .map(tp -> tp.teamId)
                                                .collect(Collectors.toSet())));
    }

    private static class TeamPool {
        private String poolId;
        private String teamId;
    }
}
