package neg5.domain.impl.mappers;

import static org.mockito.ArgumentMatchers.any;

import neg5.domain.api.TournamentDTO;
import neg5.domain.impl.entities.Account;
import neg5.domain.impl.entities.Tournament;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TournamentMapperTest {

    @Mock private TournamentPhaseMapper phaseMapper;
    @Mock private TournamentPoolMapper divisionMapper;
    @Mock private TournamentTossupValueMapper tournamentTossupValueMapper;

    @InjectMocks private TournamentMapper tournamentMapper;

    private static final String TOURNAMENT_ID = "THIS_ID";

    @Test
    public void testMapsOwnPropertiesCorrectlyFromEntityToDTO() {
        Tournament tournament = buildTournament();

        TournamentDTO dto = tournamentMapper.toDTO(tournament);

        Assert.assertEquals(tournament.getId(), dto.getId());
        Assert.assertEquals(tournament.getName(), dto.getName());
        Assert.assertEquals(tournament.getBonusPointValue(), dto.getBonusPointValue());
    }

    @Test
    public void testDoesNotCallSubMappersIfNullFromEntityToDTO() {
        Tournament tournament = buildTournament();
        tournament.setPhases(null);
        tournament.setDivisions(null);
        tournament.setTossupValues(null);

        tournamentMapper.toDTO(tournament);
        Mockito.verify(phaseMapper, Mockito.never()).toDTO(any());
        Mockito.verify(divisionMapper, Mockito.never()).toDTO(any());
        Mockito.verify(tournamentTossupValueMapper, Mockito.never()).toDTO(any());
    }

    @Test
    public void testMergeToEntityMapsPropertiesCorrectly() {
        TournamentDTO tournament = buildTournamentDTO();
        Tournament entity = tournamentMapper.mergeToEntity(tournament);

        Assert.assertEquals(tournament.getId(), entity.getId());
        Assert.assertEquals(tournament.getName(), entity.getName());
        Assert.assertEquals(tournament.getBonusPointValue(), entity.getBonusPointValue());
    }

    @Test
    public void testMergeToEntityPhaseIdMapped() {
        TournamentDTO tournament = buildTournamentDTO();

        String phaseId = "PHASE";

        tournament.setCurrentPhaseId(phaseId);

        Tournament entity = tournamentMapper.mergeToEntity(tournament);
        Assert.assertNotNull(entity.getCurrentPhase());
        Assert.assertEquals(tournament.getCurrentPhaseId(), entity.getCurrentPhase().getId());
    }

    @Test
    public void testDtoMappingForDirector() {
        Tournament tournament = buildTournament();
        tournament.setDirector(new Account());
        tournament.getDirector().setEmail("test@domain.com");
        tournament.getDirector().setId("User_ID");
        tournament.getDirector().setName("Mostafa");

        TournamentDTO dto = tournamentMapper.toDTO(tournament);
        Assert.assertNotNull(dto.getDirectorId());
        Assert.assertEquals(tournament.getDirector().getId(), dto.getDirectorId());
    }

    @Test
    public void testMergeToEntityForDirector() {
        TournamentDTO dto = buildTournamentDTO();
        dto.setDirectorId("USER_ID");

        Tournament entity = tournamentMapper.mergeToEntity(dto);
        Assert.assertNotNull(entity.getDirector());
        Assert.assertNotNull(entity.getDirector().getId());
        Assert.assertEquals(dto.getDirectorId(), entity.getDirector().getId());
    }

    private Tournament buildTournament() {
        Tournament tournament = new Tournament();
        tournament.setId(TOURNAMENT_ID);
        tournament.setName("NAME");
        tournament.setBonusPointValue(10L);

        return tournament;
    }

    private TournamentDTO buildTournamentDTO() {
        TournamentDTO tournament = new TournamentDTO();
        tournament.setId(TOURNAMENT_ID);
        tournament.setName("NAME");
        tournament.setBonusPointValue(10L);

        return tournament;
    }
}
