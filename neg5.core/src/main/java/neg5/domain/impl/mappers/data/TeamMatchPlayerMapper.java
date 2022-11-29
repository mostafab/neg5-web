package neg5.domain.impl.mappers.data;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import neg5.domain.impl.entities.transformers.data.TeamMatchPlayer;
import neg5.domain.impl.mappers.AbstractObjectMapper;
import org.neg5.MatchPlayerDTO;

import java.util.HashSet;
import java.util.stream.Collectors;

@Singleton
public class TeamMatchPlayerMapper extends AbstractObjectMapper<TeamMatchPlayer, MatchPlayerDTO> {

    @Inject private TeamMatchPlayerAnswerMapper teamMatchPlayerAnswerMapper;

    protected TeamMatchPlayerMapper() {
        super(TeamMatchPlayer.class, MatchPlayerDTO.class);
    }

    @Override
    protected void enrichDTO(MatchPlayerDTO matchPlayerDTO, TeamMatchPlayer teamMatchPlayer) {
        if (teamMatchPlayer.getTossupValues() != null) {
            matchPlayerDTO.setAnswers(teamMatchPlayer.getTossupValues().stream()
                    .map(teamMatchPlayerAnswerMapper::toDTO).collect(Collectors.toSet()));
        } else {
            matchPlayerDTO.setAnswers(new HashSet<>());
        }
    }
}
