package lab3;

public class ThreadsNicolae {


    public static class Task1 implements Runnable {
        @Override
        public void run() {

            Thread t = Thread.currentThread();

            System.out.println("=== TH1 – Josan Nicolae ===");
            System.out.println("Thread name: " + t.getName());
            System.out.println("Thread ID: " + t.getId());
            System.out.println("Rol: Sume produse numerelor de pe poziții pare două câte două");
            System.out.println("Th1: Procesare array1 - produse pe poziții pare (de la început)");

            int[] array = LaboratorThreadsMain.array1;
            long totalSum = 0;

            for (int i = 0; i < array.length - 2; i += 4) {

                if (t.isInterrupted()) {
                    System.out.println("Th1: Thread întrerupt!");
                    return;
                }

                int a = array[i];
                int b = array[i + 2];
                long product = (long) a * b;
                totalSum += product;

                System.out.println("Th1: " + a + " * " + b + " = " + product);

                try {
                    Thread.sleep(3);
                } catch (InterruptedException e) {
                    System.out.println("Th1: întrerupt în sleep!");
                    return;
                }
            }

            System.out.println("\n>>> Th1 FINAL: SUMA TOTALĂ = " + totalSum +
                    " [realizat de: Josan Nicolae]");

            LaboratorThreadsMain.threadFinished();
            LaboratorThreadsMain.waitForAllThreads();
            LaboratorThreadsMain.displayInOrder("Thread-1", LaboratorThreadsMain.NUME_STUDENT);
        }
    }

    public static class Task2 implements Runnable {
        @Override
        public void run() {

            Thread t = Thread.currentThread();

            System.out.println("=== TH2 – Josan Nicolae ===");
            System.out.println("Thread name: " + t.getName());
            System.out.println("Thread ID: " + t.getId());
            System.out.println("Rol: Produsele numerelor de pe poziții impare, două câte două");
            System.out.println("Th2: Procesare array2 - produse pe poziții impare (de la sfârșit)");

            int[] array = LaboratorThreadsMain.array2;
            long totalSum = 0;

            for (int i = array.length - 1; i >= 3; i -= 4) {

                if (t.isInterrupted()) {
                    System.out.println("Th2: Thread întrerupt!");
                    return;
                }

                int a = array[i];
                int b = array[i - 2];
                long product = (long) a * b;
                totalSum += product;

                System.out.println("Th2: " + a + " * " + b + " = " + product);

                try {
                    Thread.sleep(3);
                } catch (InterruptedException e) {
                    System.out.println("Th2: întrerupt în sleep!");
                    return;
                }
            }

            System.out.println("\n>>> Th2 FINAL: SUMA TOTALĂ = " + totalSum +
                    " [realizat de: Josan Nicolae]");

            LaboratorThreadsMain.threadFinished();
            LaboratorThreadsMain.waitForAllThreads();
            LaboratorThreadsMain.displayInOrder("Thread-2", LaboratorThreadsMain.PRENUME_STUDENT);
        }
    }
}
