import java.util.*;

public class lab2{
    public static void main(String[] args) {
        int[] tablou = new int[20];
        for (int i = 0; i < tablou.length; i++) {
            tablou[i] = (int)(Math.random() * 99 + 1);
            System.out.print(tablou[i] + " ");
        }
        System.out.println("\n-----------------------------------------");

        // Fire pentru condiția 1
        Counter1 cnt1 = new Counter1(0, 19, 1, tablou);
        Counter2 cnt2 = new Counter2(19, 0, -1, tablou);

        // Fire pentru condiția 2
        Counter3 cnt3 = new Counter3(19, 0, -1, tablou);
        Counter4 cnt4 = new Counter4(0, 19, 1, tablou);

        cnt1.setName("Fir 1");
        cnt2.setName("Fir 2");
        cnt3.setName("Fir 3");
        cnt4.setName("Fir 4");

        cnt1.start();
        cnt2.start();
        cnt3.start();
        cnt4.start();
    }
}


class Counter1 extends Thread {
    private int from, to, step;
    private int[] tablou;

    public Counter1(int from, int to, int step, int[] tablou) {
        this.from = from;
        this.to = to;
        this.step = step;
        this.tablou = tablou;
    }

    // CONDIȚIA 1: sumele produselor numerelor de pe poziții pare, două câte două, începând cu primul element
    public void run() {
        int s = 0;
        int i = from;
        while (i < tablou.length - 1 && i != to) {
            if ((i + 1) % 2 == 0) { 
                int prod = tablou[i] * tablou[i + 1];
                s += prod;
                System.out.println(getName() + " -> i=" + i + " j=" + (i + 1) + " Produs=" + prod);
            }
            i += step * 2;
        }
        System.out.println(getName() + " | Suma totală (condiția 1): " + s);
    }
}

class Counter2 extends Thread {
    private int from, to, step;
    private int[] tablou;

    public Counter2(int from, int to, int step, int[] tablou) {
        this.from = from;
        this.to = to;
        this.step = step;
        this.tablou = tablou;
    }

    // CONDIȚIA 1: aceeași logică, dar de la sfârșit spre început
    public void run() {
        int s = 0;
        int i = from;
        while (i > 0 && i != to) {
            if ((i + 1) % 2 == 0) {
                int prod = tablou[i] * tablou[i - 1];
                s += prod;
                System.out.println(getName() + " -> i=" + i + " j=" + (i - 1) + " Produs=" + prod);
            }
            i += step * 2;
        }
        System.out.println(getName() + " | Suma totală (condiția 1): " + s);
    }
}

class Counter3 extends Thread {
    private int from, to, step;
    private int[] tablou;

    public Counter3(int from, int to, int step, int[] tablou) {
        this.from = from;
        this.to = to;
        this.step = step;
        this.tablou = tablou;
    }

    // CONDIȚIA 2: sumele produselor numerelor de pe poziții pare, începând cu ultimul element
    public void run() {
        int s = 0;
        int i = from;
        while (i > 0 && i != to) {
            if ((i + 1) % 2 == 0) {
                int prod = tablou[i] * tablou[i - 1];
                s += prod;
                System.out.println(getName() + " -> i=" + i + " j=" + (i - 1) + " Produs=" + prod);
            }
            i += step * 2;
        }
        System.out.println(getName() + " | Suma totală (condiția 2): " + s);
    }
}

class Counter4 extends Thread {
    private int from, to, step;
    private int[] tablou;

    public Counter4(int from, int to, int step, int[] tablou) {
        this.from = from;
        this.to = to;
        this.step = step;
        this.tablou = tablou;
    }

    // CONDIȚIA 2: dar de la început spre sfârșit
    public void run() {
        int s = 0;
        int i = from;
        while (i < tablou.length - 1 && i != to) {
            if ((i + 1) % 2 == 0) {
                int prod = tablou[i] * tablou[i + 1];
                s += prod;
                System.out.println(getName() + " -> i=" + i + " j=" + (i + 1) + " Produs=" + prod);
            }
            i += step * 2;
        }
        System.out.println(getName() + " | Suma totală (condiția 2): " + s);
    }
}

