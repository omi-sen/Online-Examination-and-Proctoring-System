package service;

import model.Question;
import storage.QuestionFileHandler;
import util.Constants;

import java.util.List;

public class QuestionService {
    private final QuestionFileHandler questionFileHandler;

    public QuestionService() {
        this.questionFileHandler = new QuestionFileHandler();
    }

    public void addQuestion(Question question) {
        questionFileHandler.saveQuestion(Constants.QUESTIONS_FILE, question);
    }

    public List<Question> getAllQuestions() {
        return questionFileHandler.loadQuestions(Constants.QUESTIONS_FILE);
    }

    public void updateQuestion(int index, Question updatedQuestion) {
        List<Question> questions = getAllQuestions();
        questions.set(index, updatedQuestion);
        questionFileHandler.saveAllQuestions(Constants.QUESTIONS_FILE, questions);
    }
}
