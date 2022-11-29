package neg5.domain.impl.mappers;

import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.neg5.TournamentMatchDTO;
import neg5.domain.impl.entities.TournamentMatch;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class TournamentMatchMapper extends AbstractObjectMapper<TournamentMatch, TournamentMatchDTO> {

    @Inject private MatchTeamMapper matchTeamMapper;

    protected TournamentMatchMapper() {
        super(TournamentMatch.class, TournamentMatchDTO.class);
    }

    @Override
    protected void enrichDTO(TournamentMatchDTO tournamentMatchDTO, TournamentMatch tournamentMatch) {
        tournamentMatchDTO.setTeams(tournamentMatch.getTeams() == null
                ? new HashSet<>()
                : tournamentMatch.getTeams().stream().map(matchTeamMapper::toDTO).collect(Collectors.toSet())
        );
        tournamentMatchDTO.setPhases(tournamentMatch.getPhases() == null
                ? new HashSet<>()
                : tournamentMatch.getPhases().stream().map(phase -> phase.getId().getPhase().getId()).collect(Collectors.toSet())
        );
    }

    @Override
    protected Set<String> getIgnoredEntityPropertyNames() {
        return Sets.newHashSet("phases");
    }
}
