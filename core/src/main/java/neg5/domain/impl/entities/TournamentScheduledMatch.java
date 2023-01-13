package neg5.domain.impl.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "tournament_schedule_match")
@DynamicUpdate
@SequenceGenerator(
        name = TournamentScheduledMatch.ID_SEQ,
        sequenceName = TournamentScheduledMatch.ID_SEQ,
        allocationSize = 1)
public class TournamentScheduledMatch extends AbstractDataObject<TournamentScheduledMatch>
        implements IdDataObject<Long> {

    public static final String ID_SEQ = "tournament_schedule_match_id_seq";

    private Long id;
    private TournamentSchedule schedule;
    private String team1Id;
    private String team2Id;
    private Integer round;

    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TournamentScheduledMatch.ID_SEQ)
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tournament_schedule_id")
    public TournamentSchedule getSchedule() {
        return schedule;
    }

    public void setSchedule(TournamentSchedule schedule) {
        this.schedule = schedule;
    }

    @Column(name = "team1_id")
    public String getTeam1Id() {
        return team1Id;
    }

    public void setTeam1Id(String team1Id) {
        this.team1Id = team1Id;
    }

    @Column(name = "team2_id")
    public String getTeam2Id() {
        return team2Id;
    }

    public void setTeam2Id(String team2Id) {
        this.team2Id = team2Id;
    }

    @Column(name = "round")
    public Integer getRound() {
        return round;
    }

    public void setRound(Integer round) {
        this.round = round;
    }
}
