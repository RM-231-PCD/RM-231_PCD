public class TemaLaborator {

    public static void enumerateGroup(ThreadGroup group) {
        System.out.println("\n*** Grup: " + group.getName() + 
                           " (Prioritate Maxima: " + group.getMaxPriority() + ") ***");

        int activeThreads = group.activeCount();
        Thread[] threads = new Thread[activeThreads * 2]; 
        int enumeratedThreads = group.enumerate(threads, false); 

        for (int i = 0; i < enumeratedThreads; i++) {
            Thread t = threads[i];
            System.out.println("  Fir: " + t.getName() + 
                               " | Prioritate: " + t.getPriority() + 
                               " | Stare: " + t.getState());
        }

        int activeGroups = group.activeGroupCount();
        ThreadGroup[] groups = new ThreadGroup[activeGroups * 2]; 
        int enumeratedGroups = group.enumerate(groups, false);

        for (int i = 0; i < enumeratedGroups; i++) {
            enumerateGroup(groups[i]); 
        }
    }

    public static void main(String[] args) {
        System.out.println(" Începerea creării structurii de fire (Varianta 3 Nivel Avansat)");
        
        ThreadGroup mainGroup = Thread.currentThread().getThreadGroup();

        
        
        ThreadGroup groupGN = new ThreadGroup(mainGroup, "GN");
        
        ThreadGroup groupGH = new ThreadGroup(groupGN, "GH");
        
        ThreadGroup groupGM = new ThreadGroup(mainGroup, "GM");

        
        new MyThread(groupGH, "Tha", 4);
        new MyThread(groupGH, "Thb", 3);
        new MyThread(groupGH, "Thc", 6);
        new MyThread(groupGH, "Thd", 3);

        new MyThread(groupGN, "ThA", 3);

        new MyThread(groupGM, "Th1", 2); 
        new MyThread(groupGM, "Th2", 3); 
        new MyThread(groupGM, "Th3", 3);

        new MyThread(mainGroup, "Th_Main_1", 8); 
        new MyThread(mainGroup, "Th_Main_2", 3); 
        try {
            Thread.sleep(500); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("\n=============================================");
        System.out.println(" Enumerarea firelor active din grupul principal 'main':");
        System.out.println("=============================================");
        e
        enumerateGroup(mainGroup);

        System.out.println("\nFinalizarea programului principal.");
    }
}