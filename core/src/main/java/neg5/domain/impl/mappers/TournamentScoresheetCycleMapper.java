package neg5.domain.impl.mappers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;
import neg5.domain.api.TournamentScoresheetCycleAnswerDTO;
import neg5.domain.api.TournamentScoresheetCycleBonusesDTO;
import neg5.domain.api.TournamentScoresheetCycleDTO;
import neg5.domain.impl.entities.TournamentScoresheetCycle;

@Singleton
public class TournamentScoresheetCycleMapper
        extends AbstractObjectMapper<TournamentScoresheetCycle, TournamentScoresheetCycleDTO> {

    private final TournamentScoresheetCycleAnswerMapper answerMapper;
    private final TournamentScoresheetCycleBonusMapper bonusMapper;

    @Inject
    protected TournamentScoresheetCycleMapper(
            TournamentScoresheetCycleAnswerMapper answerMapper,
            TournamentScoresheetCycleBonusMapper bonusMapper) {
        super(TournamentScoresheetCycle.class, TournamentScoresheetCycleDTO.class);
        this.answerMapper = answerMapper;
        this.bonusMapper = bonusMapper;
    }

    @Override
    protected void enrichDTO(
            TournamentScoresheetCycleDTO scoresheetCycleDTO,
            TournamentScoresheetCycle tournamentScoresheetCycle) {
        scoresheetCycleDTO.setAnswers(
                tournamentScoresheetCycle.getAnswers() == null
                        ? new ArrayList<>()
                        : tournamentScoresheetCycle.getAnswers().stream()
                                .map(answerMapper::toDTO)
                                .sorted(
                                        Comparator.comparing(
                                                TournamentScoresheetCycleAnswerDTO::getNumber))
                                .collect(Collectors.toList()));

        scoresheetCycleDTO.setBonuses(
                tournamentScoresheetCycle.getBonuses() == null
                        ? new ArrayList<>()
                        : tournamentScoresheetCycle.getBonuses().stream()
                                .map(bonusMapper::toDTO)
                                .sorted(
                                        Comparator.comparing(
                                                TournamentScoresheetCycleBonusesDTO::getNumber))
                                .collect(Collectors.toList()));
    }

    @Override
    protected void addMappings() {
        getEntityToDTOTypeMap()
                .addMappings(
                        m -> {
                            m.skip(TournamentScoresheetCycleDTO::setAnswers);
                            m.skip(TournamentScoresheetCycleDTO::setBonuses);
                        });
        getDtoToEntityTypeMap()
                .addMappings(
                        m -> {
                            m.skip(TournamentScoresheetCycle::setAnswers);
                            m.skip(TournamentScoresheetCycle::setBonuses);
                        });
    }
}
