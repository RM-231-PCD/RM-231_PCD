/*
Lucrarea de laborator 6.
Sincronizarea firelor de execuție prin clase de sincronizare în problema producător-
consumator
Scopul lucrării
Scopul acestei lucrări de laborator este de a familiariza studenții cu programarea concurentă în
Java, în special cu sincronizarea firelor de execuție folosind mecanisme dedicate. Prin
intermediul problemei clasice Producător-Consumator, lucrarea își propune să demonstreze atât
aspectele teoretice, cât și pe cele practice ale sincronizării. Studenții vor înțelege necesitatea
coordonării firelor care accesează resurse comune și vor învăța să utilizeze diferite clase de
sincronizare oferite de Java (precum synchronized, ReentrantLock, Condition, BlockingQueue)
pentru a asigura cooperarea corectă între fire. Lucrarea urmărește dezvoltarea abilităților de a
identifica secțiunile critice din cod, de a preveni condițiile de cursă și blocajele, și de a implementa
soluții concurrente robuste și eficiente



Sunt dați 3 producători care generează aleatoriu 1 obiecte care sunt consumate de 2
consumatori. De afişat informaţia despre producerea şi consumarea obiectelor, mesajele despre
cazurile când “depozitul e gol sau plin”. Dimensiunea depozitului este 11. Producătorii comlectează
depozitul cu D obiecte. Consumatorii nu pot consuma, până când depozitul nu va fi plin. După
care consumatorii consumă. Producărorii nu pot produce, până când depozitul nu va fi gol. Toate
operaţiile se efectuează până când nu vor fi produse și consumate 66 obiecte.
Tip Obiecte Consoane
Sarcina de realizat în grup. Un membru a grupului realizează sarcina și sincronizarea
producătorului. Al doilea membru a grupului realizează sarcina și sincronizarea consumatorului.
Sarcina poate fi realizată prin thread-uri clasice sau prin pool-uri de thread-uri.
Sincronizarea de realizat numai prin clase se sincronizare din Java.

*/


package Lab6;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.*;
import java.util.concurrent.ArrayBlockingQueue;

public class Lab6 {
    static final int DEPOZIT = 11;
    static final int PRODUCERS = 3;
    static final int CONSUMERS = 2;
    static final int OBJECTS_PER_PRODUCER = 22; // 66 obiecte
    static final int TOTAL_OBJECTS = PRODUCERS * OBJECTS_PER_PRODUCER;

    static final BlockingQueue<Object> depozit = new ArrayBlockingQueue<>(DEPOZIT);



    public static void main(String[] args) {
        ExecutorService producerPool = Executors.newFixedThreadPool(PRODUCERS);
        ExecutorService consumerPool = Executors.newFixedThreadPool(CONSUMERS);

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
                for (int i = 0; i < OBJECTS_PER_PRODUCER; i++) {
                    char obiect = CONSOANE[random.nextInt(CONSOANE.length)];
                    depozit.put(obiect);
                    System.out.println("Producător " + id + " a produs obiectul: " + obiect + ". Dimensiunea depozitului: " + depozit.size());
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Producător " + id + " a terminat producția.");
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
                for (int i = 0; i < (TOTAL_OBJECTS / CONSUMERS); i++) {
                    Object obiect = depozit.take();
                    System.out.println("Consumator " + id + " a consumat obiectul: " + obiect + ". Dimensiunea depozitului: " + depozit.size());
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Consumator " + id + " a terminat consumul.");
        }
    }


}