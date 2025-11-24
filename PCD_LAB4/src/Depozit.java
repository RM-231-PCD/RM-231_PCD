package PCD_LAB4;

import java.util.LinkedList;
import java.util.Queue;

public class Depozit {
    private Queue<String> coada = new LinkedList<>();
    private int capacity;
    private int totalToConsume;
    private int produced = 0;
    private int consumed = 0;

    private AppWindow gui;

    public Depozit(int capacity, int totalToConsume, AppWindow gui) {
        this.capacity = capacity;
        this.totalToConsume = totalToConsume;
        this.gui = gui;
    }

    public synchronized void store(String obj, int producerId) {
        while (coada.size() == capacity && consumed < totalToConsume) {
            gui.log("Depozitul este plin → Producătorul " + producerId + " așteaptă...");
            try {
                wait();
            } catch (InterruptedException ignored) {}
        }

        if (produced >= totalToConsume) return;

        coada.add(obj);
        produced++;
        gui.log("Producătorul " + producerId + " a produs: " + obj);
        gui.updateStatus(coada.size(), capacity);

        notifyAll();
    }

    public synchronized String read(int consumerId) {
        while (coada.isEmpty()) {
            gui.log("Depozitul este gol → Consumatorul " + consumerId + " așteaptă...");
            try {
                wait();
            } catch (InterruptedException ignored) {}
        }

        String obj = coada.remove();
        consumed++;
        gui.log("Consumatorul " + consumerId + " a consumat: " + obj);
        gui.updateStatus(coada.size(), capacity);

        notifyAll();
        return obj;
    }

    public synchronized boolean productionFinished() {
        return produced >= totalToConsume;
    }
}
