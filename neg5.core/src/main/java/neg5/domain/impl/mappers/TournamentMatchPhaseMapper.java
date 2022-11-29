package neg5.domain.impl.mappers;

import com.google.inject.Singleton;
import org.neg5.TournamentMatchPhaseDTO;
import neg5.domain.impl.entities.TournamentMatchPhase;

@Singleton
public class TournamentMatchPhaseMapper extends AbstractObjectMapper<TournamentMatchPhase, TournamentMatchPhaseDTO> {

    public TournamentMatchPhaseMapper() {
        super(TournamentMatchPhase.class, TournamentMatchPhaseDTO.class);
    }
}
