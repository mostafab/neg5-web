package neg5.domain.impl.mappers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.ArrayList;
import java.util.stream.Collectors;
import neg5.domain.api.TournamentScheduleDTO;
import neg5.domain.impl.entities.TournamentSchedule;

@Singleton
public class TournamentScheduleMapper
        extends AbstractObjectMapper<TournamentSchedule, TournamentScheduleDTO> {

    private final TournamentScheduledMatchMapper matchMapper;

    @Inject
    TournamentScheduleMapper(TournamentScheduledMatchMapper matchMapper) {
        super(TournamentSchedule.class, TournamentScheduleDTO.class);
        this.matchMapper = matchMapper;
    }

    @Override
    protected void enrichDTO(TournamentScheduleDTO dto, TournamentSchedule entity) {
        dto.setMatches(
                entity.getMatches() == null
                        ? new ArrayList<>()
                        : entity.getMatches().stream()
                                .map(matchMapper::toDTO)
                                .collect(Collectors.toList()));
    }

    @Override
    protected void addMappings() {
        getEntityToDTOTypeMap()
                .addMappings(
                        m -> {
                            m.skip(TournamentScheduleDTO::setMatches);
                        });
        getDtoToEntityTypeMap()
                .addMappings(
                        m -> {
                            m.skip(TournamentSchedule::setMatches);
                        });
    }
}
