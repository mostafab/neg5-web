package neg5.domain.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import neg5.domain.api.ScoresheetDTO;
import neg5.domain.api.TournamentMatchApi;
import neg5.domain.api.TournamentMatchDTO;
import neg5.domain.api.TournamentScoresheetApi;
import neg5.domain.api.TournamentScoresheetCycleApi;
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
    private final TournamentScoresheetCycleApi scoresheetCycleApi;

    @Inject
    public TournamentScoresheetApiImpl(
            ScoresheetToMatchConverter scoresheetConverter,
            TournamentMatchApi matchApi,
            TournamentScoresheetDAO scoresheetDAO,
            TournamentScoresheetMapper scoresheetMapper,
            TournamentScoresheetCycleApi scoresheetCycleApi) {
        this.scoresheetConverter = scoresheetConverter;
        this.matchApi = matchApi;
        this.scoresheetDAO = scoresheetDAO;
        this.scoresheetMapper = scoresheetMapper;
        this.scoresheetCycleApi = scoresheetCycleApi;
    }

    @Override
    @Transactional
    public ScoresheetDTO create(@Nonnull ScoresheetDTO dto) {
        ScoresheetDTO result = super.create(dto);
        result.setCycles(
                dto.getCycles().stream()
                        .map(
                                cycle -> {
                                    cycle.setScoresheetId(result.getId());
                                    return scoresheetCycleApi.create(cycle);
                                })
                        .collect(Collectors.toList()));

        return result;
    }

    @Override
    public TournamentMatchDTO convertToMatch(ScoresheetDTO scoresheet) {
        return scoresheetConverter.convert(scoresheet);
    }

    @Override
    @Transactional
    public TournamentMatchDTO submitScoresheet(ScoresheetDTO scoresheet) {
        scoresheet.setStatus(ScoresheetStatus.SUBMITTED);
        ScoresheetDTO result = scoresheet.getId() == null ? create(scoresheet) : update(scoresheet);
        TournamentMatchDTO converted = convertToMatch(scoresheet);
        converted.setScoresheetId(result.getId());
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
