package lab3;

public class ThreadsNicolae {


    public static class Task1 implements Runnable {
        @Override
        public void run() {

            Thread currentThread = Thread.currentThread();
            System.out.println("=== TH1 – Josan Nicolae ===");
            System.out.println("Thread name: " + currentThread.getName());
            System.out.println("Thread ID: " + currentThread.getId());
            System.out.println("Rol: Sumele produselor numerelor de pe poziții pare două câte două începând cu primul element");
            System.out.println("Th1 started: Procesare array1 - sume produse pe poziții pare (de la început)");

            int[] array = LaboratorThreadsMain.array1;
            long totalSum = 0;

            // Parcurgem pozițiile pare (0, 2, 4, 6, 8, ...) și le grupez două câte două
            // Perechea 1: (0, 2), Perechea 2: (4, 6), Perechea 3: (8, 10), etc.
            for (int i = 0; i < array.length - 2; i += 4) {
                // Verifică dacă thread-ul a fost întrerupt
                if (currentThread.isInterrupted()) {
                    System.out.println("\nTh1: Thread-ul a fost întrerupt!");
                    currentThread.interrupt();
                    return;
                }

                // Prima poziție pară din pereche
                int pos1 = i;
                // A doua poziție pară din pereche (următoarea poziție pară)
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

    // ============================
    // THREAD 2 – Josan Nicolae
    // ============================
    public static class Task2 implements Runnable {
        @Override
        public void run() {

            Thread currentThread = Thread.currentThread();
            System.out.println("=== TH2 – Josan Nicolae ===");
            System.out.println("Thread name: " + currentThread.getName());
            System.out.println("Thread ID: " + currentThread.getId());
            System.out.println("Rol: Sumele produselor numerelor de pe poziții pare două câte două începând cu ultimul element");
            System.out.println("Th2 started: Procesare array1 - sume produse pe poziții pare (de la sfârșit)");

            int[] array = LaboratorThreadsMain.array1;
            long totalSum = 0;

            // Găsim ultima poziție pară și parcurgem înapoi
            // Pozițiile pare: 0, 2, 4, 6, 8, 10, ...
            // Dacă lungimea este impară, ultima poziție pară este length-2
            // Dacă lungimea este pară, ultima poziție pară este length-2
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

                // Prima poziție pară din pereche (de la sfârșit)
                int pos1 = i;
                // A doua poziție pară din pereche (anterioară)
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
