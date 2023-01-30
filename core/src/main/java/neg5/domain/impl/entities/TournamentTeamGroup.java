package neg5.domain.impl.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import neg5.domain.api.enums.State;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "tournament_team_group")
@DynamicUpdate
@SequenceGenerator(
        name = TournamentTeamGroup.ID_SEQ,
        sequenceName = TournamentTeamGroup.ID_SEQ,
        allocationSize = 1)
public class TournamentTeamGroup extends AbstractDataObject<TournamentTeamGroup>
        implements IdDataObject<Long> {

    static final String ID_SEQ = "tournament_team_group_id_seq";

    private Long id;
    private String tournamentId;
    private String name;
    private State stateCode;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TournamentTeamGroup.ID_SEQ)
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "tournament_id")
    public String getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(String tournamentId) {
        this.tournamentId = tournamentId;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "state_code")
    public State getStateCode() {
        return stateCode;
    }

    public void setStateCode(State stateCode) {
        this.stateCode = stateCode;
    }
}
