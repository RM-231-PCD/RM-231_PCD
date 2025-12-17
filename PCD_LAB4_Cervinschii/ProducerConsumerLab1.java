import java.util.ArrayList;
import java.util.List;

public class ProducerConsumerLab1 {

    public static void main(String[] args) throws InterruptedException {
        Store store = new Store(8);

        List<Producer> producers = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            Producer p = new Producer(store, "Производитель-" + i);
            producers.add(p);
            p.start();
        }

        List<Consumer> consumers = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            Consumer c = new Consumer(store, "Потребитель-" + i, 11);
            consumers.add(c);
            c.start();
        }

        for (Consumer c : consumers) {
            c.join();
        }

        for (Producer p : producers) {
            p.interrupt();
        }

        System.out.println("\n=== Все потребители удовлетворены. Работа завершена ===");
    }
}

class Store {

    private final List<Integer> stock = new ArrayList<>();
    private final int capacity;

    public Store(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void put(List<Integer> items, String producerName)
            throws InterruptedException {

        while (stock.size() + items.size() > capacity) {
            System.out.println(producerName + ": Stock full. Waiting...");
            wait();
        }

        stock.addAll(items);

        System.out.println(producerName + " added: " + items);
        System.out.println("Stock: " + stock);

        notifyAll();
    }

    public synchronized int get(String monitorName)
            throws InterruptedException {

        while (stock.isEmpty()) {
            System.out.println(monitorName + ": Stock empty. Waiting...");
            wait();
        }

        int value = stock.remove(0);
        System.out.println(monitorName + " took: " + value);
        System.out.println("Stock left: " + stock);

        notifyAll();
        return value;
    }
}


class Producer extends Thread {

    private final Store store;

    public Producer(Store store, String name) {
        super(name);
        this.store = store;
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {

                int a = (int) (Math.random() * 50) * 2;
                int b = (int) (Math.random() * 50) * 2;

                List<Integer> produced = new ArrayList<>();
                produced.add(a);
                produced.add(b);

                store.put(produced, getName());

                Thread.sleep(300);
            }
        } catch (InterruptedException e) {
            System.out.println(getName() + " stopped.");
        }
    }
}


class Consumer extends Thread {
    private Store store;
    private int needed;
    private int taken = 0;

    public Consumer(Store store, String name, int needed) {
        super(name);
        this.store = store;
        this.needed = needed;
    }

    @Override
    public void run() {
        try {
            while (taken < needed) {
                store.get(getName());
                taken++;
                Thread.sleep((int) (Math.random() * 300 + 100));
            }
            System.out.println(getName() + " удовлетворён (взял " + taken + " из " + needed + ").");
        } catch (InterruptedException e) {
            System.out.println(getName() + " прерван.");
        }
    }
}