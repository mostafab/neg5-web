package neg5.domain.impl.mappers;

import com.google.inject.Singleton;
import neg5.domain.api.TournamentScoresheetCycleAnswerDTO;
import neg5.domain.impl.entities.TournamentScoresheetCycleAnswer;

@Singleton
public class TournamentScoresheetCycleAnswerMapper
        extends AbstractObjectMapper<
                TournamentScoresheetCycleAnswer, TournamentScoresheetCycleAnswerDTO> {

    protected TournamentScoresheetCycleAnswerMapper() {
        super(TournamentScoresheetCycleAnswer.class, TournamentScoresheetCycleAnswerDTO.class);
    }
}
