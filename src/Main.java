
public class Main {
    public static void main(String []args) throws InterruptedException {
        int tablou[] = new int[60];

        for(int i=0; i<60; i++){
            tablou[i] = (int)(Math.random()*50);

            System.out.print(" " + tablou[i]);
        }

        System.out.println("");

        Thread_1 t1 = new Thread_1(tablou, 0, tablou.length, 1);

        t1.setName("fir_1");
        t1.start();
        System.out.println("primul fir de executie este intrerupt." + Thread.currentThread().getName());

        Thread_2 t2 = new Thread_2(tablou, tablou.length - 1, 0, -1);
        t2.setName("fir_2");
        t2.start();

    }
}


class Thread_1 extends Thread{
    int tablou[];
    int from;
    int to;
    int step;

    public Thread_1(int tablou[], int from, int to, int step){
        this.tablou = tablou;
        this.from = from;
        this.to = to;
        this.step = step;
    }

    @Override
    public void run() {

        int p = 1;
        int c = 0;

        for (int i = from; i != to; i += step) {
            if (tablou[i] < 20) {
                p = p * tablou[i];
                c++;
                if (c >= 2) {
                    System.out.println("Produsul este: " + Thread.currentThread().getName() + " " + p);
                    p = 1;
                    c = 0;
                }
            }
        }


        String name = ("Sevciuc Roman");


        for (char s : name.toCharArray()) {
            System.out.print(s);
            try {
                sleep(150);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }




}


class Thread_2 extends Thread{
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
    public void run(){

        int p = 1;
        int c = 0;
        for(int i=from; i!=to; i+=step){
            if(tablou[i]<20){
                p = p * tablou[i];
                c++;
                if(c >= 2){
                    System.out.println("Produsul este: " + Thread.currentThread().getName() + " " + p);
                    p = 1;
                    c = 0;
                }
            }
        }


        try {
            Thread.currentThread().join(3500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        String name = ("Obiectul : Programarea concurenta si distributiva. ");
        System.out.println();
        for (char s : name.toCharArray()) {
            System.out.print(s);
            try {
                join(150);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}