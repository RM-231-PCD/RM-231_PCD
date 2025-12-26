import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProducerConsumerGUI {

    private static final int BUFFER_CAPACITY = 12;
    private static final BlockingQueue<Integer> buffer = new ArrayBlockingQueue<>(BUFFER_CAPACITY);

    private static final int PRODUCER_COUNT = 2;
    private static final int CONSUMER_COUNT = 5;
    private static final int CONSUMER_GOAL = 3;
    private static final int TOTAL_OBJECTS = CONSUMER_COUNT * CONSUMER_GOAL;

    private static final AtomicInteger totalProduced = new AtomicInteger(0);
    private static final AtomicInteger totalConsumed = new AtomicInteger(0);
    private static final Map<Integer, AtomicInteger> consumerCounters = new ConcurrentHashMap<>();

    private static JTextArea logArea;

    public static void main(String[] args) {
        // Creare fereastră
        JFrame frame = new JFrame("Producer-Consumer Simulator");
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);

        JButton startButton = new JButton("Start Simulare");
        startButton.addActionListener(e -> startSimulation());

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(startButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private static void startSimulation() {
        ExecutorService executor = Executors.newFixedThreadPool(PRODUCER_COUNT + CONSUMER_COUNT);

        for (int i = 1; i <= CONSUMER_COUNT; i++) {
            consumerCounters.put(i, new AtomicInteger(0));
        }

        for (int i = 1; i <= PRODUCER_COUNT; i++) {
            executor.execute(new Producer(i));
        }

        for (int i = 1; i <= CONSUMER_COUNT; i++) {
            executor.execute(new Consumer(i));
        }

        executor.shutdown();

        new Thread(() -> {
            try {
                executor.awaitTermination(60, TimeUnit.SECONDS);
                SwingUtilities.invokeLater(() -> {
                    logArea.append("\n========== RAPORT FINAL ==========\n");
                    logArea.append("Total produse: " + totalProduced.get() + "\n");
                    logArea.append("Total consumate: " + totalConsumed.get() + "\n");
                    consumerCounters.forEach((id, count) ->
                            logArea.append("Consumator " + id + ": " + count.get() + " obiecte consumate\n"));
                    logArea.append("==================================\n");
                });
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    static class Producer implements Runnable {
        private final int id;
        private final Random random = new Random();

        Producer(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            try {
                while (totalProduced.get() < TOTAL_OBJECTS) {
                    int item = 2 * (random.nextInt(50) + 1);

                    buffer.put(item);
                    int produced = totalProduced.incrementAndGet();

                    SwingUtilities.invokeLater(() -> logArea.append("[Producător " + id + "] a produs: " + item
                            + " | Total produse: " + produced
                            + " | Capacitate curentă: " + buffer.size() + "/" + BUFFER_CAPACITY + "\n"));

                    if (produced >= TOTAL_OBJECTS) {
                        SwingUtilities.invokeLater(() -> logArea.append("Producătorul " + id + " s-a finalizat\n"));
                        break;
                    }
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    static class Consumer implements Runnable {
        private final int id;

        Consumer(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            try {
                while (consumerCounters.get(id).get() < CONSUMER_GOAL) {
                    int item = buffer.take();
                    consumerCounters.get(id).incrementAndGet();
                    int consumed = totalConsumed.incrementAndGet();

                    SwingUtilities.invokeLater(() -> logArea.append("[Consumator " + id + "] a consumat: " + item
                            + " | Total consumate: " + consumerCounters.get(id).get()
                            + " | În depozit: " + buffer.size()
                            + " | Total global: " + consumed + "\n"));

                    Thread.sleep(200);
                }

                SwingUtilities.invokeLater(() -> logArea.append("[Consumator " + id + "] a fost îndestulat cu " + CONSUMER_GOAL + " obiecte!\n"));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
