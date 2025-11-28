public class Create_Thread {

    private static void afisareLent(String text) {
        try {
            for (char c : text.toCharArray()) {
                System.out.print(c);
                Thread.sleep(100);
            }
            System.out.println();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static class Th1 extends Thread {

        @Override
        public void run() {
            long sum = 0;

            for (int i = 655; i <= 1277; i += 2) {
                if (i + 2 <= 1277) {
                    int produs = i * (i + 2);
                    sum += produs;
                    System.out.println("Th1: " + i + " * " + (i + 2) + " = " + produs);
                }
            }
            System.out.println("Suma Th1 = " + sum);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            System.out.print("Th1 (Prenume): ");
            afisareLent("Andrei Victoria");
        }
    }

    public static class Th2 extends Thread {
        @Override
        public void run() {
            long sum = 0;

            for (int i = 907; i >= 125; i -= 2) {
                if (i - 2 >= 125) {
                    int produs = i * (i - 2);
                    sum += produs;
                    System.out.println("Th2: " + i + " * " + (i - 2) + " = " + produs);
                }
            }
            System.out.println("Suma Th2 = " + sum);

            try {
                Thread.sleep(2700);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.print("Th2 (Nume): ");
            afisareLent("Lozinschi Muntean");
        }
    }

    public static class Th3 extends Thread {

        @Override
        public void run() {

            for (int i = 654; i < 1278; i++) {
                System.out.print("Th3 " + i + " ");
                if (i == 862 || i == 1070) {
                    System.out.println();
                }
            }
            System.out.println();

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.print("Th3 (Disciplina): ");
            afisareLent("Programarea Concurenta si Distribuita");
        }
    }

    public static class Th4 extends Thread {

        @Override
        public void run() {

            for (int i = 908; i > 123; i--) {
                System.out.print("Th4 " + i + " ");
                if (i == 647 || i == 386) {
                    System.out.println();
                }
            }

            System.out.println();

            try {
                Thread.sleep(9000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.print("Th4 (Grupa): ");
            afisareLent("RM-231");
        }
    }

    public static void main(String[] args) {

        Th2 t2 = new Th2();
        Th4 t4 = new Th4();
        Th1 t1 = new Th1();
        Th3 t3 = new Th3();

        t3.start();
        t4.start();
        t1.start();
        t2.start();

        try {
            t3.join();
            t4.join();
            t1.join();
            t2.join();
        } catch (InterruptedException ignored) {}
    }
}
