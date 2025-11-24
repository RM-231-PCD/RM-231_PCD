package PCD_LAB2;

import javax.swing.*;

public class Thread_2 extends Thread {
  int[] mas;
  int from;
  int to;
  int step;

  JTextArea textArea;

  public Thread_2(int[] mas, int from, int to, int step, JTextArea textArea) {
    this.mas = mas;
    this.from = from;
    this.to = to;
    this.step = step;
    this.textArea = textArea;
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
          try {
            sleep(100);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          primaValoare = -1;
        }
      }
    }
  }
}
