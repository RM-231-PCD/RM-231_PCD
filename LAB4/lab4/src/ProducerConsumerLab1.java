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
    private List<Integer> stock = new ArrayList<>();
    private final int capacity;

    public Store(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void put(String producerName) throws InterruptedException {
        while (stock.size() >= capacity) {
            System.out.println(producerName + ": Склад полон. Жду...");
            wait();
        }

        int a = (int) (Math.random() * 50) * 2;
        int b = (int) (Math.random() * 50) * 2;

        stock.add(a);
        stock.add(b);

        System.out.println(producerName + " добавил: " + a + ", " + b);
        System.out.println("На складе: " + stock.size() + " единиц -> " + stock);

        notifyAll();
    }


    public synchronized int get(String consumerName) throws InterruptedException {
        while (stock.isEmpty()) {
            System.out.println(consumerName + ": Склад пуст. Жду...");
            wait();
        }

        int value = stock.remove(0);
        System.out.println(consumerName + " взял: " + value);
        System.out.println("На складе осталось: " + stock.size() + " единиц -> " + stock);

        notifyAll();
        return value;
    }
}

class Producer extends Thread {
    private Store store;

    public Producer(Store store, String name) {
        super(name);
        this.store = store;
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                store.put(getName());
                Thread.sleep((int) (Math.random() * 300 + 200));
            }
        } catch (InterruptedException e) {
            System.out.println(getName() + " завершён.");
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