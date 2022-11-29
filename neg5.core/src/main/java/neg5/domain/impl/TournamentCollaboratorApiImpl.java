package neg5.domain.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import neg5.domain.api.TournamentApi;
import neg5.domain.api.TournamentCollaboratorApi;
import neg5.domain.impl.entities.compositeIds.TournamentCollaboratorId;
import neg5.domain.api.FieldValidationErrors;
import neg5.domain.api.TournamentCollaboratorDTO;
import neg5.domain.api.UserTournamentsDTO;
import neg5.domain.impl.dataAccess.TournamentCollaboratorDAO;
import neg5.domain.impl.entities.TournamentCollaborator;
import neg5.domain.impl.mappers.TournamentCollaboratorMapper;

import javax.persistence.NoResultException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static neg5.validation.FieldValidation.requireNotNull;

@Singleton
public class TournamentCollaboratorApiImpl
        extends AbstractApiLayerImpl<TournamentCollaborator, TournamentCollaboratorDTO, TournamentCollaboratorId>
        implements TournamentCollaboratorApi {

    private final TournamentCollaboratorMapper tournamentCollaboratorMapper;
    private final TournamentCollaboratorDAO tournamentCollaboratorDAO;
    private final TournamentApi tournamentManager;

    @Inject
    public TournamentCollaboratorApiImpl(TournamentCollaboratorMapper tournamentCollaboratorMapper,
                                         TournamentCollaboratorDAO tournamentCollaboratorDAO,
                                         TournamentApi tournamentManager) {
        this.tournamentCollaboratorMapper = tournamentCollaboratorMapper;
        this.tournamentCollaboratorDAO = tournamentCollaboratorDAO;
        this.tournamentManager = tournamentManager;
    }

    @Override
    protected TournamentCollaboratorDAO getDao() {
        return tournamentCollaboratorDAO;
    }

    @Override
    protected TournamentCollaboratorMapper getMapper() {
        return tournamentCollaboratorMapper;
    }

    public TournamentCollaboratorDTO addOrUpdateCollaborator(TournamentCollaboratorDTO collaborator) {
        if (tournamentManager.get(collaborator.getTournamentId()).getDirectorId()
                .equals(collaborator.getUserId())) {
            throw new IllegalArgumentException(
                    "Attempting to add tournament director as collaborator to " + collaborator.getTournamentId()
            );
        }
        Optional<TournamentCollaboratorDTO> existing =
                getByTournamentAndUsername(collaborator.getTournamentId(), collaborator.getUserId());
        if (existing.isPresent()) {
            return update(collaborator);
        }
        return create(collaborator);
    }

    @Transactional
    public UserTournamentsDTO getUserTournaments(String userId) {
        UserTournamentsDTO userTournaments = new UserTournamentsDTO();
        userTournaments.setUserOwnedTournaments(tournamentManager.getTournamentsOwnedByUser(userId));

        Set<String> tournamentIds = getTournamentIdsThatUserCollaboratesOn(userId);
        userTournaments.setCollaboratingTournaments(tournamentManager.get(tournamentIds));

        return userTournaments;
    }

    @Transactional
    public Set<String> getTournamentIdsThatUserCollaboratesOn(String userId) {
        return new HashSet<>(getDao().getTournamentIdsThatUserCollaboratesOn(userId));
    }

    public Optional<TournamentCollaboratorDTO> getByTournamentAndUsername(String tournamentId,
                                                                          String username) {
        try {
            return Optional.of(tournamentCollaboratorMapper
                    .toDTO(getDao().getCollaboratorByUsernameAndTournament(tournamentId, username)));
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    protected Optional<FieldValidationErrors> validateObject(TournamentCollaboratorDTO dto) {
        FieldValidationErrors errors = new FieldValidationErrors();
        requireNotNull(errors, dto.getTournamentId(), "tournamentId");
        requireNotNull(errors, dto.getUserId(), "userId");

        return Optional.of(errors);
    }
}
