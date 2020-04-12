package org.neg5.managers;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import org.neg5.MatchPlayerDTO;
import org.neg5.daos.MatchPlayerDAO;
import org.neg5.data.MatchPlayer;
import org.neg5.data.embeddables.MatchPlayerId;
import org.neg5.mappers.MatchPlayerMapper;

import java.util.HashSet;
import java.util.stream.Collectors;

public class MatchPlayerManager extends AbstractDTOManager<MatchPlayer, MatchPlayerDTO, MatchPlayerId> {

    private MatchPlayerDAO matchPlayerDAO;
    private MatchPlayerMapper matchPlayerMapper;
    private final MatchPlayerAnswerManager matchPlayerAnswerManager;

    @Inject
    public MatchPlayerManager(MatchPlayerDAO matchPlayerDAO,
                              MatchPlayerMapper matchPlayerMapper,
                              MatchPlayerAnswerManager matchPlayerAnswerManager) {
        this.matchPlayerDAO = matchPlayerDAO;
        this.matchPlayerMapper = matchPlayerMapper;
        this.matchPlayerAnswerManager = matchPlayerAnswerManager;
    }

    @Override
    @Transactional
    public MatchPlayerDTO create(MatchPlayerDTO matchPlayerDTO) {
        MatchPlayerDTO matchPlayer = super.create(matchPlayerDTO);
        matchPlayer.setAnswers(matchPlayerDTO.getAnswers() == null
            ? new HashSet<>()
            : matchPlayerDTO.getAnswers()
                .stream()
                .map(answer -> {
                    answer.setMatchId(matchPlayerDTO.getMatchId());
                    answer.setPlayerId(matchPlayerDTO.getPlayerId());
                    answer.setTournamentId(matchPlayerDTO.getTournamentId());
                    return matchPlayerAnswerManager.create(answer);
                })
                .collect(Collectors.toSet())
        );
        return matchPlayer;
    }

    @Override
    protected MatchPlayerMapper getMapper() {
        return matchPlayerMapper;
    }

    @Override
    protected MatchPlayerDAO getRwDAO() {
        return matchPlayerDAO;
    }
}
