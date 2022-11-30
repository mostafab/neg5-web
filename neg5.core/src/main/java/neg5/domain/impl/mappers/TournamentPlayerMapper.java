package neg5.domain.impl.mappers;

import com.google.inject.Singleton;
import neg5.domain.api.TournamentPlayerDTO;
import neg5.domain.impl.entities.TournamentPlayer;

@Singleton
public class TournamentPlayerMapper
        extends AbstractObjectMapper<TournamentPlayer, TournamentPlayerDTO> {

    protected TournamentPlayerMapper() {
        super(TournamentPlayer.class, TournamentPlayerDTO.class);
    }
}
