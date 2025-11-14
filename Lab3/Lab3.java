import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class Lab3 {

    static CountDownLatch latch12 = new CountDownLatch(1);
    static Semaphore sem34 = new Semaphore(0);

    public static void main(String[] args) {

        Thread th1 = new Thread(new Task1());
        Thread th2 = new Thread(new Task2());
        Thread th3 = new Thread(new Task3());
        Thread th4 = new Thread(new Task4());

        th1.start();
        th2.start();
        th3.start();
        th4.start();
    }

    static class Task1 implements Runnable {
        @Override
        public void run() {
            System.out.println("Th1: Задача 1");

            List<Integer> even = getEven();

            for (int i = 0; i < even.size() - 1; i += 2) {
                int a = even.get(i);
                int b = even.get(i + 1);
                System.out.println(a + "*" + b + " = " + (a * b));
            }

            latch12.countDown(); 

            printSlow("Фамилия: Marinov");
        }
    }

    static class Task2 implements Runnable {
        @Override
        public void run() {

            try { latch12.await(); } catch (Exception ignored) {}

            System.out.println("Th2: Задача 2");

            List<Integer> even = getEven();

            for (int i = even.size() - 1; i > 0; i -= 2) {
                int a = even.get(i);
                int b = even.get(i - 1);
                System.out.println(a + "*" + b + " = " + (a * b));
            }

            printSlow("Имя: Nicolai");
        }
    }

    static class Task3 implements Runnable {
        @Override
        public void run() {
            System.out.println("Th3: Задача 3");

            for (int i = 234; i <= 1000; i++) {
                System.out.print(i + " ");
            }
            System.out.println();

            try { sem34.acquire(); } catch (Exception ignored) {}

            printSlow("Дисциплина: Конкурентное и распределенное программирование");
        }
    }

    static class Task4 implements Runnable {
        @Override
        public void run() {
            System.out.println("Th4: Задача 4");

            for (int i = 1234; i >= 456; i--) {
                System.out.print(i + " ");
            }
            System.out.println();

            sem34.release(); 

            printSlow("Группа: IS-203");
        }
    }

    static List<Integer> getEven() {
        List<Integer> even = new ArrayList<>();
        for (int i = 10; i < 200; i++) {
            if (i % 2 == 0) even.add(i);
        }
        return even;
    }

    static void printSlow(String s) {
        for (char c : s.toCharArray()) {
            System.out.print(c);
            try { Thread.sleep(100); } catch (Exception ignored) {}
        }
        System.out.println();
    }
}
