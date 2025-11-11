/*
Sumele poziţiilor numerelor
impare două câte două
începând căutarea şi
sumarea de la primul
element
Sumele poziţiilor numerelor
impare două câte două
începând căutarea şi sumarea
de la ultimul element
De parcurs
de la
început
intervalul
[234, 987]
De parcurs
de la sfârșit
intervalul
[123, 890]



11 Diferența produselor
numerelor pare două câte
două începând căutarea și
sumarea cu primul element
Sumele produselor numerelor
pare două câte două începând
căutarea și sumarea cu ultimul
element
De parcurs
intervalul
de la
început
[385, 1024]
De parcurs
de la sfârșit
intervalul
[1000, 408]
12 Sumele produselor
numerelor impare două câte
două începând căutarea și
sumarea cu primul element
Sumele produselor numerelor
impare două câte două
începând căutarea și sumarea
cu ultimul element
De parcurs
de la
început
intervalul
[1111,
1748]
De parcurs
de la sfârșit
intervalul
[2000,
1478]
Criterii de evaluare:
1. Crearea şi iniţializarea thread-urilor pentru realizarea sarcinilor.
2. Alegerea metodelor de sincronizare a firelor.
3. Sincronizarea thread-urilor cu metodele potrivite.
4. Crearea interfeţei grafice a programului.
5. Corectitudinea codului - verificarea dacă codul este corect, fără erori de funcționare.
fir etalon de sistem
*/
public class Lab3 {
 public static void main(String[] args) {
    int tablou[] = new int[100];
    for (int i = 0; i < tablou.length; i++) {
        tablou[i] = (int) (Math.random() * 100);
        System.out.print(" " + tablou[i]);
        
    }
    Thread r1 = new Thread(new Thread_1(tablou, 234, 987, 1));
    r1.setName("fir_1");
    r1.start();

    Thread r2 = new Thread(new Thread_2(tablou, 123, 890, -1));
    r2.setName("fir_2");
    r2.start();

    Thread_3 r3 = new Thread_3(tablou, 385, 1024, 1);
    Thread t3 = new Thread(r3);
    t3.setName("fir_3");
    t3.start();

    Thread_4 r4 = new Thread_4(tablou, 1000,408, -1);
    Thread t4 = new Thread(r4);
    t4.setName("fir_4");
    t4.start();
 }}
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
                if (tablou[i] % 2 != 0) {
                    c++;
                    if (c <= 2) {
                        p = p * tablou[i];
                    } else if (c <= 4) {
                        d = d * tablou[i];
                        s = p + d;
                    }
                }
            }
            System.out.println("Suma produselor numerelor impare doua cate doua incepand cautarea si sumarea cu primul element este: " + s);
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
            int d = 1;
            int c = 0;
            int s = 0;

            for (int i = from; i != to; i += step) {
                if (tablou[i] % 2 != 0) {
                    c++;
                    if (c <= 2) {
                        p = p * tablou[i];
                    } else if (c <= 4) {
                        d = d * tablou[i];
                        s = p + d;
                    }
                }
            }
            System.out.println("Suma produselor numerelor impare doua cate doua incepand cautarea si sumarea cu ultimul element este: " + s);
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
            int d = 1;
            int c = 0;
            int s = 0;

            for (int i = from; i != to; i += step) {
                if (tablou[i] % 2 == 0) {
                    c++;
                    if (c <= 2) {
                        p = p * tablou[i];
                    } else if (c <= 4) {
                        d = d * tablou[i];
                        s = p + d;
                    }
                }
            }
            System.out.println("Suma produselor numerelor pare doua cate doua incepand cautarea si sumarea cu primul element este: " + s);
        }

    }
    class Thread_4 implements Runnable {
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
            int d = 1;
            int c = 0;
            int s = 0;

            for (int i = from; i != to; i += step) {
                if (tablou[i] % 2 == 0) {
                    c++;
                    if (c <= 2) {
                        p = p * tablou[i];
                    } else if (c <= 4) {
                        d = d * tablou[i];
                        s = p + d;
                    }
                }
            }
            System.out.println("Suma produselor numerelor pare doua cate doua incepand cautarea si sumarea cu ultimul element este: " + s);
        }

    }
