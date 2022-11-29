package neg5.domain.impl.mappers;

import neg5.domain.api.TournamentDTO;
import neg5.domain.api.TournamentRulesDTO;

public class TournamentRulesMapper extends AbstractObjectMapper<TournamentDTO, TournamentRulesDTO> {

    protected TournamentRulesMapper() {
        super(TournamentDTO.class, TournamentRulesDTO.class);
    }
}
