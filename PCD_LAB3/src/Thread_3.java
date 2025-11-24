package PCD_LAB3;
import javax.swing.*;

class Thread_3 extends Thread {
    int mas[];
    int from;
    int to;
    int step;
    JTextArea textArea;
    Thread_1 t1;

    public Thread_3(int mas[], int from, int to, int step, JTextArea textArea, Thread_1 t1) {
        this.mas = mas;
        this.from = from;
        this.to = to;
        this.step = step;
        this.textArea = textArea;
        this.t1 = t1;
    }


    @Override
    public void run() {
        try {
            while (t1.isAlive() && !textArea.getText().contains("[Thread_1 for terminat]")) {
                sleep(50);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = from; i != to; i += step) {
            textArea.append(" " + getName() + " " + mas[i]);
        }
        textArea.append("\n[Thread_3 for terminat]\n");


        try { t1.join(); } catch (InterruptedException e) { e.printStackTrace(); }


        String Nume = "\nProgramarea concurenta si distribuita";
        for (char simbol : Nume.toCharArray()) {
            textArea.append(simbol + " ");
            try {
                sleep(100);
            } catch (InterruptedException e) {
                System.out.print(e);
            }
        }
        textArea.append("\n");
    }
}