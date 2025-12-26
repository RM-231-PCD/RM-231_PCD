package lab4;

import java.util.ArrayList;
import java.util.Random;

public class lab4 {
    public static void main(String[] args) throws InterruptedException {

        Storage storage = new Storage();

        Producer Nicolae_p1 = new Producer(storage, "Producător 1: Nicolae");
        Producer Nicolae_p2 = new Producer(storage, "Producător 2: Nicolae");
        Producer Nicolae_p3 = new Producer(storage, "Producător 3: Nicolae");

        Consumer Maxim_c1 = new Consumer(storage, "Consumator 1: Maxim");
        Consumer Maxim_c2 = new Consumer(storage, "Consumator 2: Maxim");
        Consumer Maxim_c3 = new Consumer(storage, "Consumator 3: Maxim");

        Nicolae_p1.setDaemon(true);
        Nicolae_p2.setDaemon(true);
        Nicolae_p3.setDaemon(true);

        Nicolae_p1.start();
        Nicolae_p2.start();
        Nicolae_p3.start();

        Maxim_c1.start();
        Maxim_c2.start();
        Maxim_c3.start();

        while (Maxim_c1.isAlive() || Maxim_c2.isAlive() || Maxim_c3.isAlive()) {}

        System.out.println("\nToți consumatorii au fost îndestulați cu 5 numere pare!");
    }
}

class Storage {
    private final ArrayList<Integer> depozit = new ArrayList<>();
    private final int MAX_SIZE = 12;
    private final int[] NUMERE_PARE = {2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30};

    private final Random rnd = new Random();

    public synchronized int get(String consumerName) {
        while (depozit.size() == 0) {
            System.out.println("Atenție! " + consumerName + ": Depozitul este gol → așteaptă...");
            try { wait(); } catch (InterruptedException ignored) {}
        }

        int numarPar = depozit.remove(depozit.size() - 1);
        System.out.println(consumerName + " a consumat numărul par: " + numarPar);

        afiseazaDepozit();

        notifyAll();
        return numarPar;
    }

    public synchronized void put(String producerName) {
        while (depozit.size() >= MAX_SIZE) {
            System.out.println("Atenție! " + producerName + ": Depozitul este plin → așteaptă...");
            try { wait(); } catch (InterruptedException ignored) {}
        }

        int n1 = NUMERE_PARE[rnd.nextInt(NUMERE_PARE.length)];
        int n2 = NUMERE_PARE[rnd.nextInt(NUMERE_PARE.length)];

        depozit.add(n1);
        depozit.add(n2);

        System.out.println(producerName + " a produs numerele pare: " + n1 + ", " + n2);

        afiseazaDepozit();

        notifyAll();
    }

    private void afiseazaDepozit() {
        if (depozit.size() == 0) {
            System.out.println("Depozitul este gol.");
            return;
        }

        System.out.print("Depozitul conține " + depozit.size() + " numere pare → ");
        for (int n : depozit) System.out.print(n + " ");
        System.out.println();
    }
}

class Producer extends Thread {
    private final Storage storage;
    private final String name;

    public Producer(Storage s, String name) {
        this.storage = s;
        this.name = name;
    }

    @Override
    public void run() {
        while (true) {
            storage.put(name);
            try {
                sleep(200);
            } catch (InterruptedException ignored) {}
        }
    }
}

class Consumer extends Thread {
    private final Storage storage;
    private final String name;

    public Consumer(Storage s, String name) {
        this.storage = s;
        this.name = name;
    }

    @Override
    public void run() {
        int consumed = 0;

        while (consumed < 5) {
            storage.get(name);
            consumed++;

            try {
                sleep(300);
            } catch (InterruptedException ignored) {}
        }

        System.out.println("Succes " + name + " a consumat 5 numere pare și s-a terminat.");
    }
}
