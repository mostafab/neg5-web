package neg5.api;

import org.neg5.TournamentTeamDTO;

import javax.annotation.Nonnull;
import java.util.Set;

public interface TournamentTeamApi extends ObjectApiLayer<TournamentTeamDTO, String> {

    TournamentTeamDTO updateTeamPools(@Nonnull String teamId, @Nonnull Set<String> poolIds);
}
