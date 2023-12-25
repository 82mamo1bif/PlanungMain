import javax.swing.*;
import java.util.Objects;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Date;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class Main extends JFrame implements ActionListener {

	private Container c;
	private ImageIcon img1, img2;
	private Font f, font;
	private Date date;
	private JLabel priorityLabel, choosenLabel, Versuchlabel;
	private JButton addButton, deleteButton, backButton, saveButton;
	private JComboBox cmbx1, cmbx2, cmbx3;
	private JTable table;
	private JTableHeader header;
	public DefaultTableModel model;
	private JScrollPane scroll;

	private String[] priorityList = { "1", "2", "3", "4", "5" };

	private String[] Versuche = { "1", "2", "3" };

	private JTextField moduleNameTextField; // Move the declaration here

	private String[] credit = { "3", "1", "3", "3", "1", "3", "0", "3", "0", "1", "3", "3", "0", "3", "3", "7.5", "0",
			"3", "3", "3", "3", "3", "3", "3", "1", "3", "1", "3", "1", "3", "3", "3", "3", "3", "1", "3", "1", "1",
			"3", "3", "3" };
	private String[] col = { "#", "Courses", "Credit", "Course Title", "Priority", "Date and Time" };
	public String[] row = new String[6];
	public String[] getRow() {
        return row;
    }
	private ArrayList<Module> selectedModules = new ArrayList<>(); // Liste zum Speichern der ausgewählten Module

	public Main() {
		super("Pre-Main");
		 // Initialisieren Sie die Tabelle und das Modell
        table = new JTable();
        model = new DefaultTableModel();
        table.setModel(model);

        // Definieren Sie die Spalten für das Modell
        model.addColumn("#");
        model.addColumn("Courses");
        model.addColumn("Credit");
        model.addColumn("Course Title");
        model.addColumn("Priority");
        model.addColumn("Date and Time");

        // Initialisieren Sie die ausgewählten Module-Liste
        selectedModules = new ArrayList<>();

        JScrollPane scrollPane = new JScrollPane(table);
		selectedModules = new ArrayList<>();
		preAdvising();

	}
	
	
	public void preAdvising() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(300, 200, 1700, 940);

		img1 = new ImageIcon(getClass().getResource("deleted.png")); // Add to JFrame logo image
		img2 = new ImageIcon(getClass().getResource("course.png"));
		this.setIconImage(img2.getImage());

		c = this.getContentPane();
		c.setLayout(null);

		f = new Font("Times New Roman", Font.BOLD, 20);
		font = new Font("Times New Roman", Font.BOLD, 22);

		deleteButton = new JButton(img1);
		deleteButton.setBackground(Color.MAGENTA);
		deleteButton.setBounds(127, 775, img1.getIconWidth(), img1.getIconHeight());
		c.add(deleteButton);
		deleteButton.addActionListener(this);

		// In der MainGUI-Klasse, in der ModulEinstellung-Klasse
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow >= 0) {
					// Das ausgewählte Modul aus der Liste entfernen
					selectedModules.remove(selectedRow);

					// Die Zeile aus der Tabelle entfernen
					model.removeRow(selectedRow);

					// Andere UI-Komponenten aktualisieren, falls notwendig
					// z.B., das JTextField für den Modulnamen leeren
				}
			}
		});
		getContentPane().add(deleteButton);

		saveButton = new JButton("Save");
		saveButton.setFont(f);
		saveButton.setBackground(Color.GREEN);
		saveButton.setBounds(70, 619, 186, 50);
		c.add(saveButton);
		saveButton.addActionListener(this);

		JButton monitorProgressButton = new JButton("Next Progress");
		monitorProgressButton.setFont(f);
		monitorProgressButton.setBackground(Color.ORANGE);
		monitorProgressButton.setBounds(70, 693, 186, 50);
		c.add(monitorProgressButton);

		monitorProgressButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == monitorProgressButton) {
					dispose();
					MainGUI pre = new MainGUI();
					pre.setVisible(true);
				}
			}
		});

		getContentPane().add(monitorProgressButton);

		backButton = new JButton("Back");
		backButton.setFont(f);
		backButton.setBackground(Color.GREEN);
		backButton.setBounds(80, 524, 186, 60);
		c.add(backButton);
		backButton.addActionListener(this);

		moduleNameTextField = new JTextField(); // Initialize it here
		moduleNameTextField.setFont(f);
		moduleNameTextField.setBounds(802, 786, 250, 50);
		c.add(moduleNameTextField);

		priorityLabel = new JLabel("Priority:");
		priorityLabel.setFont(font);
		priorityLabel.setBounds(936, 785, 90, 50);
		c.add(priorityLabel);

		Versuchlabel = new JLabel("Versuche:");
		Versuchlabel.setFont(font);
		Versuchlabel.setBounds(1543, 785, 100, 50);
		c.add(Versuchlabel);

		cmbx1 = new JComboBox(Versuche);
		cmbx1.setFont(f);
		cmbx1.setSelectedItem("CSE225");
		cmbx1.setBounds(1071, 809, 250, 50);
		cmbx1.setEditable(true);// editable to combo box
		c.add(cmbx1);

		cmbx2 = new JComboBox(priorityList);
		cmbx2.setFont(f);
		cmbx2.setSelectedItem("1");
		cmbx2.setBounds(585, 809, 138, 50);
		cmbx2.setEditable(false);// editable to combo box
		c.add(cmbx2);

		addButton = new JButton("Add");
		addButton.setFont(f);
		addButton.setBounds(70, 273, 175, 60);
		c.add(addButton);

		choosenLabel = new JLabel("Your choosen courses:");
		choosenLabel.setFont(font);
		choosenLabel.setBounds(323, 32, 300, 40);
		c.add(choosenLabel);

		table = new JTable();

		model = new DefaultTableModel();
		model.setColumnIdentifiers(col);
		table.setModel(model);
		table.setFont(f);
		table.setDefaultEditor(Object.class, null);// now table not editable
		table.setBackground(Color.WHITE);
		table.setRowHeight(35);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setPreferredWidth(150);
		table.getColumnModel().getColumn(2).setPreferredWidth(120);
		table.getColumnModel().getColumn(3).setPreferredWidth(460);
		table.getColumnModel().getColumn(4).setPreferredWidth(130);
		table.getColumnModel().getColumn(5).setPreferredWidth(375);

		// In der Preadvising-Klasse, wo Sie die Tabelle erstellen
		table.getSelectionModel().addListSelectionListener((ListSelectionListener) new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow >= 0) {
					// Hier können Sie auf das ausgewählte Modul zugreifen:
					 selectedModules.get(selectedRow);
				}
			}
		});

		// change to table header font and color
		header = table.getTableHeader();
		header.setFont(new Font("Times New Roman", Font.BOLD, 18));

		scroll = new JScrollPane(table);
		scroll.setFont(f);
		scroll.setBounds(314, 70, 1328, 684);
		c.add(scroll);

		addButton.addActionListener(this);

		Panel panel = new Panel();
		panel.setBackground(new Color(173, 216, 230)); // RGB values for light blue
		panel.setBounds(0, 0, 273, 210);
		getContentPane().add(panel);

	}

	/*
	 * // Check if ModulAnlegenSwingApp is already open boolean
	 * isModulAnlegenSwingAppOpen = false;
	 * 
	 * for (Frame frame : Frame.getFrames()) { if (frame instanceof JFrame &&
	 * frame.isVisible()) { isModulAnlegenSwingAppOpen = true; break; } }
	 * 
	 * // Display an error message if already open if (isModulAnlegenSwingAppOpen) {
	 * 
	 * JOptionPane.showMessageDialog(null, "ModulAnlegenSwingApp is already open.");
	 */

	@Override
	public void actionPerformed(ActionEvent e) {

		// Open the ModulAnlegenSwingApp GUI
		ModulAnlegenSwingApp modulAnlegenSwingApp = new ModulAnlegenSwingApp(this);
		modulAnlegenSwingApp.setVisible(true);

		if (e.getSource() == backButton) {
			// ... (unchanged)
		} else if (e.getSource() == deleteButton) {
			int selectedRow = table.getSelectedRow();
			if (selectedRow >= 0) {
				// Das ausgewählte Modul aus der Liste entfernen
				selectedModules.remove(selectedRow);

				// Die Zeile aus der Tabelle entfernen
				model.removeRow(selectedRow);
			}

		} else if (e.getSource() == saveButton) {
			try {
				File fp = new File("Preadvising.txt");
				if (!fp.exists()) {
					fp.createNewFile();
				}

				FileWriter fw = new FileWriter(fp.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);

				for (int i = 0; i < table.getRowCount(); i++) {
					for (int j = 0; j < table.getColumnCount(); j++) {
						bw.write(table.getModel().getValueAt(i, j) + " ");
					}
					bw.write("\r\n"); // Use a single newline character
				}
				bw.close();
				fw.close();
				JOptionPane.showMessageDialog(null, "Data saved successfully");

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "An error occurred");
			}
		}

	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new Main().setVisible(true);

			}
		});
	}

	public class Module {
	    private String moduleName;
	    private String ects;
	    private String sws;
	    private String priority;
	    private String date;

	    public Module(String moduleName, String ects, String sws, String priority, String date) {
	        this.moduleName = moduleName;
	        this.ects = ects;
	        this.sws = sws;
	        this.priority = priority;
	        this.date = date;
	    }

	    public String getModuleName() {
	        return moduleName;
	    }

	    public String getEcts() {
	        return ects;
	    }

	    public String getSws() {
	        return sws;
	    }

	    public String getPriority() {
	        return priority;
	    }

	    public String getDate() {
	        return date;
	    }

	    @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;
	        Module module = (Module) o;
	        return Objects.equals(moduleName, module.moduleName) &&
	                Objects.equals(ects, module.ects) &&
	                Objects.equals(sws, module.sws) &&
	                Objects.equals(priority, module.priority) &&
	                Objects.equals(date, module.date);
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(moduleName, ects, sws, priority, date);
	    }
	


	}
	public void addRowToTable(Object[] rowData) {
        model.addRow(rowData);
    }

    // Methode zum Hinzufügen eines Moduls zur ausgewählten Module-Liste
    public void addModuleToList(Module module) {
        selectedModules.add(module);
    }
	

	public void addModuleToList(DefaultTableModel model) {
		int rowCount = model.getRowCount();
		if (rowCount < 8) {
			String moduleName = model.getValueAt(rowCount - 1, 1).toString();
			String ects = model.getValueAt(rowCount - 1, 2).toString();
			String sws = model.getValueAt(rowCount - 1, 3).toString();
			String priority = model.getValueAt(rowCount - 1, 4).toString();
			selectedModules.add(new Module(moduleName, ects, sws, priority, ""));
		} else {
			JOptionPane.showMessageDialog(null, "You can't add more courses.");
		}
	}


}
