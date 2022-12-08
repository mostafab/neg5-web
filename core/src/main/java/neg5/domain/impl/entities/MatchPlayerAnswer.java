package neg5.domain.impl.entities;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import neg5.domain.impl.entities.compositeIds.MatchPlayerAnswerId;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "player_match_tossup")
@DynamicUpdate
public class MatchPlayerAnswer extends AbstractDataObject<MatchPlayerAnswer>
        implements CompositeIdObject<MatchPlayerAnswerId> {

    private MatchPlayerAnswerId id;

    private TournamentTossupValue tournamentTossupValue;
    private Integer numberGotten;

    @EmbeddedId
    @Override
    public MatchPlayerAnswerId getId() {
        return id;
    }

    @Override
    public void setId(MatchPlayerAnswerId id) {
        this.id = id;
    }

    @Column(name = "number_gotten")
    public Integer getNumberGotten() {
        return numberGotten;
    }

    public void setNumberGotten(Integer numberGotten) {
        this.numberGotten = numberGotten;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "tournament_id", insertable = false, updatable = false),
        @JoinColumn(name = "tossup_value", insertable = false, updatable = false)
    })
    public TournamentTossupValue getTournamentTossupValue() {
        return tournamentTossupValue;
    }

    public void setTournamentTossupValue(TournamentTossupValue tournamentTossupValue) {
        this.tournamentTossupValue = tournamentTossupValue;
    }
}
