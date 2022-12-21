package neg5.domain.api;

import neg5.domain.api.enums.TournamentAccessLevel;

public class TournamentPermissionsDTO {

    private TournamentAccessLevel accessLevel;

    public TournamentAccessLevel getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(TournamentAccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }
}
