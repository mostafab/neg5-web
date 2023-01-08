package neg5.domain.impl.dataAccess;

import com.google.inject.Singleton;
import neg5.domain.impl.entities.TournamentScoresheetCycleBonus;

@Singleton
public class TournamentScoresheetCycleBonusDAO
        extends AbstractDAO<TournamentScoresheetCycleBonus, Long> {

    protected TournamentScoresheetCycleBonusDAO() {
        super(TournamentScoresheetCycleBonus.class);
    }
}
