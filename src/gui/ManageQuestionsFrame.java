package gui;

import model.Question;
import service.QuestionService;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.util.List;

public class ManageQuestionsFrame extends JFrame {
    private JComboBox<String> questionSelector;
    private JTextArea questionArea;
    private JTextField optionAField;
    private JTextField optionBField;
    private JTextField optionCField;
    private JTextField optionDField;
    private JTextField correctOptionField;
    private JButton updateButton;
    private final QuestionService questionService;
    private List<Question> questions;

    public ManageQuestionsFrame() {
        this.questionService = new QuestionService();
        setTitle("Manage Questions");
        setSize(550, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        registerEvents();
        loadQuestions();
    }

    private void initComponents() {
        setLayout(new GridLayout(8, 2, 10, 10));

        questionSelector = new JComboBox<>();
        questionArea = new JTextArea(3, 20);
        optionAField = new JTextField();
        optionBField = new JTextField();
        optionCField = new JTextField();
        optionDField = new JTextField();
        correctOptionField = new JTextField();
        updateButton = new JButton("Update Question");

        add(new JLabel("Select Question:"));
        add(questionSelector);
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
        add(new JLabel("Correct Option:"));
        add(correctOptionField);
        add(new JLabel());
        add(updateButton);
    }

    private void registerEvents() {
        questionSelector.addActionListener(e -> showSelectedQuestion());
        updateButton.addActionListener(e -> updateQuestion());
    }

    private void loadQuestions() {
        questions = questionService.getAllQuestions();
        questionSelector.removeAllItems();

        for (int i = 0; i < questions.size(); i++) {
            questionSelector.addItem("Question " + (i + 1));
        }

        if (questions.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No saved questions found.");
            updateButton.setEnabled(false);
            return;
        }

        questionSelector.setSelectedIndex(0);
        showSelectedQuestion();
    }

    private void showSelectedQuestion() {
        int index = questionSelector.getSelectedIndex();
        if (index < 0 || index >= questions.size()) {
            return;
        }

        Question question = questions.get(index);
        questionArea.setText(question.getQuestionText());
        optionAField.setText(question.getOptionA());
        optionBField.setText(question.getOptionB());
        optionCField.setText(question.getOptionC());
        optionDField.setText(question.getOptionD());
        correctOptionField.setText(question.getCorrectOption());
    }

    private void updateQuestion() {
        int index = questionSelector.getSelectedIndex();
        if (index < 0) {
            return;
        }

        String questionText = questionArea.getText().trim();
        String optionA = optionAField.getText().trim();
        String optionB = optionBField.getText().trim();
        String optionC = optionCField.getText().trim();
        String optionD = optionDField.getText().trim();
        String correctOption = correctOptionField.getText().trim().toUpperCase();

        if (questionText.isEmpty() || optionA.isEmpty() || optionB.isEmpty() || optionC.isEmpty() || optionD.isEmpty() || correctOption.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        if (!("A".equals(correctOption) || "B".equals(correctOption) || "C".equals(correctOption) || "D".equals(correctOption))) {
            JOptionPane.showMessageDialog(this, "Correct option must be A, B, C, or D.");
            return;
        }

        Question updatedQuestion = new Question(index + 1, questionText, optionA, optionB, optionC, optionD, correctOption);
        questionService.updateQuestion(index, updatedQuestion);
        questions = questionService.getAllQuestions();
        JOptionPane.showMessageDialog(this, "Question updated successfully.");
    }
}
