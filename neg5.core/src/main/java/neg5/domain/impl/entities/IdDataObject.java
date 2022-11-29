package neg5.domain.impl.entities;

import java.io.Serializable;

public interface IdDataObject<IdType extends Serializable> {

    IdType getId();

    void setId(IdType id);
}
