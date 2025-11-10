public class Creare_thread_V {
    private String memberName;
    private int[] sharedArray;

    public Creare_thread_V (String name,int[]array){
        this.memberName=name;
        this.sharedArray=array;
    }

    class Th1 extends Thread{
        @Override
        public void run(){
            System.out.println(memberName +" Th1 Definitia produselor de la inceput");
         
            int diff=0;
            for (int i=0;i<sharedArray.length-2;i+=2){
                    int prod1 = sharedArray[i] * sharedArray[i+2];
                    int prod2 = sharedArray[i + 2] * sharedArray[i + 4];

                    diff += Math.abs(prod1 - prod2); 
                
            }   

            System.out.println(memberName + " Diferenta totala (de la inceput): " + diff);
        }
    }

    class Th2 extends Thread{
        @Override
        public void run(){
            System.out.println(memberName +" Th2 Definitia produselor de la sfarsit");
            
        int diff = 0;
        for (int i = sharedArray.length - 1; i >= 3; i -= 2) {
            if (i % 2 == 0) { 
                int prod1 = sharedArray[i] * sharedArray[i - 2];
                int prod2 = sharedArray[i - 2] * sharedArray[i - 4];
                
                diff += Math.abs(prod1 - prod2);
            }
        }
        System.out.println(memberName + " Diferenta totala (de la sfarsit): " + diff);
    }
}

    public void startThreads(){
        Th1 t1=new Th1();
        Th2 t2=new Th2();

        System.out.println(memberName +" Starting threads...");
        t1.start();
        t2.start();
        try{
            t1.join();
            t2.join();
            System.out.println(memberName +" Threads finished.");
        }
        catch(InterruptedException e){
            System.out.println(memberName +" Thread interrupted: " + e.getMessage());
        }
    }
}
    
