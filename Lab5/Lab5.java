
package Lab5;
/*
Tema lucrării: Pool-uri de fire de execuție în Java. Sincronizarea thread-urilor în Java.
Scopul lucrării:
Este studierea și aplicarea mecanismelor de gestionare și sincronizare a firelor de execuție în Java, prin
utilizarea pool-urilor de thread-uri și a conceptului clasic producător–consumator, pentru a asigura
execuția concurentă eficientă și sigură a proceselor.

Sunt dați 3 producători care generează aleatoriu 2 obiecte care sunt consumate de 2
consumatori. De afişat informaţia despre producerea şi consumarea obiectelor, mesajele despre
cazurile când “depozitul e gol sau plin”. Toate operaţiile se efectuează până când fiecare
consumator este îndestulat cu 12 obiecte.
Dimensiunea depozitului este 11.
Tip Obiecte Consoane


*/


import java.util.concurrent.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Lab5 {
        final static int DEPOZIT = 11;
        static final BlockingDeque<Object> depozit = new LinkedBlockingDeque<>(DEPOZIT);
        final static int PRODUCERS = 3;
        final static int CONSUMERS = 2;
        final static int OBJECTS_PER_CONSUMER = 12;
        final static int TOTAL_OBJECTS = 24;  // 2 consumatori * 12 obiecte fiecare


        //contor pentru obiectele produse si consumate
        static final AtomicInteger totalProduced = new AtomicInteger(0);
        static final AtomicInteger totalConsumed = new AtomicInteger(0);
        static final Map<Integer, AtomicInteger> consumerObjectCount = new ConcurrentHashMap<>();

        public static void main(String[] args) {
            ExecutorService producerPool = Executors.newFixedThreadPool(PRODUCERS);
            ExecutorService consumerPool = Executors.newFixedThreadPool(CONSUMERS);

            // Initialize consumer object count
            for (int i = 0; i < CONSUMERS; i++) {
                consumerObjectCount.put(i, new AtomicInteger(0));
            }

            // Start producers
            for (int i = 0; i < PRODUCERS; i++) {
                producerPool.execute(new Producer(i));
            }
            producerPool.shutdown();

            // Start consumers
            for (int i = 0; i < CONSUMERS; i++) {
                consumerPool.execute(new Consumer(i));
            }
            consumerPool.shutdown();
            try {
                consumerPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Total obiecte produse: " + totalProduced.get());
            System.out.println("Total obiecte consumate: " + totalConsumed.get());
        }
        static class Producer implements Runnable {
            private final int id;
            private final Random random = new Random();
            private final char[] CONSOANE = {'B', 'C', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'X', 'Y', 'Z'};

            public Producer(int id) {
                this.id = id;
            }

            @Override
            public void run() {
                try {
                    while (totalProduced.get() < TOTAL_OBJECTS) {
                        char obiect = CONSOANE[random.nextInt(CONSOANE.length)];
                        if (depozit.remainingCapacity() == 0) {
                            System.out.println("Depozitul este plin. Producător " + id + " așteaptă...");
                        }
                        depozit.put(obiect);
                        int produced = totalProduced.incrementAndGet();
                        System.out.println("Producător " + id + " a produs obiectul: " + obiect + ". Total produse: " + produced);
                    }
                    if (totalProduced.get() >= TOTAL_OBJECTS) {
                        System.out.println("Producător " + id + " a terminat producția.");
                    }   
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        static class Consumer implements Runnable {
            private final int id;

            public Consumer(int id) {
                this.id = id;
            }

            @Override
            public void run() {
                try {
                    while (consumerObjectCount.get(id).get() < OBJECTS_PER_CONSUMER) {
                        if (depozit.isEmpty()) {
                            System.out.println("Depozitul este gol. Consumator " + id + " așteaptă...");
                        }
                        char obiect = (char) depozit.take();
                        totalConsumed.incrementAndGet();
                        int consumed = consumerObjectCount.get(id).incrementAndGet();
                        System.out.println("Consumator " + id + " a consumat obiectul: " + obiect + ". Total consumate: " + consumed);
                        Thread.sleep(100); 
                    }
                    System.out.println("Consumator " + id + " a terminat consumul.");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
