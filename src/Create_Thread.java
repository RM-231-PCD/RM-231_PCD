import java.util.concurrent.Semaphore;

public class Create_Thread {

    private static final Object lock = new Object();
    private static boolean th1Done = false;
    private static boolean th2Done = false;
    private static long th1Sum = 0;
    private static long th2Sum = 0;

    private static final Semaphore semTh3 = new Semaphore(1);
    private static final Semaphore semTh4 = new Semaphore(0);

    private static final Object textLock = new Object();

    private static void afisareLent(String text) {
        try {
            for (char c : text.toCharArray()) {
                System.out.print(c);
                Thread.sleep(100);
            }
            System.out.println();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


    public static class Th1 extends Thread {
        @Override
        public void run() {
            long sum = 0;

            for (int i = 655; i <= 1277; i += 2) {
                if (i + 2 <= 1277) {
                    int produs = i * (i + 2);
                    sum += produs;
                    System.out.println("Th1: " + i + " * " + (i + 2) + " = " + produs);
                }
            }

            th1Sum = sum;

            synchronized (lock) {
                th1Done = true;
                lock.notifyAll();
            }

            synchronized (lock) {
                while (!th2Done) {
                    try {
                        lock.wait();
                    } catch (InterruptedException ignored) {}
                }
            }

            synchronized (textLock) {
                System.out.print("Th1 (Nume student 1): ");
                afisareLent("Lozinschi Andrei");
            }
        }
    }


    public static class Th2 extends Thread {
        @Override
        public void run() {
            long sum = 0;

            synchronized (lock) {
                while (!th1Done) {
                    try {
                        lock.wait();
                    } catch (InterruptedException ignored) {}
                }
            }

            for (int i = 907; i >= 125; i -= 2) {
                if (i - 2 >= 125) {
                    int produs = i * (i - 2);
                    sum += produs;
                    System.out.println("Th2: " + i + " * " + (i - 2) + " = " + produs);
                }
            }

            th2Sum = sum;

            synchronized (lock) {
                th2Done = true;
                lock.notifyAll();
            }

            synchronized (textLock) {
                System.out.print("Th2 (Nume studetn 2): ");
                afisareLent("Muntean Victoria");
            }
        }
    }

    public static class Th3 extends Thread {
        @Override
        public void run() {
            try {
                semTh3.acquire();
            } catch (InterruptedException ignored) {}

            System.out.println("Incepe Thread_3");

            for (int i = 654; i < 1278; i++) {
                System.out.print(i + " ");
                if (i == 862 || i == 1070) {
                    System.out.println();
                }
            }
            System.out.println();

            semTh4.release();

            synchronized (textLock) {
                System.out.print("Th3 (Disciplina): ");
                afisareLent("Programarea Concurenta si Distribuita");
            }
        }
    }

    public static class Th4 extends Thread {
        @Override
        public void run() {
            try {
                // așteaptă până termină Th3
                semTh4.acquire();
            } catch (InterruptedException ignored) {}

            System.out.println("Incepe Thread_4");

            for (int i = 908; i > 123; i--) {
                System.out.print(i + " ");
                if (i == 647 || i == 386) {
                    System.out.println();
                }
            }
            System.out.println();

            synchronized (textLock) {
                System.out.print("Th4 (Grupa): ");
                afisareLent("RM-231");
            }
        }
    }

    public static void main(String[] args) {

        Th1 t1 = new Th1();
        Th2 t2 = new Th2();
        Th3 t3 = new Th3();
        Th4 t4 = new Th4();

        t3.start();
        t4.start();

        try {
            t3.join();
            t4.join();
        } catch (InterruptedException ignored) {}

        System.out.println("\n=== Th3 și Th4 au terminat ===\n");

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException ignored) {}

        System.out.println("\n=== REZULTATE ===");
        System.out.println("Suma Th1 = " + th1Sum);
        System.out.println("Suma Th2 = " + th2Sum);
        System.out.println("Suma totală = " + (th1Sum + th2Sum));
    }
}
