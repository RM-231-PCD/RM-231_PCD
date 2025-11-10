import javax.swing.*;

class Thread_4 extends Thread {
    int mas[];
    int from;
    int to;
    int step;
    JTextArea textArea;
    Thread_3 t3;

    public Thread_4(int mas[], int from, int to, int step, JTextArea textArea, Thread_3 t3) {
        this.mas = mas;
        this.from = from;
        this.to = to;
        this.step = step;
        this.textArea = textArea;
        this.t3 = t3;
    }

    @Override
    public void run() {

        try {
            while (t3.isAlive() && !textArea.getText().contains("[Thread_3 for terminat]")) {
                sleep(50);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        for (int i = from; i != to; i += step) {
            textArea.append(" " + mas[i]);
        }
        textArea.append("\n[Thread_4 for terminat]\n");

        try {
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String Nume = "\nGrupa : RM-231";
        for (char simbol : Nume.toCharArray()) {
            textArea.append(simbol + " ");
            try {
                sleep(100);
            } catch (InterruptedException e) {
                System.out.print(e);
            }
        }
    }
}