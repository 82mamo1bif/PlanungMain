import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Bearbeiten extends JFrame implements ActionListener, ChangeListener {

    private JTextField cpField;
    private JTextField lernaufwandField;
    private JTextField istStundenField;
    private JProgressBar lernprozessBar;
    private JSlider weekSlider;
    private JButton calculateButton;
    private JButton istStundenButton;
    private JButton saveButton;
    private JButton backButton;

    public Bearbeiten() {
        super("Learning Tracker");


        initializeComponents();
    }

    private void initializeComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(400, 300, 1041, 639);

        JPanel modulEinstellung = new JPanel();
        modulEinstellung.setLayout(new GridLayout(7, 2));

        cpField = new JTextField();
        modulEinstellung.add(new JLabel("Credit Points (CP):"));
        modulEinstellung.add(cpField);

        lernaufwandField = new JTextField();
        lernaufwandField.setEditable(false);
        modulEinstellung.add(new JLabel("Lernaufwand (Stunden):"));
        modulEinstellung.add(lernaufwandField);

        istStundenField = new JTextField();
        modulEinstellung.add(new JLabel("Ist Stunden:"));
        modulEinstellung.add(istStundenField);

        lernprozessBar = new JProgressBar(0, 100);
        lernprozessBar.setStringPainted(true);
        modulEinstellung.add(new JLabel("Lernprozess:"));
        modulEinstellung.add(lernprozessBar);

        weekSlider = new JSlider(1, 15);
        weekSlider.setValue(1);
        weekSlider.addChangeListener(this);
        weekSlider.setPaintTicks(true);
        modulEinstellung.add(new JLabel("Anzahl der Wochen im Semester:"));
        modulEinstellung.add(weekSlider);

        calculateButton = new JButton("Berechnen");
        calculateButton.addActionListener(this);
        modulEinstellung.add(calculateButton);

        istStundenButton = new JButton("Ist Stunden");
        istStundenButton.addActionListener(e -> updateLernprozess());
        modulEinstellung.add(istStundenButton);

        saveButton = new JButton("Speichern");
        saveButton.addActionListener(this);
        modulEinstellung.add(saveButton);

        getContentPane().add(modulEinstellung);
        
        
        
        backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (e.getSource() == backButton) {
                    Main s = new Main();
                    s.setVisible(true);
        	}}
        });
        
        modulEinstellung.add(backButton);
        
        
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == calculateButton) {
            calculateLernaufwand();
        } else if (e.getSource() == saveButton) {
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
                showError("Credit Points sollte größer als 0 sein.");
            }
        } catch (NumberFormatException ex) {
            showError("Ungültige Eingabe. Bitte geben Sie Zahlen ein.");
        }
    }

    private void updateLernprozess() {
        try {
            int istStunden = Integer.parseInt(istStundenField.getText());
            int berechneterLernaufwand = Integer.parseInt(lernaufwandField.getText());

            if (berechneterLernaufwand > 0) {
                int lernprozess = (istStunden * 100) / berechneterLernaufwand;
                lernprozessBar.setValue(lernprozess);
            } else {
                showError("Berechneter Lernaufwand sollte größer als 0 sein.");
            }
        } catch (NumberFormatException ex) {
            showError("Ungültige Eingabe. Bitte geben Sie Zahlen ein.");
        }
    }

    private void saveData() {
        System.out.println("Daten gespeichert: CP=" + cpField.getText() +
                ", Lernaufwand=" + lernaufwandField.getText() +
                ", Wochen=" + weekSlider.getValue());
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Fehler", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new Bearbeiten().setVisible(true));
    }
}
