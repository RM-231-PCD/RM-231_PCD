public class labpcd1 {
    public static void main(String []args){
        int mas[] = new int[100];

        for(int i=0; i<100; i++){
            mas[i] = (int)(Math.random()*100);

            System.out.print(" " + mas[i]);
        }

        System.out.println("");

        Thread_3 r3 = new Thread_3(mas, 0, mas.length, 2);
        Thread t3 = new Thread(r3);

        t3.setName("fir_3");
        t3.start();
        // try {
        //     t3.join();
        // } catch (Exception e) {
        //     System.out.println(e);
        // test
        // }



        Thread_4 r4 = new Thread_4(mas, mas.length - 1, -1, -2);
        Thread t4 =  new Thread(r4);
        t4.setName("fir_4");
        t4.start();
    }
}

class Thread_3 implements Runnable {
    int tablou[];
    int from;
    int to;
    int step;

    public Thread_3(int tablou[], int from, int to, int step) {
        this.tablou = tablou;
        this.from = from;
        this.to = to;
        this.step = step;
    }
    @Override
    public void run() {

        int p = 1;
        int c=0;
        int pp=1;
        int s=0;

        for (int i = from; i != to; i += step) {
            c++;
            if (c <= 2) {
                p = p * tablou[i];                }
            else if(c<=4){
                pp=pp*tablou[i];
                s=p+pp;
            }
            if(c==4){
                System.out.println("Produsul este: " + Thread.currentThread().getName() + " " + "primul produs: " + p + " al doilea produs: " + pp + " suma: " + s);                p=1;
                pp=1;
                p=1;
                c=0;
            }
        }
    }
}

class Thread_4 implements Runnable{
    int tablou[];
    int from;
    int to;
    int step;

    public Thread_4(int tablou[], int from, int to, int step) {
        this.tablou = tablou;
        this.from = from;
        this.to = to;
        this.step = step;
    }
    @Override
    public void run() {

        int p = 1;
        int c=0;
        int pp=1;
        int s=0;

        for (int i = from; i != to; i += step) {
            c++;
            if (c <= 2) {
                p = p * tablou[i];                }
            else if(c<=4){
                pp=pp*tablou[i];
                s=p+pp;
            }
            if(c==4){
                System.out.println("Produsul este: " + Thread.currentThread().getName() + " " + "primul produs: " + p + " al doilea produs: " + pp + " suma: " + s);                p=1;
                pp=1;
                p=1;
                c=0;
            }
        }
        String numePrenume = "DDonesco Alina";

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