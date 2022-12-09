package neg5.domain.api;

import java.time.LocalDate;
import java.util.List;
import javax.annotation.Nonnull;

public interface TournamentSearchApi {

    @Nonnull
    List<TournamentSearchResultDTO> findByMatchingPrefix(
            @Nonnull String name, boolean includeHidden);

    @Nonnull
    List<TournamentSearchResultDTO> findTournamentsWithDateSinceDays(
            @Nonnull Integer days, boolean includeHidden);

    @Nonnull
    List<TournamentSearchResultDTO> findTournamentsWithDateInRange(
            @Nonnull LocalDate start, @Nonnull LocalDate end, boolean includeHidden);
}
