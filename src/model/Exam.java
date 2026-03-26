package model;

import java.util.ArrayList;
import java.util.List;

public class Exam {
    private String title;
    private int durationMinutes;
    private List<Question> questions;

    public Exam() {
        this.questions = new ArrayList<>();
    }

    public Exam(String title, int durationMinutes, List<Question> questions) {
        this.title = title;
        this.durationMinutes = durationMinutes;
        this.questions = questions;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
