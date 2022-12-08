package neg5.domain.api;

import java.util.Set;
import javax.annotation.Nonnull;

public interface TournamentTeamApi extends DomainObjectApiLayer<TournamentTeamDTO, String> {

    TournamentTeamDTO updateTeamPools(@Nonnull String teamId, @Nonnull Set<String> poolIds);
}
