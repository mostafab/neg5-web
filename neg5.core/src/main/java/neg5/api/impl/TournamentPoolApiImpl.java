package neg5.api.impl;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import neg5.api.TournamentPhaseApi;
import neg5.api.TournamentPoolApi;
import org.neg5.FieldValidationErrors;
import org.neg5.TournamentPhaseDTO;
import org.neg5.TournamentPoolDTO;
import org.neg5.daos.TournamentPoolDAO;
import org.neg5.data.TournamentPool;
import org.neg5.mappers.TournamentPoolMapper;

import java.util.Optional;

import static org.neg5.validation.FieldValidation.requireNotNull;

public class TournamentPoolApiImpl
        extends AbstractApiLayerImpl<TournamentPool, TournamentPoolDTO, String> implements TournamentPoolApi {

    private final TournamentPoolMapper mapper;
    private final TournamentPoolDAO divisionDAO;

    private final TournamentPhaseApi phaseManager;

    @Inject
    public TournamentPoolApiImpl(TournamentPoolMapper mapper,
                                 TournamentPoolDAO divisionDAO,
                                 TournamentPhaseApi phaseManager) {
        this.mapper = mapper;
        this.divisionDAO = divisionDAO;
        this.phaseManager = phaseManager;
    }

    @Override
    @Transactional
    public TournamentPoolDTO create(TournamentPoolDTO tournamentPoolDTO) {
        TournamentPhaseDTO phase = phaseManager.get(tournamentPoolDTO.getPhaseId());
        tournamentPoolDTO.setTournamentId(phase.getTournamentId());
        return super.create(tournamentPoolDTO);
    }

    @Override
    @Transactional
    public TournamentPoolDTO update(TournamentPoolDTO tournamentPoolDTO) {
        TournamentPoolDTO original = get(tournamentPoolDTO.getId());
        tournamentPoolDTO.setTournamentId(original.getTournamentId());
        tournamentPoolDTO.setPhaseId(original.getPhaseId());
        return super.update(tournamentPoolDTO);
    }

    @Override
    protected Optional<FieldValidationErrors> validateObject(TournamentPoolDTO dto) {
        FieldValidationErrors errors = new FieldValidationErrors();
        requireNotNull(errors, dto.getTournamentId(), "tournamentId");
        requireNotNull(errors, dto.getName(), "name");
        requireNotNull(errors, dto.getPhaseId(), "phaseId");
        return Optional.of(errors);
    }

    @Override
    protected TournamentPoolDAO getDao() {
        return divisionDAO;
    }

    @Override
    protected TournamentPoolMapper getMapper() {
        return mapper;
    }
}
