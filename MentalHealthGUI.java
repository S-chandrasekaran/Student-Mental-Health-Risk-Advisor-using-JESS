import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import jess.*;

public class MentalHealthGUI extends JFrame {
    private JTextField nameField;
    private JComboBox<String> attendanceBox, gradesBox, moodBox, sleepBox, socialBox;
    private JTextArea resultArea;

    public MentalHealthGUI() {
        setTitle("Student Mental Health Risk Advisor");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 2));

        nameField = new JTextField();
        attendanceBox = new JComboBox<>(new String[]{"low", "medium", "high"});
        gradesBox = new JComboBox<>(new String[]{"poor", "average", "good"});
        moodBox = new JComboBox<>(new String[]{"sad", "anxious", "happy"});
        sleepBox = new JComboBox<>(new String[]{"irregular", "regular"});
        socialBox = new JComboBox<>(new String[]{"withdrawn", "normal", "active"});
        resultArea = new JTextArea();
        resultArea.setEditable(false);

        add(new JLabel("Name:")); add(nameField);
        add(new JLabel("Attendance:")); add(attendanceBox);
        add(new JLabel("Grades:")); add(gradesBox);
        add(new JLabel("Mood:")); add(moodBox);
        add(new JLabel("Sleep Pattern:")); add(sleepBox);
        add(new JLabel("Social Interaction:")); add(socialBox);

        JButton assessButton = new JButton("Assess Risk");
        assessButton.addActionListener(e -> runJess());
        add(assessButton);
        add(new JScrollPane(resultArea));

        setVisible(true);
    }

    private void runJess() {
        try {
            Rete engine = new Rete();
            engine.batch("rules.clp");

            String fact = String.format(
                "(student (name %s) (attendance %s) (grades %s) (mood %s) (sleep %s) (social %s))",
                nameField.getText(),
                attendanceBox.getSelectedItem(),
                gradesBox.getSelectedItem(),
                moodBox.getSelectedItem(),
                sleepBox.getSelectedItem(),
                socialBox.getSelectedItem()
            );

            engine.addOutputRouter("t", new StringWriterRouter(resultArea));
            engine.assertString(fact);
            engine.run();

        } catch (JessException ex) {
            resultArea.setText("Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new MentalHealthGUI();
    }

    // Custom output router to display Jess output in JTextArea
    static class StringWriterRouter implements jess.OutputRouter {
        private JTextArea output;

        public StringWriterRouter(JTextArea output) {
            this.output = output;
        }

        public void push(char c) {
            output.append(String.valueOf(c));
        }

        public void push(String s) {
            output.append(s);
        }

        public void flush() {}
        public void close() {}
    }
}
