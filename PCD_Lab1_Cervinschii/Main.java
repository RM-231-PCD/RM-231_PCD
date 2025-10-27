public class Main 
{
    public static void main(String[] args) 
    {
        ThreadGroup sys = Thread.currentThread().getThreadGroup();
        Thread curr = Thread.currentThread();
        curr.setPriority(curr.getPriority());

        Thread Th1_main = new Thread(sys, "Th1");
        Th1_main.setPriority(7);
        Thread Th2_main = new Thread(sys, "Th2");
        Th2_main.setPriority(7);
        Thread ThA_main = new Thread(sys, "ThA");
        ThA_main.setPriority(3);
        
        Th1_main.start();
        Th2_main.start();
        ThA_main.start();
        
        sys.list();
        
        ThreadGroup G1 = new ThreadGroup("G1");
        
        ThreadGroup G3 = new ThreadGroup(G1, "G3");
        
        Thread Tha = new Thread(G3, "Tha")
        {
            public void run() 
            {
                System.out.println(getName() + " is running!");
                System.out.println("Priority: " + getPriority());
            }
        };

        Tha.setPriority(3);
        Thread Thb = new Thread(G3, "Thb");
        Thb.setPriority(3);
        Thread Thc = new Thread(G3, "Thc");
        Thc.setPriority(3);
        Thread Thd = new Thread(G3, "Thd");
        Thd.setPriority(3);
        
        Tha.start();
        Thb.start();
        Thc.start();
        Thd.start();

        G3.list();
        
        ThreadGroup G2 = new ThreadGroup("G2");
        
        Thread Th1_G2 = new Thread(G2, "Th1");
        Th1_G2.setPriority(4);
        Thread Th2_G2 = new Thread(G2, "Th2");
        Th2_G2.setPriority(5);
        Thread Th3_G2 = new Thread(G2, "Th3");
        Th3_G2.setPriority(5);
        
        Th1_G2.start();
        Th2_G2.start();
        Th3_G2.start();

        G2.list();
        
    
    }
}