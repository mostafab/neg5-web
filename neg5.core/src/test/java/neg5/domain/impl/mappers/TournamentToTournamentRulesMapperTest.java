package neg5.domain.impl.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashSet;
import neg5.domain.api.TournamentDTO;
import neg5.domain.api.TournamentRulesDTO;
import neg5.domain.api.TournamentTossupValueDTO;
import neg5.domain.api.enums.TossupAnswerType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TournamentToTournamentRulesMapperTest {

    private TournamentToTournamentRulesMapper rulesMapper;

    private TournamentDTO tournament;

    @BeforeEach
    public void setup() {
        rulesMapper = new TournamentToTournamentRulesMapper();
    }

    @Test
    public void testMapsTournamentDTOToRules() {
        tournament = new TournamentDTO();
        tournament.setBonusPointValue(20L);
        tournament.setUsesBouncebacks(false);
        tournament.setAllowTies(false);
        tournament.setPartsPerBonus(3L);
        tournament.setMaxActivePlayersPerTeam(5);
        tournament.setTossupValues(new HashSet<>());

        TournamentTossupValueDTO tv = new TournamentTossupValueDTO();
        tv.setTournamentId("Test");
        tv.setAnswerType(TossupAnswerType.BASE);
        tv.setValue(10);
        tournament.getTossupValues().add(tv);

        TournamentRulesDTO rules = rulesMapper.toDTO(tournament);
        assertEquals(tournament.getBonusPointValue(), rules.getBonusPointValue());
        assertEquals(tournament.getUsesBouncebacks(), rules.getUsesBouncebacks());
        assertEquals(tournament.getAllowTies(), rules.getAllowTies());
        assertEquals(tournament.getPartsPerBonus(), rules.getPartsPerBonus());
        assertEquals(tournament.getMaxActivePlayersPerTeam(), rules.getMaxActivePlayersPerTeam());

        assertNotNull(rules.getTossupValues());
        assertEquals(1, rules.getTossupValues().size());
        assertEquals(
                tv.getTournamentId(),
                rules.getTossupValues().stream().findFirst().get().getTournamentId());
        assertEquals(
                tv.getAnswerType(),
                rules.getTossupValues().stream().findFirst().get().getAnswerType());
        assertEquals(tv.getValue(), rules.getTossupValues().stream().findFirst().get().getValue());
    }
}
