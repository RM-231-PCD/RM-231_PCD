import java.util.concurrent.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class PCDlab5{
    private static final int BUFFER_CAPACITY = 7; 
    private static final BlockingQueue<Integer> buffer = new ArrayBlockingQueue<>(BUFFER_CAPACITY);
    
    private static final int CONSUMER_GOAL = 4; 
    private static final int PRODUCER_COUNT = 2; 
    private static final int CONSUMER_COUNT = 4; 
    private static final int ITEMS_PER_PRODUCTION = 2; 
    private static final int TOTAL_OBJECTS = CONSUMER_GOAL * CONSUMER_COUNT; 
    
    // Contoare atomice
    private static final AtomicInteger totalProduced = new AtomicInteger(0);
    private static final AtomicInteger totalConsumed = new AtomicInteger(0);
    private static final Map<Integer, AtomicInteger> consumerCounters = new ConcurrentHashMap<>();
    private static final Map<Integer, AtomicInteger> producerCounters = new ConcurrentHashMap<>();
    
    public static void main(String[] args) {
        System.out.println("=== PROGRAM STARTED ===");
        System.out.println("Parameters: X=" + PRODUCER_COUNT + " Y=" + CONSUMER_COUNT + 
                         " Z=" + CONSUMER_GOAL + " D=" + BUFFER_CAPACITY + " F=" + ITEMS_PER_PRODUCTION);
        
        ExecutorService executor = Executors.newFixedThreadPool(PRODUCER_COUNT + CONSUMER_COUNT);
        
        // Inițializăm contoarele pentru consumatori
        for (int i = 1; i <= CONSUMER_COUNT; i++) {
            consumerCounters.put(i, new AtomicInteger(0));
        }
        
        // Inițializăm contoarele pentru producători
        for (int i = 1; i <= PRODUCER_COUNT; i++) {
            producerCounters.put(i, new AtomicInteger(0));
        }
        
        // Pornim producătorii
        for (int i = 1; i <= PRODUCER_COUNT; i++) {
            executor.execute(new Producer(i));
        }
        
        // Pornim consumatorii
        for (int i = 1; i <= CONSUMER_COUNT; i++) {
            executor.execute(new Consumer(i));
        }
        
        executor.shutdown();
        
        try {
            // Așteptăm ca toate thread-urile să se termine
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
        System.out.println("==================================");
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
                    List<Integer> items = new ArrayList<>();
                    for (int i = 0; i < ITEMS_PER_PRODUCTION; i++) {
                        items.add(generateOddNumber());
                    }
                    
                    if (buffer.remainingCapacity() < ITEMS_PER_PRODUCTION) {
                        System.out.println("[Producător " + id + "] Depozitul e plin, așteaptă...");
                    }
                    
                    // Adăugăm fiecare obiect în buffer
                    for (Integer item : items) {
                        buffer.put(item);
                        int produced = totalProduced.incrementAndGet();
                        int producerTotal = producerCounters.get(id).incrementAndGet();
                        
                        System.out.println("[Producător " + id + "] a produs: " + item +
                                " | Total producător: " + producerTotal +
                                " | Total global: " + produced +
                                " | Capacitate curentă: " + buffer.size() + "/" + BUFFER_CAPACITY);
                        
                        // Verificăm dacă s-a atins totalul necesar
                        if (produced >= TOTAL_OBJECTS) {
                            System.out.println("Producătorul " + id + " s-a finalizat");
                            break;
                        }
                    }
                    
                    Thread.sleep(200 + random.nextInt(100)); // Pauză între producții
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        private int generateOddNumber() {
            // Generăm numere impare între 1 și 19
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
                while (consumerCounters.get(id).get() < CONSUMER_GOAL) {
                    // Verificăm dacă depozitul este gol
                    if (buffer.isEmpty()) {
                        System.out.println("[Consumator " + id + "] Depozitul e gol, așteaptă...");
                    }
                    
                    Integer item = buffer.take();
                    int consumerTotal = consumerCounters.get(id).incrementAndGet();
                    int consumed = totalConsumed.incrementAndGet();
                    
                    System.out.println("[Consumator " + id + "] a consumat: " + item +
                            " | Progres: " + consumerTotal + "/" + CONSUMER_GOAL +
                            " | În depozit: " + buffer.size() + "/" + BUFFER_CAPACITY +
                            " | Total global: " + consumed);
                    
                    // Verificăm dacă consumatorul a fost îndestulat
                    if (consumerTotal >= CONSUMER_GOAL) {
                        System.out.println("[Consumator " + id + "] a fost îndestulat cu " + CONSUMER_GOAL + " obiecte!");
                        break;
                    }
                    
                    Thread.sleep(150 + random.nextInt(100)); // Pauză între consumații
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}