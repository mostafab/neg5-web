package neg5.domain.impl.matchValidators;

import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import neg5.domain.api.TournamentMatchDTO;
import neg5.domain.api.TournamentPlayerDTO;
import neg5.domain.api.TournamentRulesDTO;
import neg5.domain.api.TournamentTeamDTO;

public class MatchValidationContext {

    private final List<TournamentMatchDTO> allMatches;
    private final TournamentMatchDTO subject;
    private final Map<String, TournamentTeamDTO> teamNamesById;
    private final Map<String, TournamentPlayerDTO> playerNamesById;
    private final TournamentRulesDTO rules;

    public MatchValidationContext(
            @Nonnull List<TournamentMatchDTO> allMatches,
            @Nonnull TournamentMatchDTO subject,
            @Nullable TournamentRulesDTO rules,
            @Nullable Map<String, TournamentTeamDTO> teamNamesById,
            @Nullable Map<String, TournamentPlayerDTO> playerNamesById) {
        this.allMatches = allMatches;
        this.subject = subject;
        this.rules = rules;
        this.teamNamesById = teamNamesById;
        this.playerNamesById = playerNamesById;
    }

    @Nonnull
    public List<TournamentMatchDTO> getAllMatches() {
        return allMatches;
    }

    @Nonnull
    public TournamentMatchDTO getSubject() {
        return subject;
    }

    @Nullable
    public TournamentRulesDTO getRules() {
        return rules;
    }

    @Nullable
    public Map<String, TournamentTeamDTO> getTeamNamesById() {
        return teamNamesById;
    }

    @Nullable
    public Map<String, TournamentPlayerDTO> getPlayerNamesById() {
        return playerNamesById;
    }
}
