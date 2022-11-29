package neg5.domain.impl.mappers.data;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import neg5.domain.impl.entities.transformers.data.Match;
import neg5.domain.impl.entities.transformers.data.Phase;
import neg5.domain.impl.mappers.AbstractObjectMapper;
import neg5.domain.api.TournamentMatchDTO;
import neg5.domain.api.TournamentTossupValueDTO;

import java.util.Map;
import java.util.stream.Collectors;

@Singleton
public class MatchToMatchDTOMapper extends AbstractObjectMapper<Match, TournamentMatchDTO> {

    @Inject private TeamInMatchMapper teamInMatchMapper;

    protected MatchToMatchDTOMapper() {
        super(Match.class, TournamentMatchDTO.class);
    }

    public TournamentMatchDTO toDTO(Match match, Map<Integer, TournamentTossupValueDTO> tossupValues) {
        TournamentMatchDTO dto = super.toDTO(match);
        if (match.getAddedAt() != null) {
            dto.setAddedAt(match.getAddedAt().toInstant());
        }
        dto.setPhases(match.getPhases().stream().map(Phase::getId).collect(Collectors.toSet()));
        dto.setTeams(match.getTeams().stream().map(teamInMatchMapper::toDTO).collect(Collectors.toSet()));

        dto.getTeams().stream()
                .flatMap(team -> team.getPlayers().stream())
                .flatMap(player -> player.getAnswers().stream())
                .forEach(answer -> {
                    if (tossupValues.containsKey(answer.getTossupValue())) {
                        answer.setAnswerType(tossupValues.get(answer.getTossupValue()).getAnswerType());
                    }
                });

        return dto;
    }
}
