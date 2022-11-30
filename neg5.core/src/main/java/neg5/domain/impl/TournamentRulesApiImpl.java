package neg5.domain.impl;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import neg5.domain.api.TournamentApi;
import neg5.domain.api.TournamentRulesApi;
import neg5.domain.api.TournamentRulesDTO;
import neg5.domain.impl.mappers.TournamentRulesMapper;

public class TournamentRulesApiImpl implements TournamentRulesApi {

    private final TournamentApi tournamentManager;
    private final TournamentRulesMapper tournamentRulesMapper;

    @Inject
    public TournamentRulesApiImpl(
            TournamentApi tournamentManager, TournamentRulesMapper tournamentRulesMapper) {
        this.tournamentManager = tournamentManager;
        this.tournamentRulesMapper = tournamentRulesMapper;
    }

    @Transactional
    public TournamentRulesDTO getForTournament(String tournamentId) {
        return tournamentRulesMapper.toDTO(tournamentManager.get(tournamentId));
    }
}
