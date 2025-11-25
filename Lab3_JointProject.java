import java.util.Random;

public class Lab3_JointProject {
    static final int ARRAY_SIZE = 100;
    static int[] b = new int[ARRAY_SIZE];
    static int[] oddNumbers; 
    static int oddCount = 0;

    static final String INFO_NUME = "\nDodon Cristin";      
    static final String INFO_PRENUME = "\nPrepelita Catalin";     
    static final String INFO_DISCIPLINA = "\nProgramarea Concurenta si Distribuita"; 
    static final String INFO_GRUPA = "\nRM-231";                   

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

    // PARTEA LUI PREPELITA CATALIN (Th1 & Th3)

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
                    Thread.sleep(50); 
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println(getName() + " a finalizat.");
        }
    }

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


    // PARTEA LUI DODON CRISTIN (Th2 & Th4)
    
    static class Thread2_Sarcina2 extends Thread {
        public Thread2_Sarcina2() { 
            setName("Th2"); 
            setPriority(MAX_PRIORITY); 
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

    // Thread 4: Parcurgere [2111, 1456] 
    static class Thread4_Sarcina4 extends Thread {
        public Thread4_Sarcina4() { setName("Th4"); }

        @Override
        public void run() {
            System.out.println("Starting " + getName() + " (Sarcina 4)...");
            for (int i = 2111; i >= 1456; i--) {
                if (i % 70 == 0) {
                    System.out.print(getName() + ":" + i + " | ");
                }
                Thread.yield(); 
            }
            System.out.println("\n" + getName() + " a finalizat parcurgerea.");
        }
    }

    // FUNCTIA MAIN (Sincronizare Principală: join())
   

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();

        System.out.println("- Varianta 3:");
        for (int i = 0; i < ARRAY_SIZE; i++) {
            b[i] = random.nextInt(100) + 1;
            if (b[i] % 2 != 0) {
                oddCount++;
            }
        }
        oddNumbers = new int[oddCount];
        int k = 0;
        for (int i = 0; i < ARRAY_SIZE; i++) {
            if (b[i] % 2 != 0) {
                oddNumbers[k++] = b[i];
            }
        }
        System.out.println("Tabloul generat. Total numere impare: " + oddCount);
        
        Thread1_Sarcina1 th1 = new Thread1_Sarcina1(); 
        Thread2_Sarcina2 th2 = new Thread2_Sarcina2(); 
        Thread3_Sarcina3 th3 = new Thread3_Sarcina3(); 
        Thread4_Sarcina4 th4 = new Thread4_Sarcina4(); 

        System.out.println("\n--- Punctul de Pornire a Execuției Concurente ---");
        th1.start();
        th2.start();
        th3.start();
        th4.start();

        System.out.println("\n--- Așteptăm finalizarea tuturor sarcinilor (Metoda: join()) ---");
        
        th1.join(); 
        th3.join();
        th2.join(); 
        th4.join();

        System.out.println("\n----------------------------------------------------");
        System.out.println("Sarcinile de calcul au fost finalizate de ambele părți.");
        System.out.println("Incepem afisarea ordonată a rezultatului final.");
        System.out.println("----------------------------------------------------");

        printWithDelay(INFO_NUME, "Th2");
        printWithDelay(INFO_GRUPA, "Th4");
        printWithDelay(INFO_PRENUME, "Th1");
        printWithDelay(INFO_DISCIPLINA, "Th3");

        System.out.println("\nProgramul s-a terminat.");
    }
}