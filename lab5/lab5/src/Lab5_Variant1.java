import java.util.concurrent.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Lab5_Variant1 {

    private static final int BUFFER_CAPACITY = 8;
    private static final int CONSUMER_GOAL = 11;
    private static final int PRODUCER_COUNT = 2;
    private static final int CONSUMER_COUNT = 3;
    private static final int PRODUCTION_BATCH = 2;

    private static final int TOTAL_OBJECTS = CONSUMER_GOAL * CONSUMER_COUNT;

    private static final BlockingQueue<Integer> buffer = new ArrayBlockingQueue<>(BUFFER_CAPACITY);
    private static final AtomicInteger totalProduced = new AtomicInteger(0);
    private static final AtomicInteger totalConsumed = new AtomicInteger(0);
    private static final Map<Integer, AtomicInteger> consumerCounters = new ConcurrentHashMap<>();

    public static void main(String[] args) {

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

        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        printFinalReport();
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
                    for (int j = 0; j < PRODUCTION_BATCH; j++) {
                        if (totalProduced.get() >= TOTAL_OBJECTS) break;

                        int number = (random.nextInt(50) + 1) * 2;

                        if (buffer.remainingCapacity() == 0) {
                            System.out.println("▲ [Производитель " + id + "] Склад заполнен, ожидание...");
                        }

                        buffer.put(number);
                        int produced = totalProduced.incrementAndGet();

                        System.out.println("██ [Производитель " + id + "] произвел: " + number +
                                " █ Всего произведено: " + produced +
                                " █ В буфере: " + buffer.size() + "/" + BUFFER_CAPACITY);
                    }
                    Thread.sleep(200 + random.nextInt(100));
                }
                System.out.println("✔ [Производитель " + id + "] завершил работу");
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
                Random random = new Random();
                while (consumerCounters.get(id).get() < CONSUMER_GOAL) {
                    if (buffer.isEmpty()) {
                        System.out.println("▼ [Потребитель " + id + "] Склад пуст, ожидание...");
                    }

                    int number = buffer.take();
                    consumerCounters.get(id).incrementAndGet();
                    int consumed = totalConsumed.incrementAndGet();

                    System.out.println("└ [Потребитель " + id + "] потребил: " + number +
                            " | Его счетчик: " + consumerCounters.get(id).get() +
                            " | В буфере: " + buffer.size() + "/" + BUFFER_CAPACITY +
                            " | Всего потреблено: " + consumed);

                    Thread.sleep(300 + random.nextInt(200));
                }
                System.out.println("✔ [Потребитель " + id + "] удовлетворен (" + CONSUMER_GOAL + " объектов)!");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private static void printFinalReport() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("ИТОГОВЫЙ ОТЧЕТ");
        System.out.println("=".repeat(40));
        System.out.println("Общее количество произведенных объектов: " + totalProduced.get());
        System.out.println("Общее количество потребленных объектов: " + totalConsumed.get());
        System.out.println("-".repeat(40));
        System.out.println("Потребление по потребителям:");
        consumerCounters.forEach((id, counter) ->
                System.out.println("  Потребитель " + id + ": " + counter.get() + " объектов")
        );
        System.out.println("=".repeat(40));
    }
}