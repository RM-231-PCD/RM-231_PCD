public class Main {
    public static void main(String[] args) {

        ThreadGroup sys = Thread.currentThread().getThreadGroup();
        System.out.println("=== (1) Grupul sys inițial ===");
        sys.list();

        sys.setMaxPriority(Thread.MAX_PRIORITY - 1);
        Thread curr = Thread.currentThread();
        curr.setPriority(curr.getPriority() + 1);
        System.out.println("=== (2) După modificarea priorității în sys ===");
        sys.list();

        ThreadGroup g2 = new ThreadGroup(sys, "G2");


        ThreadGroup g4 = new ThreadGroup(g2, "G4");


        ThreadGroup g1 = new ThreadGroup(g4, "G1");
        new MyThread(g1, "Tha", 1);
        new MyThread(g1, "Thb", 3);
        new MyThread(g1, "Thc", 8);
        new MyThread(g1, "Thd", 3);
        System.out.println("=== (3) Grupul G1 ===");
        g1.list();


        new MyThread(g2, "ThA", 1);
        System.out.println("=== (4) Grupul G2 ===");
        g2.list();

        // Grup G3 în sys (nu în G2)
        ThreadGroup g3 = new ThreadGroup(sys, "G3");
        new MyThread(g3, "Th1_G3", 4);
        new MyThread(g3, "Th2_G3", 3);
        new MyThread(g3, "Th3_G3", 5);
        System.out.println("=== (5) Grupul G3 ===");
        g3.list();

        // Fire direct în sys (main)
        new MyThread(null, "Th1_Main", 3);
        new MyThread(null, "Th2_Main", 6);
        System.out.println("=== (6) Grupul sys final ===");
        sys.list();

        System.out.println("=== Toate firele au fost pornite ===");
    }
}

class MyThread extends Thread {
    public MyThread(ThreadGroup group, String name, int priority) {
        super(group, name);
        setPriority(priority);
        start();
    }

    @Override
    public void run() {
        String groupName = (getThreadGroup() != null) ? getThreadGroup().getName() : "Main";
        System.out.println("Fir: " + getName() +
                " | Grup: " + groupName +
                " | Prioritate: " + getPriority());
    }
}
