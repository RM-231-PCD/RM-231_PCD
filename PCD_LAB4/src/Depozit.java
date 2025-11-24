package PCD_LAB4;

public class Depozit {

    private String[] depozit;
    private int capacity;
    private int count = 0;

    private int totalToConsume;
    private int produced = 0;
    private int consumed = 0;

    private int totalConsumers;
    private int finishedConsumers = 0;

    private AppWindow gui;

    public Depozit(int capacity, int totalToConsume, int totalConsumers, AppWindow gui) {
        this.capacity = capacity;
        this.totalToConsume = totalToConsume;
        this.totalConsumers = totalConsumers;
        this.gui = gui;
        this.depozit = new String[capacity];
    }

    public synchronized void store(String p1, String p2, int producerId) {

        while (count + 2 > capacity && consumed < totalToConsume) {
            gui.log("Depozitul este plin → Producătorul " + producerId + " așteaptă...");
            try {
                wait();
            } catch (InterruptedException ignored) {

            }
        }

        if (produced >= totalToConsume) return;

        depozit[count] = p1;
        count++;

        depozit[count] = p2;
        count++;

        produced += 2;

        gui.log("Producătorul " + producerId + " a produs: " + p1 + " și " + p2);
        gui.updateStatus(count, capacity);

        notifyAll();
    }

    public synchronized String[] readTwo(int consumerId) {

        if (productionFinished() && count < 2) {
            return null;
        }

        while (count < 2) {

            if (productionFinished()) {
                return null;
            }

            gui.log("Depozitul este gol → Consumatorul " + consumerId + " așteaptă...");
            try { wait(); } catch (InterruptedException ignored) {}
        }

        String p2 = depozit[count - 1];
        String p1 = depozit[count - 2];

        count -= 2;
        consumed += 2;

        gui.log("Consumatorul " + consumerId + " a consumat: " + p1 + " și " + p2);
        gui.updateStatus(count, capacity);

        notifyAll();
        return new String[]{p1, p2};
    }

    public synchronized void consumerDone() {
        finishedConsumers++;
        if (finishedConsumers == totalConsumers) {
            gui.log("\n=== SFÂRȘIT EXERCIȚIU ===");
        }
    }

    public synchronized boolean productionFinished() {
        return produced >= totalToConsume;
    }
}
