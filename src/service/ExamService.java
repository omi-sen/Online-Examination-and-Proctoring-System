package service;

import model.Exam;
import model.Question;
import storage.ExamSettingsFileHandler;
import util.Constants;

import java.util.List;

public class ExamService {
    private final QuestionService questionService;
    private final ExamSettingsFileHandler examSettingsFileHandler;

    public ExamService() {
        this.questionService = new QuestionService();
        this.examSettingsFileHandler = new ExamSettingsFileHandler();
    }

    public Exam loadExam() {
        Exam exam = examSettingsFileHandler.loadSettings(Constants.EXAM_SETTINGS_FILE);
        List<Question> questions = questionService.getAllQuestions();
        exam.setQuestions(questions);
        return exam;
    }

    public void saveExamSettings(String title, int durationMinutes) {
        examSettingsFileHandler.saveSettings(Constants.EXAM_SETTINGS_FILE, title, durationMinutes);
    }
}
