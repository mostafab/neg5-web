package neg5.domain.api;

import neg5.domain.impl.entities.compositeIds.TournamentTossupValueId;
import org.neg5.TournamentTossupValueDTO;

import java.util.Set;

public interface TournamentTossupValueApi extends DomainObjectApiLayer<TournamentTossupValueDTO, TournamentTossupValueId> {

    void deleteAllFromTournament(String tournamentId);

    Set<TournamentTossupValueDTO> getDefaultTournamentValues();
}