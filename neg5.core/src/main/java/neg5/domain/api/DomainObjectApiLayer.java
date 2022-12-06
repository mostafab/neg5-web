package neg5.domain.api;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import javax.annotation.Nonnull;

public interface DomainObjectApiLayer<ApiObject, IdType extends Serializable> {

    ApiObject get(@Nonnull IdType id);

    List<ApiObject> get(@Nonnull Set<IdType> ids);

    ApiObject create(@Nonnull ApiObject dto);

    ApiObject update(@Nonnull ApiObject dto);

    void delete(@Nonnull IdType id);

    void delete(@Nonnull ApiObject apiObject);

    List<ApiObject> findAllByTournamentId(@Nonnull String tournamentId);
}
