package neg5.domain.impl.entities;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import neg5.domain.impl.entities.compositeIds.MatchPlayerId;
import org.hibernate.annotations.DynamicUpdate;

@Table(name = "player_plays_in_tournament_match")
@Entity
@DynamicUpdate
public class MatchPlayer extends AbstractDataObject<MatchPlayer>
        implements CompositeIdObject<MatchPlayerId> {

    private MatchPlayerId id;
    private Integer tossupsHeard;

    private Set<MatchPlayerAnswer> answers;

    /*
    Since this table has a composite primary key, we need to use an {@link EmbeddedId} to represent it
     */
    @EmbeddedId
    @Override
    public MatchPlayerId getId() {
        return id;
    }

    @Override
    public void setId(MatchPlayerId id) {
        this.id = id;
    }

    @Column(name = "tossups_heard")
    public Integer getTossupsHeard() {
        return tossupsHeard;
    }

    public void setTossupsHeard(Integer tossupsHeard) {
        this.tossupsHeard = tossupsHeard;
    }

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(
                name = "player_id",
                referencedColumnName = "player_id",
                updatable = false,
                insertable = false),
        @JoinColumn(
                name = "match_id",
                referencedColumnName = "match_id",
                updatable = false,
                insertable = false),
        @JoinColumn(
                name = "tournament_id",
                referencedColumnName = "tournament_id",
                updatable = false,
                insertable = false)
    })
    public Set<MatchPlayerAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<MatchPlayerAnswer> answers) {
        this.answers = answers;
    }

    @Transient
    public TournamentPlayer getPlayer() {
        return id.getPlayer();
    }

    @Transient
    public TournamentMatch getMatch() {
        return id.getMatch();
    }
}
