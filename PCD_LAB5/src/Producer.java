package PCD_LAB5;

import java.util.Random;

public class Producer implements Runnable {

    private int id;
    private AppWindowLab5 gui;
    private Random rand = new Random();

    public Producer(int id, AppWindowLab5 gui) {
        this.id = id;
        this.gui = gui;
    }

    private int generateOdd() {
        return rand.nextInt(10) * 2 + 1;
    }

    @Override
    public void run() {
        try {
            while (gui.totalProduced.get() < AppWindowLab5.TOTAL_OBJECTS) {

                int free = AppWindowLab5.BUFFER_CAPACITY - gui.buffer.size();

                if (free == 0) {
                    gui.log("Producătorul " + id + ": Depozitul este plin, așteaptă...");
                }

                int canAdd = Math.min(2, free);

                for (int i = 0; i < canAdd; i++) {

                    if (gui.totalProduced.get() >= AppWindowLab5.TOTAL_OBJECTS)
                        break;

                    int value = generateOdd();

                    gui.buffer.put(value);

                    int producedNow = gui.totalProduced.incrementAndGet();

                    gui.log("Producătorul " + id + " a produs: " + value +
                            " (binar: " + Integer.toBinaryString(value) + ")" +
                            " | total produse: " + producedNow);

                    gui.updateStatus();
                }

                Thread.sleep(200);
            }

            gui.log("Producătorul " + id + ": a terminat producția.");

        } catch (Exception ignored) {}
    }
}
