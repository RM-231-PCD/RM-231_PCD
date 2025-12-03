import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Create_Thread {

    private static final Object lock1 = new Object();
    private static final ReentrantLock lock2 = new ReentrantLock();
    private static final Semaphore semaphore3 = new Semaphore(0);
    private static final CountDownLatch latch4 = new CountDownLatch(1);

    private static final Semaphore th1ToTh2 = new Semaphore(0);

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
            System.out.println("Suma Th1 = " + sum);
            try { Thread.sleep(200); } catch (InterruptedException e) {Thread.currentThread().interrupt();}

            synchronized (lock1) {
                System.out.print("Th1 (Prenume): ");
                afisareLent("Andrei Victoria");
            }
            th1ToTh2.release();
        }
    }

    public static class Th2 extends Thread {
        @Override
        public void run() {
            long sum = 0;

            for (int i = 907; i >= 125; i -= 2) {
                if (i - 2 >= 125) {
                    int produs = i * (i - 2);
                    sum += produs;
                    System.out.println("Th2: " + i + " * " + (i - 2) + " = " + produs);
                }
            }
            System.out.println("Suma Th2 = " + sum);

            try { Thread.sleep(250); } catch (InterruptedException e) {Thread.currentThread().interrupt();}

            try {
                th1ToTh2.acquire();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            lock2.lock();
            try {
                System.out.print("Th2 (Nume): ");
                afisareLent("Lozinschi Muntean");
            } finally {
                lock2.unlock();
                semaphore3.release();
            }
        }
    }

    public static class Th3 extends Thread {

        @Override
        public void run() {

            for (int i = 654; i < 1278; i++) {
                System.out.print("Th3 " + i + " ");
                if (i == 862 || i == 1070) {
                    System.out.println();
                }
            }
            System.out.println();

            try { Thread.sleep(300);} catch (InterruptedException e) {Thread.currentThread().interrupt();}

            try {
                semaphore3.acquire();
                System.out.print("Th3 (Disciplina): ");
                afisareLent("Programarea Concurenta si Distribuita");
            } catch (InterruptedException e) {}
            finally {
                latch4.countDown();
            }

        }
    }

    public static class Th4 extends Thread {

        @Override
        public void run() {

            for (int i = 908; i > 123; i--) {
                System.out.print("Th4 " + i + " ");
                if (i == 647 || i == 386) {
                    System.out.println();
                }
            }

            System.out.println();

            try {Thread.sleep(350);} catch (InterruptedException e) {Thread.currentThread().interrupt();}

            try {
                latch4.await();
                System.out.print("Th4 (Grupa): ");
                afisareLent("RM-231");
            } catch (InterruptedException e) {}
        }
    }

    public static void main(String[] args) {

        Th2 t2 = new Th2();
        Th4 t4 = new Th4();
        Th1 t1 = new Th1();
        Th3 t3 = new Th3();

        t3.start();
        t4.start();
        t1.start();
        t2.start();

        try {
            t3.join();
            t4.join();
            t1.join();
            t2.join();
        } catch (InterruptedException ignored) {}
    }
}
