import java.util.*;

public class Create_Thread {
    private static final int X_producatori = 2;
    private static final int Y_consumatori = 4;
    private static final int Z_tinta = 4;
    private static final int D_marime_depozit = 7;
    private static final int F_obiecte_ciclu = 2;

    public static void main(String[] args) {
        System.out.println("Parametri: X=" + X_producatori + " Y=" + Y_consumatori +
                " Z=" + Z_tinta + " D=" + D_marime_depozit);

        Store store = new Store(D_marime_depozit);

        Producer[] producers = new Producer[X_producatori];
        for (int i = 0; i < X_producatori; i++) {
            producers[i] = new Producer(store, F_obiecte_ciclu);
            producers[i].setName("Producer-" + (i + 1));
            System.out.println("Creat " + producers[i].getName());
        }

        Consumer[] consumers = new Consumer[Y_consumatori];
        for (int i = 0; i < Y_consumatori; i++) {
            consumers[i] = new Consumer(store, Z_tinta);
            consumers[i].setName("Consumer-" + (i + 1));
            System.out.println("Creat " + consumers[i].getName());
        }

        for (Producer p : producers) p.start();
        for (Consumer c : consumers) c.start();

        try {
            System.out.println("Se asteapta consumatorii sa termine...");

            for (Consumer c : consumers) {
                c.join();
            }

            System.out.println("\nToti consumatorii sunt satisfacuti!");
            System.out.println("Se opresc consumatorii...");

            for (Producer p : producers) {
                p.interrupt();
            }

            for (Producer p : producers) {
                p.join();
            }

            System.out.println("\n   STATISTICI FINALE    ");
            int totalConsumed = 0;
            for (Consumer c : consumers) {
                System.out.println(c.getName() + " consumat " + c.getConsumedCount() + " obiecte");
                totalConsumed += c.getConsumedCount();
            }
            System.out.println("Total consumat: " + totalConsumed + " obiecte");
            System.out.println("S-au asteptat : " + (Y_consumatori * Z_tinta) + " obiecte");

            if (totalConsumed == Y_consumatori * Z_tinta) {
                System.out.println(" Sarcina completata cu succes!");
            }

            System.out.println(" PROGRAMUL S-A FINALIZAT ");

        } catch (InterruptedException e) {
            System.out.println("THREAD-UL PRINCIPAL A FOST INTRERUPT!");
        }
    }
}

class Store {
    private List<Integer> buffer = new ArrayList<>();
    private int bufferSize;
    private int totalConsumed = 0;

    public Store(int bufferSize) {
        this.bufferSize = bufferSize;
        System.out.println("S-A CREAT STORE CU MARIMEA D=" + bufferSize);
    }

    public synchronized int take(String consumerName) throws InterruptedException {

        while (buffer.isEmpty()) {
            System.out.println(" Store este gol! " + consumerName + " asteapta...");
            wait();
        }

        int item = buffer.remove(0);
        totalConsumed++;

        System.out.println(consumerName + " a luat din store : " + item + " (total consumat: " + totalConsumed + ")");

        showBufferStatus();

        notifyAll();

        return item;
    }

    public synchronized void put(String producerName, List<Integer> items) throws InterruptedException {

        while (buffer.size() + items.size() > bufferSize) {
            System.out.println(" Store este plin! " + producerName + " asteapta...");
            wait();
        }

        buffer.addAll(items);

        System.out.print(producerName + " a pus in store " + items.size() + " obiecte: ");
        for (int item : items) {
            System.out.print(item + " ");
        }
        System.out.println();

        showBufferStatus();

        notifyAll();
    }

    private void showBufferStatus() {
        if (buffer.isEmpty()) {
            System.out.println("Store status: Gol [0/" + bufferSize + "]");
        } else {
            System.out.print("Store status: " + buffer.size() + "/" + bufferSize + " obiecte [");
            for (int i = 0; i < buffer.size(); i++) {
                System.out.print(buffer.get(i));
                if (i < buffer.size() - 1) System.out.print(", ");
            }
            System.out.println("]");
        }
    }


}

class Producer extends Thread {
    private Store store;
    private int itemsPerProduction;
    private Random random = new Random();

    public Producer(Store store, int itemsPerProduction) {
        this.store = store;
        this.itemsPerProduction = itemsPerProduction;
    }

    @Override
    public void run() {
        System.out.println(getName() + " a inceput munca ");

        try {
            while (!isInterrupted()) {

                List<Integer> items = new ArrayList<>();
                for (int i = 0; i < itemsPerProduction; i++) {
                    items.add(generateOddNumber());
                }

                store.put(getName(), items);

                Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            System.out.println(getName() + " termina munca");
        }
        System.out.println("=== " + getName() + " A terminat munca ");
    }

    private int generateOddNumber() {
        int num;
        do {
            num = random.nextInt(19) + 1;
        } while (num % 2 == 0);
        return num;
    }
}

class Consumer extends Thread {
    private Store store;
    private int targetCount;
    private int consumed = 0;

    public Consumer(Store store, int targetCount) {
        this.store = store;
        this.targetCount = targetCount;
    }

    @Override
    public void run() {
        System.out.println(getName() + " a inceput munca (tinta: " + targetCount + " obiecte)");

        while (consumed < targetCount) {
            try {
                int item = store.take(getName());
                consumed++;
                System.out.println(">>> " + getName() + " consumat: " + item +
                        " (" + consumed + "/" + targetCount + ")");

                Thread.sleep(150);

            } catch (InterruptedException e) {
                System.out.println(getName() + " a fost intrerupt");
                break;
            }
        }

        System.out.println("=== " + getName() + " A terminat munca ");
    }

    public int getConsumedCount() {
        return consumed;
    }
}