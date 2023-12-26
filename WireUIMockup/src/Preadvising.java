import javax.swing.*;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Date;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class Main extends JFrame implements ActionListener {

	private static final Graphics2D Graphics2D = null;
	private Container c;
	private ImageIcon img1, img2;
	private Font f, font;
	private Date date;
	private JButton addButton, deleteButton, backButton, saveButton;

	public static JTable table;
	public static JTableHeader header;
	public static DefaultTableModel model;
	private JScrollPane scroll;



	private String[] col = { "#", "Courses", "Credit", "ECTS", " ", "Date and Time" };
	
	public ArrayList<Module> selectedModules; // Liste zum Speichern der ausgewählten Module

	private static Main instance; // Ändern Sie instance zu static

	Main() {
		super("Pre-Advising");
		selectedModules = new ArrayList<>();
		instance = this; // Aktualisieren Sie die Instanz auf die aktuelle Instanz von Main
		preAdvising();
	}

	public static Main getInstance() {
		if (instance == null) {
			instance = new Main();
		}
		return instance;
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

		addButton = new JButton("Add");
		addButton.setFont(f);
		addButton.setBounds(20, 66, 302, 42);
		c.add(addButton);

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

		

		// change to table header font and color
		header = table.getTableHeader();
		header.setFont(new Font("Times New Roman", Font.BOLD, 18));

		scroll = new JScrollPane(table);
		scroll.setFont(f);
		scroll.setBounds(334, 6, 1313, 821);
		c.add(scroll);

		deleteButton = new JButton(img1);
		deleteButton.setBackground(Color.MAGENTA);
		deleteButton.setBounds(35, 775, img1.getIconWidth(), img1.getIconHeight());
		c.add(deleteButton);

		backButton = new JButton("Back");
		backButton.setFont(f);
		backButton.setBackground(Color.GREEN);
		;
		backButton.setBounds(20, 311, 302, 36);
		c.add(backButton);
		// In der Preadvising-Klasse, wo Sie die Tabelle erstellen
				table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent e) {
						if (!e.getValueIsAdjusting()) {
							int selectedRow = table.getSelectedRow();
							if (selectedRow >= 0) {
								// Auf die Daten in der ausgewählten Zeile zugreifen
								String courseName = table.getValueAt(selectedRow, 1).toString();
								String credit = table.getValueAt(selectedRow, 2).toString();
								String versuche = table.getValueAt(selectedRow, 3).toString();
								String dateTime = table.getValueAt(selectedRow, 4).toString();

								// Überprüfen, ob das Modul bereits ausgewählt wurde
								boolean moduleAlreadySelected = false;
								for (Main.Module module : selectedModules) {
									if (module.getCourseName().equals(courseName)) {
										moduleAlreadySelected = true;
										break;
									}
								}

								// Nur hinzufügen, wenn das Modul noch nicht ausgewählt wurde
								if (!moduleAlreadySelected) {
									selectedModules.add(new Main.Module(courseName, credit, versuche, "", // Placeholder for
																											// 'priority'
											dateTime));
								}
							}
						}
					}
				});
		saveButton = new JButton("Save");
		saveButton.setFont(f);
		saveButton.setBackground(Color.GREEN);
		;
		saveButton.setBounds(20, 225, 302, 36);
		c.add(saveButton);

		addButton.addActionListener(this);
		deleteButton.addActionListener(this);
		backButton.addActionListener(this);
		saveButton.addActionListener(this);

		JButton deleteButton = new JButton("Löschen");
		deleteButton.setFont(f);
		deleteButton.setBackground(Color.RED);
		deleteButton.setBounds(20, 401, 302, 28);
		deleteButton.addActionListener(this);
		c.add(deleteButton);

		JButton monitorProgressButton = new JButton("Next Progress");
		monitorProgressButton.setFont(f);
		monitorProgressButton.setBackground(Color.ORANGE);
		monitorProgressButton.setBounds(20, 147, 300, 36);
		c.add(monitorProgressButton);

		monitorProgressButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == monitorProgressButton) {
					
					Bearbeiten pre = new Bearbeiten();
					pre.setVisible(true);
				}
			}
		});
		getContentPane().add(monitorProgressButton);

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
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 6, 334, 735);
		getContentPane().add(panel);
		panel.setBackground(new Color(255, 182, 193)); // Light Magenta		
		
	
}


	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addButton) {
			if (table.getRowCount() == 8) {
				JOptionPane.showMessageDialog(null, "You can't add more courses.");
			} else {
				ModulAnlegen m = new ModulAnlegen();
				m.setVisible(true);
			}
		} else if (e.getSource() == backButton) {
			// ... (unchanged)
		} else if (e.getSource() == deleteButton) {
			int selectedRow = table.getSelectedRow();
			if (selectedRow >= 0) {
				// Das ausgewählte Modul aus der Liste entfernen
				selectedModules.remove(selectedRow);

				// Die Zeile aus der Tabelle entfernen
				model.removeRow(selectedRow);

				// Andere UI-Komponenten aktualisieren, falls notwendig
				// z.B., das JTextField für den Modulnamen leeren
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

	public void addModule(Module module) {

		// Add the module data to the table
		String[] row = { String.valueOf(model.getRowCount() + 1), module.getCourseName(), module.getCredit(),
				module.getCourseTitle(), module.getPriority(), module.getDateTime() };

		model.addRow(row);
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException
				| IllegalAccessException e) {
			e.printStackTrace();
		}

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {

				new Main().setVisible(true);
			}
		});
	}

	public static class Module {
		private String courseName;

		public String getCourseName() {
			return courseName;
		}

		public void setCourseName(String courseName) {
			this.courseName = courseName;
		}

		public String getCredit() {
			return credit;
		}

		public void setCredit(String credit) {
			this.credit = credit;
		}

		public String getCourseTitle() {
			return courseTitle;
		}

		public void setCourseTitle(String courseTitle) {
			this.courseTitle = courseTitle;
		}

		public String getPriority() {
			return priority;
		}

		public void setPriority(String priority) {
			this.priority = priority;
		}

		public String getDateTime() {
			return dateTime;
		}

		public void setDateTime(String dateTime) {
			this.dateTime = dateTime;
		}

		private String credit;
		private String courseTitle;
		private String priority;
		private String dateTime;

		public Module(String courseName, String credit, String courseTitle, String priority, String dateTime) {
			this.courseName = courseName;
			this.credit = credit;
			this.courseTitle = courseTitle;
			this.priority = priority;
			this.dateTime = dateTime;
		}

	}
}
