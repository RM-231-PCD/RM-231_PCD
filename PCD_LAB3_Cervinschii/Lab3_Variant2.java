public class Lab3_Variant2 {
    static int size = 100;
    static int[] randomArray = new int[size];
    static int[] evenPositions;
    static int evenCount = 0;
    
    static Thread1 th1 = new Thread1();
    static Thread2 th2 = new Thread2();
    
    public static void main(String[] args) throws InterruptedException {
        
        System.out.println("[STEP 1] Generating random array of " + size + " elements:");
        for (int i = 0; i < size; i++) {
            randomArray[i] = (int) (Math.random() * 100) + 15;
            System.out.print(randomArray[i] + " ");
            
            if ((i + 1) % 20 == 0) {
                System.out.println();
            }
            
            if (randomArray[i] % 2 == 0) {
                evenCount++;
            }
        }
        
        System.out.println("[RESULT] Found even numbers: " + evenCount);
        
        evenPositions = new int[evenCount];
        int k = 0;
        
        for (int i = 0; i < size; i++) {
            if (randomArray[i] % 2 == 0) {
                evenPositions[k] = i;
                k++;
            }
        }
        
        System.out.println("[STEP 2] Positions of even numbers in array:");
        for (int i = 0; i < evenCount; i++) {
            System.out.print("Pos[" + evenPositions[i] + "]=" + randomArray[evenPositions[i]] + "  ");
            if ((i + 1) % 10 == 0) {
                System.out.println();
            }
        }

        
        th1.setName("Thread-1");
        th2.setName("Thread-2");
        
        System.out.println(" " + th1.getName() + ": Sum of positions from start");
        System.out.println(" " + th2.getName() + ": Sum of positions from end");
        
        th1.start();
        th2.start();
        
        System.out.println("[MAIN] Waiting for threads completion (using join())...");
        th1.join();
        th2.join();
    }
    
    static void displayText(String text) {
        for (int i = 0; i < text.length(); i++) {
            System.out.print(text.charAt(i));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println();
    }
}

class Thread1 extends Thread {
    public void run() {
        if (!isInterrupted()) {
            System.out.println("[THREAD 1] Algorithm: taking pairs of positions from start");
            System.out.println("[THREAD 1] Formula: (pos[0] + pos[1]) + (pos[2] + pos[3]) + ...");
            
            int totalSum = 0;
            int pairNumber = 1;
            
            for (int i = 0; i < Lab3_Variant2.evenCount - 1; i += 2) {
                int pos1 = Lab3_Variant2.evenPositions[i];
                int pos2 = Lab3_Variant2.evenPositions[i + 1];
                int sum = pos1 + pos2;
                totalSum += sum;
                
                System.out.println("[THREAD 1] Pair " + pairNumber + ": position[" + i + "]=" + pos1 + 
                                 " + position[" + (i+1) + "]=" + pos2 + " = " + sum);
                
                pairNumber++;
                
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    System.out.println("[THREAD 1] Interrupted during calculations!");
                    return;
                }
            }
            
            System.out.println("TOTAL SUM OF ALL PAIRS: " + totalSum);
            
            this.interrupt();
            System.out.println("[THREAD 1] Task completed! interrupt() called");
        }
        
        System.out.println("[THREAD 1] Waiting for Thread 2 completion (using isAlive())...");
        while (Lab3_Variant2.th2.isAlive()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println("[THREAD 1] Thread 2 completed! Starting information output.");
        
        Lab3_Variant2.displayText("Surname: Cervinschii");
        
        System.out.println("THREAD 1 COMPLETED");
    }
}

class Thread2 extends Thread {
    public void run() {
        System.out.println("[THREAD 2] START - Sum of positions (from end)");
        
        if (!isInterrupted()) {
            System.out.println("[THREAD 2] Algorithm: taking pairs of positions from end");
            System.out.println("[THREAD 2] Formula: (pos[N] + pos[N-1]) + (pos[N-2] + pos[N-3]) + ...");
            
            int totalSum = 0;
            int pairNumber = 1;
            
            for (int i = Lab3_Variant2.evenCount - 1; i > 0; i -= 2) {
                int pos1 = Lab3_Variant2.evenPositions[i];
                int pos2 = Lab3_Variant2.evenPositions[i - 1];
                int sum = pos1 + pos2;
                totalSum += sum;
                
                System.out.println("[THREAD 2] Pair " + pairNumber + ": position[" + i + "]=" + pos1 + 
                                 " + position[" + (i-1) + "]=" + pos2 + " = " + sum);
                
                pairNumber++;
                
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    System.out.println("[THREAD 2] Interrupted during calculations!");
                    return;
                }
            }
            
            System.out.println("[THREAD 2] TOTAL SUM OF ALL PAIRS: " + totalSum);
            
            this.interrupt();
            System.out.println("[THREAD 2] Task completed! interrupt() called");
        }
        
        Lab3_Variant2.displayText("Name: Chiril");
        
        System.out.println("[THREAD 2] COMPLETED");
    }
}