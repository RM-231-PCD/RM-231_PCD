import java.util.Random;

public class Main 
{
    static int[] mas = new int[100];

    public static void main(String[] args) 
    {
        Random rand = new Random();

        for (int i = 0; i < mas.length; i++) 
        {
            mas[i] = rand.nextInt(100) + 1;
        }

        ThreadGroup G1 = new ThreadGroup("G1");

        Thread th1 = new Thread(G1, "Th1")
        {
            public void run()
            {
                int sum = 0;
                for (int i = 0; i < mas.length; i += 2) 
                {
                    if (mas[i] % 2 == 0) 
                    {
                        sum += i;
                    }
                }
                System.out.println("Th1 - Sum of positions of even numbers (from start): " + sum);
            }
        };

        Thread th2 = new Thread(G1, "Th2")
        {
            public void run()
            {
                int sum = 0;
                for (int i = mas.length - 1; i >= 0; i -= 2) 
                {
                    if (mas[i] % 2 == 0) 
                    {
                        sum += i; 
                    }
                }
                System.out.println("Th2 - Sum of positions of even numbers (from end): " + sum);
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

        String text = "Completed by: Cervinschii Chiril";
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
    }
}
