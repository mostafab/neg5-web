package neg5.domain.impl.mappers;

import com.google.inject.Singleton;
import neg5.domain.api.ScoresheetCycleAnswerDTO;
import neg5.domain.impl.entities.TournamentScoresheetCycleAnswer;

@Singleton
public class TournamentScoresheetCycleAnswerMapper
        extends AbstractObjectMapper<TournamentScoresheetCycleAnswer, ScoresheetCycleAnswerDTO> {

    protected TournamentScoresheetCycleAnswerMapper() {
        super(TournamentScoresheetCycleAnswer.class, ScoresheetCycleAnswerDTO.class);
    }
}
