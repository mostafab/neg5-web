package neg5.domain.impl.mappers;

import com.google.inject.Singleton;
import neg5.domain.api.ScoresheetCycleDTO;
import neg5.domain.impl.entities.TournamentScoresheetCycle;

@Singleton
public class TournamentScoresheetCycleMapper
        extends AbstractObjectMapper<TournamentScoresheetCycle, ScoresheetCycleDTO> {

    protected TournamentScoresheetCycleMapper() {
        super(TournamentScoresheetCycle.class, ScoresheetCycleDTO.class);
    }
}
