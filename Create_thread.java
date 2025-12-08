import java.util.concurrent.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Create_thread{
    private static final int D_marima_depozit = 7;
    private static final BlockingQueue<Integer> buffer = new ArrayBlockingQueue<>(D_marima_depozit);

    private static final int Z_tinta = 4;
    private static final int X_producatori = 2;
    private static final int Y_consumatori = 4;
    private static final int F_obiecte_ciclu = 2;
    private static final int TOTAL_OBIECTE = Z_tinta * Y_consumatori;

    private static final AtomicInteger totalProduced = new AtomicInteger(0);
    private static final AtomicInteger totalConsumed = new AtomicInteger(0);


    private static final Map<Integer, AtomicInteger> consumerCounters = new ConcurrentHashMap<>();
    private static final Map<Integer, AtomicInteger> producerCounters = new ConcurrentHashMap<>();

    public static void main(String[] args) {

        System.out.println("=== PROGRAM STARTED ===");
        System.out.println("Parameters: X=" + X_producatori + " Y=" + Y_consumatori +
                " Z=" + Z_tinta + " D=" + D_marima_depozit + " F=" + F_obiecte_ciclu);

        ExecutorService executor = Executors.newFixedThreadPool(X_producatori + Y_consumatori);

        for (int i = 1; i <= Y_consumatori; i++) {
            consumerCounters.put(i, new AtomicInteger(0));
        }

        for (int i = 1; i <= X_producatori; i++) {
            producerCounters.put(i, new AtomicInteger(0));
        }

        for (int i = 1; i <= X_producatori; i++) {
            executor.execute(new Producer(i));
        }

        for (int i = 1; i <= Y_consumatori; i++) {
            executor.execute(new Consumer(i));
        }

        executor.shutdown();

        try {
            executor.awaitTermination(60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n========== RAPORT FINAL ==========");

        System.out.println("Total produse: " + totalProduced.get());

        System.out.println("Total consumate: " + totalConsumed.get());

        consumerCounters.forEach((id, count) ->

                System.out.println("Consumator " + id + ": " + count.get() + " obiecte consumate"));

        producerCounters.forEach((id, count) ->

                System.out.println("Producător " + id + ": " + count.get() + " obiecte produse"));

        System.out.println("Obiecte rămase în buffer: " + buffer.size());
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
                while (totalProduced.get() < TOTAL_OBIECTE) {
                    List<Integer> items = new ArrayList<>();
                    for (int i = 0; i < F_obiecte_ciclu; i++) {
                        items.add(generateOddNumber());
                    }

                    if (buffer.remainingCapacity() < F_obiecte_ciclu) {
                        System.out.println("[Producător " + id + "] Depozitul e plin, așteaptă...");
                    }

                    for (Integer item : items) {
                        buffer.put(item);
                        int produced = totalProduced.incrementAndGet();
                        int producerTotal = producerCounters.get(id).incrementAndGet();

                        System.out.println(" [Producător " + id + "] a produs: " + item +
                                " | Total producător: " + producerTotal +
                                " | Total global: " + produced +
                                " | Capacitate curentă: " + buffer.size() + "/" + D_marima_depozit);

                        if (produced >= TOTAL_OBIECTE) {
                            System.out.println(" Producătorul " + id + " s-a finalizat");
                            break;
                        }
                    }

                    Thread.sleep(200 + random.nextInt(100));
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        private int generateOddNumber() {
            int num;
            do {
                num = random.nextInt(19) + 1;
            } while (num % 2 == 0);
            return num;
        }
    }

    static class Consumer implements Runnable {
        private final int id;
        private final Random random = new Random();

        Consumer(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            try {
                while (consumerCounters.get(id).get() < Z_tinta) {
                    if (buffer.isEmpty()) {
                        System.out.println(" [Consumator " + id + "] Depozitul e gol, așteaptă...");
                    }

                    Integer item = buffer.take();
                    int consumerTotal = consumerCounters.get(id).incrementAndGet();
                    int consumed = totalConsumed.incrementAndGet();

                    System.out.println(" [Consumator " + id + "] a consumat: " + item +
                            " | Progres: " + consumerTotal + "/" + Z_tinta +
                            " | În depozit: " + buffer.size() + "/" + D_marima_depozit +
                            " | Total global: " + consumed);

                    if (consumerTotal >= Z_tinta) {
                        System.out.println(" [Consumator " + id + "] a fost îndestulat cu " + Z_tinta + " obiecte!");
                        break;
                    }

                    Thread.sleep(150 + random.nextInt(100));
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}