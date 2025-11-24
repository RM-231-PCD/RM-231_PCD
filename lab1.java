public class lab1 {
    public static void main(String []args){
        int tablou[] = new int[100];

        for (int i = 0; i < 100; i++) {
            tablou[i] = (int) (Math.random() * 100);
            System.out.print(" " + tablou[i]);
        }

        System.out.println("");

        Thread_1 r1 = new Thread_1(tablou, 0, tablou.length, 1);
        Thread t1 = new Thread(r1);
        t1.setName("fir_1");
        t1.start();

        Thread_2 r2 = new Thread_2(tablou, tablou.length - 1, -1, -1);
        Thread t2 = new Thread(r2);
        t2.setName("fir_2");
        t2.start();

        Thread_3 r3 = new Thread_3(tablou, 0, tablou.length, 2);
        Thread t3 = new Thread(r3);
        t3.setName("fir_3");
        t3.start();

        Thread_4 r4 = new Thread_4(tablou, tablou.length - 1, -1, -2);
        Thread t4 = new Thread(r4);
        t4.setName("fir_4");
        t4.start();
    }
}

class Thread_1 implements Runnable {
    int tablou[], from, to, step;

    public Thread_1(int tablou[], int from, int to, int step) {
        this.tablou = tablou;
        this.from = from;
        this.to = to;
        this.step = step;
    }

    @Override
    public void run() {

        int p = 1, d = 1, c = 0, s = 0;

        for (int i = from; i != to; i += step) {
            if (tablou[i] % 2 != 0) {
                c++;
                if (c <= 2) p *= tablou[i];
                else if (c <= 4) {
                    d *= tablou[i];
                    s = p + d;
                }

                if (c == 4) {
                    synchronized (System.out) {
                        System.out.println("Produsul este: " + Thread.currentThread().getName() +
                                " primul produs: " + p + " al doilea produs: " + d + " suma: " + s);
                    }
                    p = 1; d = 1; c = 0; s = 0;
                }
            }
        }
    }
}

class Thread_2 implements Runnable {
    int tablou[], from, to, step;

    public Thread_2(int tablou[], int from, int to, int step) {
        this.tablou = tablou;
        this.from = from;
        this.to = to;
        this.step = step;
    }

    @Override
    public void run() {

        int p = 1, c = 0, d = 1, s = 0;

        for (int i = from; i > to; i += step) {
            if (tablou[i] % 2 != 0) {
                c++;
                if (c <= 2) p *= tablou[i];
                else if (c <= 4) {
                    d *= tablou[i];
                    s = p + d;
                }

                if (c == 4) {
                    synchronized (System.out) {
                        System.out.println("Produsul este: " + Thread.currentThread().getName() +
                                " primul produs: " + p + " al doilea produs: " + d + " suma: " + s);
                    }
                    p = 1; d = 1; c = 0; s = 0;
                }
            }
        }

        String nume = "Dodon Cristin";

        synchronized (System.out) {
            for (char simbol : nume.toCharArray()) {
                System.out.print(simbol);
                try { Thread.sleep(100); } catch (InterruptedException e) {}
            }
            System.out.println();
        }
    }
}

class Thread_3 implements Runnable {
    int tablou[], from, to, step;

    public Thread_3(int tablou[], int from, int to, int step) {
        this.tablou = tablou;
        this.from = from;
        this.to = to;
        this.step = step;
    }

    @Override
    public void run() {

        int p = 1, c = 0, pp = 1, s = 0;

        for (int i = from; i != to; i += step) {
            c++;
            if (c <= 2) p *= tablou[i];
            else if (c <= 4) {
                pp *= tablou[i];
                s = p + pp;
            }

            if (c == 4) {
                synchronized (System.out) {
                    System.out.println("Produsul este: " + Thread.currentThread().getName() +
                            " primul produs: " + p + " al doilea produs: " + pp + " suma: " + s);
                }
                p = 1; pp = 1; c = 0;
            }
        }
    }
}

class Thread_4 implements Runnable {
    int tablou[], from, to, step;

    public Thread_4(int tablou[], int from, int to, int step) {
        this.tablou = tablou;
        this.from = from;
        this.to = to;
        this.step = step;
    }

    @Override
    public void run() {

        int p = 1, c = 0, pp = 1, s = 0;

        for (int i = from; i != to; i += step) {
            c++;
            if (c <= 2) p *= tablou[i];
            else if (c <= 4) {
                pp *= tablou[i];
                s = p + pp;
            }

            if (c == 4) {
                synchronized (System.out) {
                    System.out.println("Produsul este: " + Thread.currentThread().getName() +
                            " primul produs: " + p + " al doilea produs: " + pp + " suma: " + s);
                }
                p = 1; pp = 1; c = 0;
            }
        }

        String nume = "Prepelita Catalin!!!";

        synchronized (System.out) {
            for (char simbol : nume.toCharArray()) {
                System.out.print(simbol);
                try { Thread.sleep(100); } catch (InterruptedException e) {}
            }
            System.out.println();
        }
    }
}
