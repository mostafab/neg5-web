package org.neg5.mappers;

import neg5.domain.impl.mappers.TournamentPlayerMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.neg5.TournamentPlayerDTO;
import neg5.domain.impl.entities.Tournament;
import neg5.domain.impl.entities.TournamentPlayer;
import neg5.domain.impl.entities.TournamentTeam;

@ExtendWith(MockitoExtension.class)
public class TournamentPlayerMapperTest {

    private static final String PLAYER_ID = "12345";
    private static final String TEAM_ID = "123";
    private static final String TOURNAMENT_ID = "1";

    @InjectMocks private TournamentPlayerMapper playerMapper;

    @Test
    public void testPlayerMappedCorrectlyEntityToDTO() {
        TournamentPlayer entity = buildEntity();

        TournamentPlayerDTO dto = playerMapper.toDTO(entity);

        Assert.assertNotNull(dto);
        Assert.assertEquals(entity.getId(), dto.getId());
        Assert.assertEquals(entity.getName(), dto.getName());

        Assert.assertNotNull(dto.getTeamId());
        Assert.assertEquals(entity.getTeam().getId(), dto.getTeamId());

        Assert.assertNotNull(dto.getTournamentId());
        Assert.assertEquals(entity.getTournament().getId(), dto.getTournamentId());
    }

    @Test
    public void testPlayerMappedCorrectlyDTOToEntity() {
        TournamentPlayerDTO dto = buildDTO();

        TournamentPlayer entity = playerMapper.mergeToEntity(dto);

        Assert.assertNotNull(entity);
        Assert.assertEquals(dto.getId(), entity.getId());
        Assert.assertEquals(dto.getName(), entity.getName());

        Assert.assertNotNull(entity.getTournament());
        Assert.assertEquals(dto.getTournamentId(), entity.getTournament().getId());

        Assert.assertNotNull(entity.getTeam());
        Assert.assertEquals(dto.getTeamId(), entity.getTeam().getId());
    }

    private TournamentPlayer buildEntity() {
        TournamentPlayer player = new TournamentPlayer();
        player.setId(PLAYER_ID);
        player.setName("TEST_NAME");

        TournamentTeam team = new TournamentTeam();
        team.setId(TEAM_ID);

        player.setTeam(team);

        Tournament tournament = new Tournament();
        tournament.setId(TOURNAMENT_ID);

        player.setTournament(tournament);

        return player;
    }

    private TournamentPlayerDTO buildDTO() {
        TournamentPlayerDTO dto = new TournamentPlayerDTO();
        dto.setId(PLAYER_ID);
        dto.setTournamentId(TOURNAMENT_ID);
        dto.setTeamId(TEAM_ID);
        dto.setName("TEST_12345");

        return dto;
    }
}
