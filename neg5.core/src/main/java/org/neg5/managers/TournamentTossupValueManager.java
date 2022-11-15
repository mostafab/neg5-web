package org.neg5.managers;

import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import org.neg5.FieldValidationErrors;
import org.neg5.TournamentTossupValueDTO;
import org.neg5.daos.TournamentTossupValueDAO;
import org.neg5.data.TournamentTossupValue;
import org.neg5.data.embeddables.TournamentTossupValueId;
import org.neg5.enums.TossupAnswerType;
import org.neg5.mappers.TournamentTossupValueMapper;
import org.neg5.validation.ObjectValidationException;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Singleton
public class TournamentTossupValueManager extends
        AbstractDTOManager<TournamentTossupValue, TournamentTossupValueDTO, TournamentTossupValueId> {

    private final TournamentTossupValueDAO rwTournamentTossupValueDAO;
    private final TournamentTossupValueMapper tournamentTossupValueMapper;

    @Inject
    public TournamentTossupValueManager(TournamentTossupValueDAO rwTournamentTossupValueDAO,
                                        TournamentTossupValueMapper tournamentTossupValueMapper) {
        this.rwTournamentTossupValueDAO = rwTournamentTossupValueDAO;
        this.tournamentTossupValueMapper = tournamentTossupValueMapper;
    }

    @Override
    @Transactional
    public TournamentTossupValueDTO create(TournamentTossupValueDTO tournamentTossupValueDTO) {
        validateUniqueTossupValue(tournamentTossupValueDTO);
        return super.create(tournamentTossupValueDTO);
    }

    @Transactional
    public void deleteAllFromTournament(String tournamentId) {
        List<TournamentTossupValueDTO> values = findAllByTournamentId(tournamentId);
        values.forEach(v -> delete(getIdFromDTO(v)));
    }

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
