public class Main {
    public static void main(String[] args) {
        ThreadGroup sys = Thread.currentThread().getThreadGroup();
        Thread curr = Thread.currentThread();
        curr.setPriority(3);

        Thread ThA = new Thread(sys,"ThA");
        ThA.start();
        Thread Th11 = new Thread(sys,"Th11");
        Th11.start();
        Thread Th22 = new Thread(sys,"Th22");
        Th22.start();
        sys.list();

        ThreadGroup g2 = new ThreadGroup("G2");
        Thread Th1 = new Thread(g2,"Th1");
        Th1.start();
        Thread Th2 = new Thread(g2,"Th2");
        Th2.start();
        Thread Th33 = new Thread(g2,"Th33");
        Th33.setPriority(7);
        Th33.start();
        g2.list();
        ThreadGroup g1 = new ThreadGroup("G1");
        ThreadGroup g3 = new ThreadGroup(g1,"G3");
        Thread Thaa = new Thread(g3,"Thaa");
        Thaa.setPriority(2);
        Thaa.start();
        Thread Thbb = new Thread(g3, "Thbb");
        Thbb.start();
        Thread Thcc = new Thread(g3, "Thcc");
        Thcc.setPriority(8);
        Thcc.start();
        Thread Thdd = new Thread(g3, "Thdd");
        Thdd.start();
        g1.list();
    }
}