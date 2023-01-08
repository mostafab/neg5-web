package neg5.domain.impl.mappers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;
import neg5.domain.api.ScoresheetCycleAnswerDTO;
import neg5.domain.api.ScoresheetCycleDTO;
import neg5.domain.impl.entities.TournamentScoresheetCycle;

@Singleton
public class TournamentScoresheetCycleMapper
        extends AbstractObjectMapper<TournamentScoresheetCycle, ScoresheetCycleDTO> {

    private final TournamentScoresheetCycleAnswerMapper answerMapper;

    @Inject
    protected TournamentScoresheetCycleMapper(TournamentScoresheetCycleAnswerMapper answerMapper) {
        super(TournamentScoresheetCycle.class, ScoresheetCycleDTO.class);
        this.answerMapper = answerMapper;
    }

    @Override
    protected void enrichDTO(
            ScoresheetCycleDTO scoresheetCycleDTO,
            TournamentScoresheetCycle tournamentScoresheetCycle) {
        scoresheetCycleDTO.setAnswers(
                tournamentScoresheetCycle.getAnswers() == null
                        ? new ArrayList<>()
                        : tournamentScoresheetCycle.getAnswers().stream()
                                .map(answerMapper::toDTO)
                                .sorted(Comparator.comparing(ScoresheetCycleAnswerDTO::getNumber))
                                .collect(Collectors.toList()));
    }

    @Override
    protected void addMappings() {
        getEntityToDTOTypeMap()
                .addMappings(
                        m -> {
                            m.skip(ScoresheetCycleDTO::setAnswers);
                        });
        getDtoToEntityTypeMap()
                .addMappings(
                        m -> {
                            m.skip(TournamentScoresheetCycle::setAnswers);
                        });
    }
}
