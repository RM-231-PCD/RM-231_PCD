public class Lab1 {
    public static void main(String []args){
        int tablou[] = new int[100];

        for(int i=0; i<100; i++){
            tablou[i] = (int)(Math.random()*100);

            System.out.print(" " + tablou[i]);
        }

        System.out.println("");

        Thread_1 r1 = new Thread_1(tablou, 0, tablou.length, 1);
        Thread t1 = new Thread(r1);

        t1.setName("fir_1");
        t1.start();

        Thread_2 r2 = new Thread_2(tablou,  tablou.length -1, -1, -1);
        Thread t2 = new Thread(r2);
        t2.setName("fir_2");
        t2.start();


    }
}


class Thread_1 implements Runnable {
    int tablou[]; 
    int from;
    int to;
    int step;

    public Thread_1(int tablou[], int from, int to, int step) {
        this.tablou = tablou;
        this.from = from;
        this.to = to;
        this.step = step;
    }

    @Override
    public void run() {

        int p = 1;
        int d = 1;
        int c = 0;
        int s = 0;


        for (int i = from; i != to; i += step) {
            if (tablou[i] %2 != 0) {
                c++;
                if (c <= 2) {
                    p = p * tablou[i];
                } else if (c <=4){
                    d = d * tablou[i];
                    s = p + d;
                }
                if ( c == 4) {
                    System.out.println("Produsul este: " + Thread.currentThread().getName() + " " + "primul produs: " + p + " al doilea produs: " + d + " suma: " + s);
                    p = 1;
                    d = 1;
                    c = 0;
                    s = 0;
                }
            }
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.out.print(e);
        }
    }
}

    class Thread_2 implements Runnable {
        int tablou[];
        int from;
        int to;
        int step;

        public Thread_2(int tablou[], int from, int to, int step) {
            this.tablou = tablou;
            this.from = from;
            this.to = to;
            this.step = step;
        }


        @Override
        public void run() {

            int p = 1;
            int c = 0;
            int d = 1;
            int s = 0;

            for (int i = from; i > to; i += step) {
                

            if (tablou[i] %2 != 0) {
                c++;
                if (c <= 2) {
                    p = p * tablou[i];
                } else if (c <=4){
                    d = d * tablou[i];
                    s = p + d;
                }
                if ( c == 4) {
                   System.out.println("Produsul este: " + Thread.currentThread().getName() + " " + "primul produs: " + p + " al doilea produs: " + d + " suma: " + s);
                    p = 1;
                    d = 1;
                    c = 0;
                    s = 0;
                }
            }
        }
         
        
        
        
        String numePrenume = "Dodon Cristin";
        for (char simbol : numePrenume.toCharArray()) {
            System.out.print(simbol);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.print(e);
            }
        }
        System.out.println();

        }
    }
//spatiu