package PCD_LAB4;

public class Consumer extends Thread {

    private int id;
    private int needed;
    private Depozit depozit;

    public Consumer(int id, int needed, Depozit depozit) {
        this.id = id;
        this.needed = needed;
        this.depozit = depozit;
    }

    @Override
    public void run() {
        for (int i = 0; i < needed; i++) {

            String[] consumed = depozit.readTwo(id);

            // dacă producția e terminată și nu sunt produse,
            // NU mai afișăm nimic, ieșim din thread
            if (consumed == null) {
                depozit.consumerDone();
                return;
            }

            try { Thread.sleep((int)(Math.random()*300)+50); }
            catch (InterruptedException ignored) {}
        }

        depozit.consumerDone();
    }

}
