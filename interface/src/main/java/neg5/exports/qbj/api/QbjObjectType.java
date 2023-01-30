package neg5.exports.qbj.api;

import neg5.domain.api.enums.StringIdentifiable;

public enum QbjObjectType implements StringIdentifiable {
    TOURNAMENT("Tournament"),
    REGISTRATION("Registration"),
    MATCH("Match");

    private final String id;

    QbjObjectType(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }
}
