
public class Main {
    public static void main(String[] args) {
        int numere[] = new int[100];
        for (int i = 0; i < 100; i++) {
            numere[i] = (int)(Math.random() * 100);
            System.out.print(" " + numere[i]);
        }
        System.out.println("");

        Thread_1 t1 = new Thread_1(numere, 1, numere.length, 2);
        t1.setName("CornelThread_1");
        t1.start();

        Thread_2 t2 = new Thread_2(numere, 99, 0, -1);
        t2.setName("CornelThread_2");
        t2.start();


    }
}