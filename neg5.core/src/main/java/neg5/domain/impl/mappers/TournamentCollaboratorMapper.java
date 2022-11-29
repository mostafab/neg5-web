package neg5.domain.impl.mappers;

import com.google.inject.Singleton;
import org.neg5.TournamentCollaboratorDTO;
import neg5.domain.impl.entities.TournamentCollaborator;

@Singleton
public class TournamentCollaboratorMapper extends AbstractObjectMapper<TournamentCollaborator, TournamentCollaboratorDTO> {

    protected TournamentCollaboratorMapper() {
        super(TournamentCollaborator.class, TournamentCollaboratorDTO.class);
    }
}
