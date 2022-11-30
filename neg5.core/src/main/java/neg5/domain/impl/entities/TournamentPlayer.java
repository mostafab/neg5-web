package neg5.domain.impl.entities;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tournament_player")
@DynamicUpdate
public class TournamentPlayer extends AbstractDataObject<TournamentPlayer>
        implements SpecificTournamentEntity, IdDataObject<String>, Auditable {

    private String id;
    private String name;
    private TournamentTeam team;
    private Tournament tournament;

    private Instant addedAt;
    private String addedBy;

    @Id
    @Override
    @GeneratedValue(generator = "uuid_generator")
    @GenericGenerator(
            name = "uuid_generator",
            strategy = "neg5.domain.impl.entities.generators.UUIDGenerator")
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    public TournamentTeam getTeam() {
        return team;
    }

    public void setTeam(TournamentTeam team) {
        this.team = team;
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

    @Override
    @Column(name = "created_at")
    public Instant getAddedAt() {
        return addedAt;
    }

    @Override
    public void setAddedAt(Instant addedAt) {
        this.addedAt = addedAt;
    }

    @Override
    @Column(name = "added_by")
    public String getAddedBy() {
        return addedBy;
    }

    @Override
    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }
}
