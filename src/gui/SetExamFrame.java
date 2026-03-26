package gui;

import service.ExamService;
import util.Constants;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.GridLayout;

public class SetExamFrame extends JFrame {
    private JTextField examTitleField;
    private JTextField durationField;
    private JButton saveButton;
    private final ExamService examService;

    public SetExamFrame() {
        this.examService = new ExamService();
        setTitle("Set Exam");
        setSize(400, 180);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        registerEvents();
        loadDefaults();
    }

    private void initComponents() {
        setLayout(new GridLayout(3, 2, 10, 10));
        examTitleField = new JTextField();
        durationField = new JTextField();
        saveButton = new JButton("Save Settings");

        add(new JLabel("Exam Title:"));
        add(examTitleField);
        add(new JLabel("Duration (minutes):"));
        add(durationField);
        add(new JLabel());
        add(saveButton);
    }

    private void registerEvents() {
        saveButton.addActionListener(e -> saveSettings());
    }

    private void loadDefaults() {
        examTitleField.setText("Online Exam");
        durationField.setText(String.valueOf(Constants.DEFAULT_DURATION_MINUTES));
    }

    private void saveSettings() {
        String title = examTitleField.getText().trim();
        String durationText = durationField.getText().trim();

        if (title.isEmpty() || durationText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        try {
            int duration = Integer.parseInt(durationText);
            if (duration <= 0) {
                JOptionPane.showMessageDialog(this, "Duration must be greater than 0.");
                return;
            }

            examService.saveExamSettings(title, duration);
            JOptionPane.showMessageDialog(this, "Exam settings saved.");
            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Duration must be a valid number.");
        }
    }
}
