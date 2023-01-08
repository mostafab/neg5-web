package neg5.domain.impl.entities;

import java.io.Serializable;
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
@Table(name = "tournament_scoresheet_cycle_bonus")
@DynamicUpdate
@SequenceGenerator(
        name = TournamentScoresheetCycleBonus.ID_SEQ,
        sequenceName = TournamentScoresheetCycleBonus.ID_SEQ,
        allocationSize = 1)
public class TournamentScoresheetCycleBonus
        extends AbstractDataObject<TournamentScoresheetCycleBonus>
        implements IdDataObject<Long>, Serializable {

    public static final String ID_SEQ = "tournament_scoresheet_cycle_bonus_id_seq";

    private Long id;
    private TournamentScoresheetCycle cycle;
    private Integer number;
    private Integer value;
    private String answeringTeamId;

    @Override
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = TournamentScoresheetCycleBonus.ID_SEQ)
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tournament_scoresheet_cycle_id")
    public TournamentScoresheetCycle getCycle() {
        return cycle;
    }

    public void setCycle(TournamentScoresheetCycle cycle) {
        this.cycle = cycle;
    }

    @Column(name = "number")
    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Column(name = "value")
    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Column(name = "answering_team_id")
    public String getAnsweringTeamId() {
        return answeringTeamId;
    }

    public void setAnsweringTeamId(String answeringTeamId) {
        this.answeringTeamId = answeringTeamId;
    }
}
