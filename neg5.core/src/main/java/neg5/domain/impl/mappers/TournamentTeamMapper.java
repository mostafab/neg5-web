package neg5.domain.impl.mappers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import neg5.domain.api.TournamentTeamDTO;
import neg5.domain.impl.entities.TournamentTeam;

import java.util.HashSet;
import java.util.stream.Collectors;

@Singleton
public class TournamentTeamMapper extends AbstractObjectMapper<TournamentTeam, TournamentTeamDTO> {

    @Inject private TournamentPoolMapper divisionMapper;
    @Inject private TournamentPlayerMapper playerMapper;

    protected TournamentTeamMapper() {
        super(TournamentTeam.class, TournamentTeamDTO.class);
    }

    @Override
    protected void enrichDTO(TournamentTeamDTO tournamentTeamDTO, TournamentTeam tournamentTeam) {
        tournamentTeamDTO.setDivisions(tournamentTeam.getDivisions() == null ? new HashSet<>() : tournamentTeam.getDivisions().stream()
                .map(divisionMapper::toDTO).collect(Collectors.toSet()));
        tournamentTeamDTO.setPlayers(tournamentTeam.getPlayers() == null
                ? new HashSet<>()
                : tournamentTeam.getPlayers().stream()
                    .map(playerMapper::toDTO)
                    .collect(Collectors.toSet())
        );
    }

    @Override
    protected void addMappings() {
        getDtoToEntityTypeMap().addMappings(mp -> {
            mp.skip(TournamentTeam::setDivisions);
            mp.skip(TournamentTeam::setPlayers);
        });
    }
}
