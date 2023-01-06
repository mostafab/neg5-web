package neg5.domain.impl;

import com.google.inject.Singleton;
import neg5.domain.api.ScoresheetDTO;
import neg5.domain.api.TournamentMatchDTO;
import neg5.domain.api.TournamentScoresheetApi;

@Singleton
public class TournamentScoresheetApiImpl implements TournamentScoresheetApi {

    @Override
    public TournamentMatchDTO convertToMatch(ScoresheetDTO scoresheet) {
        return new TournamentMatchDTO();
    }
}
