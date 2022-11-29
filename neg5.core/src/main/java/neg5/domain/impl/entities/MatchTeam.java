package neg5.domain.impl.entities;

import neg5.domain.impl.entities.compositeIds.MatchTeamId;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "team_plays_in_tournament_match")
@DynamicUpdate
public class MatchTeam extends AbstractDataObject<MatchTeam> implements CompositeIdObject<MatchTeamId> {

    private MatchTeamId id;

    private Integer score;
    private Integer bouncebackPoints;
    private Integer overtimeTossupsGotten;

    /*
    Since this table has a composite primary key, we need to use an {@link EmbeddedId} to represent it
     */
    @EmbeddedId
    @Override
    public MatchTeamId getId() {
        return id;
    }

    @Override
    public void setId(MatchTeamId id) {
        this.id = id;
    }

    @Column(name = "score")
    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Column(name = "bounceback_points")
    public Integer getBouncebackPoints() {
        return bouncebackPoints;
    }

    public void setBouncebackPoints(Integer bouncebackPoints) {
        this.bouncebackPoints = bouncebackPoints;
    }

    @Column(name = "overtime_tossups_gotten")
    public Integer getOvertimeTossupsGotten() {
        return overtimeTossupsGotten;
    }

    public void setOvertimeTossupsGotten(Integer overtimeTossupsGotten) {
        this.overtimeTossupsGotten = overtimeTossupsGotten;
    }

    @Transient
    public TournamentMatch getMatch() {
        if (id == null) {
            return null;
        }
        return id.getMatch();
    }

    @Transient
    public TournamentTeam getTeam() {
        return id.getTeam();
    }

    @Transient
    public Tournament getTournament() {
        return id.getTournament();
    }
}
