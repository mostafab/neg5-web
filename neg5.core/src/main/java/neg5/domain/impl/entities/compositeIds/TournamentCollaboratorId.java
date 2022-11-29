package neg5.domain.impl.entities.compositeIds;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import neg5.domain.impl.entities.Account;
import neg5.domain.impl.entities.CompositeId;
import neg5.domain.impl.entities.Tournament;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class TournamentCollaboratorId implements Serializable, CompositeId {

    private Account user;
    private Tournament tournament;

    @JoinColumn(name = "username", nullable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    public Account getUser() {
        return user;
    }

    public void setUser(Account user) {
        this.user = user;
    }

    @JoinColumn(name = "tournament_id", nullable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 3)
                .append(user.getId())
                .append(tournament.getId())
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof TournamentCollaboratorId)) {
            return false;
        }
        TournamentCollaboratorId that = (TournamentCollaboratorId) obj;
        return new EqualsBuilder()
                .append(user.getId(), that.getUser().getId())
                .append(tournament.getId(), that.getTournament().getId())
                .isEquals();
    }
}
