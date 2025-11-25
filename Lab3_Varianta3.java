import java.util.Random;

public class Lab3_Varianta3 {
    // Variabile partajate și constante
    static final int ARRAY_SIZE = 100;
    static int[] b = new int[ARRAY_SIZE]; // Tabloul original
    static int[] oddNumbers; // Tabloul doar cu numere impare
    static int oddCount = 0;

    // Informații de afișat la final (ADAPTĂ ACESTEA CU DATELE TALE)
    static final String INFO_NUME = "\nDodon Cristin "; // Afișat de Th2 (Coleg)
    static final String INFO_PRENUME = "\nPrepelita Catalin"; // Afișat de Th1 (Partea ta)
    static final String INFO_DISCIPLINA = "\nProgramarea Concurenta si Distribuita"; // Afișat de Th3 (Partea ta)
    static final String INFO_GRUPA = "\nRM-231"; // Afișat de Th4 (Coleg)

    // Clasa pentru Thread 1 (Partea ta) - Sarcina 1
    static class Thread1_Sarcina1 extends Thread {
        public Thread1_Sarcina1() {
            setName("Th1");
        }

        @Override
        public void run() {
            System.out.println("Starting " + getName() + " (Sarcina 1)...");
            long totalSum = 0;
            int count = 0;

            // Sumele numerelor impare două câte două începând de la primul element
            for (int i = 0; i < oddCount; i += 2) {
                if (i + 1 < oddCount) {
                    int sum = oddNumbers[i] + oddNumbers[i + 1];
                    totalSum += sum;
                    System.out.println(getName() + ": Suma " + oddNumbers[i] + " + " + oddNumbers[i + 1] + " = " + sum);
                    count++;
                }
                try {
                    // Sincronizare 1: Simulează munca și concurența
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println(getName() + " a finalizat. Total sume: " + totalSum + " din " + count + " perechi.");
        }
    }

    // Clasa pentru Thread 3 (Partea ta) - Sarcina 3
    static class Thread3_Sarcina3 extends Thread {
        public Thread3_Sarcina3() {
            setName("Th3");
        }

        @Override
        public void run() {
            System.out.println("Starting " + getName() + " (Sarcina 3)...");
            // De parcurs de la început intervalul [0, 798]
            for (int i = 0; i <= 798; i++) {
                // Afișează doar un eșantion pentru a nu supraîncărca output-ul
                if (i % 80 == 0) {
                    System.out.print(getName() + ":" + i + " | ");
                }
                try {
                    // Sincronizare 1: Simulează munca și concurența
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("\n" + getName() + " a finalizat parcurgerea.");
        }
    }

    // Clasa pentru Thread 2 (Coleg) - Sarcina 2
    // Aici se folosește metoda setPriority() ca altă metodă de sincronizare
    static class Thread2_Sarcina2 extends Thread {
        public Thread2_Sarcina2() {
            setName("Th2");
            // setPriority(MAX_PRIORITY); // O metodă pe care o poate folosi colegul
        }

        @Override
        public void run() {
            System.out.println("Starting " + getName() + " (Sarcina 2)...");
            long totalSum = 0;
            int count = 0;

            // Sumele numerelor impare două câte două începând de la ultimul element
            for (int i = oddCount - 1; i >= 0; i -= 2) {
                if (i - 1 >= 0) {
                    int sum = oddNumbers[i] + oddNumbers[i - 1];
                    totalSum += sum;
                    System.out.println(getName() + ": Suma " + oddNumbers[i] + " + " + oddNumbers[i - 1] + " = " + sum);
                    count++;
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println(getName() + " a finalizat. Total sume: " + totalSum + " din " + count + " perechi.");
        }
    }

    // Clasa pentru Thread 4 (Coleg) - Sarcina 4
    static class Thread4_Sarcina4 extends Thread {
        public Thread4_Sarcina4() {
            setName("Th4");
        }

        @Override
        public void run() {
            System.out.println("Starting " + getName() + " (Sarcina 4)...");
            // De parcurs de la sfârșit intervalul [1456, 2111]
            for (int i = 2111; i >= 1456; i--) {
                // Afișează doar un eșantion
                if (i % 70 == 0) {
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

    // Metodă ajutătoare pentru afișarea textului cu întârziere (Partea ta)
    private static void printWithDelay(String text, String threadName) {
        System.out.print(threadName + " afiseaza: ");
        for (int i = 0; i < text.length(); i++) {
            System.out.print(text.charAt(i));
            try {
                // Sincronizare 2: Thread.sleep(100) pentru pauză între litere
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println();
    }

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();

        // 1. Inițializarea tabloului b și extragerea numerelor impare
        System.out.println("Printing Array b (Random 1-100):");
        for (int i = 0; i < ARRAY_SIZE; i++) {
            b[i] = random.nextInt(100) + 1;
            System.out.print(b[i] + (i % 20 == 19 ? "\n" : " "));
            if (b[i] % 2 != 0) {
                oddCount++;
            }
        }
        System.out.println("\nTotal numere impare: " + oddCount);

        // Crearea tabloului cu numere impare (oddNumbers)
        oddNumbers = new int[oddCount];
        int k = 0;
        for (int i = 0; i < ARRAY_SIZE; i++) {
            if (b[i] % 2 != 0) {
                oddNumbers[k++] = b[i];
            }
        }

        // 2. Crearea și pornirea thread-urilor
        Thread1_Sarcina1 th1 = new Thread1_Sarcina1();
        Thread2_Sarcina2 th2 = new Thread2_Sarcina2();
        Thread3_Sarcina3 th3 = new Thread3_Sarcina3();
        Thread4_Sarcina4 th4 = new Thread4_Sarcina4();

        th1.start();
        th2.start();
        th3.start();
        th4.start();

        // 3. Sincronizare 1: Așteptăm finalizarea tuturor thread-urilor cu join()
        // Thread-ul principal (main) așteaptă cele 4 thread-uri să-și termine sarcinile
        System.out.println("\nThread-ul principal asteapta finalizarea sarcinilor de calcul...");
        
        // **PARTEA TA: Astepti Th1 si Th3**
        th1.join(); 
        th3.join();
        
        // **PARTEA COLEGULUI: Astepti Th2 si Th4**
        th2.join(); 
        th4.join();

        System.out.println("\n----------------------------------------------------");
        System.out.println("Toate sarcinile de calcul/parcurgere au fost finalizate.");
        System.out.println("Incepem afisarea textului final in ordine strictă.");
        System.out.println("----------------------------------------------------");


        // 4. Afișarea finală (Ordinea cerută: Th2 -> Th4 -> Th1 -> Th3)
        // Folosim thread-ul principal pentru a garanta ordinea și a folosi sleep() doar pentru afișare.
        
        // 1. Th2 (Coleg): Numele
        printWithDelay(INFO_NUME, "Th2");
        
        // 2. Th4 (Coleg): Grupa
        printWithDelay(INFO_GRUPA, "Th4");
        
        // 3. Th1 (Partea ta): Prenumele
        printWithDelay(INFO_PRENUME, "Th1");

        // 4. Th3 (Partea ta): Disciplina
        printWithDelay(INFO_DISCIPLINA, "Th3");

        System.out.println("\nProgramul s-a terminat.");
    }
}