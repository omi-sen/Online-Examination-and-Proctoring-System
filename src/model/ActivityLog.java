package model;

public class ActivityLog {
    private String studentUsername;
    private String eventType;
    private String timestamp;
    private String remarks;

    public ActivityLog() {
    }

    public ActivityLog(String studentUsername, String eventType, String timestamp, String remarks) {
        this.studentUsername = studentUsername;
        this.eventType = eventType;
        this.timestamp = timestamp;
        this.remarks = remarks;
    }

    public String getStudentUsername() {
        return studentUsername;
    }

    public void setStudentUsername(String studentUsername) {
        this.studentUsername = studentUsername;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
