package neg5.domain.impl.entities;

import java.time.Instant;

public interface Auditable {

    String getAddedBy();

    void setAddedBy(String addedBy);

    Instant getAddedAt();

    void setAddedAt(Instant addedAt);
}
