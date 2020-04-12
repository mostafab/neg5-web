package org.neg5.data;

import org.hibernate.annotations.DynamicUpdate;
import org.neg5.data.embeddables.MatchPhaseId;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "match_is_part_of_phase")
@DynamicUpdate
public class TournamentMatchPhase
        extends AbstractDataObject<TournamentMatchPhase>
        implements CompositeIdObject<MatchPhaseId>  {

    private MatchPhaseId id;

    @Override
    @EmbeddedId
    public MatchPhaseId getId() {
        return id;
    }

    @Override
    public void setId(MatchPhaseId id) {
        this.id = id;
    }
}
