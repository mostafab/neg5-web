package neg5.domain.impl.entities;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "tournament_schedule")
@DynamicUpdate
@SequenceGenerator(
        name = TournamentSchedule.ID_SEQ,
        sequenceName = TournamentSchedule.ID_SEQ,
        allocationSize = 1)
public class TournamentSchedule extends AbstractDataObject<TournamentSchedule>
        implements SpecificTournamentEntity, IdDataObject<Long> {

    public static final String ID_SEQ = "tournament_schedule_id_seq";

    private Long id;
    private Tournament tournament;
    private String tournamentPhaseId;

    private List<TournamentScheduledMatch> matches;

    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TournamentSchedule.ID_SEQ)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tournament_id")
    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    @Column(name = "phase_id")
    public String getTournamentPhaseId() {
        return tournamentPhaseId;
    }

    public void setTournamentPhaseId(String tournamentPhaseId) {
        this.tournamentPhaseId = tournamentPhaseId;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "schedule")
    public List<TournamentScheduledMatch> getMatches() {
        return matches;
    }

    public void setMatches(List<TournamentScheduledMatch> matches) {
        this.matches = matches;
    }
}
