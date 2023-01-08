package neg5.domain.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import neg5.domain.api.ScoresheetCycleDTO;
import neg5.domain.api.TournamentScoresheetCycleApi;
import neg5.domain.impl.dataAccess.TournamentScoresheetCycleDAO;
import neg5.domain.impl.entities.TournamentScoresheetCycle;
import neg5.domain.impl.mappers.TournamentScoresheetCycleMapper;

@Singleton
public class TournamentScoresheetCycleApiImpl
        extends AbstractApiLayerImpl<TournamentScoresheetCycle, ScoresheetCycleDTO, Long>
        implements TournamentScoresheetCycleApi {

    private final TournamentScoresheetCycleMapper mapper;
    private final TournamentScoresheetCycleDAO dao;

    @Inject
    public TournamentScoresheetCycleApiImpl(
            TournamentScoresheetCycleMapper mapper, TournamentScoresheetCycleDAO dao) {
        this.mapper = mapper;
        this.dao = dao;
    }

    @Override
    protected TournamentScoresheetCycleDAO getDao() {
        return dao;
    }

    @Override
    protected TournamentScoresheetCycleMapper getMapper() {
        return mapper;
    }
}
