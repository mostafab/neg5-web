package neg5.domain.impl.entities.embeddables;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import neg5.domain.impl.entities.CompositeId;
import neg5.domain.impl.entities.Tournament;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class TournamentTossupValueId implements Serializable, CompositeId {

    private Tournament tournament;
    private Integer value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tournament_id", nullable = false, updatable = false)
    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    @Column(name = "tossup_value")
    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof TournamentTossupValueId)) {
            return false;
        }
        TournamentTossupValueId that = (TournamentTossupValueId) obj;
        return that.getTournament().getId().equals(tournament.getId())
                && that.getValue().equals(value);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 3)
                .append(tournament.getId())
                .append(value)
                .toHashCode();
    }
}
