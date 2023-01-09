package neg5.domain.impl.mappers;

import com.google.inject.Singleton;
import neg5.domain.api.TournamentScoresheetCycleBonusesDTO;
import neg5.domain.impl.entities.TournamentScoresheetCycleBonus;

@Singleton
public class TournamentScoresheetCycleBonusMapper
        extends AbstractObjectMapper<
                TournamentScoresheetCycleBonus, TournamentScoresheetCycleBonusesDTO> {

    protected TournamentScoresheetCycleBonusMapper() {
        super(TournamentScoresheetCycleBonus.class, TournamentScoresheetCycleBonusesDTO.class);
    }
}
