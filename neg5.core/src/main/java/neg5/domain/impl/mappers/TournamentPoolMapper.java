package neg5.domain.impl.mappers;

import com.google.inject.Singleton;
import org.neg5.TournamentPoolDTO;
import neg5.domain.impl.entities.TournamentPool;

@Singleton
public class TournamentPoolMapper extends AbstractObjectMapper<TournamentPool, TournamentPoolDTO> {

    protected TournamentPoolMapper() {
        super(TournamentPool.class, TournamentPoolDTO.class);
    }
}
