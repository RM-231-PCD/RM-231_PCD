import java.util.Random;

public class App
{
    static int[] mas = new int[100];

    public static void main(String[] args)
    {
        Random rand = new Random();
        for (int i = 0; i < mas.length; i++)
        {
            mas[i] = rand.nextInt(100) + 1;
        }

        System.out.println("Сгенерированный массив:");
        for (int i = 0; i < mas.length; i++) {
            System.out.print(mas[i] + " ");
            if ((i + 1) % 50 == 0) System.out.println();
        }
        System.out.println();

        ThreadGroup G1 = new ThreadGroup("G1");
        Thread th1 = new Thread(G1, "Th1")
        {
            public void run()
            {
                System.out.println("Th1 начал работу");
                for (int i = 0; i < mas.length - 1; i += 2)
                {
                    if (mas[i] % 2 == 0 && mas[i + 1] % 2 == 0)
                    {
                        int sum = mas[i] + mas[i + 1];
                        System.out.println("Th1: " + mas[i] + " + " + mas[i + 1] + " = " + sum + " (позиции " + i + " и " + (i + 1) + ")");
                    }
                }
                System.out.println("Th1 завершил работу");
            }
        };

        Thread th2 = new Thread(G1, "Th2")
        {
            public void run()
            {
                System.out.println("Th2 начал работу");
                for (int i = mas.length - 1; i > 0; i -= 2)
                {
                    if (mas[i] % 2 == 0 && mas[i - 1] % 2 == 0)
                    {
                        int sum = mas[i] + mas[i - 1];
                        System.out.println("Th2: " + mas[i] + " + " + mas[i - 1] + " = " + sum + " (позиции " + i + " и " + (i - 1) + ")");
                    }
                }
                System.out.println("Th2 завершил работу");
            }
        };

        th1.start();
        th2.start();

        try {
            th1.join();
            th2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\nИнформация о выполнении:");
        String text = "Лабораторную работу выполнили: Каранфил Дмитрий и Червинский Кирилл";
        for (char c : text.toCharArray())
        {
            System.out.print(c);
            try
            {
                Thread.sleep(100);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        System.out.println("\nПрограмма завершена.");
    }
}