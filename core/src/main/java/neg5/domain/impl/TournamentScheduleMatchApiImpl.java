package neg5.domain.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import neg5.domain.api.TournamentScheduleMatchApi;
import neg5.domain.api.TournamentScheduledMatchDTO;
import neg5.domain.impl.dataAccess.TournamentScheduleMatchDAO;
import neg5.domain.impl.entities.TournamentScheduledMatch;
import neg5.domain.impl.mappers.TournamentScheduledMatchMapper;

@Singleton
public class TournamentScheduleMatchApiImpl
        extends AbstractApiLayerImpl<TournamentScheduledMatch, TournamentScheduledMatchDTO, Long>
        implements TournamentScheduleMatchApi {

    private final TournamentScheduleMatchDAO dao;
    private final TournamentScheduledMatchMapper mapper;

    @Inject
    public TournamentScheduleMatchApiImpl(
            TournamentScheduleMatchDAO dao, TournamentScheduledMatchMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @Override
    protected TournamentScheduleMatchDAO getDao() {
        return dao;
    }

    @Override
    protected TournamentScheduledMatchMapper getMapper() {
        return mapper;
    }
}
