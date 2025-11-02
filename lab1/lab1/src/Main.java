public class Main {
    public static void main(String[] args) {

        ThreadGroup mainGroup = new ThreadGroup("Main");

        ThreadGroup g1 = new ThreadGroup(mainGroup, "G1");
        ThreadGroup g2 = new ThreadGroup(mainGroup, "G2");
        ThreadGroup g3 = new ThreadGroup(g1, "G3");

        Thread tha = new CustomThread(g3, "Tha", 3);
        Thread thb = new CustomThread(g3, "Thb", 3);
        Thread thc = new CustomThread(g3, "Thc", 3);
        Thread thd = new CustomThread(g3, "Thd", 3);

        Thread th1_g2 = new CustomThread(g2, "Th1", 4);
        Thread th2_g2 = new CustomThread(g2, "Th2", 5);
        Thread th3_g2 = new CustomThread(g2, "Th3", 5);

        Thread th1_main = new CustomThread(mainGroup, "Th1", 7);
        Thread th2_main = new CustomThread(mainGroup, "Th2", 7);
        Thread thA_main = new CustomThread(mainGroup, "ThA", 3);

        tha.start();
        thb.start();
        thc.start();
        thd.start();
        th1_g2.start();
        th2_g2.start();
        th3_g2.start();
        th1_main.start();
        th2_main.start();
        thA_main.start();

        // Ждем завершения всех потоков
        try {
            tha.join();
            thb.join();
            thc.join();
            thd.join();
            th1_g2.join();
            th2_g2.join();
            th3_g2.join();
            th1_main.join();
            th2_main.join();
            thA_main.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\nВсе потоки завершили выполнение.");
    }
}

class CustomThread extends Thread {
    public CustomThread(ThreadGroup group, String name, int priority) {
        super(group, name);
        this.setPriority(priority);
    }

    @Override
    public void run() {
        System.out.println("Поток: " + getName() +
                " | Группа: " + getThreadGroup().getName() +
                " | Приоритет: " + getPriority());
    }
}