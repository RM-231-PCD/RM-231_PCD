import java.util.concurrent.*;
import java.util.Random;

public class lab5 {

    static final int X = 3;
    static final int Y = 3;
    static final int Z = 5;
    static final int D = 6;
    static final int F = 2;

    static final char[] VOCALS = {'A','E','I','O','U','Y'};

    static BlockingQueue<Character> buffer = new ArrayBlockingQueue<>(D);

    public static void main(String[] args) {

        ExecutorService pool = Executors.newFixedThreadPool(X + Y);

        for (int i = 1; i <= X; i++) {
            int id = i;
            pool.execute(() -> producer(id));
        }
        for (int i = 1; i <= Y; i++) {
            int id = i;
            pool.execute(() -> consumer(id));
        }

        pool.shutdown();
    }

    static void producer(int id) {
        Random r = new Random();
        try {
            while (true) {
                char item = VOCALS[r.nextInt(F)];

                if (buffer.remainingCapacity() == 0) {
                    System.out.println("Prod" + id + ": склад полон, жду...");
                }

                buffer.put(item);

                System.out.println("Prod" + id + " → " + item +
                        " | склад: " + buffer.size() + "/" + D);
            }
        } catch (InterruptedException e) {}
    }

    static void consumer(int id) {
        int count = 0;
        try {
            while (count < Z) {

                if (buffer.isEmpty()) {
                    System.out.println("Cons" + id + ": склад пуст, жду...");
                }

                char item = buffer.take();
                count++;

                System.out.println("Cons" + id + " ← " + item +
                        " | получил: " + count + "/" + Z);

                Thread.sleep(150);
            }

            System.out.println("Cons" + id + " получил все " + Z + " объектов!");

        } catch (InterruptedException e) {}
    }
}
