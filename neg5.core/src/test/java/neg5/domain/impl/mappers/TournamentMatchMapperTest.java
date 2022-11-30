package neg5.domain.impl.mappers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import neg5.domain.api.MatchTeamDTO;
import neg5.domain.api.TournamentMatchDTO;
import neg5.domain.impl.entities.Tournament;
import neg5.domain.impl.entities.TournamentMatch;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TournamentMatchMapperTest {

    @Mock private MatchTeamMapper matchTeamMapper;
    @InjectMocks private TournamentMatchMapper matchMapper;

    private static final String TOURNAMENT_ID = "TEST_ID";

    @Before
    public void setup() {
        when(matchTeamMapper.toDTO(any())).thenReturn(new MatchTeamDTO());
    }

    @Test
    public void testEntityToDtoMapping() {
        TournamentMatch entity = buildEntity();

        TournamentMatchDTO dto = matchMapper.toDTO(entity);

        Assert.assertEquals(entity.getId(), dto.getId());
        Assert.assertEquals(entity.getModerator(), dto.getModerator());
        Assert.assertEquals(entity.getNotes(), dto.getNotes());
        Assert.assertEquals(entity.getPacket(), dto.getPacket());
        Assert.assertEquals(entity.getRoom(), dto.getRoom());
        Assert.assertEquals(entity.getRound(), dto.getRound());
        Assert.assertEquals(entity.getSerialId(), dto.getSerialId());
        Assert.assertEquals(entity.getTossupsHeard(), dto.getTossupsHeard());

        Assert.assertEquals(entity.getTournament().getId(), dto.getTournamentId());

        Assert.assertNotNull(dto.getPhases());
        Assert.assertEquals(0, dto.getPhases().size());

        Assert.assertNotNull(dto.getTeams());
        Assert.assertEquals(0, dto.getTeams().size());

        verify(matchTeamMapper, times(0)).toDTO(any());
    }

    @Test
    public void testEntityToDtoMappingNoTournament() {
        TournamentMatch entity = buildEntity();
        entity.setTournament(null);

        TournamentMatchDTO dto = matchMapper.toDTO(entity);
        Assert.assertNull(dto.getTournamentId());
    }

    @Test
    public void testDtoToEntityMapping() {
        TournamentMatchDTO dto = buildDTO();

        TournamentMatch entity = matchMapper.mergeToEntity(dto);

        Assert.assertEquals(dto.getId(), entity.getId());
        Assert.assertEquals(dto.getModerator(), entity.getModerator());
        Assert.assertEquals(dto.getNotes(), entity.getNotes());
        Assert.assertEquals(dto.getPacket(), entity.getPacket());
        Assert.assertEquals(dto.getRoom(), entity.getRoom());
        Assert.assertEquals(dto.getRound(), entity.getRound());
        Assert.assertEquals(dto.getSerialId(), entity.getSerialId());
        Assert.assertEquals(dto.getTossupsHeard(), entity.getTossupsHeard());

        Assert.assertNotNull(entity.getTournament());
        Assert.assertEquals(dto.getTournamentId(), entity.getTournament().getId());
    }

    @Test
    public void testDtoToEntityMappingNoTournamentId() {
        TournamentMatchDTO dto = buildDTO();
        dto.setTournamentId(null);

        TournamentMatch entity = matchMapper.mergeToEntity(dto);

        Assert.assertNull(entity.getTournament());
    }

    private TournamentMatchDTO buildDTO() {
        TournamentMatchDTO match = new TournamentMatchDTO();
        match.setId("ID");
        match.setModerator("MODERATOR");
        match.setNotes("NOTES");
        match.setPacket("PACKET");
        match.setRoom("ROOM");
        match.setRound(1L);
        match.setSerialId("12345-LS");
        match.setTossupsHeard(20);
        match.setTournamentId(TOURNAMENT_ID);

        return match;
    }

    private TournamentMatch buildEntity() {
        TournamentMatch match = new TournamentMatch();
        match.setId("ID");
        match.setModerator("MODERATOR");
        match.setNotes("NOTES");
        match.setPacket("PACKET");
        match.setRoom("ROOM");
        match.setRound(1L);
        match.setSerialId("12345-LS");
        match.setTossupsHeard(20);

        match.setTournament(new Tournament());
        match.getTournament().setId(TOURNAMENT_ID);

        return match;
    }
}
