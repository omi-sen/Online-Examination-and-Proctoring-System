package gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.GridLayout;

public class AdminDashboard extends JFrame {
    private JButton addQuestionButton;
    private JButton manageQuestionButton;
    private JButton setExamButton;

    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        registerEvents();
    }

    private void initComponents() {
        setLayout(new GridLayout(3, 1, 10, 10));
        addQuestionButton = new JButton("Add Questions");
        manageQuestionButton = new JButton("Manage Questions");
        setExamButton = new JButton("Set Exam Duration");

        add(addQuestionButton);
        add(manageQuestionButton);
        add(setExamButton);
    }

    private void registerEvents() {
        addQuestionButton.addActionListener(e -> new AddQuestionFrame().setVisible(true));
        manageQuestionButton.addActionListener(e -> new ManageQuestionsFrame().setVisible(true));
        setExamButton.addActionListener(e -> new SetExamFrame().setVisible(true));
    }
}
