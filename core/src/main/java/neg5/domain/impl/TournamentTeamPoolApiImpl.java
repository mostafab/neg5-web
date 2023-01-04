package neg5.domain.impl;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import neg5.domain.api.BatchTeamPoolUpdatesDTO;
import neg5.domain.api.TeamPoolAssignmentsDTO;
import neg5.domain.api.TournamentPoolApi;
import neg5.domain.api.TournamentPoolDTO;
import neg5.domain.api.TournamentTeamApi;
import neg5.domain.api.TournamentTeamPoolApi;
import neg5.domain.api.TournamentTeamPoolDTO;
import neg5.domain.api.TournamentTeamsPoolsDTO;
import neg5.domain.impl.dataAccess.TournamentTeamPoolDAO;
import neg5.domain.impl.entities.TournamentTeamPool;
import neg5.domain.impl.entities.compositeIds.TournamentTeamPoolId;
import neg5.domain.impl.mappers.TournamentTeamPoolMapper;

@Singleton
public class TournamentTeamPoolApiImpl
        extends AbstractApiLayerImpl<
                TournamentTeamPool, TournamentTeamPoolDTO, TournamentTeamPoolId>
        implements TournamentTeamPoolApi {

    private final TournamentTeamApi teamApi;
    private final TournamentPoolApi poolApi;
    private final TournamentTeamPoolMapper mapper;
    private final TournamentTeamPoolDAO dao;

    @Inject
    public TournamentTeamPoolApiImpl(
            TournamentTeamPoolMapper mapper,
            TournamentTeamPoolDAO dao,
            TournamentTeamApi teamApi,
            TournamentPoolApi poolApi) {
        this.mapper = mapper;
        this.dao = dao;
        this.teamApi = teamApi;
        this.poolApi = poolApi;
    }

    @Transactional
    public List<TournamentTeamPoolDTO> associateTeamWithPools(
            @Nonnull TeamPoolAssignmentsDTO assignment) {
        deleteExistingAssociations(assignment.getTeamId());
        String tournamentId = teamApi.get(assignment.getTeamId()).getTournamentId();
        List<TournamentPoolDTO> pools = poolApi.get(assignment.getPoolIds());
        Preconditions.checkArgument(
                pools.stream().allMatch(pool -> tournamentId.equals(pool.getTournamentId())));
        return assignment.getPoolIds().stream()
                .map(
                        poolId -> {
                            TournamentTeamPoolDTO dto = new TournamentTeamPoolDTO();
                            dto.setPoolId(poolId);
                            dto.setTeamId(assignment.getTeamId());
                            dto.setTournamentId(tournamentId);
                            return create(dto);
                        })
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<TournamentTeamsPoolsDTO> batchAssociateWithPools(
            @Nonnull BatchTeamPoolUpdatesDTO updates) {
        return updates.getAssignments().stream()
                .map(
                        assignment -> {
                            List<TournamentTeamPoolDTO> updated =
                                    associateTeamWithPools(assignment);
                            TournamentTeamsPoolsDTO result = new TournamentTeamsPoolsDTO();
                            result.setTeamId(assignment.getTeamId());
                            result.setPools(updated);
                            return result;
                        })
                .collect(Collectors.toList());
    }

    private void deleteExistingAssociations(String teamId) {
        getDao().findByTeamId(teamId).forEach(entity -> delete(entity.getId()));
    }

    @Override
    protected TournamentTeamPoolDAO getDao() {
        return dao;
    }

    @Override
    protected TournamentTeamPoolMapper getMapper() {
        return mapper;
    }
}
