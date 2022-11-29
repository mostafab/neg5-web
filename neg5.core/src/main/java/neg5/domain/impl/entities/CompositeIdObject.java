package neg5.domain.impl.entities;

import java.io.Serializable;

public interface CompositeIdObject<IdType extends Serializable & CompositeId> extends IdDataObject<IdType> {

}
