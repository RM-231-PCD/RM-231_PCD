public class Main {
    public static void main(String[] args) throws InterruptedException {
        int[] mas = new int[100];

        for (int i = 0; i < mas.length; i++) {
            mas[i] = (int) (Math.random() * 100) + 1;
            System.out.print(mas[i] + " ");
        }
        System.out.println("\n");

        Th1 t1 = new Th1(mas);
        t1.setName("Th1");
        Th2 t2 = new Th2(mas);
        t2.setName("Th2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        String studenti = "Lucrarea a fost realizata de: Sevciuc Roman";
        for (char c : studenti.toCharArray()) {
            System.out.print(c);
            Thread.sleep(100); // pauză de 100ms
        }
        System.out.println();
    }
}

class Th1 extends Thread {
    int[] mas;

    public Th1(int[] mas) {
        this.mas = mas;
    }

    @Override
    public void run() {
        System.out.println("Fir " + Thread.currentThread().getName() + " executa Conditia 1:");
        int suma = 0;
        for (int num : mas) {
            if (num % 2 == 0) { // exemplu: Condiția 1 = numere pare
                suma += num;
            }
        }
        System.out.println("Suma numerelor pare: " + suma);
    }
}

class Th2 extends Thread {
    int[] mas;

    public Th2(int[] mas) {
        this.mas = mas;
    }

    @Override
    public void run() {
        System.out.println("Fir " + Thread.currentThread().getName() + " executa Conditia 2:");
        int suma = 0;
        for (int num : mas) {
            if (num % 2 != 0) { // exemplu: Condiția 2 = numere impare
                suma += num;
            }
        }
        System.out.println("Suma numerelor impare: " + suma);
    }
}