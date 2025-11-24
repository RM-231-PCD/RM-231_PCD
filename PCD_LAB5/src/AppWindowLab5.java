package PCD_LAB5;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ConcurrentHashMap;

public class AppWindowLab5 extends JFrame {

    private JTextArea logArea;
    private JLabel lblStatus;
    private JButton btnStart;

    // ====== Parametrii LAB 5 ======
    public static final int BUFFER_CAPACITY = 5;
    public static final int PRODUCER_COUNT = 3;
    public static final int CONSUMER_COUNT = 4;
    public static final int CONSUMER_GOAL = 2;
    public static final int F_PER_PRODUCER = 2;
    public static final int TOTAL_OBJECTS = CONSUMER_COUNT * CONSUMER_GOAL;

    // ====== Structuri partajate ======
    BlockingQueue<Integer> buffer = new ArrayBlockingQueue<>(BUFFER_CAPACITY);

    AtomicInteger totalProduced = new AtomicInteger(0);
    AtomicInteger totalConsumed = new AtomicInteger(0);

    Map<Integer, AtomicInteger> consumerCounters = new ConcurrentHashMap<>();

    public AppWindowLab5() {
        setTitle("Lab 5 – Producer–Consumer (ExecutorService + BlockingQueue)");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // zona log
        logArea = new JTextArea();
        logArea.setEditable(false);
        add(new JScrollPane(logArea), BorderLayout.CENTER);

        // top panel
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnStart = new JButton("Start");
        lblStatus = new JLabel("Depozit: 0 / 5");
        top.add(btnStart);
        top.add(lblStatus);
        add(top, BorderLayout.NORTH);

        // inițializare consumatori
        for (int i = 1; i <= CONSUMER_COUNT; i++) {
            consumerCounters.put(i, new AtomicInteger(0));
        }

        btnStart.addActionListener(e -> startSimulation());

        setVisible(true);
    }

    private void startSimulation() {

        btnStart.setEnabled(false);

        ExecutorService executor =
                Executors.newFixedThreadPool(PRODUCER_COUNT + CONSUMER_COUNT);

        // pornesc producătorii
        for (int i = 1; i <= PRODUCER_COUNT; i++) {
            executor.execute(new Producer(i, this));
        }

        // pornesc consumatorii
        for (int i = 1; i <= CONSUMER_COUNT; i++) {
            executor.execute(new Consumer(i, this));
        }

        log("Sistem pornit.");

        executor.shutdown();

        // Fir separat pentru a aștepta finalizarea
        new Thread(() -> {
            try {
                executor.awaitTermination(60, TimeUnit.SECONDS);
            } catch (InterruptedException ignored) {}

            log("\n===== RAPORT FINAL =====");
            log("Total produse:   " + totalProduced.get());
            log("Total consumate: " + totalConsumed.get());

            consumerCounters.forEach((id, c) ->
                    log("Consumator " + id + ": a consumat " + c.get() + " obiecte")
            );

            log("========================");
            log("SFÂRȘIT EXERCITIU");

        }).start();
    }

    // ===== Funcții utile =====

    public void log(String msg) {
        SwingUtilities.invokeLater(() -> {
            logArea.append(msg + "\n");
            logArea.setCaretPosition(logArea.getDocument().getLength());
        });
    }

    public void updateStatus() {
        SwingUtilities.invokeLater(() ->
                lblStatus.setText("Depozit: " + buffer.size() + " / " + BUFFER_CAPACITY)
        );
    }
}
