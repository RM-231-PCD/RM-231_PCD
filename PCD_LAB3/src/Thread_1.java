package PCD_LAB3;
import javax.swing.*;

public class Thread_1 extends Thread {
    int[] mas;
    int from;
    int to;
    int step;
    JTextArea textArea;
    Thread_4 t4;

    public Thread_1(int[] mas, int from, int to, int step, JTextArea textArea, Thread_4 t4) {
        this.mas = mas;
        this.from = from;
        this.to = to;
        this.step = step;
        this.textArea = textArea;
        this.t4 = t4;
    }

    @Override
    public void run() {
        try {
            while (t4.isAlive() && !textArea.getText().contains("[Thread_4 for terminat]")) {
                sleep(50);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int primaValoare = -1;

        textArea.append(getName() + " începe procesarea:\n");

        for (int i = from; i <= to; i += step) {
            if (mas[i] % 2 == 1) {
                if (primaValoare == -1) {
                    primaValoare = mas[i];
                } else {
                    int s = primaValoare + mas[i];
                    textArea.append(getName() + " -> pereche: " + primaValoare + " + " + mas[i] + " = " + s);
                    primaValoare = -1;
                }
            }
        }
        textArea.append("\n[Thread_1 for terminat]\n");

        try{ Thread.sleep(300);
        } catch(InterruptedException e){
            System.out.print(e);
        }



        try { t4.join(); } catch (InterruptedException e) { e.printStackTrace(); }


        String prenume_1 = "\nCulev";
        for (char c : prenume_1.toCharArray()) {
            textArea.append(c + " ");
            try{ Thread.sleep(100); } catch(InterruptedException e){ System.out.print(e); }
        }
        System.out.println();

        String prenume_2 = "\nPanteleiciuc\n";
        for (char c : prenume_2.toCharArray()) {
            textArea.append(c + " ");
            try{ Thread.sleep(100); } catch(InterruptedException e){ System.out.print(e); }
        }
        System.out.println();
    }
}
