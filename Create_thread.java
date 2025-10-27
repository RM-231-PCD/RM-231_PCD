import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class Create_thread {
    public static void main(String []args){
        int mas[] = new int[100];

        for(int i=0; i<100; i++){
            mas[i] = (int)(Math.random()*100);

            System.out.print(" " + mas[i]);
        }

        System.out.println("");

        Andrei_1 t1 = new Andrei_1(mas, 0, mas.length, 1);

        t1.setName("Andrei_1");
        t1.start();

        Andrei_2 t2 = new Andrei_2(mas, mas.length - 1, 0, -1);
        t2.setName("Andrei_2");
        t2.start();


        System.out.println();
        DiffEvenProducts th = new DiffEvenProducts(0, 99, 1, mas);
        Thread t = new Thread(th);
        t.setName("Th-Cond9-Start");
        t.start();

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

class DiffEvenProducts implements Runnable {
    private int from, to, step;
    private int[] arr;

    public DiffEvenProducts(int from, int to, int step, int[] arr) {
        this.from = from;
        this.to = to;
        this.step = step;
        this.arr = arr;
    }

    public void run() {

        List<Integer> products = new ArrayList<>();

        int pos = from;
        while (pos <= to && pos < arr.length) {

            if (pos % 2 == 0) {
                int firstIndex = pos;

                int nextPos = firstIndex + 1;
                while (nextPos <= to && nextPos < arr.length && nextPos % 2 != 0) {
                    nextPos++;
                }

                if (nextPos <= to && nextPos < arr.length && nextPos % 2 == 0) {
                    int secondIndex = nextPos;
                    int prod = arr[firstIndex] * arr[secondIndex];
                    products.add(prod);
                    System.out.println(Thread.currentThread().getName()
                            + " Pair: (" + firstIndex + "," + secondIndex + ")"
                            + " Values: (" + arr[firstIndex] + "," + arr[secondIndex] + ")"
                            + " Product: " + prod);

                    pos = secondIndex + 1;
                    continue;
                } else {
                    break;
                }
            }
            pos++;
        }

        for (int i = 0; i + 1 < products.size(); i++) {
            int diff = products.get(i) - products.get(i + 1);
            System.out.println(Thread.currentThread().getName()
                    + " Diff between product " + i + " and " + (i + 1) + ": "
                    + products.get(i) + " - " + products.get(i + 1) + " = " + diff);
        }
        if (products.size() < 2) {
            System.out.println(Thread.currentThread().getName()
                    + " Not enough pairs to compute differences (found " + products.size() + " product(s)).");
        }

        try {
            sleep(4000);
        }
        catch (InterruptedException e) {
            System.out.print(e);
        }

        String informatie = "Munteanu Victoria / Grupa RM-231";
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


