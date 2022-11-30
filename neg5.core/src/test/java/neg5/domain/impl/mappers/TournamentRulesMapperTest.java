package neg5.domain.impl.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import neg5.domain.api.TournamentDTO;
import neg5.domain.api.TournamentRulesDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
