package neg5.api;

import org.neg5.TournamentCollaboratorDTO;
import org.neg5.UserTournamentsDTO;
import org.neg5.data.embeddables.TournamentCollaboratorId;

import java.util.Optional;
import java.util.Set;

public interface TournamentCollaboratorApi extends ObjectApiLayer<TournamentCollaboratorDTO, TournamentCollaboratorId> {

    TournamentCollaboratorDTO addOrUpdateCollaborator(TournamentCollaboratorDTO collaborator);

    UserTournamentsDTO getUserTournaments(String userId);

    Set<String> getTournamentIdsThatUserCollaboratesOn(String userId);

    Optional<TournamentCollaboratorDTO> getByTournamentAndUsername(String tournamentId,
                                                                   String username);
}
