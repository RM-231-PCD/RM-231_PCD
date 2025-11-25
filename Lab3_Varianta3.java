import java.util.Random;

public class Lab3_Varianta3 {
    // Variabile partajate și constante
    static final int ARRAY_SIZE = 100;
    static int[] b = new int[ARRAY_SIZE]; 
    static int[] oddNumbers; 
    static int oddCount = 0;

    // Informații de afișat la final
    static final String INFO_NUME = "\nDodon Cristin";       // Afișat de Th2 (setPriority)
    static final String INFO_PRENUME = "\nPrepelita Catalin";     // Afișat de Th1 (sleep)
    static final String INFO_DISCIPLINA = "\nProgramarea Concurenta si Distribuita"; // Afișat de Th3 (join)
    static final String INFO_GRUPA = "\nRM-231";                   // Afișat de Th4 (yield)

    // Metodă Ajutătoare (Comună)
    private static void printWithDelay(String text, String threadName) {
        System.out.print(threadName + " afiseaza: ");
        for (int i = 0; i < text.length(); i++) {
            System.out.print(text.charAt(i));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println();
    }
    
    // ====================================================================
    // PARTEA LUI PREPELITA CATALIN (Th1 & Th3)
    // ====================================================================

    // Thread 1: Sume impare de la primul element (Sincronizare: sleep())
    static class Thread1_Sarcina1 extends Thread {
        public Thread1_Sarcina1() { setName("Th1"); }

        @Override
        public void run() {
            System.out.println("Starting " + getName() + " (Sarcina 1)...");
            long totalSum = 0;
            for (int i = 0; i < oddCount; i += 2) {
                if (i + 1 < oddCount) {
                    int sum = oddNumbers[i] + oddNumbers[i + 1];
                    totalSum += sum;
                    System.out.println(getName() + ": Suma " + oddNumbers[i] + " + " + oddNumbers[i + 1] + " = " + sum);
                }
                try {
                    Thread.sleep(50); // Sincronizare: sleep()
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println(getName() + " a finalizat.");
        }
    }

    // Thread 3: Parcurgere interval [0, 798] (Sincronizare: join() în main)
    static class Thread3_Sarcina3 extends Thread {
        public Thread3_Sarcina3() { setName("Th3"); }

        @Override
        public void run() {
            System.out.println("Starting " + getName() + " (Sarcina 3)...");
            for (int i = 0; i <= 798; i++) {
                if (i % 80 == 0) {
                    System.out.print(getName() + ":" + i + " | ");
                }
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("\n" + getName() + " a finalizat parcurgerea.");
        }
    }

    // ====================================================================
    // PARTEA LUI DODON CRISTIN (Th2 & Th4)
    // ====================================================================
    
    // Thread 2: Sume impare de la sfârșit (Sincronizare: setPriority())
    static class Thread2_Sarcina2 extends Thread {
        public Thread2_Sarcina2() { 
            setName("Th2"); 
            setPriority(MAX_PRIORITY); // Sincronizare: setPriority()
        }

        @Override
        public void run() {
            System.out.println("Starting " + getName() + " (Sarcina 2)...");
            long totalSum = 0;

            for (int i = oddCount - 1; i >= 0; i -= 2) {
                if (i - 1 >= 0) {
                    int sum = oddNumbers[i] + oddNumbers[i - 1];
                    totalSum += sum;
                    System.out.println(getName() + ": Suma " + oddNumbers[i] + " + " + oddNumbers[i - 1] + " = " + sum);
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println(getName() + " a finalizat.");
        }
    }

    // Thread 4: Parcurgere [2111, 1456] (Sincronizare: yield())
    static class Thread4_Sarcina4 extends Thread {
        public Thread4_Sarcina4() { setName("Th4"); }

        @Override
        public void run() {
            System.out.println("Starting " + getName() + " (Sarcina 4)...");
            for (int i = 2111; i >= 1456; i--) {
                if (i % 70 == 0) {
                    System.out.print(getName() + ":" + i + " | ");
                }
                Thread.yield(); // Sincronizare: yield()
            }
            System.out.println("\n" + getName() + " a finalizat parcurgerea.");
        }
    }

    // ====================================================================
    // FUNCTIA MAIN (Sincronizare Principală: join())
    // ====================================================================

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();

        // 1. Initializare date
        System.out.println("Printing Array b (Random 1-100):");
        for (int i = 0; i < ARRAY_SIZE; i++) {
            b[i] = random.nextInt(100) + 1;
            System.out.print(b[i] + (i % 20 == 19 ? "\n" : " "));
            if (b[i] % 2 != 0) {
                oddCount++;
            }
        }
        System.out.println("\nTotal numere impare: " + oddCount);

        oddNumbers = new int[oddCount];
        int k = 0;
        for (int i = 0; i < ARRAY_SIZE; i++) {
            if (b[i] % 2 != 0) {
                oddNumbers[k++] = b[i];
            }
        }
        
        // 2. Creare și pornire thread-uri
        Thread1_Sarcina1 th1 = new Thread1_Sarcina1(); 
        Thread2_Sarcina2 th2 = new Thread2_Sarcina2(); 
        Thread3_Sarcina3 th3 = new Thread3_Sarcina3(); 
        Thread4_Sarcina4 th4 = new Thread4_Sarcina4(); 

        System.out.println("\nThread-urile au pornit.");
        th1.start();
        th2.start();
        th3.start();
        th4.start();

        // 3. Sincronizare Principală: join() - Așteaptă finalizarea tuturor
        System.out.println("\nThread-ul principal asteapta finalizarea sarcinilor de calcul...");
        
        th1.join(); 
        th3.join();
        th2.join(); 
        th4.join();

        System.out.println("\n----------------------------------------------------");
        System.out.println("Toate sarcinile de calcul/parcurgere au fost finalizate.");
        System.out.println("Incepem afisarea textului final in ordine strictă.");
        System.out.println("----------------------------------------------------");

        // 4. Afișarea finală (Ordinea cerută: Th2 -> Th4 -> Th1 -> Th3)
        printWithDelay(INFO_NUME, "Th2");
        printWithDelay(INFO_GRUPA, "Th4");
        printWithDelay(INFO_PRENUME, "Th1");
        printWithDelay(INFO_DISCIPLINA, "Th3");

        System.out.println("\nProgramul s-a terminat.");
    }
}