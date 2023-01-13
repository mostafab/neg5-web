package neg5.domain.impl.mappers;

import com.google.inject.Singleton;
import neg5.domain.api.TournamentScheduleDTO;
import neg5.domain.impl.entities.TournamentSchedule;

@Singleton
public class TournamentScheduleMapper
        extends AbstractObjectMapper<TournamentSchedule, TournamentScheduleDTO> {

    TournamentScheduleMapper() {
        super(TournamentSchedule.class, TournamentScheduleDTO.class);
    }
}
