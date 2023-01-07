package neg5.domain.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import neg5.domain.api.ScoresheetDTO;
import neg5.domain.api.TournamentMatchApi;
import neg5.domain.api.TournamentMatchDTO;
import neg5.domain.api.TournamentScoresheetApi;
import neg5.domain.impl.scoresheet.ScoresheetToMatchConverter;

@Singleton
public class TournamentScoresheetApiImpl implements TournamentScoresheetApi {

    private final ScoresheetToMatchConverter scoresheetConverter;
    private final TournamentMatchApi matchApi;

    @Inject
    public TournamentScoresheetApiImpl(
            ScoresheetToMatchConverter scoresheetConverter, TournamentMatchApi matchApi) {
        this.scoresheetConverter = scoresheetConverter;
        this.matchApi = matchApi;
    }

    @Override
    public TournamentMatchDTO convertToMatch(ScoresheetDTO scoresheet) {
        return scoresheetConverter.convert(scoresheet);
    }

    @Override
    public TournamentMatchDTO submitScoresheet(ScoresheetDTO scoresheet) {
        TournamentMatchDTO converted = convertToMatch(scoresheet);
        return matchApi.create(converted);
    }
}
