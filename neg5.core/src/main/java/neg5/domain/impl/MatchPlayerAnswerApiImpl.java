package neg5.domain.impl;

import com.google.inject.Inject;
import neg5.domain.api.MatchPlayerAnswerApi;
import neg5.domain.impl.entities.embeddables.MatchPlayerAnswerId;
import org.neg5.MatchPlayerAnswerDTO;
import neg5.domain.impl.dataAccess.MatchPlayerAnswerDAO;
import neg5.domain.impl.entities.MatchPlayerAnswer;
import neg5.domain.impl.mappers.MatchPlayerAnswerMapper;

public class MatchPlayerAnswerApiImpl
        extends AbstractApiLayerImpl<MatchPlayerAnswer, MatchPlayerAnswerDTO, MatchPlayerAnswerId>
        implements MatchPlayerAnswerApi {

    private final MatchPlayerAnswerMapper playerAnswerMapper;
    private final MatchPlayerAnswerDAO matchPlayerAnswerDAO;

    @Inject
    public MatchPlayerAnswerApiImpl(MatchPlayerAnswerMapper playerAnswerMapper,
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
