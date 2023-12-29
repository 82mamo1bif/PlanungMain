import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

public class Editor extends JFrame implements ActionListener {

	private JComboBox<String> modulComboBox;
	private JTextField cpTextField; // Replace JSlider with JTextField for Credit Points
	private JTextField vonTextField;
	private JTextField bisTextField;
	private JTextField planungTextField;
	private JLabel stundenLabel;
	private JCheckBox speichernCheckBox;
	private JButton speichernButton;
	private JButton backButton;
	private JPanel northPanel;
	private JLabel stundenLabel_1;
	private int creditPoints;
	private static Editor instance;
	// Beispiel-Daten (kannst du durch deine Textfelder ersetzen)
	String vonDatumStr = "";
	String bisDatumStr = "";

	Editor() {
		super("Semester Info");
		northPanel = new JPanel(new GridLayout(0, 1)); // Initialize northPanel in the constructor
		initComponents();
	}

	public void initComponents() {

		// Fenster-Eigenschaften
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(120, 200, 508, 891);
		setTitle("Stunden Planer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setSize(428, 451);
		setVisible(true);

		modulComboBox = new JComboBox<>(new String[] {});

		cpTextField = new JTextField(" "); // Default value
		cpTextField.setEditable(false); // Set it to not editable
		northPanel.add(new JLabel("Credit Points (CP):"));

		vonTextField = new JTextField(new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
		bisTextField = new JTextField(new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
		planungTextField = new JTextField("Stunden gesamt");
		planungTextField.setEditable(false); // Set it to not editable

		stundenLabel = new JLabel("");
		stundenLabel.setBackground(getBackground().RED);
		speichernCheckBox = new JCheckBox("Ergebnis speichern");
		speichernButton = new JButton("Rechnen");

		// Setzen der Layouts und Hinsnzufügen der Komponenten
		getContentPane().setLayout(new BorderLayout());
		JPanel northPanel = new JPanel(new GridLayout(0, 1));

		// Set layouts and add components

		northPanel.add(new JLabel("Modul:"));
		northPanel.add(modulComboBox);
		northPanel.add(new JLabel("Credit Points (CP):"));
		northPanel.add(cpTextField);
		northPanel.add(new JLabel("Von:"));
		northPanel.add(vonTextField);
		northPanel.add(new JLabel("Bis:"));
		northPanel.add(bisTextField);
		northPanel.add(new JLabel("Wochenplanung: gesamte Stunde Pro Woche"));
		northPanel.add(planungTextField);
		getContentPane().add(northPanel, BorderLayout.NORTH);

		JPanel centerPanel = new JPanel(new FlowLayout());

		stundenLabel_1 = new JLabel("geplante Stunden pro Semester");
		centerPanel.add(stundenLabel_1);
		centerPanel.add(stundenLabel);
		getContentPane().add(centerPanel, BorderLayout.CENTER);

		JPanel southPanel = new JPanel(new FlowLayout());
		southPanel.add(speichernCheckBox);
		backButton = new JButton("Zurück");
		southPanel.add(backButton);

		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == backButton) {
					dispose();
					Main s = new Main();
					s.setVisible(true);
				}
			}
		});
		southPanel.add(speichernButton);
		getContentPane().add(southPanel, BorderLayout.SOUTH);

		JPanel EastPanel = new JPanel(new FlowLayout());
		getContentPane().add(EastPanel, BorderLayout.EAST);

		// Listener for the Speichern-Button
		speichernButton.addActionListener(this);
	}

	private long berechneDifferenzInWochen() {
	    try {
	        // Get the texts from the JTextFields
	        String vonDatumStr = vonTextField.getText();
	        String bisDatumStr = bisTextField.getText();

	        // Überprüfung des Uhrzeitformats
	        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	        LocalDate vonDatum = LocalDate.parse(vonDatumStr, dateFormatter);
	        LocalDate bisDatum = LocalDate.parse(bisDatumStr, dateFormatter);

	        // Berechnung der Differenz in Wochen
	        long differenzInWochen = vonDatum.until(bisDatum, ChronoUnit.WEEKS);

	        // Anzeige der Differenz in der planungTextField
	        planungTextField.setText(String.valueOf(differenzInWochen));

	        // Rückgabe der Differenz für andere Verwendungszwecke
	        return differenzInWochen;
	    } catch (DateTimeParseException e) {
	        // Hier wird auf eine falsche Eingabe reagiert, z.B. eine Meldung anzeigen
	        JOptionPane.showMessageDialog(this, "Invalid input format. Please use the format dd.MM.yyyy.", "Error", JOptionPane.ERROR_MESSAGE);
	        return -1; // Gib einen ungültigen Wert zurück, um auf einen Fehler hinzuweisen
	    }
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == speichernButton) {
			berechneStunden();
		}
	}

	private void berechneStunden() {
        try {
            // Get the difference in weeks
            long differenzInWochen = berechneDifferenzInWochen();

            // Check if the difference is valid
            if (differenzInWochen >= 0) {
                // Perform your calculation based on the difference
                int creditPoints = Integer.parseInt(cpTextField.getText());

                // Calculate the total planned hours per semester
                int prSemester = (int) differenzInWochen * 5 * creditPoints;
                stundenLabel.setText(prSemester + "");
            } else {
                // Handle the case where there's an error in the calculation of the difference
                System.out.println("Fehler bei der Berechnung der Differenz.");
            }
        } catch (NumberFormatException e) {
            // Handle incorrect input for Credit Points or Weekly Planning
            JOptionPane.showMessageDialog(this, "Invalid input for Credit Points or Weekly Planning.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

	// Methode, um den Modulnamen zum JComboBox hinzuzufügen
	public void addModuleNameToComboBox(String moduleName) {
		modulComboBox.addItem(moduleName);
	}

	// Method to add priority to the JTextField
	public void addPriorityToTextField(String priority) {
		cpTextField.setText(priority);
	}

	public static Editor getInstance() {
		if (instance == null) {
			instance = new Editor();
		}
		return instance;
	}

	private void speichereErgebnis() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("ergebnis.txt"));
			writer.write("Modul: " + modulComboBox.getSelectedItem().toString());
			writer.newLine();
			writer.write(": " + cpTextField.getText() + " CP");
			writer.newLine();
			writer.write("Von: " + vonTextField.getText());
			writer.newLine();
			writer.write("Bis: " + bisTextField.getText());
			writer.newLine();
			writer.write("Wochenplanung: " + planungTextField.getText() + " Stunden pro Woche");
			writer.newLine();
			writer.write("Gesamtstunden: " + stundenLabel.getText());
			writer.newLine();
			writer.close();
			JOptionPane.showMessageDialog(this, "Ergebnis erfolgreich gespeichert.");
		} catch (IOException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Fehler beim Speichern des Ergebnisses.", "Fehler",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException
				| IllegalAccessException e) {
			e.printStackTrace();
		}

		SwingUtilities.invokeLater(() -> new Editor());
	}

	public void setModuleNameAndCredit(String moduleName, String creditPoints) {
		modulComboBox.addItem(moduleName);
		cpTextField.setText(creditPoints);
	}

}
