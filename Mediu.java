public class Mediu {
    public static void main(String[] args) {
        ThreadGroup principal = Thread.currentThread().getThreadGroup();


        ThreadGroup g1 = new ThreadGroup("G1");
        Thread tha = new Thread(g1, "Tha");
        tha.setPriority(1);
        Thread thb = new Thread(g1, "Thb");
        thb.setPriority(3);
        Thread thc = new Thread(g1, "Thc");
        thc.setPriority(8);
        Thread thd = new Thread(g1, "Thd");
        thd.setPriority(3);

        ThreadGroup g2 = new ThreadGroup("G2");
        Thread thA = new Thread(g2, "ThA");
        thA.setPriority(1);


        Thread th1 = new Thread(principal, "Th1");
        th1.setPriority(3);
        Thread th2 = new Thread(principal, "Th2");
        th2.setPriority(6);

        //Aici pornim threadul
        tha.start();
        thb.start();
        thc.start();
        thd.start();
        thA.start();
        th1.start();
        th2.start();

        //Afisare
        System.out.println("=== Fire din grupul principal ===");
        System.out.println("Fir: " + th1.getName() + " | Grup: " + th1.getThreadGroup().getName() + " | Prioritate: " + th1.getPriority());
        System.out.println("Fir: " + th2.getName() + " | Grup: " + th2.getThreadGroup().getName() + " | Prioritate: " + th2.getPriority());

        System.out.println("=== Fire din grupul G1 ===");
        System.out.println("Fir: " + tha.getName() + " | Grup: " + tha.getThreadGroup().getName() + " | Prioritate: " + tha.getPriority());
        System.out.println("Fir: " + thb.getName() + " | Grup: " + thb.getThreadGroup().getName() + " | Prioritate: " + thb.getPriority());
        System.out.println("Fir: " + thc.getName() + " | Grup: " + thc.getThreadGroup().getName() + " | Prioritate: " + thc.getPriority());
        System.out.println("Fir: " + thd.getName() + " | Grup: " + thd.getThreadGroup().getName() + " | Prioritate: " + thd.getPriority());

        System.out.println("=== Fire din grupul G2 ===");
        System.out.println("Fir: " + thA.getName() + " | Grup: " + thA.getThreadGroup().getName() + " | Prioritate: " + thA.getPriority());
    }
}
