package neg5.domain.impl.mappers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import neg5.domain.api.MatchPlayerDTO;
import neg5.domain.impl.entities.MatchPlayer;

import java.util.HashSet;
import java.util.stream.Collectors;

@Singleton
public class MatchPlayerMapper extends AbstractObjectMapper<MatchPlayer, MatchPlayerDTO> {

    @Inject private MatchPlayerAnswerMapper matchPlayerAnswerMapper;

    protected MatchPlayerMapper() {
        super(MatchPlayer.class, MatchPlayerDTO.class);
    }

    @Override
    protected void enrichDTO(MatchPlayerDTO matchPlayerDTO, MatchPlayer matchPlayer) {
        matchPlayerDTO.setAnswers(matchPlayer.getAnswers() == null ? new HashSet<>() : matchPlayer.getAnswers().stream()
                .map(answer -> matchPlayerAnswerMapper.toDTO(answer)).collect(Collectors.toSet())
        );
    }
}
