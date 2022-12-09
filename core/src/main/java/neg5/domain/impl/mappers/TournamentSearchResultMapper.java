package neg5.domain.impl.mappers;

import com.google.inject.Singleton;
import neg5.domain.api.TournamentSearchResultDTO;
import neg5.domain.impl.entities.Tournament;

@Singleton
public class TournamentSearchResultMapper
        extends AbstractObjectMapper<Tournament, TournamentSearchResultDTO> {

    public TournamentSearchResultMapper() {
        super(Tournament.class, TournamentSearchResultDTO.class);
    }
}
