package neg5.domain.api;

import org.neg5.TournamentTeamDTO;

import javax.annotation.Nonnull;
import java.util.Set;

public interface TournamentTeamApi extends DomainObjectApiLayer<TournamentTeamDTO, String> {

    TournamentTeamDTO updateTeamPools(@Nonnull String teamId, @Nonnull Set<String> poolIds);
}