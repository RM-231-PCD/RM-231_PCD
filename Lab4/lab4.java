import java.util.ArrayList;

public class lab4 {

    public static void main(String[] args) {

        int X = 3;
        int Y = 3;
        int Z = 5;
        int D = 6;
        int F = 2;

        Store store = new Store(D, Y);

        for (int i = 1; i <= X; i++) {
            Producer p = new Producer(store, F);
            p.setName("Производитель №" + i);
            p.start();
        }

        for (int i = 1; i <= Y; i++) {
            Consumer c = new Consumer(store, Z);
            c.setName("Потребитель №" + i);
            c.start();
        }
    }
}

class Store {

    private final int capacity;
    private final ArrayList<Character> buffer = new ArrayList<>();
    private int consumersLeft;

    public Store(int capacity, int consumers) {
        this.capacity = capacity;
        this.consumersLeft = consumers;
    }

    public synchronized void put(String name, char[] items) {

        while (buffer.size() + items.length > capacity && consumersLeft > 0) {
            System.out.println("Склад полон — " + name + " ждёт...");
            try { wait(); } catch (InterruptedException ignored) {}
        }

        if (consumersLeft == 0) return;

        System.out.print(name + " произвёл: ");
        for (char item : items) {
            buffer.add(item);
            System.out.print(item + " ");
        }
        System.out.println();

        System.out.println("Склад содержит " + buffer.size() + " объектов");

        notifyAll();
    }

    public synchronized char get(String name) {

        while (buffer.isEmpty()) {
            System.out.println("Склад пуст — " + name + " ждёт...");
            try { wait(); } catch (InterruptedException ignored) {}
        }

        char value = buffer.remove(buffer.size() - 1);
        System.out.println(name + " получил: " + value);

        notifyAll();
        return value;
    }

    public synchronized void consumerFinished() {
        consumersLeft--;
        if (consumersLeft == 0) {
            System.out.println("Все потребители удовлетворены. Производство завершено.");
        }
        notifyAll();
    }
}

class Producer extends Thread {

    private final Store store;
    private final int F;

    private final char[] vowels = {'A', 'E', 'I', 'O', 'U'};

    public Producer(Store store, int F) {
        this.store = store;
        this.F = F;
    }

    @Override
    public void run() {

        while (true) {

            char[] produced = new char[F];

            for (int i = 0; i < F; i++) {
                produced[i] = vowels[(int)(Math.random() * vowels.length)];
            }

            store.put(getName(), produced);

            try { Thread.sleep(100); } catch (InterruptedException ignored) {}
        }
    }
}

class Consumer extends Thread {

    private final Store store;
    private final int Z;

    public Consumer(Store store, int Z) {
        this.store = store;
        this.Z = Z;
    }

    @Override
    public void run() {

        for (int i = 0; i < Z; i++) {
            store.get(getName());
        }

        System.out.println(getName() + " получил " + Z + " объектов — завершён.");
        store.consumerFinished();
    }
}
