import java.util.Arrays;
import java.util.Random;

class FirUnu extends Thread {
    private final int[] v;

    public FirUnu(int[] v) {
        this.v = v;
    }

    @Override
    public void run() {
        for (int i = 0; i < v.length - 8; i += 8) {
            int a = v[i] * v[i + 2];
            int b = v[i + 4] + v[i + 6];
            int produs = a + b;
            int dif = Math.abs(a - b);
            System.out.println(" p1= " + a + " p2= " + b + " suma= " + produs + " dif= " + dif);
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
        for (int i = v.length - 2; i > 0; i -= 2) {
            int a = v[i];
            int b = v[i - 1];
            int produs = a * b;
            int dif = Math.abs(a - b);
            System.out.println("Doi " + i + " " + (i - 1) + " produs= " + produs + " dif= " + dif + " b= " + b);
        }
    }
}

public class Lab2Variant5 {
    public static void main(String[] args) throws InterruptedException {
        int[] v = new int[100];
        Random r = new Random();
        for (int i = 0; i < 100; i++) v[i] = r.nextInt(100);

        System.out.println("Vectorul: " + Arrays.toString(v));

        FirUnu unu = new FirUnu(v);
        FirDoi doi = new FirDoi(v);

        unu.start();
        doi.start();

        unu.join();
        doi.join();

        System.out.println("\nBUILD SUCCESSFUL\n");

        String infoStudenti = "Student: Josan Nicolae - RM-231 - Lucrarea de laborator 2";
        for (char c : infoStudenti.toCharArray()) {
            System.out.print(c);
            try {
                Thread.sleep(100); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(); 
    }
}
