package neg5.domain.impl.mappers;

import neg5.domain.api.TournamentDTO;
import neg5.domain.api.UpdateTournamentRequestDTO;

public class UpdateTournamentRequestMapper
        extends AbstractObjectMapper<TournamentDTO, UpdateTournamentRequestDTO> {

    protected UpdateTournamentRequestMapper() {
        super(TournamentDTO.class, UpdateTournamentRequestDTO.class);
    }
}
