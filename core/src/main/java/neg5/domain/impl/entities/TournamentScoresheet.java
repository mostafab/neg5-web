package neg5.domain.impl.entities;

import java.io.Serializable;
import java.time.Instant;
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
import neg5.domain.api.enums.ScoresheetStatus;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "tournament_scoresheet")
@DynamicUpdate
@SequenceGenerator(
        name = TournamentScoresheet.ID_SEQ,
        sequenceName = TournamentScoresheet.ID_SEQ,
        allocationSize = 1)
public class TournamentScoresheet extends AbstractDataObject<TournamentScoresheet>
        implements SpecificTournamentEntity, IdDataObject<Long>, Auditable, Serializable {

    public static final String ID_SEQ = "tournament_scoresheet_id_seq";

    private Long id;
    private ScoresheetStatus status;
    private Tournament tournament;
    private String team1Id;
    private String team2Id;
    private Integer round;
    private String room;
    private String moderator;
    private String packet;
    private String notes;
    private Boolean isTiebreaker;
    private String phases;
    private String activePlayers;

    private Instant createdAt;
    private Instant lastUpdatedAt;
    private String addedBy;

    private List<TournamentScoresheetCycle> cycles;

    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TournamentScoresheet.ID_SEQ)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "status")
    public ScoresheetStatus getStatus() {
        return status;
    }

    public void setStatus(ScoresheetStatus status) {
        this.status = status;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tournament_id")
    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
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

    @Column(name = "room")
    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    @Column(name = "moderator")
    public String getModerator() {
        return moderator;
    }

    public void setModerator(String moderator) {
        this.moderator = moderator;
    }

    @Column(name = "packet")
    public String getPacket() {
        return packet;
    }

    public void setPacket(String packet) {
        this.packet = packet;
    }

    @Column(name = "notes")
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Column(name = "tiebreaker")
    public Boolean getTiebreaker() {
        return isTiebreaker;
    }

    public void setTiebreaker(Boolean tiebreaker) {
        isTiebreaker = tiebreaker;
    }

    @Column(name = "phases")
    public String getPhases() {
        return phases;
    }

    public void setPhases(String phases) {
        this.phases = phases;
    }

    @Column(name = "active_players")
    public String getActivePlayers() {
        return activePlayers;
    }

    public void setActivePlayers(String activePlayers) {
        this.activePlayers = activePlayers;
    }

    @Column(name = "created_at")
    public Instant getAddedAt() {
        return createdAt;
    }

    public void setAddedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @Column(name = "last_updated_at")
    public Instant getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(Instant lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "scoresheet")
    public List<TournamentScoresheetCycle> getCycles() {
        return cycles;
    }

    public void setCycles(List<TournamentScoresheetCycle> cycles) {
        this.cycles = cycles;
    }
}
