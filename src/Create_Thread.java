public class Create_Thread {

    private static void afisareLent(String text) {
        try {
            for (char c : text.toCharArray()) {
                System.out.print(c);
                Thread.sleep(80); // metoda Thread.sleep()
            }
            System.out.println();
        } catch (InterruptedException ignored) {}
    }

    public static class Th1 extends Thread {
        @Override
        public void run() {

            Thread.yield(); // ------------------ metoda 1: yield()

            long sum = 0;
            for (int i = 655; i <= 1277; i += 2) {
                int produs = i * (i + 2);
                sum += produs;
                System.out.println("Th1: " + i + " * " + (i + 2) + " = " + produs);
            }
            System.out.println("Suma Th1 = " + sum);

            System.out.println("Th1 (Prenume): ");
            afisareLent("Andrei Victoria");
        }
    }

    public static class Th2 extends Thread {
        private final Thread before;

        public Th2(Thread before) {
            this.before = before;
        }

        @Override
        public void run() {

            long sum = 0;
            for (int i = 907; i >= 125; i -= 2) {
                int produs = i * (i - 2);
                sum += produs;
                System.out.println("Th2: " + i + " * " + (i - 2) + " = " + produs);
            }
            System.out.println("Suma Th2 = " + sum);

            try {
                before.join();      // ----------- metoda 2: join()
            } catch (InterruptedException ignored) {}

            System.out.println("Th2 (Nume): ");
            afisareLent("Lozinschi Muntean");

            before.interrupt();     // ---------- metoda 3: interrupt()
        }
    }

    public static class Th3 extends Thread {
        private final Thread before;

        public Th3(Thread before) {
            this.before = before;
        }

        @Override
        public void run() {

            for (int i = 654; i < 1278; i++) {
                System.out.print("Th3 " + i + " ");
                if (i == 862 || i == 1070) System.out.println();
            }
            System.out.println();

            if (Thread.interrupted()) {
                System.out.println("Th3: Am fost întrerupt, dar continui executia...");
            }

            try { Thread.sleep(200); } catch (InterruptedException ignored) {}  // metoda 4: sleep()

            try { before.join(); } catch (InterruptedException ignored) {}

            System.out.println("Th3 (Disciplina): ");
            afisareLent("Programarea Concurenta si Distribuita");
        }
    }

    public static class Th4 extends Thread {
        private final Thread before;

        public Th4(Thread before) {
            this.before = before;
        }

        @Override
        public void run() {

            for (int i = 908; i > 123; i--) {
                System.out.print("Th4 " + i + " ");
                if (i == 647 || i == 386) System.out.println();
            }
            System.out.println();

            try { before.join(); } catch (InterruptedException ignored) {}

            System.out.println("Th4 (Grupa): ");
            afisareLent("RM-231");
        }
    }

    public static void main(String[] args) {

        Th1 t1 = new Th1();
        Th2 t2 = new Th2(t1);
        Th3 t3 = new Th3(t2);
        Th4 t4 = new Th4(t3);

        t1.setPriority(Thread.MAX_PRIORITY);
        t2.setPriority(7);
        t3.setPriority(5);
        t4.setPriority(3);

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException ignored) {}
    }
}
