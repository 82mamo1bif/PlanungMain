import javax.swing.*;



import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StundenPlaner extends JFrame {
    // Deklaration der Komponenten
    private JComboBox<String> modulComboBox;
    private JSlider cpSlider;
    private JTextField vonTextField;
    private JTextField bisTextField;
    private JTextField planungTextField;
    private JLabel stundenLabel;
    private JCheckBox speichernCheckBox;
    private JButton speichernButton;
	  private JFrame frame;
    private JButton backButton ;
    private Timer timer;
    private int seconds;
    
    StundenPlaner() {
        super("Semester Info");
        initComponents();

        // Setze das Look and Feel (Nimbus)
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void initComponents() {
    	
     // Initialisierung der Komponenten
        modulComboBox = new JComboBox<>(new String[]{"Unternehmenssoftware 1", "Modul 2", "Modul 3"});
        cpSlider = new JSlider(1, 10, 7);
        vonTextField = new JTextField(new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
        bisTextField = new JTextField(new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
        planungTextField = new JTextField("Stunden gesamt");
        stundenLabel = new JLabel("0 Stunden gesamt");
        speichernCheckBox = new JCheckBox("Ergebnis speichern");
        speichernButton = new JButton("Speichern");
         backButton = new JButton("Zurück");

        
        // Setzen der Layouts und Hinzufügen der Komponenten
        getContentPane().setLayout(new BorderLayout());
        JPanel northPanel = new JPanel(new GridLayout(0, 1));

        northPanel.add(modulComboBox);
        northPanel.add(cpSlider);
        northPanel.add(vonTextField);
        northPanel.add(bisTextField);
        northPanel.add(planungTextField);
        getContentPane().add(northPanel, BorderLayout.NORTH);
        
        JPanel centerPanel = new JPanel(new FlowLayout());
        centerPanel.add(stundenLabel);
        getContentPane().add(centerPanel, BorderLayout.CENTER);
        
        JPanel southPanel = new JPanel(new FlowLayout());
        southPanel.add(speichernCheckBox);
        southPanel.add(speichernButton);
        getContentPane().add(southPanel, BorderLayout.SOUTH);
        
        JPanel EastPanel = new JPanel(new FlowLayout());
        EastPanel.add(backButton);
        getContentPane().add(EastPanel, BorderLayout.EAST);
        
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seconds++;
                updateTimeLabel();
            }
        });
        JPanel panel = new JPanel();

        getContentPane().add(panel);
    
        // Listener für den Speichern-Button
        speichernButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                berechneStunden();
                if(speichernCheckBox.isSelected()) {
                    speichereErgebnis();
                }
            }
        });
        
        // Fenster-Eigenschaften
        setTitle("Stunden Planer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setSize(517, 311);
        setVisible(true);


        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == backButton) {
                    dispose();
                    Preadvising s = new Preadvising();
                    s.setVisible(true);
                }
            }
        });
    }
    
    private void berechneStunden() {
        // Berechnung der Stunden (Platzhalterlogik)
        int wochen = 1; // Hier sollten Sie die Differenz zwischen den Daten berechnen
        int planStunden = Integer.parseInt(planungTextField.getText());
        int gesamtStunden = wochen * planStunden * cpSlider.getValue();
        stundenLabel.setText(gesamtStunden + " Stunden gesamt");
    }
    
    private void speichereErgebnis() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("ergebnis.txt"));
            writer.write("Modul: " + modulComboBox.getSelectedItem().toString());
            writer.newLine();
            writer.write(": " + cpSlider.getValue() + " CP");
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
            JOptionPane.showMessageDialog(this, "Fehler beim Speichern des Ergebnisses.", "Fehler", JOptionPane.ERROR_MESSAGE);
            }
            }
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {  
    	try {
    		UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName());
    } catch (UnsupportedLookAndFeelException e) {
        e.printStackTrace();
    }

    SwingUtilities.invokeLater(new Runnable() {
        public void run() {
            new StundenPlaner();
        }
    });
    }

    private void updateTimeLabel() {

    }

}