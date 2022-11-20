package org.neg5.mappers;

import org.neg5.TournamentDTO;
import org.neg5.TournamentRulesDTO;

public class TournamentRulesMapper extends AbstractObjectMapper<TournamentDTO, TournamentRulesDTO> {

    protected TournamentRulesMapper() {
        super(TournamentDTO.class, TournamentRulesDTO.class);
    }
}
