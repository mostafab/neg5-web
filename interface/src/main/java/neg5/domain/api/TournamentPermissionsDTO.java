package neg5.domain.api;

import neg5.domain.api.enums.TournamentAccessLevel;

public class TournamentPermissionsDTO {

    private String tournamentId;
    private TournamentAccessLevel accessLevel;
    private boolean canEditRules;
    private boolean canEditTeams;
    private boolean canEditInfo;
    private boolean canEditCollaborators;
    private boolean canEditPools;
    private boolean canEditMatches;
    private boolean canEditSchedule;

    public boolean getCanEditTeams() {
        return canEditTeams;
    }

    public void setCanEditTeams(boolean canEditTeams) {
        this.canEditTeams = canEditTeams;
    }

    public boolean getCanEditCollaborators() {
        return canEditCollaborators;
    }

    public void setCanEditCollaborators(boolean canEditCollaborators) {
        this.canEditCollaborators = canEditCollaborators;
    }

    public boolean getCanEditPools() {
        return canEditPools;
    }

    public void setCanEditPools(boolean canEditPools) {
        this.canEditPools = canEditPools;
    }

    public boolean getCanEditMatches() {
        return canEditMatches;
    }

    public void setCanEditMatches(boolean canEditMatches) {
        this.canEditMatches = canEditMatches;
    }

    public String getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(String tournamentId) {
        this.tournamentId = tournamentId;
    }

    public TournamentAccessLevel getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(TournamentAccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }

    public boolean getCanEditInfo() {
        return canEditInfo;
    }

    public void setCanEditInfo(boolean canEditInfo) {
        this.canEditInfo = canEditInfo;
    }

    public boolean getCanEditRules() {
        return canEditRules;
    }

    public void setCanEditRules(boolean canEditRules) {
        this.canEditRules = canEditRules;
    }

    public boolean getCanEditSchedule() {
        return canEditSchedule;
    }

    public void setCanEditSchedule(boolean canEditSchedule) {
        this.canEditSchedule = canEditSchedule;
    }
}
