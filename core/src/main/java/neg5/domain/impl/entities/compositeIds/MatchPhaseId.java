package neg5.domain.impl.entities.compositeIds;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import neg5.domain.impl.entities.CompositeId;
import neg5.domain.impl.entities.Tournament;
import neg5.domain.impl.entities.TournamentMatch;
import neg5.domain.impl.entities.TournamentPhase;

@Embeddable
public class MatchPhaseId implements Serializable, CompositeId {

    private TournamentMatch match;
    private Tournament tournament;
    private TournamentPhase phase;

    @JoinColumn(name = "tournament_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    @JoinColumn(name = "match_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    public TournamentMatch getMatch() {
        return match;
    }

    public void setMatch(TournamentMatch match) {
        this.match = match;
    }

    @JoinColumn(name = "phase_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    public TournamentPhase getPhase() {
        return phase;
    }

    public void setPhase(TournamentPhase phase) {
        this.phase = phase;
    }
}
