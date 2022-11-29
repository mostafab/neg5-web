package neg5.domain.impl.mappers;

import org.neg5.TournamentDTO;
import org.neg5.UpdateTournamentRequestDTO;

public class UpdateTournamentRequestMapper extends AbstractObjectMapper<TournamentDTO, UpdateTournamentRequestDTO> {

    protected UpdateTournamentRequestMapper() {
        super(TournamentDTO.class, UpdateTournamentRequestDTO.class);
    }
}
