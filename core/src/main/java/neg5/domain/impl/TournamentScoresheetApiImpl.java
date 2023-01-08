package neg5.domain.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import neg5.domain.api.ScoresheetDTO;
import neg5.domain.api.TournamentMatchApi;
import neg5.domain.api.TournamentMatchDTO;
import neg5.domain.api.TournamentScoresheetApi;
import neg5.domain.api.enums.ScoresheetStatus;
import neg5.domain.impl.dataAccess.TournamentScoresheetDAO;
import neg5.domain.impl.entities.TournamentScoresheet;
import neg5.domain.impl.mappers.TournamentScoresheetMapper;
import neg5.domain.impl.scoresheet.ScoresheetToMatchConverter;

@Singleton
public class TournamentScoresheetApiImpl
        extends AbstractApiLayerImpl<TournamentScoresheet, ScoresheetDTO, Long>
        implements TournamentScoresheetApi {

    private final ScoresheetToMatchConverter scoresheetConverter;
    private final TournamentMatchApi matchApi;
    private final TournamentScoresheetDAO scoresheetDAO;
    private final TournamentScoresheetMapper scoresheetMapper;

    @Inject
    public TournamentScoresheetApiImpl(
            ScoresheetToMatchConverter scoresheetConverter,
            TournamentMatchApi matchApi,
            TournamentScoresheetDAO scoresheetDAO,
            TournamentScoresheetMapper scoresheetMapper) {
        this.scoresheetConverter = scoresheetConverter;
        this.matchApi = matchApi;
        this.scoresheetDAO = scoresheetDAO;
        this.scoresheetMapper = scoresheetMapper;
    }

    @Override
    public TournamentMatchDTO convertToMatch(ScoresheetDTO scoresheet) {
        return scoresheetConverter.convert(scoresheet);
    }

    @Override
    @Transactional
    public TournamentMatchDTO submitScoresheet(ScoresheetDTO scoresheet) {
        TournamentMatchDTO converted = convertToMatch(scoresheet);
        scoresheet.setStatus(ScoresheetStatus.SUBMITTED);
        ScoresheetDTO result = scoresheet.getId() == null ? create(scoresheet) : update(scoresheet);
        return matchApi.create(converted);
    }

    @Override
    protected TournamentScoresheetDAO getDao() {
        return scoresheetDAO;
    }

    @Override
    protected TournamentScoresheetMapper getMapper() {
        return scoresheetMapper;
    }
}
