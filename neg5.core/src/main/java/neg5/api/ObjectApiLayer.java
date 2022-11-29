package neg5.api;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public interface ObjectApiLayer<ApiObject, IdType extends Serializable> {

    ApiObject get(IdType id);

    List<ApiObject> get(Set<IdType> ids);

    ApiObject create(ApiObject dto);

    ApiObject update(ApiObject dto);

    void delete(IdType id);

    void delete(ApiObject collaborator);

    List<ApiObject> findAllByTournamentId(String tournamentId);
}
