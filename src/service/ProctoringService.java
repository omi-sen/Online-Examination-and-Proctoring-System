package service;

import model.ActivityLog;
import storage.ActivityLogFileHandler;
import util.Constants;
import util.DateTimeUtil;

public class ProctoringService {
    private final ActivityLogFileHandler activityLogFileHandler;
    private final DateTimeUtil dateTimeUtil;

    public ProctoringService() {
        this.activityLogFileHandler = new ActivityLogFileHandler();
        this.dateTimeUtil = new DateTimeUtil();
    }

    public void logSuspiciousActivity(String studentUsername, int violationCount, String eventType) {
        String remarks = "Violation count: " + violationCount;
        ActivityLog activityLog = new ActivityLog(studentUsername, eventType, dateTimeUtil.getCurrentTimestamp(), remarks);
        activityLogFileHandler.appendLog(Constants.ACTIVITY_LOG_FILE, activityLog);
    }

    public boolean shouldAutoSubmit(int suspiciousCount) {
        return suspiciousCount >= 3;
    }
}
