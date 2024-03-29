package neg5.domain.impl;

import static neg5.validation.FieldValidation.requireCustomValidation;
import static neg5.validation.FieldValidation.requireNonEmpty;
import static neg5.validation.FieldValidation.requireNotNull;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import neg5.domain.api.FieldValidationErrors;
import neg5.domain.api.TournamentMatchApi;
import neg5.domain.api.TournamentMatchDTO;
import neg5.domain.api.TournamentPlayerApi;
import neg5.domain.api.TournamentPlayerDTO;
import neg5.domain.api.TournamentTeamApi;
import neg5.domain.api.TournamentTeamDTO;
import neg5.domain.api.TournamentTeamGroupApi;
import neg5.domain.impl.dataAccess.TournamentTeamDAO;
import neg5.domain.impl.entities.TournamentTeam;
import neg5.domain.impl.mappers.TournamentTeamMapper;
import neg5.validation.ObjectValidationException;

@Singleton
public class TournamentTeamApiImpl
        extends AbstractApiLayerImpl<TournamentTeam, TournamentTeamDTO, String>
        implements TournamentTeamApi {

    private final TournamentTeamDAO rwTournamentTeamDAO;
    private final TournamentTeamMapper tournamentTeamMapper;
    private final TournamentPlayerApi tournamentPlayerApi;
    private final TournamentMatchApi tournamentMatchApi;
    private final TournamentTeamGroupApi teamGroupApi;

    @Inject
    public TournamentTeamApiImpl(
            TournamentTeamDAO rwTournamentTeamDAO,
            TournamentTeamMapper tournamentTeamMapper,
            TournamentPlayerApi tournamentPlayerApi,
            TournamentMatchApi tournamentMatchApi,
            TournamentTeamGroupApi teamGroupApi) {
        this.rwTournamentTeamDAO = rwTournamentTeamDAO;
        this.tournamentTeamMapper = tournamentTeamMapper;
        this.tournamentPlayerApi = tournamentPlayerApi;
        this.tournamentMatchApi = tournamentMatchApi;
        this.teamGroupApi = teamGroupApi;
    }

    @Override
    @Transactional
    public TournamentTeamDTO create(@Nonnull TournamentTeamDTO tournamentTeamDTO) {
        TournamentTeamDTO created = super.create(tournamentTeamDTO);
        if (tournamentTeamDTO.getPlayers() != null) {
            created.setPlayers(
                    tournamentTeamDTO.getPlayers().stream()
                            .map(
                                    player -> {
                                        player.setTeamId(created.getId());
                                        player.setTournamentId(created.getTournamentId());
                                        return tournamentPlayerApi.create(player);
                                    })
                            .collect(Collectors.toSet()));
        }
        created.setDivisions(new HashSet<>());
        return created;
    }

    @Override
    @Transactional
    public TournamentTeamDTO update(@Nonnull TournamentTeamDTO tournamentTeamDTO) {
        TournamentTeamDTO original = get(tournamentTeamDTO.getId());
        tournamentTeamDTO.setTournamentId(original.getTournamentId());
        TournamentTeamDTO result = super.update(tournamentTeamDTO);
        if (tournamentTeamDTO.getPlayers() != null) {
            Set<String> deletedPlayerIds =
                    deletePlayersNotInIntersection(original, tournamentTeamDTO);
            result.setPlayers(
                    tournamentTeamDTO.getPlayers().stream()
                            .filter(player -> !deletedPlayerIds.contains(player.getId()))
                            .map(
                                    player -> {
                                        player.setTeamId(result.getId());
                                        player.setTournamentId(result.getTournamentId());
                                        return tournamentPlayerApi.createOrUpdate(player);
                                    })
                            .collect(Collectors.toSet()));
        }
        return result;
    }

    @Override
    @Transactional
    public void delete(@Nonnull String id) {
        TournamentTeamDTO team = get(id);
        List<TournamentMatchDTO> teamMatches =
                tournamentMatchApi
                        .groupMatchesByTeams(team.getTournamentId(), null)
                        .getOrDefault(id, new ArrayList<>());
        if (!teamMatches.isEmpty()) {
            throw new ObjectValidationException(
                    new FieldValidationErrors()
                            .add(
                                    "matches",
                                    String.format(
                                            "%s cannot be deleted because it has existing matches.",
                                            team.getName())));
        }
        boolean shouldDeleteGroup = false;
        if (team.getTeamGroupId() != null) {
            shouldDeleteGroup =
                    findAllByTournamentId(team.getTournamentId()).stream()
                            .noneMatch(
                                    t ->
                                            !t.getId().equals(team.getId())
                                                    && team.getTeamGroupId()
                                                            .equals(t.getTeamGroupId()));
        }
        super.delete(id);
        if (shouldDeleteGroup) {
            teamGroupApi.delete(team.getTeamGroupId());
        }
    }

    @Override
    protected Optional<FieldValidationErrors> validateObject(TournamentTeamDTO dto) {
        FieldValidationErrors errors = new FieldValidationErrors();
        requireNotNull(errors, dto.getTournamentId(), "tournamentId");
        requireNonEmpty(errors, dto.getName(), "name");
        requireCustomValidation(errors, () -> ensureUniqueTeamName(dto));
        return Optional.of(errors);
    }

    @Transactional
    protected Set<String> deletePlayersNotInIntersection(
            TournamentTeamDTO original, TournamentTeamDTO updated) {
        Set<String> playerIdsToKeep =
                updated.getPlayers().stream()
                        .map(TournamentPlayerDTO::getId)
                        .collect(Collectors.toSet());
        Set<String> deleted = new HashSet<>();
        original.getPlayers()
                .forEach(
                        player -> {
                            if (!playerIdsToKeep.contains(player.getId())) {
                                tournamentPlayerApi.delete(player.getId());
                                deleted.add(player.getId());
                            }
                        });
        return deleted;
    }

    private void ensureUniqueTeamName(TournamentTeamDTO dto) {
        if (dto.getName() == null || dto.getTournamentId() == null) {
            return;
        }
        List<TournamentTeamDTO> teams = findAllByTournamentId(dto.getTournamentId());
        final String normalizedName = dto.getName().trim().toLowerCase();
        teams.stream()
                .filter(team -> !team.getId().equals(dto.getId()))
                .filter(team -> team.getName().trim().toLowerCase().equals(normalizedName))
                .findFirst()
                .ifPresent(
                        match -> {
                            String message =
                                    String.format(
                                            "A different team with this name (%s) has already been added to the tournament.",
                                            match.getName());
                            throw new ObjectValidationException(
                                    new FieldValidationErrors().add("name", message));
                        });
    }

    @Override
    protected TournamentTeamDAO getDao() {
        return rwTournamentTeamDAO;
    }

    @Override
    protected TournamentTeamMapper getMapper() {
        return tournamentTeamMapper;
    }

    @Override
    protected String getIdFromDTO(TournamentTeamDTO tournamentTeamDTO) {
        return tournamentTeamDTO.getId();
    }
}
