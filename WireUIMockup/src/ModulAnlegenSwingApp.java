import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

import javax.swing.event.ListSelectionListener;
import java.util.ArrayList;
import java.util.Date;

public class ModulAnlegenSwingApp {

	private JTextField moduleNameField;
	private JComboBox<String> priorityComboBox;
	private JTable table;
	private JFrame frame;
	 private Main mainInstance;

	    

	   
	/**
	 * @wbp.parser.entryPoint
	 */
	ModulAnlegenSwingApp(Main mainInstance) {
        this.mainInstance = mainInstance;

		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		frame = new JFrame("Modul anlegen");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 600);

		JPanel panel = createFormPanel();
		frame.getContentPane().add(panel);

		frame.setVisible(true);
	}
	
    
	private JPanel createFormPanel() {
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		gbl_panel.columnWeights = new double[] { 0.0, 1.0 };
		JPanel panel = new JPanel(gbl_panel);

		// Modulname
		JLabel nameLabel = new JLabel("Name:");
		GridBagConstraints nameLabelConstraints = new GridBagConstraints();
		nameLabelConstraints.gridx = 0;
		nameLabelConstraints.gridy = 0;
		nameLabelConstraints.insets = new Insets(10, 10, 10, 10);
		panel.add(nameLabel, nameLabelConstraints);

		moduleNameField = new JTextField("Programmieren 2", 10);
		GridBagConstraints moduleNameFieldConstraints = new GridBagConstraints();
		moduleNameFieldConstraints.gridx = 1;
		moduleNameFieldConstraints.gridy = 0;
		moduleNameFieldConstraints.insets = new Insets(10, 10, 10, 10);
		panel.add(moduleNameField, moduleNameFieldConstraints);

		// ECTS
		JLabel ectsLabel = new JLabel("ECTS:");
		GridBagConstraints ectsLabelConstraints = new GridBagConstraints();
		ectsLabelConstraints.gridx = 0;
		ectsLabelConstraints.gridy = 1;
		ectsLabelConstraints.insets = new Insets(10, 10, 10, 10);
		panel.add(ectsLabel, ectsLabelConstraints);

		SpinnerNumberModel ectsModel = new SpinnerNumberModel(7, 0, 30, 1);
		JSpinner ectsSpinner = new JSpinner(ectsModel);
		GridBagConstraints ectsSpinnerConstraints = new GridBagConstraints();
		ectsSpinnerConstraints.gridx = 1;
		ectsSpinnerConstraints.gridy = 1;
		ectsSpinnerConstraints.insets = new Insets(10, 10, 10, 10);
		panel.add(ectsSpinner, ectsSpinnerConstraints);

		// SWS
		JLabel swsLabel = new JLabel("SWS:");
		GridBagConstraints swsLabelConstraints = new GridBagConstraints();
		swsLabelConstraints.gridx = 0;
		swsLabelConstraints.gridy = 2;
		swsLabelConstraints.insets = new Insets(10, 10, 10, 10);
		panel.add(swsLabel, swsLabelConstraints);

		SpinnerNumberModel swsModel = new SpinnerNumberModel(8, 0, 30, 1);
		JSpinner swsSpinner = new JSpinner(swsModel);
		GridBagConstraints swsSpinnerConstraints = new GridBagConstraints();
		swsSpinnerConstraints.gridx = 1;
		swsSpinnerConstraints.gridy = 2;
		swsSpinnerConstraints.insets = new Insets(10, 10, 10, 10);
		panel.add(swsSpinner, swsSpinnerConstraints);

		// Priorität
		JLabel priorityLabel = new JLabel("Priorität:");
		GridBagConstraints priorityLabelConstraints = new GridBagConstraints();
		priorityLabelConstraints.gridx = 0;
		priorityLabelConstraints.gridy = 3;
		priorityLabelConstraints.insets = new Insets(10, 10, 10, 10);
		panel.add(priorityLabel, priorityLabelConstraints);

		String[] priorities = { "None", "niedrig", "Mittelständig", "Hoch" };
		priorityComboBox = new JComboBox<>(priorities);
		GridBagConstraints priorityComboBoxConstraints = new GridBagConstraints();
		priorityComboBoxConstraints.gridx = 1;
		priorityComboBoxConstraints.gridy = 3;
		priorityComboBoxConstraints.insets = new Insets(10, 10, 10, 10);
		panel.add(priorityComboBox, priorityComboBoxConstraints);

		// Versuche (Buttons)
		JPanel attemptsPanel = new JPanel(new FlowLayout());
		JButton firstAttemptButton = new JButton("1. Versuch");
		JButton secondAttemptButton = new JButton("2. Versuch");
		JButton thirdAttemptButton = new JButton("3. Versuch");
		attemptsPanel.add(firstAttemptButton);
		attemptsPanel.add(secondAttemptButton);
		attemptsPanel.add(thirdAttemptButton);
		GridBagConstraints attemptsPanelConstraints = new GridBagConstraints();
		attemptsPanelConstraints.gridx = 0;
		attemptsPanelConstraints.gridy = 4;
		attemptsPanelConstraints.gridwidth = 2;
		attemptsPanelConstraints.insets = new Insets(10, 10, 10, 10);
		panel.add(attemptsPanel, attemptsPanelConstraints);

		// Schieberegler für Wochen
		JLabel sliderLabel = new JLabel("0 / Woche");
		GridBagConstraints sliderLabelConstraints = new GridBagConstraints();
		sliderLabelConstraints.gridx = 0;
		sliderLabelConstraints.gridy = 5;
		sliderLabelConstraints.gridwidth = 2;
		sliderLabelConstraints.insets = new Insets(10, 10, 10, 10);
		panel.add(sliderLabel, sliderLabelConstraints);

		JSlider weeksSlider = new JSlider(0, 14);
		weeksSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				sliderLabel.setText(weeksSlider.getValue() + " / Woche");
			}
		});
		GridBagConstraints weeksSliderConstraints = new GridBagConstraints();
		weeksSliderConstraints.gridx = 0;
		weeksSliderConstraints.gridy = 6;
		weeksSliderConstraints.gridwidth = 2;
		weeksSliderConstraints.insets = new Insets(10, 10, 10, 10);
		panel.add(weeksSlider, weeksSliderConstraints);
		// Initialize the table and model
		table = new JTable();

		JButton addButton = new JButton("Add");
		GridBagConstraints addButtonConstraints = new GridBagConstraints();
		addButtonConstraints.anchor = GridBagConstraints.WEST;
		addButtonConstraints.gridx = 1;
		addButtonConstraints.gridy = 9;
		addButtonConstraints.insets = new Insets(10, 10, 10, 10);
		panel.add(addButton, addButtonConstraints);

		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// This method is called whenever the selection value changes in the table
				int selectedRow = table.getSelectedRow();
				// Check if a row is actually selected
				if (selectedRow >= 0) {
					// Access the selected module: selectedModules.get(selectedRow)
					// You can perform actions based on the selected row here
				}
			}
		});
		addButton.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        // Rufen Sie die Methode zum Zugriff auf die Werte von row auf
		    	String[] rowValues = mainInstance.getRow();
			    // Füllen Sie das row-Array mit den entsprechenden Daten aus den UI-Feldern
			    rowValues[0] = "1";  // Beispiel: Fügen Sie Ihre Daten entsprechend ein
			    rowValues[1] = moduleNameField.getText();
			    rowValues[2] = ectsSpinner.getValue().toString();
			    rowValues[3] = swsSpinner.getValue().toString();
			    rowValues[4] = priorityComboBox.getSelectedItem().toString();
			    rowValues[5] = new Date().toString();

			    // Rufen Sie die Methode in der Main-Klasse auf, um die Zeile zur Tabelle hinzuzufügen
			    mainInstance.addRowToTable(rowValues);
		    }
		});


		// Abschluss-Buttons
		JButton cancelButton = new JButton("Abbrechen");
		GridBagConstraints cancelButtonConstraints = new GridBagConstraints();
		cancelButtonConstraints.anchor = GridBagConstraints.WEST;
		cancelButtonConstraints.gridx = 1;
		cancelButtonConstraints.gridy = 10;
		cancelButtonConstraints.insets = new Insets(10, 10, 10, 10);
		panel.add(cancelButton, cancelButtonConstraints);
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == cancelButton) {
					// Get the reference to the JFrame containing the cancel button

					// Close/dispose the current frame
					frame.dispose();

					// Open the Preadvising GUI
					Main Main = new Main();
					Main.setVisible(true);
				}
			}
		});

		return panel;
	}



	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new ModulAnlegenSwingApp(new Main()).createFormPanel();
		});
	}

	public void setVisible(boolean b) {
		SwingUtilities.invokeLater(() -> {
			new ModulAnlegenSwingApp(new Main()).createFormPanel();
		});
	}
}
