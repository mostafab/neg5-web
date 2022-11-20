package org.neg5.mappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.neg5.TournamentDTO;
import org.neg5.TournamentRulesDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TournamentRulesMapperTest {

    private TournamentRulesMapper rulesMapper;

    private TournamentDTO tournament;

    @BeforeEach
    public void setup() {
        rulesMapper = new TournamentRulesMapper();
    }

    @Test
    public void testMapsTournamentDTOToRules() {
        tournament = new TournamentDTO();
        tournament.setBonusPointValue(20L);
        tournament.setUsesBouncebacks(false);
        tournament.setPartsPerBonus(3L);
        tournament.setMaxActivePlayersPerTeam(5);

        TournamentRulesDTO rules = rulesMapper.toDTO(tournament);
        assertEquals(tournament.getBonusPointValue(), rules.getBonusPointValue());
        assertEquals(tournament.getUsesBouncebacks(), rules.getUsesBouncebacks());
        assertEquals(tournament.getPartsPerBonus(), rules.getPartsPerBonus());
        assertEquals(tournament.getMaxActivePlayersPerTeam(), rules.getMaxActivePlayersPerTeam());
    }
}
