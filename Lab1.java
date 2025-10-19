/*

    Sunt date 2 fire de executie: fir_1, fir_2. Este dat un sir de numere intregi, din diapazonul de la 0 la 100. Si contine 60 elemente.
firul 1
Sumele produselor numerelor impare
două câte două începând căutarea și
sumarea cu primul element
firul 2
Sumele produselor numerelor impare două câte
două începând căutarea și sumarea cu ultimul
element

*/
public class Lab1 {
    public static void main(String []args){
        int tablou[] = new int[100];

        for(int i=0; i<100; i++){
            tablou[i] = (int)(Math.random()*100);

            System.out.print(" " + tablou[i]);
        }

        System.out.println("");

        Thread_1 t1 = new Thread_1(tablou, 0, tablou.length, 1);

        t1.setName("fir_1");
        t1.start();
        try {
            t1.join();
        } catch (Exception e) {
            // TODO: handle exception
        }

    Thread_2 t2 = new Thread_2(tablou, tablou.length - 1, -1, -1);
        t2.setName("fir_2");
        t2.start();

    }
}


class Thread_1 extends Thread {
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
                    s = p * d;
                }
                if ( c == 4) {
                    System.out.println("Produsul este: " + Thread.currentThread().getName() + " " + "primul produs: " + p + " al doilea produs: " + d + " suma: " + s);
                    p = 1;
                    d = 1;
                    c = 0;
                }
            }
        }
    


    }
}

    class Thread_2 extends Thread {
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

            for (int i = from; i > to; i += step) {

            if (tablou[i] %2 != 0) {
                c++;
                if (c <= 2) {
                    p = p * tablou[i];
                } else if (c <=4){
                    p = p * tablou[i];
                }
                if ( c == 4) {
                    System.out.println("Produsul este: " + Thread.currentThread().getName() + " " + p);
                    p = 1;
                    d = 1;
                    c = 0;
                }
            }
        }
        
        String numePrenume = "Procopciuc Daniel";

        for (char simbol : numePrenume.toCharArray()) {
            System.out.print(simbol);
            try {
                sleep(100);
            } catch (InterruptedException e) {
                System.out.print(e);
            }
        }
        System.out.println();


        }
    }   
   

