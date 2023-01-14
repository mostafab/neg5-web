package neg5.domain.impl.mappers;

import com.google.inject.Singleton;
import neg5.domain.api.TournamentScheduledMatchDTO;
import neg5.domain.impl.entities.TournamentScheduledMatch;

@Singleton
public class TournamentScheduledMatchMapper
        extends AbstractObjectMapper<TournamentScheduledMatch, TournamentScheduledMatchDTO> {

    protected TournamentScheduledMatchMapper() {
        super(TournamentScheduledMatch.class, TournamentScheduledMatchDTO.class);
    }
}
