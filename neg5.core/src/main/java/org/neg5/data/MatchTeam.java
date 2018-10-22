package org.neg5.data;

import org.hibernate.annotations.DynamicUpdate;
import org.neg5.data.embeddables.MatchTeamId;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "team_plays_in_tournament_match")
@DynamicUpdate
public class MatchTeam extends AbstractDataObject<MatchTeam> implements CompositeIdObject<MatchTeamId> {

    private MatchTeamId matchTeamId;

    private Integer score;
    private Integer bouncebackPoints;
    private Integer overtimeTossupsGotten;

    private TournamentTeam team;
    private TournamentMatch match;

    /*
    Since this table has a composite primary key, we need to use an {@link EmbeddedId} to represent it
     */
    @EmbeddedId
    @Override
    public MatchTeamId getId() {
        return matchTeamId;
    }

    @Override
    public void setId(MatchTeamId matchTeamId) {
        this.matchTeamId = matchTeamId;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id", updatable = false, insertable = false)
    public TournamentMatch getMatch() {
        return match;
    }

    public void setMatch(TournamentMatch match) {
        this.match = match;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", updatable = false, insertable = false)
    public TournamentTeam getTeam() {
        return team;
    }

    public void setTeam(TournamentTeam team) {
        this.team = team;
    }
}
