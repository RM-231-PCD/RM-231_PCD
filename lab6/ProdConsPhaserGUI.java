import javax.swing.*;
import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ProdConsPhaserGUI {

    // LR6 Varianta 5: X=2, Y=5, Z=60, D=12, Tip: numere pare, F=1
    static final int CAPACITY = 12;      // D
    static final int TARGET_TOTAL = 60;  // Z
    static final int PRODUCERS = 2;      // X
    static final int CONSUMERS = 5;      // Y

    // ---------------- GUI ----------------
    static class AppUI {
        private final JFrame frame = new JFrame("LR6 Producer-Consumer (Phaser) - Varianta 5");

        private final JTextArea logArea = new JTextArea(18, 70);

        private final JLabel phaseLabel = new JLabel("Faza: 0 (FILL)");
        private final JLabel stockLabel = new JLabel("Stoc: 0/" + CAPACITY);
        private final JLabel producedLabel = new JLabel("Total produs: 0/" + TARGET_TOTAL);
        private final JLabel consumedLabel = new JLabel("Total consumat: 0/" + TARGET_TOTAL);

        private final JProgressBar producedBar = new JProgressBar(0, TARGET_TOTAL);
        private final JProgressBar consumedBar = new JProgressBar(0, TARGET_TOTAL);

        AppUI() {
            logArea.setEditable(false);
            logArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

            producedBar.setStringPainted(true);
            consumedBar.setStringPainted(true);

            JPanel top = new JPanel(new GridLayout(2, 2, 8, 6));
            top.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            top.add(phaseLabel);
            top.add(stockLabel);
            top.add(producedLabel);
            top.add(consumedLabel);

            JPanel bars = new JPanel(new GridLayout(2, 1, 8, 6));
            bars.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
            bars.add(producedBar);
            bars.add(consumedBar);

            JScrollPane scroll = new JScrollPane(logArea);

            JButton clearBtn = new JButton("Curata log");
            clearBtn.addActionListener(e -> SwingUtilities.invokeLater(() -> logArea.setText("")));

            JPanel south = new JPanel(new BorderLayout());
            south.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
            south.add(scroll, BorderLayout.CENTER);

            JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            btnPanel.add(clearBtn);
            south.add(btnPanel, BorderLayout.SOUTH);

            frame.setLayout(new BorderLayout());
            frame.add(top, BorderLayout.NORTH);
            frame.add(bars, BorderLayout.CENTER);
            frame.add(south, BorderLayout.SOUTH);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }

        void show() {
            SwingUtilities.invokeLater(() -> frame.setVisible(true));
        }

        void log(String msg) {
            SwingUtilities.invokeLater(() -> {
                logArea.append(msg + "\n");
                logArea.setCaretPosition(logArea.getDocument().getLength());
            });
        }

        void updatePhase(int phase) {
            String mode = (phase % 2 == 0) ? "FILL" : "DRAIN";
            SwingUtilities.invokeLater(() -> phaseLabel.setText("Faza: " + phase + " (" + mode + ")"));
        }

        void updateStats(int stock, int produced, int consumed) {
            SwingUtilities.invokeLater(() -> {
                stockLabel.setText("Stoc: " + stock + "/" + CAPACITY);

                producedLabel.setText("Total produs: " + produced + "/" + TARGET_TOTAL);
                consumedLabel.setText("Total consumat: " + consumed + "/" + TARGET_TOTAL);

                producedBar.setValue(produced);
                producedBar.setString("Produs: " + produced + "/" + TARGET_TOTAL);

                consumedBar.setValue(consumed);
                consumedBar.setString("Consumat: " + consumed + "/" + TARGET_TOTAL);
            });
        }
    }

    // ---------------- Model ----------------
    // Faza para = FILL, faza impara = DRAIN
    static class Depot {
        final Deque<Integer> buffer = new ArrayDeque<>(CAPACITY);
        final Random rnd = new Random();

        int producedTotal = 0;
        int consumedTotal = 0;
        volatile boolean done = false;

        final AppUI ui;

        private final ReentrantLock lock = new ReentrantLock(true);
        private final Condition notFull = lock.newCondition();
        private final Condition notEmpty = lock.newCondition();

        // чтобы не спамить "plin/gol" слишком часто
        private boolean printedFull = false;
        private boolean printedEmpty = false;

        Depot(AppUI ui) {
            this.ui = ui;
        }

        private void printFullOnce() {
            if (!printedFull) {
                ui.log(">>> Depozitul e plin (" + buffer.size() + "/" + CAPACITY + ")");
                printedFull = true;
            }
        }

        private void printEmptyOnce() {
            if (!printedEmpty) {
                ui.log("<<< Depozitul e gol (0/" + CAPACITY + ")");
                printedEmpty = true;
            }
        }

        // produce 1 obiect (F=1)
        // ВАЖНО: НЕ await. Если полный - вернуть false и пусть поток идет на Phaser.
        boolean tryProduceOne(String name) {
            lock.lock();
            try {
                if (done) return false;
                if (producedTotal >= TARGET_TOTAL) return false;

                if (buffer.size() == CAPACITY) {
                    printFullOnce();
                    return false;
                }

                int value = 2 * (1 + rnd.nextInt(50)); // numar par intre 2 si 100
                buffer.addLast(value);
                producedTotal++;

                // раз склад не полный - сбросить флаг полного
                if (buffer.size() < CAPACITY) printedFull = false;

                ui.log(name + " a produs " + value
                        + " | stoc=" + buffer.size() + "/" + CAPACITY
                        + " | totalProd=" + producedTotal);

                ui.updateStats(buffer.size(), producedTotal, consumedTotal);

                // после добавления буфер не пуст => можно будить потребителя
                printedEmpty = false;
                notEmpty.signal();

                return true;
            } finally {
                lock.unlock();
            }
        }

        // consume 1 obiect (F=1)
        // ВАЖНО: НЕ await. Если пусто - вернуть false и пусть поток идет на Phaser.
        boolean tryConsumeOne(String name) {
            lock.lock();
            try {
                if (done) return false;

                if (buffer.isEmpty()) {
                    printEmptyOnce();
                    return false;
                }

                int value = buffer.removeFirst();
                consumedTotal++;

                // раз не пусто - сбросить флаг пустого
                if (!buffer.isEmpty()) printedEmpty = false;

                ui.log(name + " a consumat " + value
                        + " | stoc=" + buffer.size() + "/" + CAPACITY
                        + " | totalCons=" + consumedTotal);

                ui.updateStats(buffer.size(), producedTotal, consumedTotal);

                // после извлечения буфер не полный => можно будить производителя
                printedFull = false;
                notFull.signal();

                // условие завершения
                if (consumedTotal >= TARGET_TOTAL && buffer.isEmpty()) {
                    done = true;
                    ui.log("=== Final: totalCons>=Z si depozitul gol. ===");
                    ui.updateStats(buffer.size(), producedTotal, consumedTotal);

                    // разбудить всех на всякий случай
                    notFull.signalAll();
                    notEmpty.signalAll();
                }

                return true;
            } finally {
                lock.unlock();
            }
        }

        int getProducedTotal() {
            lock.lock();
            try {
                return producedTotal;
            } finally {
                lock.unlock();
            }
        }

        int getConsumedTotal() {
            lock.lock();
            try {
                return consumedTotal;
            } finally {
                lock.unlock();
            }
        }

        int getStock() {
            lock.lock();
            try {
                return buffer.size();
            } finally {
                lock.unlock();
            }
        }
    }

    static class Producer extends Thread {
        private final Depot depot;
        private final Phaser phaser;

        Producer(Depot depot, Phaser phaser, String name) {
            super(name);
            this.depot = depot;
            this.phaser = phaser;
        }

        @Override
        public void run() {
            try {
                while (!depot.done) {
                    int phase = phaser.getPhase();

                    if (phase % 2 == 0) { // FILL
                        // производим пока получается (пока не full или не достигли Z)
                        while (!depot.done) {
                            boolean ok = depot.tryProduceOne(getName());
                            if (!ok) break;
                        }
                        phaser.arriveAndAwaitAdvance();
                    } else {
                        phaser.arriveAndAwaitAdvance();
                    }
                }
            } finally {
                try { phaser.arriveAndDeregister(); } catch (IllegalStateException ignored) {}
            }
        }
    }

    static class Consumer extends Thread {
        private final Depot depot;
        private final Phaser phaser;

        Consumer(Depot depot, Phaser phaser, String name) {
            super(name);
            this.depot = depot;
            this.phaser = phaser;
        }

        @Override
        public void run() {
            try {
                while (!depot.done) {
                    int phase = phaser.getPhase();

                    if (phase % 2 == 1) { // DRAIN
                        // потребляем пока получается (пока не empty или done)
                        while (!depot.done) {
                            boolean ok = depot.tryConsumeOne(getName());
                            if (!ok) break;
                        }
                        phaser.arriveAndAwaitAdvance();
                    } else {
                        phaser.arriveAndAwaitAdvance();
                    }
                }
            } finally {
                try { phaser.arriveAndDeregister(); } catch (IllegalStateException ignored) {}
            }
        }
    }

    // ---------------- main ----------------
    public static void main(String[] args) throws InterruptedException {
        AppUI ui = new AppUI();
        ui.show();

        Depot depot = new Depot(ui);

        Phaser phaser = new Phaser(PRODUCERS + CONSUMERS) {
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                ui.updatePhase(phase + 1);
                return depot.done || registeredParties == 0;
            }
        };

        Thread[] producers = new Thread[PRODUCERS];
        Thread[] consumers = new Thread[CONSUMERS];

        for (int i = 0; i < PRODUCERS; i++) {
            producers[i] = new Producer(depot, phaser, "Producator-" + (i + 1));
        }
        for (int i = 0; i < CONSUMERS; i++) {
            consumers[i] = new Consumer(depot, phaser, "Consumator-" + (i + 1));
        }

        ui.log("Start: X=" + PRODUCERS + ", Y=" + CONSUMERS + ", Z=" + TARGET_TOTAL + ", D=" + CAPACITY);
        ui.log("Faza 0 (FILL): consumatorii asteapta, producatorii umplu depozitul.");

        for (Thread t : producers) t.start();
        for (Thread t : consumers) t.start();

        for (Thread t : producers) t.join();
        for (Thread t : consumers) t.join();

        ui.log("=== Gata: au fost produse si consumate " + TARGET_TOTAL + " obiecte ===");
    }
}
