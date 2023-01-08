package neg5.domain.impl.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tournament_match")
@DynamicUpdate
public class TournamentMatch extends AbstractDataObject<TournamentMatch>
        implements SpecificTournamentEntity, IdDataObject<String>, Auditable, Serializable {

    private String id;
    private Tournament tournament;

    private Long round;
    private String room;
    private String moderator;

    private String packet;
    private Integer tossupsHeard;
    private Boolean isTiebreaker;

    private String notes;
    private String serialId;

    private Set<MatchTeam> teams;
    private Set<MatchPlayer> players;

    private Set<TournamentMatchPhase> phases;

    private String addedBy;
    private Instant addedAt;

    private TournamentScoresheet scoresheet;

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

    @Override
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tournament_id", nullable = false, updatable = false)
    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    @Column(name = "round")
    public Long getRound() {
        return round;
    }

    public void setRound(Long round) {
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

    @Column(name = "tossups_heard")
    public Integer getTossupsHeard() {
        return tossupsHeard;
    }

    public void setTossupsHeard(Integer tossupsHeard) {
        this.tossupsHeard = tossupsHeard;
    }

    @Column(name = "is_tiebreaker")
    public Boolean getIsTiebreaker() {
        return isTiebreaker;
    }

    public void setIsTiebreaker(Boolean isTiebreaker) {
        this.isTiebreaker = isTiebreaker;
    }

    @Column(name = "notes")
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Column(name = "serial_id")
    public String getSerialId() {
        return serialId;
    }

    public void setSerialId(String serialId) {
        this.serialId = serialId;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "id.match")
    public Set<MatchTeam> getTeams() {
        return teams;
    }

    public void setTeams(Set<MatchTeam> teams) {
        this.teams = teams;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "id.match")
    public Set<MatchPlayer> getPlayers() {
        return players;
    }

    public void setPlayers(Set<MatchPlayer> players) {
        this.players = players;
    }

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(
                name = "match_id",
                referencedColumnName = "id",
                updatable = false,
                insertable = false),
        @JoinColumn(
                name = "tournament_id",
                referencedColumnName = "tournament_id",
                updatable = false,
                insertable = false),
    })
    public Set<TournamentMatchPhase> getPhases() {
        return phases;
    }

    public void setPhases(Set<TournamentMatchPhase> phases) {
        this.phases = phases;
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
    @Column(name = "added_at")
    public Instant getAddedAt() {
        return addedAt;
    }

    @Override
    public void setAddedAt(Instant addedAt) {
        this.addedAt = addedAt;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scoresheet_id", nullable = true)
    public TournamentScoresheet getScoresheet() {
        return scoresheet;
    }

    public void setScoresheet(TournamentScoresheet scoresheet) {
        this.scoresheet = scoresheet;
    }
}
