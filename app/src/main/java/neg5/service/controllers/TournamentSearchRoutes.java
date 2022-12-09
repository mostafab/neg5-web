package neg5.service.controllers;

import com.google.inject.Inject;
import neg5.domain.api.TournamentSearchApi;

public class TournamentSearchRoutes extends AbstractJsonRoutes {

    private final TournamentSearchApi tournamentSearchApi;

    @Inject
    public TournamentSearchRoutes(TournamentSearchApi tournamentSearchApi) {
        this.tournamentSearchApi = tournamentSearchApi;
    }

    @Override
    protected String getBasePath() {
        return "/neg5-api/search/tournaments";
    }

    @Override
    public void registerRoutes() {
        get(
                "/name",
                (req, res) ->
                        tournamentSearchApi.findByMatchingPrefix(req.queryParams("name"), false));
    }
}
