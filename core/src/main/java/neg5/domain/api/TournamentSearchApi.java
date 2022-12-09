package neg5.domain.api;

import java.time.LocalDate;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface TournamentSearchApi {

    @Nonnull
    List<TournamentSearchResultDTO> findByMatchingPrefix(
            @Nonnull String name, boolean includeHidden);

    @Nonnull
    List<TournamentSearchResultDTO> findTournamentsWithDateInRange(
            @Nonnull LocalDate start, @Nullable LocalDate end, boolean includeHidden);
}
