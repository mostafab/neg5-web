package neg5.domain.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import neg5.domain.api.FieldValidationErrors;
import neg5.domain.api.TournamentSearchApi;
import neg5.domain.api.TournamentSearchResultDTO;
import neg5.domain.impl.dataAccess.TournamentDAO;
import neg5.domain.impl.mappers.TournamentSearchResultMapper;
import neg5.validation.ObjectValidationException;
import org.apache.commons.lang3.StringUtils;

@Singleton
public class TournamentSearchApiImpl implements TournamentSearchApi {

    private final TournamentDAO tournamentDao;
    private final TournamentSearchResultMapper searchResultMapper;

    @Inject
    public TournamentSearchApiImpl(
            TournamentDAO tournamentDAO, TournamentSearchResultMapper searchResultMapper) {
        this.tournamentDao = tournamentDAO;
        this.searchResultMapper = searchResultMapper;
    }

    @Override
    @Nonnull
    public List<TournamentSearchResultDTO> findTournamentsWithDateSinceDays(
            @Nonnull Integer days, boolean includeHidden) {
        return new ArrayList<>();
    }

    @Override
    @Nonnull
    public List<TournamentSearchResultDTO> findTournamentsWithDateInRange(
            @Nonnull LocalDate start, @Nonnull LocalDate end, boolean includeHidden) {
        return new ArrayList<>();
    }

    @Override
    @Nonnull
    @Transactional
    public List<TournamentSearchResultDTO> findByMatchingPrefix(
            @Nonnull String name, boolean includeHidden) {
        if (StringUtils.isBlank(name)) {
            throw new ObjectValidationException(
                    new FieldValidationErrors().add("name", "name cannot be empty"));
        }
        return tournamentDao.findByMatchingPrefix(name).stream()
                .filter(tournament -> includeHidden || !Boolean.TRUE.equals(tournament.getHidden()))
                .map(searchResultMapper::toDTO)
                .collect(Collectors.toList());
    }
}
