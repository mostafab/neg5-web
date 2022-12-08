package neg5.domain.impl.mappers;

import com.google.inject.Singleton;
import neg5.domain.api.TournamentPoolDTO;
import neg5.domain.impl.entities.TournamentPool;

@Singleton
public class TournamentPoolMapper extends AbstractObjectMapper<TournamentPool, TournamentPoolDTO> {

    protected TournamentPoolMapper() {
        super(TournamentPool.class, TournamentPoolDTO.class);
    }
}
