package lab3;

public class LaboratorThreadsMain {

    public static final String NUME_STUDENT = "Nicolae si Maxim";
    public static final String PRENUME_STUDENT = "Josan si Secrieru";
    public static final String GRUPA = "RM-232";
    public static final String DISCIPLINA = "Programarea Concurentă și distribuită";

    public static final int[] array1 = generateArray(567, 1002);
    public static final int[] array2 = generateArray(567, 1100);

    public static volatile int currentDisplay = 0;
    public static Object thread1;
    public static Object thread2;

    public static final String[] DISPLAY_ORDER = {
            "Thread-2", "Thread-4", "Thread-1", "Thread-3"
    };

    public static void main(String[] args) {

        System.out.println("LABORATOR RM-231");
        System.out.println("Echipa: Josan Nicolae si Secrieru Maxim\n");

        // Creare thread-uri
        Thread th1 = new Thread(new ThreadsNicolae.Task1(), "Thread-1");
        Thread th2 = new Thread(new ThreadsNicolae.Task2(), "Thread-2");
        Thread th3 = new Thread(new ThreadsMaxim.Task3(), "Thread-3");
        Thread th4 = new Thread(new ThreadsMaxim.Task4(), "Thread-4");

        System.out.println("Starting Thread 1");
        System.out.println("Starting Thread 2");
        System.out.println("Starting Thread 3");
        System.out.println("Starting Thread 4\n");

        // Start thread-uri
        th1.start();
        th2.start();
        th3.start();
        th4.start();

        // Așteptăm toate thread-urile să se termine cu join
        try {
            th1.join();
            th2.join();
            th3.join();
            th4.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread întrerupt!");
            return;
        }

        System.out.println("\nToate firele s-au încheiat.");
    }

    public static int[] generateArray(int start, int end) {
        int size = end - start + 1;
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) arr[i] = start + i;
        return arr;
    }

    public static void printWithDelay(String text, int delay) {
        for (char c : text.toCharArray()) {
            System.out.print(c);
            try { Thread.sleep(delay); } catch (InterruptedException ignored) {}
        }
        System.out.println();
    }

    public static void displayInOrder(String threadName, String text) {

        // Busy waiting pentru afișare în ordine
        while (!DISPLAY_ORDER[currentDisplay].equals(threadName)) {
            try { Thread.sleep(10); } catch (InterruptedException ignored) {}
        }

        System.out.print(threadName + ": ");
        printWithDelay(text, 100);

        currentDisplay++;
    }

    public static void printArray(int[] arr) {
        int cnt = 0;
        for (int n : arr) {
            System.out.print(n + " ");
            if (++cnt == 20) { System.out.println(); cnt = 0; }
        }
        System.out.println();
    }
}
