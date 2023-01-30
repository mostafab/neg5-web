package neg5.exports.qbj.api;

import java.util.List;

public class QbjRootDTO {

    private String version;
    private List<Object> objects;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<Object> getObjects() {
        return objects;
    }

    public void setObjects(List<Object> objects) {
        this.objects = objects;
    }
}
