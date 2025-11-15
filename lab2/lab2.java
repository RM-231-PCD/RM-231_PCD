import java.util.Random;

class Th1 implements Runnable {
    private int[] mas;

    public Th1(int[] mas) {
        this.mas = mas;
    }

    @Override
    public void run() {
        System.out.println("\n--- Th1: Суммы чётных чисел по два (с начала) ---");

        for (int i = 0; i < mas.length - 1; i++) {
            if (mas[i] % 2 == 0) {
                for (int j = i + 1; j < mas.length; j++) {
                    if (mas[j] % 2 == 0) {
                        int sum = mas[i] + mas[j];
                        System.out.println(Thread.currentThread().getName()+": [" + i + "]=" + mas[i] +
                                " + [" + j + "]=" + mas[j] + " = " + sum);
                        i = j;
                        break;
                    }
                }
            }
        }
    }
}

class Th2 implements Runnable {
    private int[] mas;

    public Th2(int[] mas) {
        this.mas = mas;
    }

    @Override
    public void run() {
        System.out.println("\n--- Th2: Произведения чётных чисел по два (с конца) ---");

        for (int i = mas.length - 1; i > 0; i--) {
            if (mas[i] % 2 == 0) {
                for (int j = i - 1; j >= 0; j--) {
                    if (mas[j] % 2 == 0) {
                        int prod = mas[i] * mas[j];
                        System.out.println(Thread.currentThread().getName()+": [" + i + "]=" + mas[i] +
                                " * [" + j + "]=" + mas[j] + " = " + prod);
                        i = j;
                        break;
                    }
                }
            }
        }
    }
}


public class lab2 {

    public static void main(String[] args) throws InterruptedException {

        int[] mas = new int[100];
        Random r = new Random();

        System.out.println("Сгенерированная матрица:");
        for (int i = 0; i < mas.length; i++) {
            mas[i] = 1 + r.nextInt(100);
            System.out.print(mas[i] + " ");
            if(i == 50){System.out.println();}
        }
        System.out.println("\n");

        Thread t1 = new Thread(new Th1(mas));
        Thread t2 = new Thread(new Th2(mas));

        t1.start();
        t2.start();

        String text = "Студент: Иванов Иван, Группа: IS-241, Дисциплина: Параллельное программирование";

        System.out.println("\n--- Информация о студенте ---\n");
        for (char c : text.toCharArray()) {
            System.out.print(c);
            Thread.sleep(100);
        }

        System.out.println("\n\nРабота завершена.");
    }
}
