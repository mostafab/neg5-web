package neg5.domain.impl.mappers.data;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import neg5.domain.impl.entities.transformers.data.TeamInMatch;
import neg5.domain.impl.mappers.AbstractObjectMapper;
import org.neg5.MatchTeamDTO;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Singleton
public class TeamInMatchMapper extends AbstractObjectMapper<TeamInMatch, MatchTeamDTO> {

    @Inject private TeamMatchPlayerMapper teamMatchPlayerMapper;

    protected TeamInMatchMapper() {
        super(TeamInMatch.class, MatchTeamDTO.class);
    }

    @Override
    protected void enrichDTO(MatchTeamDTO matchTeamDTO, TeamInMatch teamInMatch) {
        if (teamInMatch.getPlayers() == null) {
            matchTeamDTO.setPlayers(new ArrayList<>());
        } else {
            matchTeamDTO.setPlayers(teamInMatch.getPlayers().stream().map(teamMatchPlayerMapper::toDTO)
                    .collect(Collectors.toList()));
        }
        matchTeamDTO.setOvertimeTossupsGotten(teamInMatch.getOvertimeTossups());
    }
}
