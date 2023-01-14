package neg5.domain.api;

public interface TournamentScheduleMatchApi
        extends DomainObjectApiLayer<TournamentScheduledMatchDTO, Long> {

    void deleteAllMatchesForSchedule(Long scheduleId);
}
