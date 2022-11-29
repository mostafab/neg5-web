package neg5.domain.impl;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import neg5.domain.api.TournamentMatchApi;
import neg5.domain.api.TournamentPlayerApi;
import org.neg5.FieldValidationErrors;
import org.neg5.MatchPlayerDTO;
import org.neg5.TournamentMatchDTO;
import org.neg5.TournamentPlayerDTO;

import org.neg5.daos.TournamentPlayerDAO;
import neg5.domain.impl.entities.TournamentPlayer;
import neg5.domain.impl.mappers.TournamentPlayerMapper;
import org.neg5.validation.ObjectValidationException;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.neg5.validation.FieldValidation.requireCustomValidation;
import static org.neg5.validation.FieldValidation.requireNotNull;

@Singleton
public class TournamentPlayerApiImpl extends AbstractApiLayerImpl<TournamentPlayer, TournamentPlayerDTO, String>
        implements TournamentPlayerApi {

    private final TournamentPlayerMapper tournamentPlayerMapper;
    private final TournamentPlayerDAO rwTournamentPlayerDAO;
    private final TournamentMatchApi tournamentMatchApi;

    @Inject
    public TournamentPlayerApiImpl(TournamentPlayerMapper tournamentPlayerMapper,
                                   TournamentPlayerDAO rwTournamentPlayerDAO,
                                   TournamentMatchApi tournamentMatchApi) {
        this.tournamentPlayerMapper = tournamentPlayerMapper;
        this.rwTournamentPlayerDAO = rwTournamentPlayerDAO;
        this.tournamentMatchApi = tournamentMatchApi;
    }

    @Override
    protected TournamentPlayerMapper getMapper() {
        return tournamentPlayerMapper;
    }

    @Override
    protected TournamentPlayerDAO getDao() {
        return rwTournamentPlayerDAO;
    }

    @Override
    protected String getIdFromDTO(TournamentPlayerDTO tournamentPlayerDTO) {
        return tournamentPlayerDTO.getId();
    }

    @Override
    public TournamentPlayerDTO update(TournamentPlayerDTO tournamentPlayerDTO) {
        TournamentPlayerDTO original = get(tournamentPlayerDTO.getId());
        tournamentPlayerDTO.setTournamentId(original.getTournamentId());
        tournamentPlayerDTO.setTeamId(original.getTeamId());

        return super.update(tournamentPlayerDTO);
    }

    @Override
    @Transactional
    public void delete(String id) {
        TournamentPlayerDTO playerDTO = get(id);
        List<TournamentMatchDTO> playerMatches = groupMatchesByPlayers(playerDTO.getTournamentId(), null)
                .getOrDefault(id, new ArrayList<>());
        if (!playerMatches.isEmpty()) {
            throw new ObjectValidationException(
                    new FieldValidationErrors()
                            .add("matches", "A player with existing matches cannot be removed.")
            );
        }
        super.delete(id);
    }

    public List<TournamentPlayerDTO> findByTeamId(@Nonnull String teamId) {
        return getDao().findByTeamId(teamId).stream().map(tournamentPlayerMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    protected Optional<FieldValidationErrors> validateObject(TournamentPlayerDTO dto) {
        FieldValidationErrors errors = new FieldValidationErrors();
        requireNotNull(errors, dto.getTournamentId(), "tournamentId");
        requireNotNull(errors, dto.getName(), "name");
        requireNotNull(errors, dto.getTeamId(), "teamId");
        requireCustomValidation(errors, () -> ensureNameUniqueInTeam(dto));
        return Optional.of(errors);
    }

    /**
     * Group matches by players, where the key is the player's id and the value is the list of matches the player is part of
     * @param tournamentId tournamentId
     * @param phaseId phaseId
     * @return mapping between player -> matches
     */
    public Map<String, List<TournamentMatchDTO>> groupMatchesByPlayers(String tournamentId, String phaseId) {
        List<TournamentMatchDTO> matches = tournamentMatchApi.findAllByTournamentAndPhase(tournamentId, phaseId);
        Map<String, List<TournamentMatchDTO>> matchesByPlayerId = new HashMap<>();
        matches.forEach(match -> {
            Set<MatchPlayerDTO> players = match.getTeams().stream()
                    .flatMap(team -> team.getPlayers().stream())
                    .collect(Collectors.toSet());
            players.forEach(player -> {
                matchesByPlayerId.computeIfPresent(player.getPlayerId(), (id, list) -> {
                    list.add(match);
                    return list;
                });
                matchesByPlayerId.computeIfAbsent(player.getPlayerId(), teamId -> Lists.newArrayList(match));
            });
        });
        List<TournamentPlayerDTO> allPlayers = findAllByTournamentId(tournamentId);
        allPlayers.forEach(player -> matchesByPlayerId.putIfAbsent(player.getId(), new ArrayList<>()));
        return matchesByPlayerId;
    }

    private void ensureNameUniqueInTeam(TournamentPlayerDTO subject) {
        if (subject.getName() == null || subject.getTeamId() == null) {
            return;
        }
        final String normalizedName = subject.getName().trim().toLowerCase();
        findByTeamId(subject.getTeamId()).stream()
                .filter(player -> !player.getId().equals(subject.getId()))
                .filter(player -> player.getName().trim().toLowerCase().equals(normalizedName))
                .findFirst()
                .ifPresent(match -> {
                    String message = String.format("A player with this name (%s) already exists on the team.", subject.getName());
                    throw new ObjectValidationException(new FieldValidationErrors().add("name", message));
                });
    }

}