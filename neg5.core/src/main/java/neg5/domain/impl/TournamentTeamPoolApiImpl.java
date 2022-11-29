package neg5.domain.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import neg5.domain.api.TournamentTeamPoolApi;
import org.neg5.TournamentTeamPoolDTO;
import org.neg5.daos.TournamentTeamPoolDAO;
import org.neg5.data.TournamentTeamPool;
import org.neg5.data.embeddables.TournamentTeamPoolId;
import org.neg5.mappers.TournamentTeamPoolMapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class TournamentTeamPoolApiImpl
        extends AbstractApiLayerImpl<TournamentTeamPool, TournamentTeamPoolDTO, TournamentTeamPoolId>
        implements TournamentTeamPoolApi {

    private final TournamentTeamPoolMapper mapper;
    private final TournamentTeamPoolDAO dao;

    @Inject
    public TournamentTeamPoolApiImpl(TournamentTeamPoolMapper mapper,
                                     TournamentTeamPoolDAO dao) {
        this.mapper = mapper;
        this.dao = dao;
    }

    @Transactional
    public List<TournamentTeamPoolDTO> associateTeamWithPools(Set<String> divisionIds,
                                                              String teamId,
                                                              String tournamentId) {
        deleteExistingAssociations(teamId);
        return divisionIds.stream()
                .map(divisionId -> {
                    TournamentTeamPoolDTO dto = new TournamentTeamPoolDTO();
                    dto.setPoolId(divisionId);
                    dto.setTeamId(teamId);
                    dto.setTournamentId(tournamentId);
                    return create(dto);
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
