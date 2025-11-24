package PCD_LAB4;

import javax.swing.*;
import java.awt.*;

public class AppWindow extends JFrame {

    private JTextArea logArea;
    private JLabel lblStatus;
    private JButton btnStart;

    public AppWindow() {
        setTitle("Producer - Consumer (Swing, Buffer Circular)");
        setSize(650, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(logArea);
        add(scroll, BorderLayout.CENTER);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        btnStart = new JButton("Start");
        lblStatus = new JLabel("Depozit: 0 / 5");

        topPanel.add(btnStart);
        topPanel.add(lblStatus);

        add(topPanel, BorderLayout.NORTH);

        btnStart.addActionListener(e -> startSimulation());

        setVisible(true);
    }

    private void startSimulation() {
        btnStart.setEnabled(false);

        int X = 3, Y = 4, Z = 2, D = 5;

        Depozit depozit = new Depozit(D, Z * Y, Y, this);

        for (int i = 1; i <= X; i++) {
            new Producer(i, depozit).start();
        }

        for (int i = 1; i <= Y; i++) {
            new Consumer(i, Z, depozit).start();
        }

        log("Sistem pornit.\n");
    }

    public void log(String message) {
        SwingUtilities.invokeLater(() -> {
            logArea.append(message + "\n");
            logArea.setCaretPosition(logArea.getDocument().getLength());
        });
    }

    public void updateStatus(int count, int capacity) {
        SwingUtilities.invokeLater(() ->
                lblStatus.setText("Depozit: " + count + " / " + capacity)
        );
    }
}
