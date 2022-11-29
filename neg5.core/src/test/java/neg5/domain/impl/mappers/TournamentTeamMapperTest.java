package neg5.domain.impl.mappers;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.neg5.TournamentPoolDTO;
import org.neg5.TournamentPlayerDTO;
import org.neg5.TournamentTeamDTO;
import neg5.domain.impl.entities.Tournament;
import neg5.domain.impl.entities.TournamentPool;
import neg5.domain.impl.entities.TournamentPlayer;
import neg5.domain.impl.entities.TournamentTeam;

import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TournamentTeamMapperTest {

    @Mock private TournamentPoolMapper divisionMapper;
    @Mock private TournamentPlayerMapper playerMapper;

    @InjectMocks private TournamentTeamMapper teamMapper;

    private static final String TEAM_ID = "1234";
    private static final String TOURNAMENT_ID = "4321";
    private static final String TEAM_NAME = "Norcross";

    @Test
    public void testTeamMappedCorrectlyEntityToDTO() {
        TournamentTeam entity = buildEntity();
        entity.setPlayers(null);
        entity.setDivisions(null);

        TournamentTeamDTO dto = teamMapper.toDTO(entity);

        Assert.assertEquals(entity.getId(), dto.getId());
        Assert.assertNotNull(dto.getPlayers());
        Assert.assertEquals(0, dto.getPlayers().size());
        Assert.assertNotNull(dto.getDivisions());
        Assert.assertEquals(0, dto.getDivisions().size());

        Mockito.verify(divisionMapper, Mockito.times(0)).toDTO(any());
        Mockito.verify(playerMapper, Mockito.times(0)).toDTO(any());
    }

    @Test
    public void testTeamMappedCorrectlyEntityToDTODivisionsAndPhases() {
        TournamentTeam entity = buildEntity();

        entity.setPlayers(new HashSet<>());
        entity.getPlayers().add(new TournamentPlayer());

        entity.setDivisions(new HashSet<>());
        entity.getDivisions().add(new TournamentPool());

        when(divisionMapper.toDTO(any()))
                .thenReturn(new TournamentPoolDTO());
        when(playerMapper.toDTO(any()))
                .thenReturn(new TournamentPlayerDTO());

        TournamentTeamDTO dto = teamMapper.toDTO(entity);

        Assert.assertEquals(1, dto.getPlayers().size());
        Assert.assertEquals(1, dto.getDivisions().size());

        Mockito.verify(divisionMapper, Mockito.times(1)).toDTO(any());
        Mockito.verify(playerMapper, Mockito.times(1)).toDTO(any());
    }

    @Test
    public void testTeamMappedCorrectlyDtoToEntity() {
        TournamentTeamDTO dto = buildDTO();

        TournamentTeam entity = teamMapper.mergeToEntity(dto);

        Assert.assertEquals(dto.getId(), entity.getId());
        Assert.assertEquals(dto.getName(), entity.getName());

        Assert.assertNotNull(entity.getTournament());
        Assert.assertEquals(dto.getTournamentId(), entity.getTournament().getId());
    }

    private TournamentTeamDTO buildDTO() {
        TournamentTeamDTO team = new TournamentTeamDTO();
        team.setId(TEAM_ID);
        team.setTournamentId(TOURNAMENT_ID);
        team.setName(TEAM_NAME);

        return team;
    }

    private TournamentTeam buildEntity() {
        TournamentTeam team = new TournamentTeam();
        team.setId(TEAM_ID);
        team.setTournament(new Tournament());
        team.getTournament().setId(TOURNAMENT_ID);
        team.setName(TEAM_NAME);

        return team;
    }
}
