package org.neg5.managers;

import com.google.inject.Inject;
import org.neg5.MatchPlayerAnswerDTO;
import org.neg5.daos.MatchPlayerAnswerDAO;
import org.neg5.data.MatchPlayerAnswer;
import org.neg5.data.embeddables.MatchPlayerAnswerId;;
import org.neg5.mappers.MatchPlayerAnswerMapper;

public class MatchPlayerAnswerManager
        extends AbstractDTOManager<MatchPlayerAnswer, MatchPlayerAnswerDTO, MatchPlayerAnswerId> {

    private final MatchPlayerAnswerMapper playerAnswerMapper;
    private final MatchPlayerAnswerDAO matchPlayerAnswerDAO;

    @Inject
    public MatchPlayerAnswerManager(MatchPlayerAnswerMapper playerAnswerMapper,
                                    MatchPlayerAnswerDAO matchPlayerAnswerDAO) {
        this.playerAnswerMapper = playerAnswerMapper;
        this.matchPlayerAnswerDAO = matchPlayerAnswerDAO;
    }

    @Override
    protected MatchPlayerAnswerDAO getDao() {
        return matchPlayerAnswerDAO;
    }

    @Override
    protected MatchPlayerAnswerMapper getMapper() {
        return playerAnswerMapper;
    }
}
