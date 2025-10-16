/*

    Sunt date 2 fire de executie: fir_1, fir_2. Este dat un sir de numere intregi, din diapazonul de la 0 la 50. Si contine 60 elemente.

    Ambele fire de executie inmultesc cate 2 numere mai mici ca 20.fir_1 de executie incepe realizarea de la inceputul sirului, fir_2 incepe de la finalul sirului.

    Dupa finalizarea calculelor de extras la ecran familia si numele prin primul fir de executie si disciplina prin al doilea fir de executie.


    de facut si prin interapt pe acasa
*/
public class Lab1 {
    public static void main(String []args){
        int tablou[] = new int[60];

        for(int i=0; i<60; i++){
            tablou[i] = (int)(Math.random()*50);

            System.out.print(" " + tablou[i]);
        }

        System.out.println("");

        Thread_1 t1 = new Thread_1(tablou, 0, tablou.length, 1);

        t1.setName("fir_1");
        t1.start();

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



        String numePrenume = "Familia Numele";

        for (char simbol : numePrenume.toCharArray()) {
            System.out.print(simbol);
            try {
                sleep(150);
            } catch (InterruptedException e) {
                System.out.print(e);
            }
        }
        System.out.println();
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

            try {
                sleep(4000);
            }
            catch (InterruptedException e) {
                System.out.print(e);
            }


            String numePrenume = "Programarea concurenta si distribuita";

            for (char simbol : numePrenume.toCharArray()) {
                System.out.print(simbol);
                try {
                    sleep(150);
                } catch (InterruptedException e) {
                    System.out.print(e);
                }
            }

            System.out.println();

        }
    }
