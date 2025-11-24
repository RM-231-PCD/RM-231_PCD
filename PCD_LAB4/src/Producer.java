package PCD_LAB4;

public class Producer extends Thread {

    private int id;
    private int amount;
    private Depozit depozit;

    public Producer(int id, int amount, Depozit depozit) {
        this.id = id;
        this.amount = amount;
        this.depozit = depozit;
    }

    @Override
    public void run() {
        while (!depozit.productionFinished()) {
            for (int i = 0; i < amount; i++) {

                int nr = ((int)(Math.random()*20)*2)+1;
                String bin = Integer.toBinaryString(nr);

                depozit.store(bin, id);

                try {
                    Thread.sleep((int)(Math.random()*400)+50);
                } catch (InterruptedException ignored) {}
            }
        }
    }
}
