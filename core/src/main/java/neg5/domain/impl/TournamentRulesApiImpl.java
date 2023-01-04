package neg5.domain.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import javax.annotation.Nonnull;
import neg5.domain.api.TournamentApi;
import neg5.domain.api.TournamentDTO;
import neg5.domain.api.TournamentRulesApi;
import neg5.domain.api.TournamentRulesDTO;
import neg5.domain.api.TournamentTossupValueApi;
import neg5.domain.impl.mappers.TournamentToTournamentRulesMapper;

@Singleton
public class TournamentRulesApiImpl implements TournamentRulesApi {

    private final TournamentApi tournamentManager;
    private final TournamentTossupValueApi tournamentTossupValueApi;
    private final TournamentToTournamentRulesMapper tournamentToTournamentRulesMapper;

    @Inject
    public TournamentRulesApiImpl(
            TournamentApi tournamentManager,
            TournamentTossupValueApi tournamentTossupValueApi,
            TournamentToTournamentRulesMapper tournamentToTournamentRulesMapper) {
        this.tournamentManager = tournamentManager;
        this.tournamentTossupValueApi = tournamentTossupValueApi;
        this.tournamentToTournamentRulesMapper = tournamentToTournamentRulesMapper;
    }

    @Override
    @Transactional
    public TournamentRulesDTO update(
            @Nonnull String tournamentId, @Nonnull TournamentRulesDTO rules) {
        TournamentDTO tournament = tournamentManager.get(tournamentId);
        tournament = tournamentToTournamentRulesMapper.mergeToEntity(rules, tournament);
        tournamentManager.update(tournament);
        if (rules.getTossupValues() != null) {
            tournamentTossupValueApi.updateTournamentTossupValues(
                    tournamentId, rules.getTossupValues());
        }
        return rules;
    }

    @Transactional
    public TournamentRulesDTO getForTournament(String tournamentId) {
        return tournamentToTournamentRulesMapper.toDTO(tournamentManager.get(tournamentId));
    }
}
