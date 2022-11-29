package neg5.domain.impl.mappers;

import com.google.inject.Singleton;
import org.neg5.TournamentPhaseDTO;
import neg5.domain.impl.entities.TournamentPhase;

@Singleton
public class TournamentPhaseMapper extends AbstractObjectMapper<TournamentPhase, TournamentPhaseDTO> {

    protected TournamentPhaseMapper() {
        super(TournamentPhase.class, TournamentPhaseDTO.class);
    }
}
