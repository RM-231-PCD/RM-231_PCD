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
            System.out.println(memberName +" Th1 condition 1");
            System.out.println(memberName +" Th1:First 5 array values:");

            for (int i=0;i<5 && i<sharedArray.length;i++){
                System.out.println(" mas["+i+"]="+sharedArray[i]);
            }
        }
    }
    class Th2 extends Thread{
        @Override
        public void run(){
            System.out.println(memberName +" Th2 condition 2");
            System.out.println(memberName +" Th2:Last 5 array values:");

            for (int i=sharedArray.length-5;i<sharedArray.length;i++){
                if(i>=0){
                    System.out.println(" mas["+i+"]="+sharedArray[i]);
                }
            }
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
    
