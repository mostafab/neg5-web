package neg5.exports.qbj.api;

public class QbjReferenceDTO {

    private String $ref;

    public String get$Ref() {
        return $ref;
    }

    public void set$Ref(String $ref) {
        this.$ref = $ref;
    }

    public static QbjReferenceDTO fromRef(String ref) {
        QbjReferenceDTO reference = new QbjReferenceDTO();
        reference.set$Ref(ref);
        return reference;
    }
}
