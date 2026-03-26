package storage;

import model.Question;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class QuestionFileHandler {
    public void saveQuestion(String filePath, Question question) {
        try {
            Path path = Path.of(filePath);
            if (!Files.exists(path)) {
                Files.createFile(path);
            }

            String line = formatQuestion(question) + System.lineSeparator();
            Files.writeString(path, line, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException("Unable to save question.", e);
        }
    }

    public List<Question> loadQuestions(String filePath) {
        List<Question> questions = new ArrayList<>();

        try {
            Path path = Path.of(filePath);
            if (!Files.exists(path)) {
                return questions;
            }

            List<String> lines = Files.readAllLines(path);
            int id = 1;
            for (String line : lines) {
                if (!line.trim().isEmpty()) {
                    Question question = parseQuestion(line);
                    question.setId(id++);
                    questions.add(question);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to load questions.", e);
        }

        return questions;
    }

    public void saveAllQuestions(String filePath, List<Question> questions) {
        List<String> lines = new ArrayList<>();
        for (Question question : questions) {
            lines.add(formatQuestion(question));
        }

        try {
            Files.write(Path.of(filePath), lines);
        } catch (IOException e) {
            throw new RuntimeException("Unable to update questions.", e);
        }
    }

    private String formatQuestion(Question question) {
        return sanitize(question.getQuestionText()) + "|"
                + sanitize(question.getOptionA()) + "|"
                + sanitize(question.getOptionB()) + "|"
                + sanitize(question.getOptionC()) + "|"
                + sanitize(question.getOptionD()) + "|"
                + sanitize(question.getCorrectOption());
    }

    private Question parseQuestion(String line) {
        String[] parts = line.split("\\|", -1);
        return new Question(0, parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
    }

    private String sanitize(String value) {
        return value.replace("|", "/").replace("\n", " ").trim();
    }
}
