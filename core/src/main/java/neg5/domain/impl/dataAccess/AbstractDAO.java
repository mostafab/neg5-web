package neg5.domain.impl.dataAccess;

import com.google.inject.Inject;
import com.google.inject.Provider;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import neg5.domain.impl.entities.AbstractDataObject;
import neg5.domain.impl.entities.IdDataObject;
import neg5.domain.impl.entities.SpecificTournamentEntity;

public abstract class AbstractDAO<
        EntityType extends AbstractDataObject<EntityType> & IdDataObject<IdType>,
        IdType extends Serializable> {

    private Class<EntityType> persistentClass;

    private static final String FIND_ALL_BY_TOURNAMENT_ID_QUERY =
            "SELECT ent from %s ent where ent.%s = :tournamentId";

    private static final String DEFAULT_TOURNAMENT_ATTRIBUTE_PATH = "tournament.id";

    private static final String TOURNAMENT_ID_PARAM = "tournamentId";

    @Inject private Provider<EntityManager> entityManagerProvider;

    AbstractDAO(Class<EntityType> persistentClass) {
        this.persistentClass = persistentClass;
    }

    public EntityType get(IdType id) {
        return getEntityManager().find(getPersistentClass(), id);
    }

    public EntityType save(EntityType entity) {
        return getEntityManager().merge(entity);
    }

    public void delete(IdType id) {
        getEntityManager().remove(get(id));
    }

    public void flush() {
        getEntityManager().flush();
    }

    public List<EntityType> findAllByTournamentId(String tournamentId) {
        validateFindByTournamentId();
        String query =
                String.format(
                        FIND_ALL_BY_TOURNAMENT_ID_QUERY,
                        persistentClass.getSimpleName(),
                        getTournamentIdAttributePath());
        return getEntityManager()
                .createQuery(query, persistentClass)
                .setParameter(TOURNAMENT_ID_PARAM, tournamentId)
                .getResultList();
    }

    public Class<EntityType> getPersistentClass() {
        return persistentClass;
    }

    protected EntityManager getEntityManager() {
        return entityManagerProvider.get();
    }

    protected String getTournamentIdAttributePath() {
        return DEFAULT_TOURNAMENT_ATTRIBUTE_PATH;
    }

    private void validateFindByTournamentId() {
        if (!SpecificTournamentEntity.class.isAssignableFrom(persistentClass)) {
            throw new IllegalArgumentException(
                    "Class "
                            + persistentClass.getName()
                            + " does not implement "
                            + SpecificTournamentEntity.class);
        }
    }
}
