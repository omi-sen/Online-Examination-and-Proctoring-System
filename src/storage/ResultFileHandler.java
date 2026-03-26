package storage;

import model.Result;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class ResultFileHandler {
    public void saveResult(String filePath, Result result) {
        try {
            Path path = Path.of(filePath);
            if (!Files.exists(path)) {
                Files.createFile(path);
            }

            String summary = result.getAnswerSummary().replace(System.lineSeparator(), " <br> ");
            String line = result.getStudentUsername() + "|"
                    + result.getScore() + "|"
                    + result.getTotalMarks() + "|"
                    + result.getSubmittedAt() + "|"
                    + result.getFlaggedCount() + "|"
                    + summary
                    + System.lineSeparator();

            Files.writeString(path, line, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException("Unable to save result.", e);
        }
    }

    public List<Result> loadResultsByStudent(String filePath, String studentUsername) {
        List<Result> results = new ArrayList<>();

        try {
            Path path = Path.of(filePath);
            if (!Files.exists(path)) {
                return results;
            }

            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                if (!line.trim().isEmpty()) {
                    Result result = parseResult(line);
                    if (result.getStudentUsername().equalsIgnoreCase(studentUsername)) {
                        results.add(result);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to load results.", e);
        }

        return results;
    }

    private Result parseResult(String line) {
        String[] parts = line.split("\\|", 6);
        int flaggedCount = parts.length > 5 ? Integer.parseInt(parts[4]) : 0;
        String summary = parts.length > 5 ? parts[5] : (parts.length > 4 ? parts[4] : "");
        summary = summary.replace(" <br> ", System.lineSeparator());

        return new Result(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), parts[3], summary, flaggedCount);
    }
}
