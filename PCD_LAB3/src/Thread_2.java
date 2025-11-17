package PCD_LAB3;
import javax.swing.*;

public class Thread_2 extends Thread {
    int mas[];
    int from;
    int to;
    int step;
    JTextArea textArea;
    Thread_1 t1;


    public Thread_2(int[] mas, int from, int to, int step, JTextArea textArea) {
        this.mas = mas;
        this.from = from;
        this.to = to;
        this.step = step;
        this.textArea = textArea;
        this.t1 = t1;
    }

    @Override
    public void run() {

        int primaValoare = -1;

        textArea.append(getName() + " începe procesarea:\n");

        for (int i = from; i >= to; i -= step) {
            if (mas[i] % 2 == 1) {
                if (primaValoare == -1) {
                    primaValoare = mas[i];
                } else {
                    int s = primaValoare + mas[i];
                    textArea.append("\n" + getName() + " -> pereche: " + primaValoare + " + " + mas[i] + " = " + s + "\n");

                    primaValoare = -1;
                }
            }
        }
        textArea.append("\n[Thread_2 for terminat]\n");

        try{ Thread.sleep(1000); } catch(InterruptedException e){ System.out.print(e); }


        String nume_1 = "\nVeaceslav";
        for (char c : nume_1.toCharArray()) {
            textArea.append(c + " ");
            try{ Thread.sleep(100); } catch(InterruptedException e){ System.out.print(e); }
        }
        System.out.println();

        String nume_2 = "\nCornel\n";
        for (char c : nume_2.toCharArray()) {
            textArea.append(c + " ");
            try{ Thread.sleep(100); } catch(InterruptedException e){ System.out.print(e); }
        }
        System.out.println();
    }
}
