package storage;

import model.ActivityLog;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class ActivityLogFileHandler {
    public void appendLog(String filePath, ActivityLog activityLog) {
        try {
            Path path = Path.of(filePath);
            if (!Files.exists(path)) {
                Files.createFile(path);
            }

            String line = activityLog.getStudentUsername() + " | "
                    + activityLog.getTimestamp() + " | "
                    + activityLog.getEventType() + " | "
                    + activityLog.getRemarks() + System.lineSeparator();

            Files.writeString(path, line, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException("Unable to write activity log.", e);
        }
    }
}
