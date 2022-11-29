package neg5.domain.impl;

import com.google.inject.Inject;
import neg5.domain.api.MatchPlayerAnswerApi;
import org.neg5.MatchPlayerAnswerDTO;
import org.neg5.daos.MatchPlayerAnswerDAO;
import neg5.domain.impl.entities.MatchPlayerAnswer;
import org.neg5.data.embeddables.MatchPlayerAnswerId;;
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
