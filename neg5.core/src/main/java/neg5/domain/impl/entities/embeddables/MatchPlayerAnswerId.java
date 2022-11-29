package neg5.domain.impl.entities.embeddables;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import neg5.domain.impl.entities.CompositeId;
import neg5.domain.impl.entities.Tournament;
import neg5.domain.impl.entities.TournamentMatch;
import neg5.domain.impl.entities.TournamentPlayer;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import java.io.Serializable;

@Embeddable
public class MatchPlayerAnswerId implements Serializable, CompositeId {

    private TournamentPlayer player;
    private TournamentMatch match;
    private Tournament tournament;

    private Integer tossupValue;

    @JoinColumn(name = "player_id")
    @ManyToOne(fetch = FetchType.LAZY)
    public TournamentPlayer getPlayer() {
        return player;
    }

    public void setPlayer(TournamentPlayer player) {
        this.player = player;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id")
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

    @Column(name = "tossup_value")
    public Integer getTossupValue() {
        return tossupValue;
    }

    public void setTossupValue(Integer tossupValue) {
        this.tossupValue = tossupValue;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof MatchPlayerAnswerId)) {
            return false;
        }
        MatchPlayerAnswerId that = (MatchPlayerAnswerId) obj;
        return that.getPlayer().getId().equals(player.getId())
                && that.getTournament().getId().equals(tournament.getId())
                && that.getMatch().getId() .equals(match.getId())
                && that.getTossupValue().equals(tossupValue);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 3)
                .append(player.getId())
                .append(tournament.getId())
                .append(match.getId())
                .append(tossupValue)
                .toHashCode();
    }
}
