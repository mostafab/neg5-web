package neg5.domain.impl.mappers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;
import neg5.domain.api.ScoresheetCycleDTO;
import neg5.domain.api.ScoresheetDTO;
import neg5.domain.impl.entities.TournamentScoresheet;

@Singleton
public class TournamentScoresheetMapper
        extends AbstractObjectMapper<TournamentScoresheet, ScoresheetDTO> {

    private final TournamentScoresheetCycleMapper cycleMapper;

    @Inject
    protected TournamentScoresheetMapper(TournamentScoresheetCycleMapper cycleMapper) {
        super(TournamentScoresheet.class, ScoresheetDTO.class);
        this.cycleMapper = cycleMapper;
    }

    @Override
    protected void enrichDTO(
            ScoresheetDTO scoresheetDTO, TournamentScoresheet tournamentScoresheet) {
        scoresheetDTO.setCycles(
                tournamentScoresheet.getCycles() == null
                        ? new ArrayList<>()
                        : tournamentScoresheet.getCycles().stream()
                                .map(cycleMapper::toDTO)
                                .sorted(Comparator.comparing(ScoresheetCycleDTO::getNumber))
                                .collect(Collectors.toList()));
    }

    @Override
    protected void addMappings() {
        getEntityToDTOTypeMap()
                .addMappings(
                        m -> {
                            m.skip(ScoresheetDTO::setCycles);
                        });
        getDtoToEntityTypeMap()
                .addMappings(
                        m -> {
                            m.skip(TournamentScoresheet::setCycles);
                        });
    }
}
