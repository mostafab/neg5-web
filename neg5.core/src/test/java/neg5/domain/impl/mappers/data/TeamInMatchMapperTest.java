package neg5.domain.impl.mappers.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import neg5.domain.api.MatchTeamDTO;
import neg5.domain.impl.entities.transformers.data.TeamInMatch;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TeamInMatchMapperTest {

    @Mock private TeamMatchPlayerMapper teamMatchPlayerMapper;
    @InjectMocks private TeamInMatchMapper teamInMatchMapper;

    @Test
    public void testMapsToDtoCorrectly() {
        TeamInMatch teamInMatch = buildTeamInMatch();

        MatchTeamDTO dto = teamInMatchMapper.toDTO(teamInMatch);

        assertEquals(teamInMatch.getTeamId(), dto.getTeamId());
        assertEquals(teamInMatch.getMatchId(), dto.getMatchId());
        assertEquals(teamInMatch.getScore(), dto.getScore());
        assertEquals(teamInMatch.getBouncebackPoints(), dto.getBouncebackPoints());
        assertEquals(teamInMatch.getForfeit(), dto.getForfeit());
        assertNotNull(dto.getPlayers());
        assertTrue(dto.getPlayers().isEmpty());
    }

    @Test
    public void testMapsToDtoWhenPlayersNull() {
        TeamInMatch teamInMatch = buildTeamInMatch();
        teamInMatch.setPlayers(null);

        MatchTeamDTO dto = teamInMatchMapper.toDTO(teamInMatch);

        Assert.assertNotNull(dto.getPlayers());
        Assert.assertTrue(dto.getPlayers().isEmpty());
    }

    private TeamInMatch buildTeamInMatch() {
        TeamInMatch teamInMatch = new TeamInMatch();
        teamInMatch.setMatchId("MATCH_ID");
        teamInMatch.setScore(100);
        teamInMatch.setBouncebackPoints(100);
        teamInMatch.setOvertimeTossups(0);
        teamInMatch.setTeamId("TEAM_ID");
        teamInMatch.setPlayers(new HashSet<>());
        teamInMatch.setForfeit(true);

        return teamInMatch;
    }
}
