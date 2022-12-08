package neg5.domain.impl.mappers;

import com.google.inject.Singleton;
import neg5.domain.api.TournamentDTO;
import neg5.domain.api.UpdateTournamentRequestDTO;

@Singleton
public class UpdateTournamentRequestMapper
        extends AbstractObjectMapper<TournamentDTO, UpdateTournamentRequestDTO> {

    protected UpdateTournamentRequestMapper() {
        super(TournamentDTO.class, UpdateTournamentRequestDTO.class);
    }
}
