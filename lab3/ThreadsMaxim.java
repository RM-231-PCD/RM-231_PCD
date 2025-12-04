package lab3;

public class ThreadsMaxim {

    public static class Task3 implements Runnable {
        @Override
        public void run() {
            Thread t = Thread.currentThread();
            System.out.println("=== TH3 – Secrieru Maxim ===");
            System.out.println("Thread name: " + t.getName());
            System.out.println("Rol: parcurgere de la ÎNCEPUT interval [567, 1002]");

            int[] array1 = LaboratorThreadsMain.array1;
            int count = 0;

            for (int value : array1) {

                if (t.isInterrupted()) return;

                System.out.print("Th3:" + value + " ");
                if (++count == 10) { System.out.println(); count = 0; }

                try { Thread.sleep(2); } catch (InterruptedException e) { return; }
            }

            System.out.println("\n>>> Th3 FINAL: " + array1.length);

            LaboratorThreadsMain.printArray(array1);
            LaboratorThreadsMain.threadFinished();
            LaboratorThreadsMain.waitForAllThreads();
            LaboratorThreadsMain.displayInOrder("Thread-3", LaboratorThreadsMain.DISCIPLINA);
        }
    }

    public static class Task4 implements Runnable {
        @Override
        public void run() {

            Thread t = Thread.currentThread();
            System.out.println("=== TH4 – Secrieru Maxim ===");
            System.out.println("Thread name: " + t.getName());
            System.out.println("Rol: parcurgere de la SFÂRȘIT interval [567, 1100]");

            int[] array2 = LaboratorThreadsMain.array2;
            int count = 0;

            for (int i = array2.length - 1; i >= 0; i--) {

                if (t.isInterrupted()) return;

                System.out.print("Th4:" + array2[i] + " ");
                if (++count == 10) { System.out.println(); count = 0; }

                try { Thread.sleep(2); } catch (InterruptedException e) { return; }
            }

            System.out.println("\n>>> Th4 FINAL: " + array2.length);

            LaboratorThreadsMain.printArray(array2);
            LaboratorThreadsMain.threadFinished();
            LaboratorThreadsMain.waitForAllThreads();
            LaboratorThreadsMain.displayInOrder("Thread-4", LaboratorThreadsMain.GRUPA);
        }
    }
}
