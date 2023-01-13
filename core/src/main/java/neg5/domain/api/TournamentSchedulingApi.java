package neg5.domain.api;

import javax.annotation.Nonnull;

public interface TournamentSchedulingApi extends DomainObjectApiLayer<TournamentScheduleDTO, Long> {

    @Nonnull
    TournamentScheduleDTO generateSchedule(@Nonnull ScheduleGenerationRequestDTO request);
}
