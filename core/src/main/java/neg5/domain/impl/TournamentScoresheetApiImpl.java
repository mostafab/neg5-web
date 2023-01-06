package neg5.domain.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import neg5.domain.api.ScoresheetDTO;
import neg5.domain.api.TournamentMatchDTO;
import neg5.domain.api.TournamentScoresheetApi;
import neg5.domain.impl.scoresheet.ScoresheetToMatchConverter;

@Singleton
public class TournamentScoresheetApiImpl implements TournamentScoresheetApi {

    private final ScoresheetToMatchConverter scoresheetConverter;

    @Inject
    public TournamentScoresheetApiImpl(ScoresheetToMatchConverter scoresheetConverter) {
        this.scoresheetConverter = scoresheetConverter;
    }

    @Override
    public TournamentMatchDTO convertToMatch(ScoresheetDTO scoresheet) {
        return scoresheetConverter.convert(scoresheet);
    }
}
