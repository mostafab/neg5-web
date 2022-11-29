package neg5.domain.impl.mappers;

import com.google.common.collect.Sets;
import com.google.inject.Singleton;
import org.neg5.MatchPlayerAnswerDTO;
import neg5.domain.impl.entities.MatchPlayerAnswer;

import java.util.Set;

@Singleton
public class MatchPlayerAnswerMapper extends AbstractObjectMapper<MatchPlayerAnswer, MatchPlayerAnswerDTO> {

    protected MatchPlayerAnswerMapper() {
        super(MatchPlayerAnswer.class, MatchPlayerAnswerDTO.class);
    }

    @Override
    public MatchPlayerAnswer mergeToEntity(MatchPlayerAnswerDTO matchPlayerAnswerDTO, MatchPlayerAnswer matchPlayerAnswer) {
        MatchPlayerAnswer ent = super.mergeToEntity(matchPlayerAnswerDTO, matchPlayerAnswer);
        ent.getId().setTossupValue(matchPlayerAnswerDTO.getTossupValue());
        return ent;
    }

    @Override
    protected void enrichDTO(MatchPlayerAnswerDTO matchPlayerAnswerDTO, MatchPlayerAnswer matchPlayerAnswer) {
        if (matchPlayerAnswer.getId() != null) {
            matchPlayerAnswerDTO.setTossupValue(matchPlayerAnswer.getId().getTossupValue());
        }
        if (matchPlayerAnswer.getTournamentTossupValue() != null) {
            matchPlayerAnswerDTO.setAnswerType(matchPlayerAnswer.getTournamentTossupValue().getAnswerType());
        }
    }

    @Override
    protected Set<String> getIgnoredEntityPropertyNames() {
        return Sets.newHashSet("player");
    }
}
