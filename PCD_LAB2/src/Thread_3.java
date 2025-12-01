package PCD_LAB2;

import javax.swing.*;

class Thread_3 extends Thread {
    int mas[];
    int from;
    int to;
    int step;
    JTextArea textArea;

    public Thread_3(int mas[], int from, int to, int step, JTextArea textArea) {
        this.mas = mas;
        this.from = from;
        this.to = to;
        this.step = step;
        this.textArea = textArea;
    }

    @Override
    public void run() {
        int p1 = 1;
        int p2 = 1;
        int c = 0;

        for (int i = from; i != to; i += step) {
            if (i % 2 != 0) {
                if (c < 2) {
                    p1 = p1 * mas[i];
                    c++;
                } else {
                    p2 = p2 * mas[i];
                    c++;
                }
                if (c >= 4) {
                    int diferenta = p1 - p2;
                    textArea.append("\n" + " Diferenta este: " + Thread.currentThread().getName() + " " + diferenta + "\n");
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    p1 = 1;
                    p2 = 1;
                    c = 0;
                }
            }
        }

        try{
            sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String Nume = "\nPanteleiciuc Cornel  Grupa RM-231";
        for (char simbol : Nume.toCharArray()) {
            textArea.append(simbol + " ");
            try {
                sleep(100);
            } catch (InterruptedException e) {
                System.out.print(e);
            }
        }
        System.out.println();
    }
}
