public class Create_Thread {

    // Afișare lentă: 100 ms între litere
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

    // Th1 – Sarcina 1: sumele produselor numerelor impare două câte două,
    // începând cu primul element (interval [654, 1278])
    public static class Th1 extends Thread {
        private Thread dep; // de cine așteaptă înainte de afișarea prenumelui

        public Th1(Thread dep) {
            this.dep = dep;
        }

        @Override
        public void run() {
            long sum = 0;

            for (int i = 655; i <= 1277; i += 2) { // numere impare din [654,1278]
                if (i + 2 <= 1277) {
                    int produs = i * (i + 2);
                    sum += produs;
                    System.out.println("Th1: " + i + " * " + (i + 2) + " = " + produs);
                }
            }
            System.out.println("Suma Th1 = " + sum);

            // Așteaptă ca Th4 să-și afișeze grupa (sincronizare cu join)
            try {
                if (dep != null) {
                    dep.join();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // Afișează prenumele
            System.out.print("Th1 (Prenume): ");
            afisareLent("Andrei Victoria");
        }
    }

    // Th2 – Sarcina 2: sumele produselor numerelor impare două câte două,
    // începând cu ultimul element (interval [123, 908])
    public static class Th2 extends Thread {
        @Override
        public void run() {
            long sum = 0;

            for (int i = 907; i >= 125; i -= 2) { // numere impare din [123,908], de la sfârșit
                if (i - 2 >= 125) {
                    int produs = i * (i - 2);
                    sum += produs;
                    System.out.println("Th2: " + i + " * " + (i - 2) + " = " + produs);
                }
            }
            System.out.println("Suma Th2 = " + sum);

            // După sarcina 2, afișează numele (primul text)
            System.out.print("Th2 (Nume): ");
            afisareLent("Lozinschi Muntean");
        }
    }

    // Th3 – Sarcina 3: de parcurs de la început intervalul [654,1278]
    public static class Th3 extends Thread {
        private Thread dep; // așteaptă Th1 înainte de a afișa disciplina

        public Th3(Thread dep) {
            this.dep = dep;
        }

        @Override
        public void run() {

            for (int i = 654; i < 1278; i++) {
                System.out.print("Th3 " + i + " ");
                if (i == 862 || i == 1070) {
                    System.out.println();
                }

                // Folosim yield() ca metodă de sincronizare pentru perechea 3–4
                if (i % 50 == 0) {
                    Thread.yield();
                }
            }
            System.out.println();

            // Așteaptă ca Th1 să termine și să afișeze prenumele
            try {
                if (dep != null) {
                    dep.join();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // Afișează disciplina (ultimul text)
            System.out.print("Th3 (Disciplina): ");
            afisareLent("Programarea Concurenta si Distribuita");
        }
    }

    // Th4 – Sarcina 4: de parcurs de la sfârșit intervalul [123,908]
    public static class Th4 extends Thread {
        private Thread dep; // așteaptă Th2 înainte de a afișa grupa

        public Th4(Thread dep) {
            this.dep = dep;
        }

        @Override
        public void run() {

            for (int i = 908; i > 123; i--) {
                System.out.print("Th4 " + i + " ");
                if (i == 647 || i == 386) {
                    System.out.println();
                }

                // Mic sleep ca metodă de sincronizare pentru perechea 3–4
                if (i % 60 == 0) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
            System.out.println();

            // Așteaptă ca Th2 să termine și să afișeze numele
            try {
                if (dep != null) {
                    dep.join();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // Afișează grupa (al doilea text)
            System.out.print("Th4 (Grupa): ");
            afisareLent("RM-231");
        }
    }

    public static void main(String[] args) {

        // Creăm firele în ordinea necesară pentru dependențe
        Th2 t2 = new Th2();
        Th4 t4 = new Th4(t2);   // Th4 așteaptă Th2
        Th1 t1 = new Th1(t4);   // Th1 așteaptă Th4
        Th3 t3 = new Th3(t1);   // Th3 așteaptă Th1

        // Pentru perechea 1–2 folosim și setPriority ca a doua metodă de sincronizare
        t2.setPriority(Thread.NORM_PRIORITY + 1);
        t1.setPriority(Thread.NORM_PRIORITY);

        t3.start();
        t4.start();
        t1.start();
        t2.start();

        // Main așteaptă toate firele (nu e cerut în mod special, dar e frumos să se termine curat)
        try {
            t3.join();
            t4.join();
            t1.join();
            t2.join();
        } catch (InterruptedException ignored) {}
    }
}
