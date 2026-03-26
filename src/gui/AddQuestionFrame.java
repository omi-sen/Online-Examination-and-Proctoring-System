package gui;

import model.Question;
import service.QuestionService;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.GridLayout;

public class AddQuestionFrame extends JFrame {
    private JTextArea questionArea;
    private JTextField optionAField;
    private JTextField optionBField;
    private JTextField optionCField;
    private JTextField optionDField;
    private JTextField correctOptionField;
    private JButton saveButton;
    private final QuestionService questionService;

    public AddQuestionFrame() {
        this.questionService = new QuestionService();
        setTitle("Add Question");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        registerEvents();
    }

    private void initComponents() {
        setLayout(new GridLayout(7, 2, 10, 10));

        questionArea = new JTextArea(3, 20);
        optionAField = new JTextField();
        optionBField = new JTextField();
        optionCField = new JTextField();
        optionDField = new JTextField();
        correctOptionField = new JTextField();
        saveButton = new JButton("Save Question");

        add(new JLabel("Question:"));
        add(new JScrollPane(questionArea));
        add(new JLabel("Option A:"));
        add(optionAField);
        add(new JLabel("Option B:"));
        add(optionBField);
        add(new JLabel("Option C:"));
        add(optionCField);
        add(new JLabel("Option D:"));
        add(optionDField);
        add(new JLabel("Correct Option (A/B/C/D):"));
        add(correctOptionField);
        add(new JLabel());
        add(saveButton);
    }

    private void registerEvents() {
        saveButton.addActionListener(e -> saveQuestion());
    }

    private void saveQuestion() {
        String questionText = questionArea.getText().trim();
        String optionA = optionAField.getText().trim();
        String optionB = optionBField.getText().trim();
        String optionC = optionCField.getText().trim();
        String optionD = optionDField.getText().trim();
        String correctOption = correctOptionField.getText().trim().toUpperCase();

        if (questionText.isEmpty() || optionA.isEmpty() || optionB.isEmpty() || optionC.isEmpty()
                || optionD.isEmpty() || correctOption.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!("A".equals(correctOption) || "B".equals(correctOption)
                || "C".equals(correctOption) || "D".equals(correctOption))) {
            JOptionPane.showMessageDialog(this, "Correct option must be A, B, C, or D.", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Question question = new Question(0, questionText, optionA, optionB, optionC, optionD, correctOption);
        questionService.addQuestion(question);
        JOptionPane.showMessageDialog(this, "Question saved successfully.");
        clearForm();
    }

    private void clearForm() {
        questionArea.setText("");
        optionAField.setText("");
        optionBField.setText("");
        optionCField.setText("");
        optionDField.setText("");
        correctOptionField.setText("");
    }
}
