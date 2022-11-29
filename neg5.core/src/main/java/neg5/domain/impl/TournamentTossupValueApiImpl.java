package neg5.domain.impl;

import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import neg5.domain.api.TournamentTossupValueApi;
import neg5.domain.impl.entities.compositeIds.TournamentTossupValueId;
import org.neg5.FieldValidationErrors;
import org.neg5.TournamentTossupValueDTO;
import neg5.domain.impl.dataAccess.TournamentTossupValueDAO;
import neg5.domain.impl.entities.TournamentTossupValue;
import org.neg5.enums.TossupAnswerType;
import neg5.domain.impl.mappers.TournamentTossupValueMapper;
import neg5.validation.ObjectValidationException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static neg5.validation.FieldValidation.requireCustomValidation;
import static neg5.validation.FieldValidation.requireNotNull;

@Singleton
public class TournamentTossupValueApiImpl extends
        AbstractApiLayerImpl<TournamentTossupValue, TournamentTossupValueDTO, TournamentTossupValueId>
        implements TournamentTossupValueApi {

    private final TournamentTossupValueDAO rwTournamentTossupValueDAO;
    private final TournamentTossupValueMapper tournamentTossupValueMapper;

    @Inject
    public TournamentTossupValueApiImpl(TournamentTossupValueDAO rwTournamentTossupValueDAO,
                                        TournamentTossupValueMapper tournamentTossupValueMapper) {
        this.rwTournamentTossupValueDAO = rwTournamentTossupValueDAO;
        this.tournamentTossupValueMapper = tournamentTossupValueMapper;
    }

    @Override
    @Transactional
    public TournamentTossupValueDTO create(TournamentTossupValueDTO tournamentTossupValueDTO) {
        return super.create(tournamentTossupValueDTO);
    }

    @Transactional
    public void deleteAllFromTournament(String tournamentId) {
        List<TournamentTossupValueDTO> values = findAllByTournamentId(tournamentId);
        values.forEach(v -> delete(getIdFromDTO(v)));
    }

    @Override
    public Set<TournamentTossupValueDTO> getDefaultTournamentValues() {
        return Sets.newHashSet(
                getStub(-5, TossupAnswerType.NEG),
                getStub(10, TossupAnswerType.BASE),
                getStub(15, TossupAnswerType.POWER)
        );
    }

    @Override
    protected TournamentTossupValueDAO getDao() {
        return rwTournamentTossupValueDAO;
    }

    @Override
    protected TournamentTossupValueMapper getMapper() {
        return tournamentTossupValueMapper;
    }

    @Override
    protected TournamentTossupValueId getIdFromDTO(TournamentTossupValueDTO tournamentTossupValueDTO) {
        return getMapper().mergeToEntity(tournamentTossupValueDTO).getId();
    }

    @Override
    protected Optional<FieldValidationErrors> validateObject(TournamentTossupValueDTO dto) {
        FieldValidationErrors errors = new FieldValidationErrors();
        requireNotNull(errors, dto.getValue(), "value");
        requireNotNull(errors, dto.getTournamentId(), "tournamentId");
        requireNotNull(errors, dto.getAnswerType(), "answerType");
        requireCustomValidation(errors, () -> validateUniqueTossupValue(dto));

        return Optional.of(errors);
    }

    private void validateUniqueTossupValue(TournamentTossupValueDTO tossupValue) {
        List<TournamentTossupValueDTO> tossupValues =
                findAllByTournamentId(tossupValue.getTournamentId());

        for (TournamentTossupValueDTO tv : tossupValues) {
            if (Objects.equals(tv.getValue(), tossupValue.getValue())) {
                throw new ObjectValidationException(
                  new FieldValidationErrors()
                    .add(
                            "value",
                            "There is already a tossup rule with value " + tossupValue.getValue())
                );
            }
        }
    }

    private TournamentTossupValueDTO getStub(int points, TossupAnswerType answerType) {
        TournamentTossupValueDTO dto = new TournamentTossupValueDTO();
        dto.setAnswerType(answerType);
        dto.setValue(points);
        return dto;
    }
}