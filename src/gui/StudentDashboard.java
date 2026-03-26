package gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.GridLayout;

public class StudentDashboard extends JFrame {
    private JButton startExamButton;
    private JButton viewResultButton;
    private final String studentUsername;

    public StudentDashboard(String studentUsername) {
        this.studentUsername = studentUsername;
        setTitle("Student Dashboard");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        registerEvents();
    }

    private void initComponents() {
        setLayout(new GridLayout(2, 1, 10, 10));
        startExamButton = new JButton("Start Exam");
        viewResultButton = new JButton("View Result");

        add(startExamButton);
        add(viewResultButton);
    }

    private void registerEvents() {
        startExamButton.addActionListener(e -> new ExamFrame(studentUsername).setVisible(true));
        viewResultButton.addActionListener(e -> new ViewResultsFrame(studentUsername).setVisible(true));
    }
}
