package neg5.domain.impl;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import neg5.domain.api.TournamentMatchPhaseApi;
import neg5.domain.impl.entities.compositeIds.MatchPhaseId;
import org.neg5.TournamentMatchPhaseDTO;
import neg5.domain.impl.dataAccess.TournamentMatchPhaseDAO;
import neg5.domain.impl.entities.TournamentMatchPhase;
import neg5.domain.impl.mappers.TournamentMatchPhaseMapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TournamentMatchPhaseApiImpl
        extends AbstractApiLayerImpl<TournamentMatchPhase, TournamentMatchPhaseDTO, MatchPhaseId>
        implements TournamentMatchPhaseApi {

    private final TournamentMatchPhaseMapper matchPhaseMapper;
    private final TournamentMatchPhaseDAO dao;

    @Inject
    public TournamentMatchPhaseApiImpl(TournamentMatchPhaseMapper matchPhaseMapper,
                                       TournamentMatchPhaseDAO dao) {
        this.matchPhaseMapper = matchPhaseMapper;
        this.dao = dao;
    }

    @Transactional
    public List<TournamentMatchPhaseDTO> associateMatchWithPhases(Set<String> phaseIds,
                                                                  String matchId,
                                                                  String tournamentId) {
        deleteOldAssociations(matchId);
        return phaseIds.stream()
                .map(phaseId -> {
                   TournamentMatchPhaseDTO dto = new TournamentMatchPhaseDTO();
                   dto.setTournamentId(tournamentId);
                   dto.setMatchId(matchId);
                   dto.setPhaseId(phaseId);
                   return create(dto);
                })
                .collect(Collectors.toList());
    }

    private void deleteOldAssociations(String matchId) {
        findByMatch(matchId).forEach(association -> delete(association.getId()));
    }

    private List<TournamentMatchPhase> findByMatch(String matchId) {
        return getDao().findByMatch(matchId);
    }

    @Override
    protected TournamentMatchPhaseMapper getMapper() {
        return matchPhaseMapper;
    }

    @Override
    protected TournamentMatchPhaseDAO getDao() {
        return dao;
    }
}
