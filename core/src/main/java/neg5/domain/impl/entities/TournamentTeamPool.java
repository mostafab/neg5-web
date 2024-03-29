package neg5.domain.impl.entities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import neg5.domain.impl.entities.compositeIds.TournamentTeamPoolId;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "tournament_team_in_division")
@DynamicUpdate
public class TournamentTeamPool extends AbstractDataObject<TournamentTeamPool>
        implements CompositeIdObject<TournamentTeamPoolId> {

    private TournamentTeamPoolId id;

    @Override
    @EmbeddedId
    public TournamentTeamPoolId getId() {
        return id;
    }

    @Override
    public void setId(TournamentTeamPoolId id) {
        this.id = id;
    }
}
