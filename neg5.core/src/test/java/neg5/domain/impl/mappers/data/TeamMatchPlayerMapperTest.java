package neg5.domain.impl.mappers.data;

import com.google.common.collect.Sets;
import neg5.domain.impl.entities.transformers.data.TeamMatchPlayer;
import neg5.domain.impl.entities.transformers.data.TeamMatchPlayerAnswer;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.neg5.MatchPlayerAnswerDTO;
import org.neg5.MatchPlayerDTO;

import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TeamMatchPlayerMapperTest {

    @Mock private TeamMatchPlayerAnswerMapper teamMatchPlayerAnswerMapper;
    @InjectMocks private TeamMatchPlayerMapper teamMatchPlayerMapper;

    @Test
    public void testMapsToDtoCorrectly() {
        TeamMatchPlayer teamMatchPlayer = buildTeamMatchPlayer();

        MatchPlayerDTO dto = teamMatchPlayerMapper.toDTO(teamMatchPlayer);

        Assert.assertEquals(teamMatchPlayer.getMatchId(), dto.getMatchId());
        Assert.assertEquals(teamMatchPlayer.getPlayerId(), dto.getPlayerId());
        Assert.assertEquals(teamMatchPlayer.getTossupsHeard(), dto.getTossupsHeard());
        Assert.assertNotNull(dto.getTossupsHeard());
    }

    @Test
    public void testMapsToDtoCorrectlyAndCallsSubmapper() {
        when(teamMatchPlayerAnswerMapper.toDTO(any())).thenAnswer(inv -> new MatchPlayerAnswerDTO());

        TeamMatchPlayer teamMatchPlayer = buildTeamMatchPlayer();
        teamMatchPlayer.setTossupValues(Sets.newHashSet(new TeamMatchPlayerAnswer(), new TeamMatchPlayerAnswer()));

        MatchPlayerDTO dto = teamMatchPlayerMapper.toDTO(teamMatchPlayer);

        Assert.assertNotNull(dto.getAnswers());
        Assert.assertEquals(teamMatchPlayer.getTossupValues().size(), dto.getAnswers().size());

        verify(teamMatchPlayerAnswerMapper, times(teamMatchPlayer.getTossupValues().size())).toDTO(any());
    }

    @Test
    public void testMapsToDtoCorrectlyIfNoTossupsValues() {
        TeamMatchPlayer teamMatchPlayer = buildTeamMatchPlayer();
        teamMatchPlayer.setTossupValues(null);

        MatchPlayerDTO dto = teamMatchPlayerMapper.toDTO(teamMatchPlayer);
        Assert.assertNotNull(dto.getAnswers());
        Assert.assertTrue(dto.getAnswers().isEmpty());
    }

    private TeamMatchPlayer buildTeamMatchPlayer() {
        TeamMatchPlayer player = new TeamMatchPlayer();
        player.setMatchId("MATCH_ID");
        player.setPlayerId("PLAYER_ID");
        player.setTeamId("TEAM_ID");
        player.setTossupsHeard(10);
        player.setTossupValues(new HashSet<>());

        return player;
    }
}
