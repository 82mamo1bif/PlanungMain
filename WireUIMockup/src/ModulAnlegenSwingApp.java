
import javax.swing.*;



import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModulAnlegenSwingApp {
	
	private DefaultListModel<String> activityListModel;
    private JList<String> activityList;
    private Timer timer;
    private int elapsedSeconds;
    private JLabel timerLabel;
	
	  private JFrame frame;

	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(() -> {
	            new ModulAnlegenSwingApp().run();
	        });
	    }

	    private void run() {
	        createAndShowGui();
	    }

	    private void createAndShowGui() {
	        try {
	            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        frame = new JFrame("Modul anlegen");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setSize(400, 600);

	        JPanel panel = createFormPanel();
	        frame.add(panel);

	        frame.setVisible(true);
	    }

	    private JPanel createFormPanel() {
	        JPanel panel = new JPanel(new GridBagLayout());
	        GridBagConstraints constraints = new GridBagConstraints();
	        constraints.fill = GridBagConstraints.HORIZONTAL;
	        constraints.insets = new Insets(10, 10, 10, 10);


	        // Setzen Sie das Look and Feel auf Nimbus (oder ein anderes Look and Feel Ihrer Wahl)
	    	 // Erstellen des Frames
	        JFrame frame = new JFrame("Modul anlegen");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setSize(400, 600);

	        // Panel für das Formular
	        JPanel panel1 = new JPanel(new GridBagLayout());
	        GridBagConstraints constraints1 = new GridBagConstraints();
	        constraints1.fill = GridBagConstraints.HORIZONTAL;
	        constraints1.insets = new Insets(10, 10, 10, 10);

	        // Modulname
	        constraints1.gridx = 0;
	        constraints1.gridy = 0;
	        panel1.add(new JLabel("Name:"), constraints1);

	        constraints1.gridx = 1;
	        constraints1.gridy = 0;
	        JTextField moduleNameField = new JTextField("Programmieren 2", 10);
	        panel1.add(moduleNameField, constraints1);

	        // ECTS
	        constraints1.gridx = 0;
	        constraints1.gridy = 1;
	        panel1.add(new JLabel("ECTS:"), constraints1);

	        constraints1.gridx = 1;
	        constraints1.gridy = 1;
	        SpinnerNumberModel ectsModel = new SpinnerNumberModel(7, 0, 30, 1);
	        JSpinner ectsSpinner = new JSpinner(ectsModel);
	        panel1.add(ectsSpinner, constraints1);

	        // SWS
	        constraints1.gridx = 0;
	        constraints1.gridy = 2;
	        panel1.add(new JLabel("SWS:"), constraints1);

	        constraints1.gridx = 1;
	        constraints1.gridy = 2;
	        SpinnerNumberModel swsModel = new SpinnerNumberModel(8, 0, 30, 1);
	        JSpinner swsSpinner = new JSpinner(swsModel);
	        panel1.add(swsSpinner, constraints1);

	        // Priorität
	        constraints1.gridx = 0;
	        constraints1.gridy = 3;
	        panel1.add(new JLabel("Priorität:"), constraints1);

	        constraints1.gridx = 1;
	        constraints1.gridy = 3;
	        String[] priorities = { "None", "!", "!!", "!!!" };
	        JComboBox<String> priorityComboBox = new JComboBox<>(priorities);
	        panel1.add(priorityComboBox, constraints1);

	        // Versuche (Buttons)
	        constraints1.gridx = 0;
	        constraints1.gridy = 4;
	        constraints1.gridwidth = 2;
	        JPanel attemptsPanel = new JPanel(new FlowLayout());
	        JButton firstAttemptButton = new JButton("1. Versuch");
	        JButton secondAttemptButton = new JButton("2. Versuch");
	        JButton thirdAttemptButton = new JButton("3. Versuch");
	        attemptsPanel.add(firstAttemptButton);
	        attemptsPanel.add(secondAttemptButton);
	        attemptsPanel.add(thirdAttemptButton);
	        panel1.add(attemptsPanel, constraints1);

	        // Schieberegler für Wochen
	        constraints1.gridx = 0;
	        constraints1.gridy = 5;
	        constraints1.gridwidth = 2;
	        JLabel sliderLabel = new JLabel("0 / Woche");
	        panel1.add(sliderLabel, constraints1);

	        constraints1.gridx = 0;
	        constraints1.gridy = 6;
	        constraints1.gridwidth = 2;
	        JSlider weeksSlider = new JSlider(0, 14);
	        weeksSlider.addChangeListener(e -> sliderLabel.setText(weeksSlider.getValue() + " / Woche"));
	        panel1.add(weeksSlider, constraints1);

	     // Abschluss-Buttons
	        constraints1.gridx = 0;
	        constraints1.gridy = 7;
	        JButton cancelButton = new JButton("Abbrechen");
	        panel1.add(cancelButton, constraints1);

	        constraints1.gridx = 1;
	        constraints1.gridy = 7;
	        JButton saveButton = new JButton("Speichern");
	        panel1.add(saveButton, constraints1);

	        return panel1;
	    }
	
}