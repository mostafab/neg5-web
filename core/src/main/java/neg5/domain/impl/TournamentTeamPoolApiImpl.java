package neg5.domain.impl;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.persistence.NoResultException;
import neg5.domain.api.BatchTeamPoolUpdatesDTO;
import neg5.domain.api.TeamPoolAssignmentsDTO;
import neg5.domain.api.TournamentPhaseApi;
import neg5.domain.api.TournamentPhaseDTO;
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
    private final TournamentPhaseApi phaseApi;
    private final TournamentTeamPoolMapper mapper;
    private final TournamentTeamPoolDAO dao;

    @Inject
    public TournamentTeamPoolApiImpl(
            TournamentTeamPoolMapper mapper,
            TournamentTeamPoolDAO dao,
            TournamentTeamApi teamApi,
            TournamentPoolApi poolApi,
            TournamentPhaseApi phaseApi) {
        this.mapper = mapper;
        this.dao = dao;
        this.teamApi = teamApi;
        this.poolApi = poolApi;
        this.phaseApi = phaseApi;
    }

    @Transactional
    public List<TournamentTeamPoolDTO> associateTeamWithPools(
            @Nonnull TeamPoolAssignmentsDTO assignment) {
        String tournamentId = teamApi.get(assignment.getTeamId()).getTournamentId();
        assignment
                .getAssignments()
                .forEach(
                        phaseAssignment -> {
                            TournamentPhaseDTO phase = phaseApi.get(phaseAssignment.getPhaseId());
                            Preconditions.checkArgument(
                                    phase.getTournamentId().equals(tournamentId));

                            if (phaseAssignment.getPoolId() != null) {
                                TournamentPoolDTO pool = poolApi.get(phaseAssignment.getPoolId());
                                Preconditions.checkArgument(
                                        pool.getTournamentId().equals(tournamentId));
                                Preconditions.checkArgument(
                                        pool.getPhaseId().equals(phaseAssignment.getPhaseId()));
                            }

                            deleteExistingAssociations(
                                    assignment.getTeamId(), phaseAssignment.getPhaseId());
                        });
        return assignment.getAssignments().stream()
                .filter(phaseAssignment -> phaseAssignment.getPoolId() != null)
                .map(
                        phaseAssignment -> {
                            TournamentTeamPoolDTO dto = new TournamentTeamPoolDTO();
                            dto.setPoolId(phaseAssignment.getPoolId());
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

    private void deleteExistingAssociations(String teamId, String phaseId) {
        try {
            Optional.ofNullable(getDao().findByTeamAndPhase(teamId, phaseId))
                    .ifPresent(result -> delete(result.getId()));
        } catch (NoResultException ignored) {

        }
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
