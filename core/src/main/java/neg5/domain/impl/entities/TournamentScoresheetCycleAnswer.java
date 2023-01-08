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
@Table(name = "tournament_scoresheet_cycle_answer")
@DynamicUpdate
@SequenceGenerator(
        name = TournamentScoresheetCycleAnswer.ID_SEQ,
        sequenceName = TournamentScoresheetCycleAnswer.ID_SEQ,
        allocationSize = 1)
public class TournamentScoresheetCycleAnswer
        extends AbstractDataObject<TournamentScoresheetCycleAnswer>
        implements IdDataObject<Long>, Serializable {

    public static final String ID_SEQ = "tournament_scoresheet_cycle_answer_id_seq";

    private Long id;
    private TournamentScoresheetCycle cycle;
    private Integer number;
    private Integer value;
    private String playerId;

    @Override
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = TournamentScoresheetCycleAnswer.ID_SEQ)
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

    @Column(name = "player_id")
    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }
}
