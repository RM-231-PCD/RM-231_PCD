import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

class Creare_thread_V {
    private String memberName;
    private int[] sharedArray;

    public Creare_thread_V(String name, int[] array) {
        this.memberName = name;
        this.sharedArray = array;
    }

    public String getMemberName() {
        return memberName;
    }

    class Th1 extends Thread {
        @Override
        public void run() {
            System.out.println(memberName + " Th1: Calculul produselor de la început");

            int diff = 0;
            // Safe indexing: ensure we don’t exceed array bounds
            for (int i = 0; i < sharedArray.length - 4; i += 2) {
                int prod1 = sharedArray[i] * sharedArray[i + 2];
                int prod2 = sharedArray[i + 1] * sharedArray[i + 3];
                diff += Math.abs(prod1 - prod2);
            }

            System.out.println(memberName + " Diferența totală (de la început): " + diff);
            try {
                sleep(4000);
            }
            catch (InterruptedException e) {
                System.out.print(e);
            }

            String informatie = "Muntean Victoria / Grupa RM-231";
            for (char simbol : informatie.toCharArray()) {
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

    class Th2 extends Thread {
        @Override
        public void run() {
            System.out.println(memberName + " Th2: Calculul produselor de la sfârșit");

            int diff = 0;
            for (int i = sharedArray.length - 1; i >= 3; i -= 2) {
                int prod1 = sharedArray[i] * sharedArray[i - 2];
                int prod2 = sharedArray[i - 1] * sharedArray[i - 3];
                diff += Math.abs(prod1 - prod2);
            }

            System.out.println(memberName + " Diferența totală (de la sfârșit): " + diff);
        }
    }

    public void startThreads() {
        Th1 t1 = new Th1();
        Th2 t2 = new Th2();

        System.out.println(memberName + " Starting threads...");
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
            System.out.println(memberName + " Threads finished.");
        } catch (InterruptedException e) {
            System.out.println(memberName + " Thread interrupted: " + e.getMessage());
        }
    }
}

public class Create_thread {
    public static void main(String[] args) {
        int mas[] = new int[100];

        for (int i = 0; i < 100; i++) {
            mas[i] = (int) (Math.random() * 100);
            System.out.print(" " + mas[i]);
        }

        System.out.println("\n");

        Andrei_1 t1 = new Andrei_1(mas, 0, mas.length, 1);
        t1.setName("Andrei_1");
        t1.start();

        Andrei_2 t2 = new Andrei_2(mas, mas.length - 1, 0, -1);
        t2.setName("Andrei_2");
        t2.start();

        try {
            sleep(100);
        } catch (InterruptedException e) {
            System.out.print(e);
        }

        Creare_thread_V obj = new Creare_thread_V("Victoria", mas);
        obj.startThreads();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted: " + e.getMessage());
        }
    }
}

class Andrei_1 extends Thread {

    int mas[];
    int from;
    int to;
    int step;

    public Andrei_1(int mas[], int from, int to, int step) {
        this.mas = mas;
        this.from = from;
        this.to = to;
        this.step = step;
    }

    @Override
    public void run() {

        int p = 1;
        int c = 0;
        int s = 1;

        for (int i = from; i != to; i += step) {
            if (mas[i] % 2 == 0) {
                if (c >= 2) {
                    s = s * mas[i];
                    c++;
                } else {
                    p = p * mas[i];
                    c++;
                }
                if( c >= 4) {
                    int suma = s + p;
                    System.out.println(" Suma este: " + Thread.currentThread().getName() + " " + suma);
                    p = 1;
                    s = 1;
                    c = 0;
                }
            }
        }

        try {
            sleep(500);
        }
        catch (InterruptedException e) {
            System.out.print(e);
        }

        String informatie = "Lozinschi Andrei / Grupa RM-231";
        for (char simbol : informatie.toCharArray()) {
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

class Andrei_2 extends Thread {
    int mas[];
    int from;
    int to;
    int step;

    public Andrei_2(int mas[], int from, int to, int step) {
        this.mas = mas;
        this.from = from;
        this.to = to;
        this.step = step;
    }

    @Override
    public void run() {

        int p = 1;
        int c = 0;
        int s = 1;

        for (int i = from; i != to; i += step) {
            if (mas[i] % 2 == 0) {
                if (c >= 2) {
                    s = s * mas[i];
                    c++;
                } else {
                    p = p * mas[i];
                    c++;
                }
                if( c >= 4) {
                    int suma = s + p;
                    System.out.println(" Suma este: " + Thread.currentThread().getName() + " " + suma);
                    p = 1;
                    s = 1;
                    c = 0;
                }
            }
        }
    }
}



