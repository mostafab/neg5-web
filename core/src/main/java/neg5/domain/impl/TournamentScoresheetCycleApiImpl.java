package neg5.domain.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import java.util.ArrayList;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import neg5.domain.api.ScoresheetCycleDTO;
import neg5.domain.api.TournamentScoresheetCycleAnswerApi;
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
    private final TournamentScoresheetCycleAnswerApi answerApi;

    @Inject
    public TournamentScoresheetCycleApiImpl(
            TournamentScoresheetCycleMapper mapper,
            TournamentScoresheetCycleDAO dao,
            TournamentScoresheetCycleAnswerApi answerApi) {
        this.mapper = mapper;
        this.dao = dao;
        this.answerApi = answerApi;
    }

    @Override
    @Transactional
    public ScoresheetCycleDTO create(@Nonnull ScoresheetCycleDTO dto) {
        ScoresheetCycleDTO result = super.create(dto);
        result.setAnswers(
                dto.getAnswers() == null
                        ? new ArrayList<>()
                        : dto.getAnswers().stream()
                                .map(
                                        answer -> {
                                            answer.setCycleId(result.getId());
                                            return answerApi.create(answer);
                                        })
                                .collect(Collectors.toList()));
        return result;
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
