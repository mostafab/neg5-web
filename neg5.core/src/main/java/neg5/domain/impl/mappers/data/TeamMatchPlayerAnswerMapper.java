package neg5.domain.impl.mappers.data;

import com.google.inject.Singleton;
import neg5.domain.impl.entities.transformers.data.TeamMatchPlayerAnswer;
import neg5.domain.impl.mappers.AbstractObjectMapper;
import neg5.domain.api.MatchPlayerAnswerDTO;

@Singleton
public class TeamMatchPlayerAnswerMapper extends AbstractObjectMapper<TeamMatchPlayerAnswer, MatchPlayerAnswerDTO> {

    protected TeamMatchPlayerAnswerMapper() {
        super(TeamMatchPlayerAnswer.class, MatchPlayerAnswerDTO.class);
    }

    @Override
    protected void enrichDTO(MatchPlayerAnswerDTO matchPlayerAnswerDTO, TeamMatchPlayerAnswer teamMatchPlayerAnswer) {
        matchPlayerAnswerDTO.setNumberGotten(teamMatchPlayerAnswer.getNumber());
        matchPlayerAnswerDTO.setTossupValue(teamMatchPlayerAnswer.getValue());
    }
}
