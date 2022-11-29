package org.neg5.data.embeddables;

import neg5.domain.impl.entities.CompositeId;
import neg5.domain.impl.entities.Tournament;
import neg5.domain.impl.entities.TournamentPool;
import neg5.domain.impl.entities.TournamentTeam;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class TournamentTeamPoolId implements Serializable, CompositeId {

    private Tournament tournament;
    private TournamentTeam team;
    private TournamentPool pool;

    @JoinColumn(name = "tournament_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    @JoinColumn(name = "team_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    public TournamentTeam getTeam() {
        return team;
    }

    public void setTeam(TournamentTeam team) {
        this.team = team;
    }

    @JoinColumn(name = "division_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    public TournamentPool getPool() {
        return pool;
    }

    public void setPool(TournamentPool pool) {
        this.pool = pool;
    }
}
