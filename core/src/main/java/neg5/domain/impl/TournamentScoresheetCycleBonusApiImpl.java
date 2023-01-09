package neg5.domain.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import neg5.domain.api.TournamentScoresheetCycleBonusApi;
import neg5.domain.api.TournamentScoresheetCycleBonusesDTO;
import neg5.domain.impl.dataAccess.TournamentScoresheetCycleBonusDAO;
import neg5.domain.impl.entities.TournamentScoresheetCycleBonus;
import neg5.domain.impl.mappers.TournamentScoresheetCycleBonusMapper;

@Singleton
public class TournamentScoresheetCycleBonusApiImpl
        extends AbstractApiLayerImpl<
                TournamentScoresheetCycleBonus, TournamentScoresheetCycleBonusesDTO, Long>
        implements TournamentScoresheetCycleBonusApi {

    private final TournamentScoresheetCycleBonusDAO dao;
    private final TournamentScoresheetCycleBonusMapper mapper;

    @Inject
    public TournamentScoresheetCycleBonusApiImpl(
            TournamentScoresheetCycleBonusDAO dao, TournamentScoresheetCycleBonusMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @Override
    protected TournamentScoresheetCycleBonusDAO getDao() {
        return dao;
    }

    @Override
    protected TournamentScoresheetCycleBonusMapper getMapper() {
        return mapper;
    }
}
