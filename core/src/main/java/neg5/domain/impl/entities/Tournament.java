package neg5.domain.impl.entities;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "tournament")
@DynamicUpdate
public class Tournament extends AbstractDataObject<Tournament>
        implements IdDataObject<String>, Auditable {

    private String id;

    private Account director;

    private String name;
    private LocalDate tournamentDate;
    private String location;
    private String questionSet;
    private String comments;

    private Set<TournamentPhase> phases;
    private Set<TournamentPool> divisions;
    private Set<TournamentTossupValue> tossupValues;

    private TournamentPhase currentPhase;

    private Boolean usesBouncebacks;
    private Boolean allowTies;
    private Long bonusPointValue;
    private Long partsPerBonus;

    private Integer maxActivePlayersPerTeam;

    private Instant addedAt;
    private String addedBy;
    private Boolean hidden;

    @Id
    @Override
    @GeneratedValue(generator = "tournament_id_generator")
    @GenericGenerator(
            name = "tournament_id_generator",
            strategy = "neg5.domain.impl.entities.generators.TournamentIdGenerator")
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @JoinColumn(name = "director_id", nullable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    public Account getDirector() {
        return director;
    }

    public void setDirector(Account director) {
        this.director = director;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "tournament_date")
    public LocalDate getTournamentDate() {
        return tournamentDate;
    }

    public void setTournamentDate(LocalDate tournamentDate) {
        this.tournamentDate = tournamentDate;
    }

    @Column(name = "location")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Column(name = "question_set")
    public String getQuestionSet() {
        return questionSet;
    }

    public void setQuestionSet(String questionSet) {
        this.questionSet = questionSet;
    }

    @Column(name = "comments")
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tournament")
    public Set<TournamentPhase> getPhases() {
        return phases;
    }

    public void setPhases(Set<TournamentPhase> phases) {
        this.phases = phases;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tournament")
    public Set<TournamentPool> getDivisions() {
        return divisions;
    }

    public void setDivisions(Set<TournamentPool> divisions) {
        this.divisions = divisions;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "id.tournament")
    public Set<TournamentTossupValue> getTossupValues() {
        return tossupValues;
    }

    public void setTossupValues(Set<TournamentTossupValue> tossupValues) {
        this.tossupValues = tossupValues;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "active_phase_id")
    @NotFound(action = NotFoundAction.IGNORE)
    public TournamentPhase getCurrentPhase() {
        return currentPhase;
    }

    public void setCurrentPhase(TournamentPhase currentPhase) {
        this.currentPhase = currentPhase;
    }

    @Column(name = "bouncebacks")
    public Boolean getUsesBouncebacks() {
        return usesBouncebacks;
    }

    public void setUsesBouncebacks(Boolean usesBouncebacks) {
        this.usesBouncebacks = usesBouncebacks;
    }

    @Column(name = "allow_ties")
    public Boolean getAllowTies() {
        return allowTies;
    }

    public void setAllowTies(Boolean allowTies) {
        this.allowTies = allowTies;
    }

    @Column(name = "bonus_point_value")
    public Long getBonusPointValue() {
        return bonusPointValue;
    }

    public void setBonusPointValue(Long bonusPointValue) {
        this.bonusPointValue = bonusPointValue;
    }

    @Column(name = "parts_per_bonus")
    public Long getPartsPerBonus() {
        return partsPerBonus;
    }

    public void setPartsPerBonus(Long partsPerBonus) {
        this.partsPerBonus = partsPerBonus;
    }

    @Column(name = "max_active_players_per_team")
    public Integer getMaxActivePlayersPerTeam() {
        return maxActivePlayersPerTeam;
    }

    public void setMaxActivePlayersPerTeam(Integer maxActivePlayersPerTeam) {
        this.maxActivePlayersPerTeam = maxActivePlayersPerTeam;
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

    @Column(name = "hidden")
    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }
}
