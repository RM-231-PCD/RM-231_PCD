package PCD_LAB4;

public class Depozit {

    private String[] buffer;
    private int capacity;

    private int writePos = 0;
    private int readPos = 0;
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
        this.buffer = new String[capacity];
    }

    // PRODUCĂTORII PRODUC 2 PRODUSE
    public synchronized void store(String p1, String p2, int producerId) {
        while (count + 2 > capacity && consumed < totalToConsume) {
            gui.log("Depozitul este plin → Producătorul " + producerId + " așteaptă...");
            try { wait(); } catch (InterruptedException ignored) {}
        }

        if (produced >= totalToConsume) return;

        buffer[writePos] = p1;
        writePos = (writePos + 1) % capacity;

        buffer[writePos] = p2;
        writePos = (writePos + 1) % capacity;

        produced += 2;
        count += 2;

        gui.log("Producătorul " + producerId + " a produs: " + p1 + " și " + p2);
        gui.updateStatus(count, capacity);

        notifyAll();
    }

    public synchronized String[] readTwo(int consumerId) {

        // Dacă producția s-a terminat și nu mai sunt produse => consumatorul iese
        if (productionFinished() && count < 2) {
            return null;   // NU mai afișăm nimic, NU mai așteptăm
        }

        while (count < 2) {

            // Dacă producția s-a terminat, nu mai are rost să așteptăm
            if (productionFinished()) {
                return null;
            }

            gui.log("Depozitul este gol → Consumatorul " + consumerId + " așteaptă...");
            try { wait(); } catch (InterruptedException ignored) {}
        }

        // consumă 2 produse
        String p1 = buffer[readPos];
        readPos = (readPos + 1) % capacity;

        String p2 = buffer[readPos];
        readPos = (readPos + 1) % capacity;

        consumed += 2;
        count -= 2;

        gui.log("Consumatorul " + consumerId + " a consumat: " + p1 + " și " + p2);
        gui.updateStatus(count, capacity);

        notifyAll();

        return new String[]{p1, p2};
    }


    // NOTIFICĂ FINALIZAREA UNUI CONSUMATOR
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
