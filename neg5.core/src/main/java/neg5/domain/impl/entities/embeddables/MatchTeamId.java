package neg5.domain.impl.entities.embeddables;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import neg5.domain.impl.entities.CompositeId;
import neg5.domain.impl.entities.Tournament;
import neg5.domain.impl.entities.TournamentMatch;
import neg5.domain.impl.entities.TournamentTeam;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class MatchTeamId implements Serializable, CompositeId {

    private TournamentTeam team;
    private TournamentMatch match;
    private Tournament tournament;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false, updatable = false)
    public TournamentTeam getTeam() {
        return team;
    }

    public void setTeam(TournamentTeam team) {
        this.team = team;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id", nullable = false, updatable = false)
    public TournamentMatch getMatch() {
        return match;
    }

    public void setMatch(TournamentMatch match) {
        this.match = match;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tournament_id", nullable = false, updatable = false)
    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof MatchTeamId)) {
            return false;
        }
        MatchTeamId that = (MatchTeamId) obj;
        return that.getTeam().getId().equals(team.getId())
                && that.getTournament().getId().equals(tournament.getId())
                && that.getMatch().getId().equals(match.getId());
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 3)
                .append(team.getId())
                .append(tournament.getId())
                .append(match.getId())
                .toHashCode();
    }
}
