package neg5.domain.api;

import javax.annotation.Nonnull;

public interface TournamentSchedulingApi {

    @Nonnull
    TournamentScheduleDTO generateSchedule(@Nonnull ScheduleGenerationRequestDTO request);
}
