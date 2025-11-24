package Lab3;

/*
Suma produselor perechilor de numere impare, luate două câte două, parcurgând șirul de la primul element fir 1
Suma produselor perechilor de numere impare, luate două câte două, parcurgând șirul de la ultimul element fir 2

De parcurs de la început intervalul [234, 987] fir 3
De parcurs de la sfârșit intervalul [123, 890] fir 4
*/
public class Lab3 {
    public static void main(String[] args) {
        int tablou[] = new int[100];

        for (int i = 0; i < 100; i++) {
            tablou[i] = (int)(Math.random() * 100);
            System.out.print(" " + tablou[i]);
        }
        System.out.println();

        Thread t1 = new Thread(new Thread_1(tablou, 0, tablou.length, 1), "fir_1");
        Thread t2 = new Thread(new Thread_2(tablou, tablou.length - 1, -1, -1), "fir_2");
        Thread t3 = new Thread(new Thread_3(234, 987), "fir_3");
        Thread t4 = new Thread(new Thread_4(890, 123), "fir_4"); 

        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}

class Thread_1 implements Runnable {
    int tablou[];
    int from, to, step;

    public Thread_1(int tablou[], int from, int to, int step) {
        this.tablou = tablou;
        this.from = from;
        this.to = to;
        this.step = step;
    }

    @Override
    public void run() {
        int count = 0;
        int[] oddNumbers = new int[4]; 

        for (int i = from; i != to; i += step) {
            if (tablou[i] % 2 != 0) {
                oddNumbers[count++] = tablou[i];
                if (count == 4) {
                    int p1 = oddNumbers[0] * oddNumbers[1];
                    int p2 = oddNumbers[2] * oddNumbers[3];
                    System.out.println(Thread.currentThread().getName() + " primul produs " + p1 + ", al doilea produs " + p2 + ", suma: " + (p1 + p2));
                    count = 0;
                }
            }
        }
    }
}

class Thread_2 implements Runnable {
    int tablou[];
    int from, to, step;

    public Thread_2(int tablou[], int from, int to, int step) {
        this.tablou = tablou;
        this.from = from;
        this.to = to;
        this.step = step;
    }

    @Override
    public void run() {
        int count = 0;
        int[] oddNumbers = new int[4];

        for (int i = from; i != to; i += step) {
            if (tablou[i] % 2 != 0) {
                oddNumbers[count++] = tablou[i];
                if (count == 4) {
                    int p1 = oddNumbers[0] * oddNumbers[1];
                    int p2 = oddNumbers[2] * oddNumbers[3];
                    System.out.println(Thread.currentThread().getName() + " primul produs: " + p1 + ", al doilea produs " + p2 + ", suma " + (p1 + p2));
                    count = 0;
                }
            }
        }
    }
}

class Thread_3 implements Runnable {
    int from, to;

    public Thread_3(int from, int to) { this.from = from; this.to = to; }

    @Override
    public void run() {
        for (int i = from; i <= to; i++) {
            System.out.print(" " + i);
            try { Thread.sleep(1000); } catch (InterruptedException e) { }
        }
        System.out.println();
    }
}

class Thread_4 implements Runnable {
    int from, to;

    public Thread_4(int from, int to) {
         this.from = from; this.to = to; 
        }

    @Override
    public void run() {
        for (int i = from; i >= to; i--) {
            System.out.print(" " + i);
            try { Thread.sleep(300); } catch (InterruptedException e) { }
        }
        System.out.println();
         String numePrenume = "Procopciuc Daniel";
        for (char c : numePrenume.toCharArray()) {
            System.out.print(c);
            try { Thread.sleep(300); } catch (InterruptedException e) { }
        }
        System.out.println();
    }
}
