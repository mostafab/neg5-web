package neg5.domain.api;

import neg5.domain.impl.entities.compositeIds.TournamentCollaboratorId;
import org.neg5.TournamentCollaboratorDTO;
import org.neg5.UserTournamentsDTO;

import java.util.Optional;
import java.util.Set;

public interface TournamentCollaboratorApi extends DomainObjectApiLayer<TournamentCollaboratorDTO, TournamentCollaboratorId> {

    TournamentCollaboratorDTO addOrUpdateCollaborator(TournamentCollaboratorDTO collaborator);

    UserTournamentsDTO getUserTournaments(String userId);

    Set<String> getTournamentIdsThatUserCollaboratesOn(String userId);

    Optional<TournamentCollaboratorDTO> getByTournamentAndUsername(String tournamentId,
                                                                   String username);
}
