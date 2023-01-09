package neg5.domain.impl.mappers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.stream.Collectors;
import neg5.domain.api.TournamentScoresheetCycleDTO;
import neg5.domain.api.TournamentScoresheetDTO;
import neg5.domain.impl.entities.TournamentScoresheet;

@Singleton
public class TournamentScoresheetMapper
        extends AbstractObjectMapper<TournamentScoresheet, TournamentScoresheetDTO> {

    private final TournamentScoresheetCycleMapper cycleMapper;

    @Inject
    protected TournamentScoresheetMapper(TournamentScoresheetCycleMapper cycleMapper) {
        super(TournamentScoresheet.class, TournamentScoresheetDTO.class);
        this.cycleMapper = cycleMapper;
    }

    @Override
    protected void enrichDTO(
            TournamentScoresheetDTO scoresheetDTO, TournamentScoresheet tournamentScoresheet) {
        scoresheetDTO.setCycles(
                tournamentScoresheet.getCycles() == null
                        ? new ArrayList<>()
                        : tournamentScoresheet.getCycles().stream()
                                .map(cycleMapper::toDTO)
                                .sorted(
                                        Comparator.comparing(
                                                TournamentScoresheetCycleDTO::getNumber))
                                .collect(Collectors.toList()));
        scoresheetDTO.setActivePlayers(
                new HashSet<>(
                        Utilities.mapFromCommaSeparatedString(
                                tournamentScoresheet.getActivePlayers())));
        scoresheetDTO.setPhases(
                new HashSet<>(
                        Utilities.mapFromCommaSeparatedString(tournamentScoresheet.getPhases())));
    }

    @Override
    protected void addMappings() {
        getEntityToDTOTypeMap()
                .addMappings(
                        m -> {
                            m.skip(TournamentScoresheetDTO::setCycles);
                        });
        getDtoToEntityTypeMap()
                .addMappings(
                        m -> {
                            m.skip(TournamentScoresheet::setCycles);
                        });
    }
}
