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

        Depozit depozit = new Depozit(11, 3);

        Thread p1 = new Thread(new Producator(depozit, 1));
        Thread p2 = new Thread(new Producator(depozit, 2));
        Thread p3 = new Thread(new Producator(depozit, 3));

        Thread c1 = new Thread(new Consumator(depozit, 1));
        Thread c2 = new Thread(new Consumator(depozit, 2));

        p1.start();
        p2.start();
        p3.start();

        c1.start();
        c2.start();
    }

    static class Producator implements Runnable {
        private final Depozit depozit;
        private final int id;

        public Producator(Depozit depozit, int id) {
            this.depozit = depozit;
            this.id = id;
        }

        @Override
        public void run() {
            for (int i = 0; i < 6; i++) {
                depozit.produce(id);
            }
            depozit.anuntaTerminareProductie();
        }
    }

    static class Consumator implements Runnable {
        private final Depozit depozit;
        private final int id;
        private int consumedCount = 0;

        public Consumator(Depozit depozit, int id) {
            this.depozit = depozit;
            this.id = id;
        }

        @Override
        public void run() {
            for (int i = 0; i < 12; i++) {
                char item = depozit.consume();
                if (item == 0) break;
                consumedCount++;
                System.out.println("Consumator " + id +
                        " a consumat: " + item +
                        " total consumat de el: " + consumedCount);
            }
        }
    }

    static class Depozit {
        private final int capacity;
        private final Queue<Character> items = new LinkedList<>();
        private final Random rand = new Random();
        private int producatoriRamas;

        private static final char[] CONSOANE =
                "BCDFGHJKLMNPQRSTVWXYZ".toCharArray();

        public Depozit(int capacity, int nrProducatori) {
            this.capacity = capacity;
            this.producatoriRamas = nrProducatori;
        }

        public synchronized void produce(int producerId) {
            while (items.size() + 2 > capacity) {
                try { wait(); } catch (InterruptedException ignored) {}
            }

            char c1 = CONSOANE[rand.nextInt(CONSOANE.length)];
            char c2 = CONSOANE[rand.nextInt(CONSOANE.length)];

            items.add(c1);
            items.add(c2);

            System.out.println("Producator " + producerId +
                    " a produs: " + c1 + ", " + c2 +
                    " total în depozit: " + items.size());

            notifyAll();
        }

        public synchronized char consume() {
            while (items.isEmpty() && producatoriRamas > 0) {
                try { wait(); } catch (InterruptedException ignored) {}
            }

            if (items.isEmpty() && producatoriRamas == 0) {
                return 0;
            }

            char item = items.remove();
            notifyAll();
            return item;
        }

        public synchronized void anuntaTerminareProductie() {
            producatoriRamas--;
            if (producatoriRamas == 0) {
                notifyAll();
            }
        }
    }
}
