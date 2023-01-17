package neg5.service.controllers;

import com.google.inject.Inject;
import neg5.accessManager.api.TournamentAccessManager;
import neg5.domain.api.ScheduleGenerationRequestDTO;
import neg5.domain.api.TournamentPhaseApi;
import neg5.domain.api.TournamentScheduleDTO;
import neg5.domain.api.TournamentSchedulingApi;
import neg5.domain.api.enums.TournamentAccessLevel;
import neg5.service.util.RequestHelper;

public class TournamentScheduleRoutes extends AbstractJsonRoutes {

    @Inject private TournamentSchedulingApi schedulingApi;
    @Inject private TournamentPhaseApi phaseApi;
    @Inject private TournamentAccessManager accessManager;
    @Inject private RequestHelper requestHelper;

    @Override
    protected String getBasePath() {
        return "/neg5-api/scheduling";
    }

    @Override
    public void registerRoutes() {
        post(
                "",
                (request, response) -> {
                    TournamentScheduleDTO schedule =
                            requestHelper.readFromRequest(request, TournamentScheduleDTO.class);
                    accessManager.requireAccessLevel(
                            phaseApi.get(schedule.getTournamentPhaseId()).getTournamentId(),
                            TournamentAccessLevel.ADMIN);
                    return schedulingApi.create(schedule);
                });
        put(
                "/:id",
                (request, response) -> {
                    TournamentScheduleDTO schedule =
                            requestHelper.readFromRequest(request, TournamentScheduleDTO.class);
                    accessManager.requireAccessLevel(
                            phaseApi.get(schedule.getTournamentPhaseId()).getTournamentId(),
                            TournamentAccessLevel.ADMIN);
                    schedule.setId(Long.parseLong(request.params("id")));
                    return schedulingApi.update(schedule);
                });
        post(
                "/generate",
                (request, response) -> {
                    ScheduleGenerationRequestDTO payload =
                            requestHelper.readFromRequest(
                                    request, ScheduleGenerationRequestDTO.class);
                    accessManager.requireAccessLevel(
                            phaseApi.get(payload.getTournamentPhaseId()).getTournamentId(),
                            TournamentAccessLevel.ADMIN);
                    return schedulingApi.generateSchedule(payload);
                });
    }
}
