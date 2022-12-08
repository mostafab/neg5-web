package neg5.domain.impl.entities;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import neg5.domain.api.enums.TossupAnswerType;
import neg5.domain.impl.entities.compositeIds.TournamentTossupValueId;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "tournament_tossup_values")
@DynamicUpdate
public class TournamentTossupValue extends AbstractDataObject<TournamentTossupValue>
        implements SpecificTournamentEntity, CompositeIdObject<TournamentTossupValueId> {

    private TournamentTossupValueId id;
    private TossupAnswerType answerType;

    @EmbeddedId
    @Override
    public TournamentTossupValueId getId() {
        return id;
    }

    @Override
    public void setId(TournamentTossupValueId id) {
        this.id = id;
    }

    @Column(name = "tossup_answer_type")
    public TossupAnswerType getAnswerType() {
        return answerType;
    }

    public void setAnswerType(TossupAnswerType answerType) {
        this.answerType = answerType;
    }

    @Override
    @Transient
    public Tournament getTournament() {
        return id.getTournament();
    }
}
