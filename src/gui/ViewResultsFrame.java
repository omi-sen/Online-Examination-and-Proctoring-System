package gui;

import model.Result;
import service.ResultService;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

public class ViewResultsFrame extends JFrame {
    private final ResultService resultService;

    public ViewResultsFrame(String studentUsername) {
        this.resultService = new ResultService();
        setTitle("My Results");
        setSize(520, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents(studentUsername);
    }

    private void initComponents(String studentUsername) {
        List<Result> results = resultService.getResultsByStudent(studentUsername);
        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No results found for this student.");
            dispose();
            return;
        }

        Result latest = results.get(results.size() - 1);
        setLayout(new BorderLayout(10, 10));

        JLabel studentLabel = new JLabel("Student: " + latest.getStudentUsername());
        JLabel scoreLabel = new JLabel("Latest Score: " + latest.getScore() + " / " + latest.getTotalMarks());
        JLabel submittedLabel = new JLabel("Submitted At: " + latest.getSubmittedAt());
        JLabel flaggedLabel = new JLabel("Flagged Questions Reviewed: " + latest.getFlaggedCount());

        JTextArea summaryArea = new JTextArea(latest.getAnswerSummary());
        summaryArea.setEditable(false);
        summaryArea.setLineWrap(true);
        summaryArea.setWrapStyleWord(true);

        java.awt.Panel topPanel = new java.awt.Panel(new GridLayout(4, 1, 10, 10));
        topPanel.add(studentLabel);
        topPanel.add(scoreLabel);
        topPanel.add(submittedLabel);
        topPanel.add(flaggedLabel);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(summaryArea), BorderLayout.CENTER);
    }
}
