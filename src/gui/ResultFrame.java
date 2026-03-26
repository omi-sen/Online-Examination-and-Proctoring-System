package gui;

import model.Result;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class ResultFrame extends JFrame {
    private JLabel studentLabel;
    private JLabel scoreLabel;
    private JLabel submittedAtLabel;
    private JLabel flaggedLabel;
    private JTextArea summaryArea;

    public ResultFrame(Result result) {
        setTitle("Exam Result");
        setSize(500, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents(result);
    }

    private void initComponents(Result result) {
        setLayout(new BorderLayout(10, 10));

        studentLabel = new JLabel("Student: " + result.getStudentUsername());
        scoreLabel = new JLabel("Score: " + result.getScore() + " / " + result.getTotalMarks());
        submittedAtLabel = new JLabel("Submitted At: " + result.getSubmittedAt());
        flaggedLabel = new JLabel("Flagged Questions Reviewed: " + result.getFlaggedCount());
        summaryArea = new JTextArea(result.getAnswerSummary());
        summaryArea.setEditable(false);
        summaryArea.setLineWrap(true);
        summaryArea.setWrapStyleWord(true);

        GridLayout topLayout = new GridLayout(4, 1, 10, 10);
        java.awt.Panel topPanel = new java.awt.Panel(topLayout);
        topPanel.add(studentLabel);
        topPanel.add(scoreLabel);
        topPanel.add(submittedAtLabel);
        topPanel.add(flaggedLabel);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(summaryArea), BorderLayout.CENTER);
    }
}
