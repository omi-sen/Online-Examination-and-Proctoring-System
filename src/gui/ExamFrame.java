package gui;

import model.Exam;
import model.Question;
import model.Result;
import service.ExamService;
import service.ProctoringService;
import service.ResultService;
import util.TimerThread;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExamFrame extends JFrame {
    private JLabel timerLabel;
    private JLabel questionNumberLabel;
    private JLabel violationLabel;
    private JLabel flaggedLabel;
    private JTextArea questionArea;
    private JRadioButton optionARadio;
    private JRadioButton optionBRadio;
    private JRadioButton optionCRadio;
    private JRadioButton optionDRadio;
    private ButtonGroup buttonGroup;
    private JButton previousButton;
    private JButton nextButton;
    private JButton submitButton;
    private JButton flagButton;

    private final String studentUsername;
    private final ExamService examService;
    private final ResultService resultService;
    private final ProctoringService proctoringService;
    private TimerThread timerThread;
    private List<Question> questions;
    private final Map<Integer, String> answers;
    private final Map<Integer, Boolean> flaggedQuestions;
    private int currentIndex;
    private int violationCount;
    private boolean submitting;
    private boolean proctoringArmed;
    private boolean ignoreFocusLoss;

    public ExamFrame(String studentUsername) {
        this.studentUsername = studentUsername;
        this.examService = new ExamService();
        this.resultService = new ResultService();
        this.proctoringService = new ProctoringService();
        this.answers = new HashMap<>();
        this.flaggedQuestions = new HashMap<>();
        this.currentIndex = 0;
        this.violationCount = 0;
        this.submitting = false;
        this.proctoringArmed = false;
        this.ignoreFocusLoss = false;

        setTitle("Exam Window");
        setSize(700, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        loadExam();
        registerEvents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        timerLabel = new JLabel("Time Left: 00:00");
        questionNumberLabel = new JLabel("Question 0");
        violationLabel = new JLabel("Violations: 0");
        flaggedLabel = new JLabel("Flagged: 0");
        questionArea = new JTextArea();
        questionArea.setEditable(false);
        questionArea.setLineWrap(true);
        questionArea.setWrapStyleWord(true);

        optionARadio = new JRadioButton();
        optionBRadio = new JRadioButton();
        optionCRadio = new JRadioButton();
        optionDRadio = new JRadioButton();
        buttonGroup = new ButtonGroup();
        buttonGroup.add(optionARadio);
        buttonGroup.add(optionBRadio);
        buttonGroup.add(optionCRadio);
        buttonGroup.add(optionDRadio);

        previousButton = new JButton("Previous");
        nextButton = new JButton("Next");
        submitButton = new JButton("Submit");
        flagButton = new JButton("Flag for Review");

        JPanel topPanel = new JPanel(new GridLayout(2, 2));
        topPanel.add(questionNumberLabel);
        topPanel.add(timerLabel);
        topPanel.add(violationLabel);
        topPanel.add(flaggedLabel);

        JPanel optionsPanel = new JPanel(new GridLayout(4, 1));
        optionsPanel.add(optionARadio);
        optionsPanel.add(optionBRadio);
        optionsPanel.add(optionCRadio);
        optionsPanel.add(optionDRadio);

        JPanel actionPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        actionPanel.add(previousButton);
        actionPanel.add(flagButton);
        actionPanel.add(nextButton);
        actionPanel.add(submitButton);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(questionArea), BorderLayout.CENTER);
        add(optionsPanel, BorderLayout.WEST);
        add(actionPanel, BorderLayout.SOUTH);
    }

    private void loadExam() {
        Exam exam = examService.loadExam();
        questions = exam.getQuestions();

        if (questions.isEmpty()) {
            showMessage("No questions available. Please ask admin to add questions first.");
            submitButton.setEnabled(false);
            nextButton.setEnabled(false);
            previousButton.setEnabled(false);
            flagButton.setEnabled(false);
            return;
        }

        displayQuestion();
        startTimer(exam.getDurationMinutes());
    }

    private void registerEvents() {
        previousButton.addActionListener(e -> goToPreviousQuestion());
        nextButton.addActionListener(e -> goToNextQuestion());
        submitButton.addActionListener(e -> submitExam(false));
        flagButton.addActionListener(e -> toggleFlag());
        addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                proctoringArmed = true;
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                if (proctoringArmed && !ignoreFocusLoss) {
                    handleViolation("Window focus lost");
                }
            }
        });
    }

    private void displayQuestion() {
        Question question = questions.get(currentIndex);
        questionNumberLabel.setText("Question " + (currentIndex + 1) + " of " + questions.size());
        questionArea.setText(question.getQuestionText());
        optionARadio.setText("A. " + question.getOptionA());
        optionBRadio.setText("B. " + question.getOptionB());
        optionCRadio.setText("C. " + question.getOptionC());
        optionDRadio.setText("D. " + question.getOptionD());
        loadSavedAnswer();
        updateFlagButton();
        updateFlaggedLabel();
        updateNavigationButtons();

        if (currentIndex == questions.size() - 1) {
            nextButton.setText("Finish");
        } else {
            nextButton.setText("Next");
        }
    }

    private void loadSavedAnswer() {
        buttonGroup.clearSelection();
        String savedAnswer = answers.get(currentIndex);

        if ("A".equals(savedAnswer)) {
            optionARadio.setSelected(true);
        } else if ("B".equals(savedAnswer)) {
            optionBRadio.setSelected(true);
        } else if ("C".equals(savedAnswer)) {
            optionCRadio.setSelected(true);
        } else if ("D".equals(savedAnswer)) {
            optionDRadio.setSelected(true);
        }
    }

    private void saveCurrentAnswer() {
        if (optionARadio.isSelected()) {
            answers.put(currentIndex, "A");
        } else if (optionBRadio.isSelected()) {
            answers.put(currentIndex, "B");
        } else if (optionCRadio.isSelected()) {
            answers.put(currentIndex, "C");
        } else if (optionDRadio.isSelected()) {
            answers.put(currentIndex, "D");
        }
    }

    private void goToPreviousQuestion() {
        saveCurrentAnswer();
        if (currentIndex > 0) {
            currentIndex--;
            displayQuestion();
        }
    }

    private void goToNextQuestion() {
        saveCurrentAnswer();

        if (currentIndex < questions.size() - 1) {
            currentIndex++;
            displayQuestion();
        } else {
            submitExam(false);
        }
    }

    private void updateNavigationButtons() {
        previousButton.setEnabled(currentIndex > 0);
    }

    private void toggleFlag() {
        boolean flagged = flaggedQuestions.getOrDefault(currentIndex, false);
        flaggedQuestions.put(currentIndex, !flagged);
        updateFlagButton();
        updateFlaggedLabel();
    }

    private void updateFlagButton() {
        if (flaggedQuestions.getOrDefault(currentIndex, false)) {
            flagButton.setText("Remove Flag");
        } else {
            flagButton.setText("Flag for Review");
        }
    }

    private void updateFlaggedLabel() {
        flaggedLabel.setText("Flagged: " + getFlaggedCount());
    }

    private int getFlaggedCount() {
        int count = 0;
        for (boolean flagged : flaggedQuestions.values()) {
            if (flagged) {
                count++;
            }
        }
        return count;
    }

    private int getAnsweredCount() {
        int count = 0;
        for (String answer : answers.values()) {
            if (answer != null && !answer.isEmpty()) {
                count++;
            }
        }
        return count;
    }

    private boolean confirmSubmission(boolean timeUp) {
        if (timeUp) {
            return true;
        }

        int answered = getAnsweredCount();
        int unanswered = questions.size() - answered;
        int flagged = getFlaggedCount();

        String message = "Review Summary\n\n"
                + "Answered: " + answered + "\n"
                + "Unanswered: " + unanswered + "\n"
                + "Flagged: " + flagged + "\n\n"
                + "Do you want to submit now?";

        return showConfirm(message, "Confirm Submit");
    }

    private void startTimer(int durationMinutes) {
        updateTimerLabel(durationMinutes * 60);
        timerThread = new TimerThread(durationMinutes, new TimerThread.TimerListener() {
            @Override
            public void onTick(int remainingSeconds) {
                updateTimerLabel(remainingSeconds);
            }

            @Override
            public void onTimeUp() {
                submitExam(true);
            }
        });
        timerThread.start();
    }

    private void updateTimerLabel(int remainingSeconds) {
        int minutes = remainingSeconds / 60;
        int seconds = remainingSeconds % 60;
        timerLabel.setText(String.format("Time Left: %02d:%02d", minutes, seconds));
    }

    private void handleViolation(String eventType) {
        if (submitting || questions == null || questions.isEmpty()) {
            return;
        }

        violationCount++;
        violationLabel.setText("Violations: " + violationCount);
        proctoringService.logSuspiciousActivity(studentUsername, violationCount, eventType);

        showWarning("Warning: You switched away from the exam window.\nViolation count: " + violationCount, "Proctoring Warning");

        if (proctoringService.shouldAutoSubmit(violationCount)) {
            showMessage("Maximum violations reached. Exam will be submitted.");
            submitExam(false);
        }
    }

    private void submitExam(boolean timeUp) {
        if (submitting) {
            return;
        }

        saveCurrentAnswer();
        if (!confirmSubmission(timeUp)) {
            return;
        }

        submitting = true;

        if (timerThread != null) {
            timerThread.stopTimer();
        }

        Result result = resultService.calculateResult(studentUsername, questions, answers, getFlaggedCount());
        resultService.saveResult(result);

        if (timeUp) {
            showMessage("Time is over. Exam submitted automatically.");
        }

        new ResultFrame(result).setVisible(true);
        dispose();
    }

    private void showMessage(String message) {
        ignoreFocusLoss = true;
        JOptionPane.showMessageDialog(this, message);
        SwingUtilities.invokeLater(() -> ignoreFocusLoss = false);
    }

    private void showWarning(String message, String title) {
        ignoreFocusLoss = true;
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.WARNING_MESSAGE);
        SwingUtilities.invokeLater(() -> ignoreFocusLoss = false);
    }

    private boolean showConfirm(String message, String title) {
        ignoreFocusLoss = true;
        int choice = JOptionPane.showConfirmDialog(this, message, title, JOptionPane.YES_NO_OPTION);
        SwingUtilities.invokeLater(() -> ignoreFocusLoss = false);
        return choice == JOptionPane.YES_OPTION;
    }
}
