import javax.swing.*;
import java.awt.*;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.ArrayList;

public class App {
    // Список запущенных потоков (чтобы можно было управлять)
    private static final List<Thread> threads = new ArrayList<>();
    private static ThreadGroup mainGroup;
    private static ThreadGroup G1;
    private static ThreadGroup G2;
    private static ThreadGroup G3;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(App::createAndShowGui);
    }

    private static void createAndShowGui() {
        JFrame frame = new JFrame("ThreadGroups Demo");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        JTextArea logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(logArea);
        frame.add(scroll, BorderLayout.CENTER);

        // Перенаправляем System.out в лог
        PrintStream ps = new PrintStream(new TextAreaOutputStream(logArea));
        System.setOut(ps);
        System.setErr(ps);

        JPanel controls = new JPanel();
        JButton startBtn = new JButton("Start");
        JButton statsBtn = new JButton("Show Stats");
        JButton stopBtn = new JButton("Interrupt");

        controls.add(startBtn);
        controls.add(statsBtn);
        controls.add(stopBtn);
        frame.add(controls, BorderLayout.NORTH);

        startBtn.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                startThreads();
            }
        });
        statsBtn.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                showStats();
            }
        });
        stopBtn.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                interruptThreads();
            }
        });

        frame.setVisible(true);
    }

    private static void initGroups() {
        mainGroup = Thread.currentThread().getThreadGroup();
        G1 = new ThreadGroup(mainGroup, "G1");
        G2 = new ThreadGroup(mainGroup, "G2");
        G3 = new ThreadGroup(G1, "G3");
    }

    private static void startThreads() {
        // Если уже запущены — не запускать повторно
        synchronized (threads) {
            if (!threads.isEmpty()) {
                System.out.println("Потоки уже запущены.");
                return;
            }

            initGroups();

            // В G3 создаём потоки: Tha(3), Thb(3), Thc(3), Thd(3)
            threads.add(createThread(G3, "Tha", 3));
            threads.add(createThread(G3, "Thb", 3));
            threads.add(createThread(G3, "Thc", 3));
            threads.add(createThread(G3, "Thd", 3));

            // В G2 создаём: Th1(4), Th2(5), Th3(5)
            threads.add(createThread(G2, "Th1", 4));
            threads.add(createThread(G2, "Th2", 5));
            threads.add(createThread(G2, "Th3", 5));

            // В main (или в mainGroup) создаём Th1(7), Th2(7), ThA(3)
            threads.add(createThread(mainGroup, "Th1_main", 7));
            threads.add(createThread(mainGroup, "Th2_main", 7));
            threads.add(createThread(mainGroup, "ThA_main", 3));

            // Запускаем
            for (Thread t : threads) t.start();

            // В отдельном наблюдателе ждём их завершения и очищаем список
            new Thread(() -> {
                for (Thread t : threads) {
                    try {
                        t.join();
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
                System.out.println("Все потоки завершили работу.");
                synchronized (threads) {
                    threads.clear();
                }
            }, "watcher").start();
        }
    }

    private static void showStats() {
        if (mainGroup == null) initGroups();
        System.out.println("Статистика групп:");
        printGroupInfo(mainGroup, "mainGroup");
        printGroupInfo(G1, "G1");
        printGroupInfo(G2, "G2");
        printGroupInfo(G3, "G3");
    }

    private static void interruptThreads() {
        synchronized (threads) {
            if (threads.isEmpty()) {
                System.out.println("Нет запущенных потоков для прерывания.");
                return;
            }
            System.out.println("Прерываем все потоки...");
            for (Thread t : threads) t.interrupt();
        }
    }

    private static Thread createThread(ThreadGroup group, String name, int seconds) {
        return new Thread(group, () -> {
            String fullName = Thread.currentThread().getName();
            System.out.printf("[%s] started in group '%s', will run %d seconds...%n",
                    fullName, group.getName(), seconds);
            try {
                for (int i = 0; i < seconds; i++) {
                    Thread.sleep(1000);
                    System.out.printf("[%s] %d/%d sec%n", fullName, i + 1, seconds);
                }
            } catch (InterruptedException e) {
                System.out.printf("[%s] interrupted%n", fullName);
                Thread.currentThread().interrupt();
            }
            System.out.printf("[%s] finished.%n", fullName);
        }, name);
    }

    private static void printGroupInfo(ThreadGroup group, String label) {
        int activeCount = group.activeCount();
        int activeGroupCount = group.activeGroupCount();
        System.out.printf("Group %s (name='%s'): activeThreads=%d, activeSubgroups=%d%n",
                label, group.getName(), activeCount, activeGroupCount);
    }

    // OutputStream, который пишет в JTextArea
    private static class TextAreaOutputStream extends OutputStream {
        private final JTextArea textArea;

        TextAreaOutputStream(JTextArea ta) { this.textArea = ta; }

        @Override
        public void write(int b) {
            write(new byte[]{(byte) b}, 0, 1);
        }

        @Override
        public void write(byte[] b, int off, int len) {
            final String text = new String(b, off, len);
            SwingUtilities.invokeLater(() -> textArea.append(text));
        }
    }
}
