package neg5.domain.impl.mappers.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import neg5.domain.api.MatchPlayerAnswerDTO;
import neg5.domain.impl.entities.transformers.data.TeamMatchPlayerAnswer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TeamMatchPlayerAnswerMapperTest {

    @InjectMocks private TeamMatchPlayerAnswerMapper teamMatchPlayerAnswerMapper;

    @Test
    public void testMapsToDtoCorrectly() {
        TeamMatchPlayerAnswer answer = buildTeamMatchPlayerAnswer();

        MatchPlayerAnswerDTO dto = teamMatchPlayerAnswerMapper.toDTO(answer);

        assertEquals(answer.getMatchId(), dto.getMatchId());
        assertEquals(answer.getNumber(), dto.getNumberGotten());
        assertEquals(answer.getPlayerId(), dto.getPlayerId());
        assertEquals(answer.getValue(), dto.getTossupValue());
    }

    private TeamMatchPlayerAnswer buildTeamMatchPlayerAnswer() {
        TeamMatchPlayerAnswer answer = new TeamMatchPlayerAnswer();
        answer.setMatchId("MATCH_ID");
        answer.setNumber(1);
        answer.setPlayerId("PLAYER_ID");
        answer.setValue(10);

        return answer;
    }
}
