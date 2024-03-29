package neg5.domain.impl.entities;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import neg5.domain.impl.entities.compositeIds.TournamentCollaboratorId;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "user_collaborates_on_tournament")
@DynamicUpdate
public class TournamentCollaborator extends AbstractDataObject<TournamentCollaborator>
        implements CompositeIdObject<TournamentCollaboratorId>, SpecificTournamentEntity {

    private TournamentCollaboratorId id;
    private Boolean isAdmin;

    @Override
    @EmbeddedId
    public TournamentCollaboratorId getId() {
        return id;
    }

    @Override
    public void setId(TournamentCollaboratorId id) {
        this.id = id;
    }

    @Column(name = "is_admin")
    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean admin) {
        isAdmin = admin;
    }

    @Override
    @Transient
    public Tournament getTournament() {
        return id.getTournament();
    }
}
