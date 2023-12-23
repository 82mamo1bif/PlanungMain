import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MainGUI extends JFrame implements ActionListener, ChangeListener {

    private JTextField cpField;
    private JTextField lernaufwandField;
    private JTextField istStundenField; // Neues Textfeld für "Ist Stunden"
    private JProgressBar lernprozessBar; // Neue ProgressBar für den Lernprozess
    private JSlider weekSlider;
    private JButton calculateButton;
    private JButton istStundenButton; // Neuer Button für "Ist Stunden"
    private JButton saveButton; // Neuer Button zum Speichern der Daten
    private ModulEinstellung modulEinstellung;

    public MainGUI() {
        super("Learning Tracker");

        // Set Nimbus Look and Feel
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        initializeComponents();
    }

    private void initializeComponents() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(400, 300, 1000, 630);

        modulEinstellung = new ModulEinstellung();
        add(modulEinstellung);

        // Neuer Button zum Speichern der Daten
        saveButton = new JButton("Speichern");
        saveButton.addActionListener(this);
        modulEinstellung.add(saveButton);
    }

    private class ModulEinstellung extends JPanel {public ModulEinstellung() {
        setLayout(new GridLayout(7, 2));

        cpField = new JTextField();
        add(new JLabel("Credit Points (CP):"));
        add(cpField);

        lernaufwandField = new JTextField();
        lernaufwandField.setEditable(false);
        add(new JLabel("Lernaufwand (Stunden):"));
        add(lernaufwandField);

        istStundenField = new JTextField();
        add(new JLabel("Ist Stunden:"));
        add(istStundenField);

        lernprozessBar = new JProgressBar(0, 100);
        lernprozessBar.setStringPainted(true);
        add(new JLabel("Lernprozess:"));
        add(lernprozessBar);

        weekSlider = new JSlider(1 ,15);
        weekSlider.setValue(1);
        weekSlider.addChangeListener(MainGUI.this);
        weekSlider.setPaintTicks(true);
        add(new JLabel("Anzahl der Wochen im Semester:"));
        add(weekSlider);
        add(weekSlider);

        // Fügen Sie Striche unter den Sliedermarkierungen hinzu
        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        for (int i = 15; i >= 1; i--) {
            labelTable.put(i, new JLabel(Integer.toString(i)));
        }
        weekSlider.setLabelTable(labelTable);
        weekSlider.setPaintLabels(true);
        
        
        calculateButton = new JButton("Berechnen");
        calculateButton.addActionListener(MainGUI.this);
        add(calculateButton);

        istStundenButton = new JButton("Ist Stunden");
        istStundenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateLernprozess();
            }
        });
        add(istStundenButton);

        JButton backButton = new JButton("Zurück");
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
        add(backButton);
        
        
    }
}

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == calculateButton) {
            calculateLernaufwand();
        }else if (e.getSource() == saveButton) {
            saveData();
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        calculateLernaufwand();
    }

    private void calculateLernaufwand() {
        try {
            int creditPoints = Integer.parseInt(cpField.getText());
            int weeks = weekSlider.getValue();

            if (creditPoints > 0) {
                int lernaufwand = creditPoints * 25 / weeks;
                lernaufwandField.setText(Integer.toString(lernaufwand));
            } else {
                JOptionPane.showMessageDialog(MainGUI.this,
                        "Credit Points sollte größer als 0 sein.",
                        "Fehler",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(MainGUI.this,
                    "Ungültige Eingabe. Bitte geben Sie Zahlen ein.",
                    "Fehler",
                    JOptionPane.ERROR_MESSAGE);
        }
    }


    // Neue Methode zum Aktualisieren des Lernprozesses
    private void updateLernprozess() {
        try {
            int istStunden = Integer.parseInt(istStundenField.getText());
            int berechneterLernaufwand = Integer.parseInt(lernaufwandField.getText());

            if (berechneterLernaufwand > 0) {
                int lernprozess = (istStunden * 100) / berechneterLernaufwand;
                lernprozessBar.setValue(lernprozess);
            } else {
                JOptionPane.showMessageDialog(MainGUI.this,
                        "Berechneter Lernaufwand sollte größer als 0 sein.",
                        "Fehler",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(MainGUI.this,
                    "Ungültige Eingabe. Bitte geben Sie Zahlen ein.",
                    "Fehler",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    private void saveData() {
        // Hier können Sie den Code hinzufügen, um die Daten zu speichern
        // (z. B. in einer Datenbank, Datei oder einer anderen Datenstruktur).
        // Für diese Beispielimplementierung verwenden wir nur eine Konsolenausgabe.
        System.out.println("Daten gespeichert: CP=" + cpField.getText() +
                ", Lernaufwand=" + lernaufwandField.getText() +
                ", Wochen=" + weekSlider.getValue());
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGUI().setVisible(true));
    }
}
