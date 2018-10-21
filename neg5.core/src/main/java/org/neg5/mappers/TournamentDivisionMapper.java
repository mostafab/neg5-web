package org.neg5.mappers;

import com.google.inject.Singleton;
import org.neg5.TournamentDivisionDTO;
import org.neg5.data.TournamentDivision;

@Singleton
public class TournamentDivisionMapper extends AbstractObjectMapper<TournamentDivision, TournamentDivisionDTO> {

    protected TournamentDivisionMapper() {
        super(TournamentDivision.class, TournamentDivisionDTO.class);
    }

    @Override
    protected void enrichDTO(TournamentDivisionDTO tournamentDivisionDTO, TournamentDivision tournamentDivision) {
        tournamentDivisionDTO.setTournamentId(tournamentDivision.getTournament().getId());
        tournamentDivisionDTO.setPhaseId(tournamentDivision.getPhase().getId());
    }
}
