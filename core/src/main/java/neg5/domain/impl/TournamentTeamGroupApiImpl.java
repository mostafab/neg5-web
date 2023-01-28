package neg5.domain.impl;

import static neg5.validation.FieldValidation.requireNonEmpty;
import static neg5.validation.FieldValidation.requireNotNull;
import static neg5.validation.ValidationUtilities.namesAreTheSame;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import neg5.domain.api.FieldValidationError;
import neg5.domain.api.FieldValidationErrors;
import neg5.domain.api.TournamentTeamApi;
import neg5.domain.api.TournamentTeamGroupApi;
import neg5.domain.api.TournamentTeamGroupDTO;
import neg5.domain.impl.dataAccess.TournamentTeamGroupDAO;
import neg5.domain.impl.entities.TournamentTeamGroup;
import neg5.domain.impl.mappers.TournamentTeamGroupMapper;

@Singleton
public class TournamentTeamGroupApiImpl
        extends AbstractApiLayerImpl<TournamentTeamGroup, TournamentTeamGroupDTO, Long>
        implements TournamentTeamGroupApi {

    private final TournamentTeamGroupDAO dao;
    private final TournamentTeamGroupMapper mapper;
    private final TournamentTeamApi teamApi;

    @Inject
    public TournamentTeamGroupApiImpl(
            TournamentTeamGroupDAO dao,
            TournamentTeamGroupMapper mapper,
            TournamentTeamApi teamApi) {
        this.dao = dao;
        this.mapper = mapper;
        this.teamApi = teamApi;
    }

    @Override
    @Transactional
    public TournamentTeamGroupDTO create(@Nonnull TournamentTeamGroupDTO dto) {
        TournamentTeamGroupDTO group = super.create(dto);
        if (dto.getRosters() != null) {
            group.setRosters(
                    dto.getRosters().stream()
                            .map(
                                    team -> {
                                        team.setTournamentId(dto.getTournamentId());
                                        team.setTeamGroupId(group.getId());
                                        return teamApi.create(team);
                                    })
                            .collect(Collectors.toList()));
        }
        return group;
    }

    @Override
    protected Optional<FieldValidationErrors> validateObject(TournamentTeamGroupDTO dto) {
        FieldValidationErrors errors = new FieldValidationErrors();
        requireNonEmpty(errors, dto.getName(), "name");
        requireNotNull(errors, dto.getTournamentId(), "tournamentId");

        if (!errors.isEmpty()) {
            return Optional.of(errors);
        }
        validateUniqueName(dto).ifPresent(errors::add);
        return Optional.of(errors);
    }

    @Override
    protected TournamentTeamGroupDAO getDao() {
        return dao;
    }

    @Override
    protected TournamentTeamGroupMapper getMapper() {
        return mapper;
    }

    private Optional<FieldValidationError> validateUniqueName(TournamentTeamGroupDTO dto) {
        List<TournamentTeamGroupDTO> groups = findAllByTournamentId(dto.getTournamentId());
        String normalizedName = dto.getName().trim().toLowerCase();
        return groups.stream()
                .filter(g -> !(dto.getId() == null || g.getId().equals(dto.getId())))
                .filter(g -> namesAreTheSame(normalizedName, g.getName()))
                .findAny()
                .map(
                        match -> {
                            FieldValidationError error = new FieldValidationError();
                            error.setField("name");
                            error.setMessage(
                                    String.format(
                                            "There is already a school/organization with this name: %s",
                                            dto.getName()));
                            return error;
                        });
    }
}
