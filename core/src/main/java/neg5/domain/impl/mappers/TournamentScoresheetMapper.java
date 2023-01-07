package neg5.domain.impl.mappers;

import com.google.inject.Singleton;
import neg5.domain.api.ScoresheetDTO;
import neg5.domain.impl.entities.TournamentScoresheet;

@Singleton
public class TournamentScoresheetMapper
        extends AbstractObjectMapper<TournamentScoresheet, ScoresheetDTO> {

    protected TournamentScoresheetMapper() {
        super(TournamentScoresheet.class, ScoresheetDTO.class);
    }
}
