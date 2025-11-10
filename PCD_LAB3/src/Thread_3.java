import javax.swing.*;

class Thread_3 extends Thread {
    int mas[];
    int from;
    int to;
    int step;
    JTextArea textArea;
    Thread_2 t2;

    public Thread_3(int mas[], int from, int to, int step, JTextArea textArea, Thread_2 t2) {
        this.mas = mas;
        this.from = from;
        this.to = to;
        this.step = step;
        this.textArea = textArea;
        this.t2 = t2;
    }


    @Override
    public void run() {
        try {
            while (t2.isAlive() && !textArea.getText().contains("[Thread_2 for terminat]")) {
                sleep(50);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = from; i != to; i += step) {
            textArea.append(" " + mas[i]);
        }
        textArea.append("\n[Thread_3 for terminat]\n");

        try {
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


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