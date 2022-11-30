package neg5.domain.api;

import java.util.Optional;
import java.util.Set;
import neg5.domain.impl.entities.compositeIds.TournamentCollaboratorId;

public interface TournamentCollaboratorApi
        extends DomainObjectApiLayer<TournamentCollaboratorDTO, TournamentCollaboratorId> {

    TournamentCollaboratorDTO addOrUpdateCollaborator(TournamentCollaboratorDTO collaborator);

    UserTournamentsDTO getUserTournaments(String userId);

    Set<String> getTournamentIdsThatUserCollaboratesOn(String userId);

    Optional<TournamentCollaboratorDTO> getByTournamentAndUsername(
            String tournamentId, String username);
}
