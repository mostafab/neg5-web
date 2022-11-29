package org.neg5.managers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import neg5.api.TournamentPlayerApi;
import neg5.api.TournamentTeamApi;
import org.neg5.FieldValidationErrors;
import org.neg5.TournamentMatchDTO;
import org.neg5.TournamentTeamDTO;
import org.neg5.TournamentTeamPoolDTO;
import org.neg5.daos.TournamentTeamDAO;
import org.neg5.data.TournamentTeam;

import org.neg5.mappers.TournamentTeamMapper;
import org.neg5.validation.ObjectValidationException;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.neg5.validation.FieldValidation.requireCustomValidation;
import static org.neg5.validation.FieldValidation.requireNonEmpty;
import static org.neg5.validation.FieldValidation.requireNotNull;

@Singleton
public class TournamentTeamManager extends AbstractDTOManager<TournamentTeam, TournamentTeamDTO, String>
        implements TournamentTeamApi {

    private final TournamentTeamDAO rwTournamentTeamDAO;
    private final TournamentTeamMapper tournamentTeamMapper;
    private final TournamentPlayerApi tournamentPlayerApi;
    private final TournamentTeamPoolManager teamDivisionManager;
    private final TournamentTeamMatchHelper teamMatchHelper;
    private final TournamentPoolManager poolManager;

    @Inject
    public TournamentTeamManager(TournamentTeamDAO rwTournamentTeamDAO,
                                 TournamentTeamMapper tournamentTeamMapper,
                                 TournamentPlayerApi tournamentPlayerApi,
                                 TournamentTeamPoolManager teamDivisionManager,
                                 TournamentTeamMatchHelper teamMatchHelper,
                                 TournamentPoolManager poolManager
    ) {
        this.rwTournamentTeamDAO = rwTournamentTeamDAO;
        this.tournamentTeamMapper = tournamentTeamMapper;
        this.tournamentPlayerApi = tournamentPlayerApi;
        this.teamDivisionManager = teamDivisionManager;
        this.teamMatchHelper = teamMatchHelper;
        this.poolManager = poolManager;
    }

    @Override
    @Transactional
    public TournamentTeamDTO create(TournamentTeamDTO tournamentTeamDTO) {
        TournamentTeamDTO created = super.create(tournamentTeamDTO);
        if (tournamentTeamDTO.getPlayers() != null) {
            created.setPlayers(tournamentTeamDTO.getPlayers().stream()
                    .map(player -> {
                        player.setTeamId(created.getId());
                        player.setTournamentId(created.getTournamentId());
                        return tournamentPlayerApi.create(player);
                    })
                    .collect(Collectors.toSet())
            );
        }
        if (tournamentTeamDTO.getDivisions() != null) {
            Set<String> divisionIds = tournamentTeamDTO.getDivisions().stream()
                    .map(d -> d.getId())
                    .collect(Collectors.toSet());
            List<TournamentTeamPoolDTO> teamPools = teamDivisionManager.associateTeamWithPools(
                    divisionIds,
                    created.getId(),
                    created.getTournamentId()
            );
            created.setDivisions(
                teamPools.stream().map(pool -> poolManager.get(pool.getPoolId())).collect(Collectors.toSet())
            );
        }
        return created;
    }

    @Override
    public TournamentTeamDTO update(TournamentTeamDTO tournamentTeamDTO) {
        TournamentTeamDTO original = get(tournamentTeamDTO.getId());
        tournamentTeamDTO.setTournamentId(original.getTournamentId());
        return super.update(tournamentTeamDTO);
    }

    @Transactional
    public TournamentTeamDTO updateTeamPools(@Nonnull String teamId, @Nonnull Set<String> poolIds) {
        TournamentTeamDTO team = get(teamId);
        List<TournamentTeamPoolDTO> teamPools = teamDivisionManager.associateTeamWithPools(
                poolIds,
                team.getId(),
                team.getTournamentId()
        );
        team.setDivisions(
                teamPools.stream().map(pool -> poolManager.get(pool.getPoolId())).collect(Collectors.toSet())
        );
        return team;
    }

    @Override
    @Transactional
    public void delete(String id) {
        TournamentTeamDTO team = get(id);
        List<TournamentMatchDTO> teamMatches = teamMatchHelper.groupMatchesByTeams(team.getTournamentId(), null)
                .getOrDefault(id, new ArrayList<>());
        if (!teamMatches.isEmpty()) {
            throw new ObjectValidationException(
                    new FieldValidationErrors()
                        .add("matches", "A team with existing matches cannot be removed.")
            );
        }
        super.delete(id);
    }

    @Override
    protected Optional<FieldValidationErrors> validateObject(TournamentTeamDTO dto) {
        FieldValidationErrors errors = new FieldValidationErrors();
        requireNotNull(errors, dto.getTournamentId(), "tournamentId");
        requireNonEmpty(errors, dto.getName(), "name");
        requireCustomValidation(errors, () -> ensureUniqueTeamName(dto));
        return Optional.of(errors);
    }

    private void ensureUniqueTeamName(TournamentTeamDTO dto) {
        if (dto.getName() == null || dto.getTournamentId() == null) {
            return;
        }
        List<TournamentTeamDTO> teams = findAllByTournamentId(dto.getTournamentId());
        final String normalizedName = dto.getName().trim().toLowerCase();
        teams.stream().filter(team -> !team.getId().equals(dto.getId()))
                .filter(team -> team.getName().trim().toLowerCase().equals(normalizedName))
                .findFirst()
                .ifPresent(match -> {
                    String message = String.format("A different team with this name (%s) has already been added to the tournament", dto.getName());
                    throw new ObjectValidationException(new FieldValidationErrors().add("name", message));
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
