package neg5.domain.impl.mappers;

import com.google.inject.Singleton;
import neg5.domain.api.TournamentDTO;
import neg5.domain.api.TournamentRulesDTO;

@Singleton
public class TournamentToTournamentRulesMapper
        extends AbstractObjectMapper<TournamentDTO, TournamentRulesDTO> {

    protected TournamentToTournamentRulesMapper() {
        super(TournamentDTO.class, TournamentRulesDTO.class);
    }
}
