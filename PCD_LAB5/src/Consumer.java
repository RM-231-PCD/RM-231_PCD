package PCD_LAB5;

public class Consumer implements Runnable {

    private int id;
    private AppWindowLab5 gui;

    public Consumer(int id, AppWindowLab5 gui) {
        this.id = id;
        this.gui = gui;
    }

    @Override
    public void run() {
        try {
            while (gui.consumerCounters.get(id).get() < AppWindowLab5.CONSUMER_GOAL) {

                if (gui.buffer.isEmpty()) {
                    gui.log("Consumatorul " + id + ": Depozitul este gol, așteaptă...");
                }

                int value = gui.buffer.take();
                int localCount = gui.consumerCounters.get(id).incrementAndGet();
                int globalCount = gui.totalConsumed.incrementAndGet();

                gui.log("Consumatorul " + id + " a consumat: " + value +
                        " (binar: " + Integer.toBinaryString(value) + ")" +
                        " | consumate de acest consumator: " + localCount + "/" + AppWindowLab5.CONSUMER_GOAL +
                        " | total consumate global: " + globalCount);

                gui.updateStatus();

                Thread.sleep(200);
            }

            gui.log("Consumatorul " + id + ": a terminat consumul.");

        } catch (Exception ignored) {}
    }
}
