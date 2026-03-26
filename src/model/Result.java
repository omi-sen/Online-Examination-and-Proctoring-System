package model;

public class Result {
    private String studentUsername;
    private int score;
    private int totalMarks;
    private String submittedAt;
    private String answerSummary;
    private int flaggedCount;

    public Result() {
    }

    public Result(String studentUsername, int score, int totalMarks, String submittedAt, String answerSummary, int flaggedCount) {
        this.studentUsername = studentUsername;
        this.score = score;
        this.totalMarks = totalMarks;
        this.submittedAt = submittedAt;
        this.answerSummary = answerSummary;
        this.flaggedCount = flaggedCount;
    }

    public String getStudentUsername() {
        return studentUsername;
    }

    public void setStudentUsername(String studentUsername) {
        this.studentUsername = studentUsername;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(int totalMarks) {
        this.totalMarks = totalMarks;
    }

    public String getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(String submittedAt) {
        this.submittedAt = submittedAt;
    }

    public String getAnswerSummary() {
        return answerSummary;
    }

    public void setAnswerSummary(String answerSummary) {
        this.answerSummary = answerSummary;
    }

    public int getFlaggedCount() {
        return flaggedCount;
    }

    public void setFlaggedCount(int flaggedCount) {
        this.flaggedCount = flaggedCount;
    }
}
