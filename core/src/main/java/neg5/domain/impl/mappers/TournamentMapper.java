package neg5.domain.impl.mappers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.stream.Collectors;
import neg5.domain.api.TournamentDTO;
import neg5.domain.impl.entities.Tournament;

@Singleton
public class TournamentMapper extends AbstractObjectMapper<Tournament, TournamentDTO> {

    @Inject private TournamentPhaseMapper tournamentPhaseMapper;
    @Inject private TournamentPoolMapper tournamentPoolMapper;
    @Inject private TournamentTossupValueMapper tournamentTossupValueMapper;

    protected TournamentMapper() {
        super(Tournament.class, TournamentDTO.class);
    }

    @Override
    protected void enrichDTO(TournamentDTO tournamentDTO, Tournament tournament) {
        if (tournament.getPhases() != null) {
            tournamentDTO.setPhases(
                    tournament.getPhases().stream()
                            .map(tournamentPhaseMapper::toDTO)
                            .collect(Collectors.toSet()));
        }
        if (tournament.getDivisions() != null) {
            tournamentDTO.setDivisions(
                    tournament.getDivisions().stream()
                            .map(tournamentPoolMapper::toDTO)
                            .collect(Collectors.toSet()));
        }
        if (tournament.getTossupValues() != null) {
            tournamentDTO.setTossupValues(
                    tournament.getTossupValues().stream()
                            .map(tournamentTossupValueMapper::toDTO)
                            .collect(Collectors.toSet()));
        }
        if (tournament.getCurrentPhase() != null) {
            tournamentDTO.setCurrentPhaseId(tournament.getCurrentPhase().getId());
        }
    }

    @Override
    protected void addMappings() {
        getDtoToEntityTypeMap()
                .addMappings(
                        mp -> {
                            mp.skip(Tournament::setPhases);
                            mp.skip(Tournament::setTossupValues);
                            mp.skip(Tournament::setDivisions);
                        });
    }
}
