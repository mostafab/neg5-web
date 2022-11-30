package neg5.domain.impl.mappers;

import neg5.domain.api.TournamentPoolDTO;
import neg5.domain.impl.entities.Tournament;
import neg5.domain.impl.entities.TournamentPhase;
import neg5.domain.impl.entities.TournamentPool;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TournamentPoolMapperTest {

    @InjectMocks private TournamentPoolMapper divisionMapper;

    private static final String TOURNAMENT_ID = "TEST_ID";
    private static final String PHASE_ID = "PHASE_ID";

    @Test
    public void testEntityToDTOMapping() {
        TournamentPool entity = buildEntity();

        TournamentPoolDTO dto = divisionMapper.toDTO(entity);

        Assert.assertEquals(entity.getId(), dto.getId());
        Assert.assertEquals(entity.getName(), dto.getName());

        Assert.assertEquals(entity.getPhase().getId(), dto.getPhaseId());
        Assert.assertEquals(entity.getTournament().getId(), dto.getTournamentId());
    }

    @Test
    public void testEntityToDtoMappingNullTournamentAndPhase() {
        TournamentPool entity = buildEntity();
        entity.setPhase(null);
        entity.setTournament(null);

        TournamentPoolDTO dto = divisionMapper.toDTO(entity);

        Assert.assertNull(dto.getPhaseId());
        Assert.assertNull(dto.getTournamentId());
    }

    @Test
    public void testDtoToEntityMapping() {
        TournamentPoolDTO dto = buildDTO();

        TournamentPool entity = divisionMapper.mergeToEntity(dto);

        Assert.assertEquals(dto.getId(), entity.getId());
        Assert.assertEquals(dto.getName(), entity.getName());

        Assert.assertNotNull(entity.getTournament());
        Assert.assertNotNull(entity.getPhase());

        Assert.assertEquals(dto.getPhaseId(), entity.getPhase().getId());
        Assert.assertEquals(dto.getTournamentId(), entity.getTournament().getId());
    }

    @Test
    public void testDtoToEntityMappingNoTournamentOrPhaseId() {
        TournamentPoolDTO dto = buildDTO();
        dto.setPhaseId(null);
        dto.setTournamentId(null);

        TournamentPool entity = divisionMapper.mergeToEntity(dto);

        Assert.assertNull(entity.getPhase());
        Assert.assertNull(entity.getTournament());
    }

    private TournamentPool buildEntity() {
        TournamentPool division = new TournamentPool();

        division.setId("TEST_ID_DIV");
        division.setName("PHASE");

        division.setTournament(new Tournament());
        division.getTournament().setId(TOURNAMENT_ID);

        division.setPhase(new TournamentPhase());
        division.getPhase().setId(PHASE_ID);

        return division;
    }

    private TournamentPoolDTO buildDTO() {
        TournamentPoolDTO division = new TournamentPoolDTO();

        division.setId("TEST_ID_DIV");
        division.setName("PHASE");

        division.setTournamentId(TOURNAMENT_ID);
        division.setPhaseId(PHASE_ID);

        return division;
    }
}
