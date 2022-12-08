package neg5.domain.impl.mappers;

import com.google.inject.Singleton;
import neg5.domain.api.TournamentTeamPoolDTO;
import neg5.domain.impl.entities.TournamentTeamPool;

@Singleton
public class TournamentTeamPoolMapper
        extends AbstractObjectMapper<TournamentTeamPool, TournamentTeamPoolDTO> {

    protected TournamentTeamPoolMapper() {
        super(TournamentTeamPool.class, TournamentTeamPoolDTO.class);
    }
}
