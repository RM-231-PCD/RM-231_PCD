package lab3;

public class ThreadsNicolae {


    public static class Task1 implements Runnable {
        @Override
        public void run() {

            Thread currentThread = Thread.currentThread();
            System.out.println("TH1 – Josan Nicolae ");
            System.out.println("Thread name: " + currentThread.getName());
            System.out.println("Thread ID: " + currentThread.getId());
            System.out.println("Rol: Sumele produselor numerelor de pe poziții pare două câte două începând cu primul element");
            System.out.println("Th1 started: Procesare array1 - sume produse pe poziții pare (de la început)");

            int[] array = LaboratorThreadsMain.array1;
            long totalSum = 0;

            for (int i = 0; i < array.length - 2; i += 4) {
                if (currentThread.isInterrupted()) {
                    System.out.println("\nTh1: Thread-ul a fost întrerupt!");
                    currentThread.interrupt();
                    return;
                }

                int pos1 = i;
                int pos2 = i + 2;

                if (pos2 < array.length) {
                    long prod = (long) array[pos1] * array[pos2];
                    totalSum += prod;
                    System.out.println("Th1: Produs pe poziții pare [" + pos1 + ", " + pos2 + 
                                     "]: arr[" + pos1 + "] * arr[" + pos2 + "] = " + 
                                     array[pos1] + " * " + array[pos2] + " = " + prod);
                }

                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    System.out.println("\nTh1: Sleep întrerupt!");
                    currentThread.interrupt();
                    return;
                }
            }

            System.out.println(">>> Th1 FINAL: Suma totală a produselor = " + totalSum + 
                             " [realizat de: Josan Nicolae]");

            LaboratorThreadsMain.threadFinished();
            LaboratorThreadsMain.waitForAllThreads();

            LaboratorThreadsMain.displayInOrder("Thread-1", LaboratorThreadsMain.PRENUME_STUDENT);
        }
    }

    public static class Task2 implements Runnable {
        @Override
        public void run() {

            Thread currentThread = Thread.currentThread();
            System.out.println("Josan Nicolae");
            System.out.println("Thread name: " + currentThread.getName());
            System.out.println("Thread ID: " + currentThread.getId());
            System.out.println("Rol: Sumele produselor numerelor de pe poziții pare două câte două începând cu ultimul element");
            System.out.println("Th2 started: Procesare array1 - sume produse pe poziții pare (de la sfârșit)");

            int[] array = LaboratorThreadsMain.array1;
            long totalSum = 0;

            int lastEvenPos = (array.length - 1) % 2 == 0 ? array.length - 1 : array.length - 2;

            // Parcurgem pozițiile pare de la sfârșit spre început, două câte două
            // Perechea 1: (lastEvenPos, lastEvenPos-2), Perechea 2: (lastEvenPos-4, lastEvenPos-6), etc.
            for (int i = lastEvenPos; i >= 2; i -= 4) {
                // Verifică dacă thread-ul a fost întrerupt
                if (currentThread.isInterrupted()) {
                    System.out.println("\nTh2: Thread-ul a fost întrerupt!");
                    currentThread.interrupt();
                    return;
                }

                int pos1 = i;
                int pos2 = i - 2;

                if (pos2 >= 0) {
                    long prod = (long) array[pos1] * array[pos2];
                    totalSum += prod;
                    System.out.println("Th2: Produs pe poziții pare [" + pos1 + ", " + pos2 + 
                                     "]: arr[" + pos1 + "] * arr[" + pos2 + "] = " + 
                                     array[pos1] + " * " + array[pos2] + " = " + prod);
                }

                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    System.out.println("\nTh2: Sleep întrerupt!");
                    currentThread.interrupt();
                    return;
                }
            }

            System.out.println(">>> Th2 FINAL: Suma totală a produselor = " + totalSum + 
                             " [realizat de: Josan Nicolae]");

            LaboratorThreadsMain.threadFinished();
            LaboratorThreadsMain.waitForAllThreads();

            LaboratorThreadsMain.displayInOrder("Thread-2", LaboratorThreadsMain.NUME_STUDENT);
        }
    }
}
