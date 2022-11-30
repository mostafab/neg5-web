package neg5.domain.impl;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import java.util.HashSet;
import java.util.stream.Collectors;
import neg5.domain.api.MatchPlayerAnswerApi;
import neg5.domain.api.MatchPlayerApi;
import neg5.domain.api.MatchPlayerDTO;
import neg5.domain.impl.dataAccess.MatchPlayerDAO;
import neg5.domain.impl.entities.MatchPlayer;
import neg5.domain.impl.entities.compositeIds.MatchPlayerId;
import neg5.domain.impl.mappers.MatchPlayerMapper;

public class MatchPlayerApiImpl
        extends AbstractApiLayerImpl<MatchPlayer, MatchPlayerDTO, MatchPlayerId>
        implements MatchPlayerApi {

    private MatchPlayerDAO matchPlayerDAO;
    private MatchPlayerMapper matchPlayerMapper;
    private final MatchPlayerAnswerApi matchPlayerAnswerManager;

    @Inject
    public MatchPlayerApiImpl(
            MatchPlayerDAO matchPlayerDAO,
            MatchPlayerMapper matchPlayerMapper,
            MatchPlayerAnswerApi matchPlayerAnswerManager) {
        this.matchPlayerDAO = matchPlayerDAO;
        this.matchPlayerMapper = matchPlayerMapper;
        this.matchPlayerAnswerManager = matchPlayerAnswerManager;
    }

    @Override
    @Transactional
    public MatchPlayerDTO create(MatchPlayerDTO matchPlayerDTO) {
        MatchPlayerDTO matchPlayer = super.create(matchPlayerDTO);
        matchPlayer.setAnswers(
                matchPlayerDTO.getAnswers() == null
                        ? new HashSet<>()
                        : matchPlayerDTO.getAnswers().stream()
                                .map(
                                        answer -> {
                                            answer.setMatchId(matchPlayerDTO.getMatchId());
                                            answer.setPlayerId(matchPlayerDTO.getPlayerId());
                                            answer.setTournamentId(
                                                    matchPlayerDTO.getTournamentId());
                                            return matchPlayerAnswerManager.create(answer);
                                        })
                                .collect(Collectors.toSet()));
        return matchPlayer;
    }

    @Override
    protected MatchPlayerMapper getMapper() {
        return matchPlayerMapper;
    }

    @Override
    protected MatchPlayerDAO getDao() {
        return matchPlayerDAO;
    }
}
