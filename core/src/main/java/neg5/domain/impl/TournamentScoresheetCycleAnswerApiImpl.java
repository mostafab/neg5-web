package neg5.domain.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import neg5.domain.api.TournamentScoresheetCycleAnswerApi;
import neg5.domain.api.TournamentScoresheetCycleAnswerDTO;
import neg5.domain.impl.dataAccess.TournamentScoresheetCycleAnswerDAO;
import neg5.domain.impl.entities.TournamentScoresheetCycleAnswer;
import neg5.domain.impl.mappers.TournamentScoresheetCycleAnswerMapper;

@Singleton
public class TournamentScoresheetCycleAnswerApiImpl
        extends AbstractApiLayerImpl<
                TournamentScoresheetCycleAnswer, TournamentScoresheetCycleAnswerDTO, Long>
        implements TournamentScoresheetCycleAnswerApi {

    private final TournamentScoresheetCycleAnswerDAO dao;
    private final TournamentScoresheetCycleAnswerMapper mapper;

    @Inject
    public TournamentScoresheetCycleAnswerApiImpl(
            TournamentScoresheetCycleAnswerDAO dao, TournamentScoresheetCycleAnswerMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @Override
    protected TournamentScoresheetCycleAnswerDAO getDao() {
        return dao;
    }

    protected TournamentScoresheetCycleAnswerMapper getMapper() {
        return mapper;
    }
}
