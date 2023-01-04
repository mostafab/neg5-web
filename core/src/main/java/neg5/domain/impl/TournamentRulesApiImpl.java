package neg5.domain.impl;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import javax.annotation.Nonnull;
import neg5.domain.api.TournamentApi;
import neg5.domain.api.TournamentRulesApi;
import neg5.domain.api.TournamentRulesDTO;
import neg5.domain.impl.mappers.TournamentToTournamentRulesMapper;

public class TournamentRulesApiImpl implements TournamentRulesApi {

    private final TournamentApi tournamentManager;
    private final TournamentToTournamentRulesMapper tournamentToTournamentRulesMapper;

    @Inject
    public TournamentRulesApiImpl(
            TournamentApi tournamentManager,
            TournamentToTournamentRulesMapper tournamentToTournamentRulesMapper) {
        this.tournamentManager = tournamentManager;
        this.tournamentToTournamentRulesMapper = tournamentToTournamentRulesMapper;
    }

    @Override
    public TournamentRulesDTO update(
            @Nonnull String tournamentId, @Nonnull TournamentRulesDTO rules) {
        return null;
    }

    @Transactional
    public TournamentRulesDTO getForTournament(String tournamentId) {
        return tournamentToTournamentRulesMapper.toDTO(tournamentManager.get(tournamentId));
    }
}
