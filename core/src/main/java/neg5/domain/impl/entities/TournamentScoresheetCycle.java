package neg5.domain.impl.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "tournament_scoresheet_cycle")
@DynamicUpdate
@SequenceGenerator(
        name = TournamentScoresheetCycle.ID_SEQ,
        sequenceName = TournamentScoresheetCycle.ID_SEQ,
        allocationSize = 1)
public class TournamentScoresheetCycle extends AbstractDataObject<TournamentScoresheetCycle>
        implements IdDataObject<Long>, Serializable {

    public static final String ID_SEQ = "tournament_scoresheet_cycle_id_seq";

    private Long id;
    private Long tournamentScoresheetId;
    private Integer number;
    private String stage;
    private String activePlayerIds;

    @Override
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = TournamentScoresheetCycle.ID_SEQ)
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "tournament_scoresheet_id", nullable = false)
    public Long getTournamentScoresheetId() {
        return tournamentScoresheetId;
    }

    public void setTournamentScoresheetId(Long tournamentScoresheetId) {
        this.tournamentScoresheetId = tournamentScoresheetId;
    }

    @Column(name = "number")
    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Column(name = "stage")
    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    @Column(name = "active_player_ids")
    public String getActivePlayerIds() {
        return activePlayerIds;
    }

    public void setActivePlayerIds(String activePlayerIds) {
        this.activePlayerIds = activePlayerIds;
    }
}