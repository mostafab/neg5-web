package neg5.domain.impl.mappers;

import com.google.inject.Singleton;
import neg5.domain.api.ScoresheetCycleBonusesDTO;
import neg5.domain.impl.entities.TournamentScoresheetCycleBonus;

@Singleton
public class TournamentScoresheetCycleBonusMapper
        extends AbstractObjectMapper<TournamentScoresheetCycleBonus, ScoresheetCycleBonusesDTO> {

    protected TournamentScoresheetCycleBonusMapper() {
        super(TournamentScoresheetCycleBonus.class, ScoresheetCycleBonusesDTO.class);
    }
}
