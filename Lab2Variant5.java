import java.util.Random;

class FirUnu extends Thread {
    private final int[] v;

    public FirUnu(int[] v) {
        this.v = v;
    }

    @Override
    public void run() {
        for (int i = 0; i < v.length - 1; i += 2) {
            int a = v[i];
            int b = v[i + 1];
            int produs = a * b;
            int dif = Math.abs(a - b);

            System.out.println("Unu " + i + " " + (i + 1) + " " + produs + " " + dif + " " + a);
            try {
                Thread.sleep(1); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class FirDoi extends Thread {
    private final int[] v;

    public FirDoi(int[] v) {
        this.v = v;
    }

    @Override
    public void run() {
        for (int i = v.length - 1; i > 0; i -= 2) {
            int a = v[i];
            int b = v[i - 1];
            int produs = a * b;
            int dif = Math.abs(a - b);

            System.out.println("Doi " + i + " " + (i - 1) + " " + produs + " " + dif + " " + b);
            try {
                Thread.sleep(1); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class Lab2Variant5 {
    public static void main(String[] args) throws InterruptedException {

        int[] v = new int[100];
        Random r = new Random();

        for (int i = 0; i < 100; i++) {
            v[i] = r.nextInt(100);
        }

        FirUnu unu = new FirUnu(v);
        FirDoi doi = new FirDoi(v);

        unu.start();
        doi.start();

        unu.join();
        doi.join();

        System.out.println("cod finalizat");
    }
}
