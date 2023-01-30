package neg5.exports.qbj.api;

public class QbjMatchDTO {

    private String id;
    private final QbjObjectType type = QbjObjectType.MATCH;

    private Integer tossupsRead;
    private Integer overtimeTossupsRead;
    private String location;
    private Boolean tiebreaker;
    private String moderator;
    private String serial;
    private String notes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public QbjObjectType getType() {
        return type;
    }

    public Integer getTossupsRead() {
        return tossupsRead;
    }

    public void setTossupsRead(Integer tossupsRead) {
        this.tossupsRead = tossupsRead;
    }

    public Integer getOvertimeTossupsRead() {
        return overtimeTossupsRead;
    }

    public void setOvertimeTossupsRead(Integer overtimeTossupsRead) {
        this.overtimeTossupsRead = overtimeTossupsRead;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getTiebreaker() {
        return tiebreaker;
    }

    public void setTiebreaker(Boolean tiebreaker) {
        this.tiebreaker = tiebreaker;
    }

    public String getModerator() {
        return moderator;
    }

    public void setModerator(String moderator) {
        this.moderator = moderator;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
