package neg5.domain.impl.entities.compositeIds;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import neg5.domain.impl.entities.CompositeId;
import neg5.domain.impl.entities.Tournament;
import neg5.domain.impl.entities.TournamentMatch;
import neg5.domain.impl.entities.TournamentPlayer;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class MatchPlayerId implements Serializable, CompositeId {

    private TournamentPlayer player;
    private TournamentMatch match;
    private Tournament tournament;

    @JoinColumn(name = "player_id")
    @ManyToOne(fetch = FetchType.LAZY)
    public TournamentPlayer getPlayer() {
        return player;
    }

    public void setPlayer(TournamentPlayer player) {
        this.player = player;
    }

    @JoinColumn(name = "match_id")
    @ManyToOne(fetch = FetchType.LAZY)
    public TournamentMatch getMatch() {
        return match;
    }

    public void setMatch(TournamentMatch match) {
        this.match = match;
    }

    @JoinColumn(name = "tournament_id")
    @ManyToOne(fetch = FetchType.LAZY)
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
        if (obj == null || !(obj instanceof MatchPlayerId)) {
            return false;
        }
        MatchPlayerId that = (MatchPlayerId) obj;
        return that.getPlayer().getId().equals(player.getId())
                && that.getTournament().getId().equals(tournament.getId())
                && that.getMatch().getId().equals(match.getId());
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 3)
                .append(player.getId())
                .append(match.getId())
                .append(tournament.getId())
                .toHashCode();
    }
}
