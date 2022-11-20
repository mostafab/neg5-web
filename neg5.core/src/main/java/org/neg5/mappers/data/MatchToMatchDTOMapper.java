package org.neg5.mappers.data;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.neg5.TournamentMatchDTO;
import org.neg5.TournamentTossupValueDTO;
import org.neg5.data.transformers.data.Match;
import org.neg5.data.transformers.data.Phase;
import org.neg5.mappers.AbstractObjectMapper;

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
