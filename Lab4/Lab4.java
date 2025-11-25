/* 
    X=Producatori: 3
    Y=Consumatori: 2
    D=Depozit: 11
    Toate operaţiile se efectuează până când fiecare consumator
este îndestulat cu 12 obiecte.
F - fiecare producător produce câte 2 obiecte de fiecare
dată
Produc consoane

    */

package Lab4;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Lab4 {
    public static void main(String[] args) {

        Depozit depozit = new Depozit(11);

        Thread p1 = new Thread(new Producator(depozit, 1), "Producator-1");
        Thread p2 = new Thread(new Producator(depozit, 2), "Producator-2");
        Thread p3 = new Thread(new Producator(depozit, 3), "Producator-3");

        Thread c1 = new Thread(new Consumator(depozit, 1), "Consumator-1");
        Thread c2 = new Thread(new Consumator(depozit, 2), "Consumator-2");

        p1.start();
        p2.start();
        p3.start();
        c1.start();
        c2.start();
    }
    static class Producator implements Runnable {
        private Depozit depozit;
        private int id;

        public Producator(Depozit depozit, int id) {
            this.depozit = depozit;
            this.id = id;
        }

        @Override
        public void run() {
            for (int i = 0; i < 6; i++) { 
                depozit.produce(id);
            }
        }
    }
    static class Consumator implements Runnable {
        private Depozit depozit;
        private int id;

        public Consumator(Depozit depozit, int id) {
            this.depozit = depozit;
            this.id = id;
        }

        @Override
        public void run() {
            for (int i = 0; i < 12; i++) { 
                depozit.consume(id);
            }
        }
    }

    static class Depozit {
        private final int capacity;
        private final Queue<Character> items = new LinkedList<>();
        private final Random rand = new Random();

        private static final char[] CONSOANE = 
                "BCDFGHJKLMNPQRSTVWXYZ".toCharArray();

        public Depozit(int capacity) {
            this.capacity = capacity;
        }

        public synchronized void produce(int producerId) {
            while (items.size() + 2 > capacity) {
                try { wait(); } catch (InterruptedException e) {}
            }

            char c1 = CONSOANE[rand.nextInt(CONSOANE.length)];
            char c2 = CONSOANE[rand.nextInt(CONSOANE.length)];

            items.add(c1);
            items.add(c2);

            System.out.println("Producator " + producerId +
                    " a produs: " + c1 + ", " + c2 +
                    " | total: " + items.size());

            notifyAll();
        }

        public synchronized void consume(int consumerId) {
            while (items.isEmpty()) {
                try { wait(); } catch (InterruptedException e) {}
            }

            char item = items.remove();

            System.out.println("Consumator " + consumerId +
                    " a consumat: " + item +
                    " | total: " + items.size());

            notifyAll();
        }
    }
}

