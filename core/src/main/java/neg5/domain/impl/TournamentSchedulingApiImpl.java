package neg5.domain.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import neg5.domain.api.ScheduleGenerationRequestDTO;
import neg5.domain.api.TournamentMatchApi;
import neg5.domain.api.TournamentPhaseApi;
import neg5.domain.api.TournamentPoolDTO;
import neg5.domain.api.TournamentScheduleDTO;
import neg5.domain.api.TournamentScheduleMatchApi;
import neg5.domain.api.TournamentScheduledMatchDTO;
import neg5.domain.api.TournamentSchedulingApi;
import neg5.domain.api.TournamentTeamApi;
import neg5.domain.api.TournamentTeamDTO;
import neg5.domain.impl.dataAccess.TournamentScheduleDAO;
import neg5.domain.impl.entities.TournamentSchedule;
import neg5.domain.impl.mappers.TournamentScheduleMapper;
import neg5.domain.impl.scheduling.RoundRobinScheduler;

@Singleton
public class TournamentSchedulingApiImpl
        extends AbstractApiLayerImpl<TournamentSchedule, TournamentScheduleDTO, Long>
        implements TournamentSchedulingApi {

    private final TournamentScheduleDAO dao;
    private final TournamentScheduleMapper mapper;
    private final TournamentTeamApi teamApi;
    private final TournamentPhaseApi phaseApi;
    private final TournamentMatchApi matchApi;
    private final TournamentScheduleMatchApi scheduleMatchApi;
    private final RoundRobinScheduler roundRobinScheduler;

    @Inject
    public TournamentSchedulingApiImpl(
            TournamentScheduleDAO dao,
            TournamentScheduleMapper mapper,
            TournamentTeamApi teamApi,
            TournamentPhaseApi phaseApi,
            TournamentMatchApi matchApi,
            TournamentScheduleMatchApi scheduleMatchApi,
            RoundRobinScheduler roundRobinScheduler) {
        this.dao = dao;
        this.mapper = mapper;
        this.teamApi = teamApi;
        this.phaseApi = phaseApi;
        this.matchApi = matchApi;
        this.scheduleMatchApi = scheduleMatchApi;
        this.roundRobinScheduler = roundRobinScheduler;
    }

    @Override
    @Transactional
    public TournamentScheduleDTO create(@Nonnull TournamentScheduleDTO dto) {
        dto.setTournamentId(phaseApi.get(dto.getTournamentPhaseId()).getTournamentId());
        TournamentScheduleDTO result = super.create(dto);
        result.setMatches(
                dto.getMatches() == null
                        ? new ArrayList<>()
                        : dto.getMatches().stream()
                                .map(
                                        m -> {
                                            m.setScheduleId(result.getId());
                                            return scheduleMatchApi.create(m);
                                        })
                                .collect(Collectors.toList()));

        return result;
    }

    @Override
    @Transactional
    public TournamentScheduleDTO update(@Nonnull TournamentScheduleDTO dto) {
        dto.setTournamentId(phaseApi.get(dto.getTournamentPhaseId()).getTournamentId());
        TournamentScheduleDTO result = super.update(dto);
        scheduleMatchApi.deleteAllMatchesForSchedule(result.getId());
        dto.setMatches(
                dto.getMatches().stream()
                        .map(
                                m -> {
                                    m.setScheduleId(result.getId());
                                    return scheduleMatchApi.create(m);
                                })
                        .collect(Collectors.toList()));

        return result;
    }

    @Override
    @Nonnull
    public TournamentScheduleDTO generateSchedule(@Nonnull ScheduleGenerationRequestDTO request) {
        Objects.requireNonNull(request, "request cannot be null");
        Objects.requireNonNull(request.getTournamentPhaseId(), "phaseId cannot be null");

        String tournamentId = phaseApi.get(request.getTournamentPhaseId()).getTournamentId();
        Map<String, Set<String>> teamsByPools =
                splitTeamsByPools(tournamentId, request.getTournamentPhaseId());

        TournamentScheduleDTO schedule = new TournamentScheduleDTO();
        schedule.setTournamentPhaseId(request.getTournamentPhaseId());
        schedule.setTournamentId(tournamentId);
        schedule.setMatches(new ArrayList<>());
        Long firstRound = getFirstRound(tournamentId, request);
        teamsByPools
                .values()
                .forEach(
                        teams -> {
                            List<TournamentScheduledMatchDTO> matches =
                                    roundRobinScheduler.generateRoundRobinMatches(
                                            teams, firstRound.intValue());
                            schedule.getMatches().addAll(matches);
                        });
        schedule.getMatches().sort(Comparator.comparing(TournamentScheduledMatchDTO::getRound));
        return schedule;
    }

    @Override
    protected TournamentScheduleMapper getMapper() {
        return mapper;
    }

    @Override
    protected TournamentScheduleDAO getDao() {
        return dao;
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

    private Long getFirstRound(String tournamentId, ScheduleGenerationRequestDTO request) {
        return Optional.ofNullable(request.getFirstRound())
                .map(Long::new)
                .orElseGet(
                        () -> {
                            Optional<Long> maxRoundSoFar =
                                    matchApi.getRoundsPlayed(tournamentId).stream()
                                            .max(Comparator.comparing(Function.identity()));
                            return maxRoundSoFar.map(round -> round + 1L).orElse(1L);
                        });
    }

    private static class TeamPool {
        private String poolId;
        private String teamId;
    }
}
