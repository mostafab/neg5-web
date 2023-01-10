package neg5.service.controllers;

import com.google.inject.Inject;
import neg5.domain.api.ScheduleGenerationRequestDTO;
import neg5.domain.api.TournamentSchedulingApi;
import neg5.service.util.RequestHelper;

public class TournamentScheduleRoutes extends AbstractJsonRoutes {

    @Inject private TournamentSchedulingApi schedulingApi;
    @Inject private RequestHelper requestHelper;

    @Override
    protected String getBasePath() {
        return "/neg5-api/scheduling";
    }

    @Override
    public void registerRoutes() {
        post(
                "/generate",
                (request, response) -> {
                    ScheduleGenerationRequestDTO payload =
                            requestHelper.readFromRequest(
                                    request, ScheduleGenerationRequestDTO.class);
                    return schedulingApi.generateSchedule(payload);
                });
    }
}
