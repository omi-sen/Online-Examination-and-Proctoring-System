package service;

import model.Question;
import model.Result;
import storage.ResultFileHandler;
import util.Constants;
import util.DateTimeUtil;

import java.util.List;
import java.util.Map;

public class ResultService {
    private final DateTimeUtil dateTimeUtil;
    private final ResultFileHandler resultFileHandler;

    public ResultService() {
        this.dateTimeUtil = new DateTimeUtil();
        this.resultFileHandler = new ResultFileHandler();
    }

    public Result calculateResult(String studentUsername, List<Question> questions, Map<Integer, String> answers, int flaggedCount) {
        int score = 0;
        StringBuilder summary = new StringBuilder();

        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            String selectedAnswer = answers.get(i);
            String correctAnswer = question.getCorrectOption();
            boolean isCorrect = correctAnswer != null && correctAnswer.equalsIgnoreCase(selectedAnswer);

            if (isCorrect) {
                score++;
            }

            summary.append("Question ").append(i + 1).append(": ")
                    .append(isCorrect ? "Correct" : "Wrong")
                    .append(System.lineSeparator())
                    .append("Your answer: ").append(formatAnswer(selectedAnswer))
                    .append(System.lineSeparator())
                    .append("Correct answer: ").append(formatAnswer(correctAnswer))
                    .append(System.lineSeparator())
                    .append(System.lineSeparator());
        }

        return new Result(
                studentUsername,
                score,
                questions.size(),
                dateTimeUtil.getCurrentTimestamp(),
                summary.toString(),
                flaggedCount
        );
    }

    public void saveResult(Result result) {
        resultFileHandler.saveResult(Constants.RESULTS_FILE, result);
    }

    public List<Result> getResultsByStudent(String studentUsername) {
        return resultFileHandler.loadResultsByStudent(Constants.RESULTS_FILE, studentUsername);
    }

    private String formatAnswer(String answer) {
        if (answer == null || answer.isEmpty()) {
            return "Not Answered";
        }
        return answer;
    }
}
