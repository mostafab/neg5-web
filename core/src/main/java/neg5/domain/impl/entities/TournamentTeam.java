package neg5.domain.impl.entities;

import java.time.Instant;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tournament_team")
@DynamicUpdate
public class TournamentTeam extends AbstractDataObject<TournamentTeam>
        implements SpecificTournamentEntity, IdDataObject<String>, Auditable {

    private String id;

    private String name;
    private Tournament tournament;

    private Set<TournamentPool> divisions;
    private Set<TournamentPlayer> players;

    private String addedBy;
    private Instant addedAt;

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
    @JoinColumn(name = "tournament_id")
    @Override
    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tournament_team_in_division",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "division_id"))
    public Set<TournamentPool> getDivisions() {
        return divisions;
    }

    public void setDivisions(Set<TournamentPool> divisions) {
        this.divisions = divisions;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "team")
    public Set<TournamentPlayer> getPlayers() {
        return players;
    }

    public void setPlayers(Set<TournamentPlayer> players) {
        this.players = players;
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

    @Override
    @Column(name = "created_at")
    public Instant getAddedAt() {
        return addedAt;
    }

    @Override
    public void setAddedAt(Instant addedAt) {
        this.addedAt = addedAt;
    }
}
