package storage;

import model.Exam;
import util.Constants;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ExamSettingsFileHandler {
    public Exam loadSettings(String filePath) {
        Exam exam = new Exam();
        exam.setTitle("Online Exam");
        exam.setDurationMinutes(Constants.DEFAULT_DURATION_MINUTES);

        try {
            Path path = Path.of(filePath);
            if (!Files.exists(path)) {
                return exam;
            }

            List<String> lines = Files.readAllLines(path);
            if (!lines.isEmpty()) {
                exam.setTitle(lines.get(0).trim().isEmpty() ? "Online Exam" : lines.get(0).trim());
            }
            if (lines.size() > 1) {
                try {
                    exam.setDurationMinutes(Integer.parseInt(lines.get(1).trim()));
                } catch (NumberFormatException e) {
                    exam.setDurationMinutes(Constants.DEFAULT_DURATION_MINUTES);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to load exam settings.", e);
        }

        return exam;
    }

    public void saveSettings(String filePath, String title, int durationMinutes) {
        try {
            Files.write(Path.of(filePath), List.of(title, String.valueOf(durationMinutes)));
        } catch (IOException e) {
            throw new RuntimeException("Unable to save exam settings.", e);
        }
    }
}
