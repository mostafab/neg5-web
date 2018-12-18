package org.neg5.mappers.data;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.neg5.MatchTeamDTO;
import org.neg5.data.transformers.data.TeamInMatch;

import java.util.HashSet;

@ExtendWith(MockitoExtension.class)
public class TeamInMatchMapperTest {

    @Mock private TeamMatchPlayerMapper teamMatchPlayerMapper;
    @InjectMocks private TeamInMatchMapper teamInMatchMapper;

    @Test
    public void testMapsToDtoCorrectly() {
        TeamInMatch teamInMatch = buildTeamInMatch();

        MatchTeamDTO dto = teamInMatchMapper.toDTO(teamInMatch);

        Assert.assertEquals(teamInMatch.getTeamId(), dto.getTeamId());
        Assert.assertEquals(teamInMatch.getMatchId(), dto.getMatchId());
        Assert.assertEquals(teamInMatch.getScore(), dto.getScore());
        Assert.assertEquals(teamInMatch.getBouncebackPoints(), dto.getBouncebackPoints());
        Assert.assertNotNull(dto.getPlayers());
        Assert.assertTrue(dto.getPlayers().isEmpty());
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

        return teamInMatch;
    }
}
