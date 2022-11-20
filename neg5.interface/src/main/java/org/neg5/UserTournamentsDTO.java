package org.neg5;

import java.util.List;

public class UserTournamentsDTO {

    private List<TournamentDTO> userOwnedTournaments;
    private List<TournamentDTO> collaboratingTournaments;

    public List<TournamentDTO> getUserOwnedTournaments() {
        return userOwnedTournaments;
    }

    public void setUserOwnedTournaments(List<TournamentDTO> userOwnedTournaments) {
        this.userOwnedTournaments = userOwnedTournaments;
    }

    public List<TournamentDTO> getCollaboratingTournaments() {
        return collaboratingTournaments;
    }

    public void setCollaboratingTournaments(List<TournamentDTO> collaboratingTournaments) {
        this.collaboratingTournaments = collaboratingTournaments;
    }
}
