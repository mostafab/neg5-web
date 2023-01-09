package neg5.domain.api;

import javax.annotation.Nonnull;

public interface TournamentScoresheetCycleApi
        extends DomainObjectApiLayer<TournamentScoresheetCycleDTO, Long> {

    void deleteScoresheetCycles(@Nonnull Long scoresheetId);
}
