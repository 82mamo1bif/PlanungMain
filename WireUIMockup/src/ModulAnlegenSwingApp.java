import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.util.Date;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class ModulAnlegen extends JFrame implements ActionListener {

	public class Module {

	}

	private Container c;
	private ImageIcon img1, img2;
	private Font f, font;
	private Date date;
	private JLabel courseLabel, priorityLabel, choosenLabel, Versuchlabel;
	private JButton addButton;
	private JComboBox cmbx1, cmbx2, cmbx3;
	private JTable table;
	private JTableHeader header;
	private DefaultTableModel model;
	private JTextField moduleNameTextField;
	static String[] coursesList = { "CSE115", "CSE115L", "CSE173", "CSE215", "CSE215L", "CSE225", "CSE225L", "CSE231",
			"CSE231L", "CSE299", "CSE332", "CSE311", "CSE331L", "CSE323", "CSE327", "CSE499A", "MAT116", "MAT120",
			"MAT125", "MAT130", "MAT250", "MAT350", "MAT361", "PHY107", "PHY107L", "CHE101", "CHE101L", "BIO103",
			"BIO103L", "BEN205", "ENG102", "ENG103", "ENG111", "EEE111", "EEE111L", "EEE141", "EEE141L", "EEE154",
			"EEE452", "POL101", "ECO101" };

	public static String[] priorityList = { "1", "2", "3", "4", "5", "6", "7", "8", "9" };
	public static String[] Versuche = { "1", "2", "3" };
	public static String[] credit = { "3", "1", "3", "3", "1", "3", "0", "3", "0", "1", "3", "3", "0", "3", "3", "7.5",
			"0", "3", "3", "3", "3", "3", "3", "3", "1", "3", "1", "3", "1", "3", "3", "3", "3", "3", "1", "3", "1",
			"1", "3", "3", "3" };
	private String[] col = { "#", "Courses", "Credit", "Course Title", "Priority", "Date and Time" };
	public static String[] row = new String[6];

	public Main mainInstance;
	private JPanel panel;

	ModulAnlegen() {
		super("ModulAnlegen");
		mainInstance = Main.getInstance(); // Verwenden Sie die Singleton-Methode für Zugriff auf die Instanz von Main
		preAdvising1();
	}

	ModulAnlegen(Main mainInstance) {
		this.mainInstance = mainInstance;
	}

	public void preAdvising1() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(120, 200, 508, 891);

		img1 = new ImageIcon(getClass().getResource("deleted.png")); // Add to JFrame logo image
		img2 = new ImageIcon(getClass().getResource("course.png"));
		this.setIconImage(img2.getImage());

		c = this.getContentPane();
		c.setLayout(null);

		f = new Font("Times New Roman", Font.BOLD, 20);
		font = new Font("Times New Roman", Font.BOLD, 22);

		courseLabel = new JLabel("Modulname :");
		courseLabel.setFont(font);
		courseLabel.setBounds(41, 128, 151, 16);
		c.add(courseLabel);

		moduleNameTextField = new JTextField();
		moduleNameTextField.setFont(f);
		moduleNameTextField.setBounds(251, 117, 201, 40);
		c.add(moduleNameTextField);

		priorityLabel = new JLabel("ECTS:");
		priorityLabel.setFont(font);
		priorityLabel.setBounds(41, 352, 88, 16);
		c.add(priorityLabel);

		Versuchlabel = new JLabel("Versuche:");
		Versuchlabel.setFont(font);
		Versuchlabel.setBounds(41, 233, 114, 16);
		c.add(Versuchlabel);

		cmbx1 = new JComboBox(Versuche);
		cmbx1.setFont(f);
		cmbx1.setSelectedItem("CSE225");
		cmbx1.setBounds(251, 234, 201, 24);
		cmbx1.setEditable(true);
		c.add(cmbx1);
		cmbx1.setEditable(false); // Set the JComboBox to be non-editable

		cmbx2 = new JComboBox(priorityList);
		cmbx2.setFont(f);
		cmbx2.setSelectedItem("1");
		cmbx2.setBounds(254, 348, 198, 24);
		cmbx2.setEditable(false);
		c.add(cmbx2);

		addButton = new JButton("Add");
		addButton.setFont(f);
		addButton.setBounds(143, 575, 208, 40);
		c.add(addButton);

		choosenLabel = new JLabel("Your choosen course:");
		choosenLabel.setFont(font);
		choosenLabel.setBounds(20, 27, 300, 16);
		c.add(choosenLabel);

		table = new JTable();
		model = new DefaultTableModel();
		model.setColumnIdentifiers(col);

		addButton.addActionListener(this);

		JButton monitorProgressButton = new JButton(" Abbrechen");
		monitorProgressButton.setFont(f);
		monitorProgressButton.setBackground(Color.ORANGE);
		monitorProgressButton.setBounds(144, 666, 207, 40);
		c.add(monitorProgressButton);

		monitorProgressButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == monitorProgressButton) {
					dispose();
					Main pre = new Main();
					pre.setVisible(true);
				}
			}
		});
		getContentPane().add(monitorProgressButton);
		
		panel = new JPanel();
		panel.setBounds(-13, 533, 527, 224);
		getContentPane().add(panel);

		panel.setBackground(new Color(255, 182, 193)); // Light Magenta
		}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addButton) {
			if (table.getRowCount() == 8) {
				JOptionPane.showMessageDialog(null, "You can't add more courses.");
			} else {
				int count = table.getRowCount() + 1;
				String total = String.valueOf(count);
				String[] row = new String[6];
				row[0] = total;
				row[1] = moduleNameTextField.getText();
				row[2] = cmbx1.getSelectedItem().toString(); // Get the selected item from cmbx1

				for (int i = 0; i < coursesList.length; i++) {
					if (cmbx1.getSelectedItem().toString().equals(coursesList[i])) {
						row[2] = credit[i];
						row[3] = Versuche[i];
						break;
					}
				}
				row[3] = cmbx2.getSelectedItem().toString();
				date = new Date();
				row[4] = date.toString();

				model.addRow(row);

				// Neues Modul erstellen und zur Liste in Main hinzufügen
				Main.Module newModule = new Main.Module(row[1], row[2], row[3], "", row[4]);
				mainInstance.addModule(newModule);
			}

		}
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
				new ModulAnlegen().setVisible(true);
			}
		});
	}
}
