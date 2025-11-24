package PCD_LAB4;

public class Producer extends Thread {

    private int id;
    private Depozit depozit;

    public Producer(int id, Depozit depozit) {
        this.id = id;
        this.depozit = depozit;
    }

    @Override
    public void run() {
        while (!depozit.productionFinished()) {

            int nr1 = ((int)(Math.random()*10) * 2) + 1;
            int nr2 = ((int)(Math.random()*10) * 2) + 1;

            String b1 = Integer.toBinaryString(nr1);
            String b2 = Integer.toBinaryString(nr2);

            depozit.store(b1, b2, id);

            try {
                Thread.sleep((int)(Math.random()*400)+1);
            }
            catch (InterruptedException ignored) {}
        }
    }
}
