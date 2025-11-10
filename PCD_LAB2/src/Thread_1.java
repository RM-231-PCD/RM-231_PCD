package PCD_LAB2;
import javax.swing.*;


public class Thread_1 extends Thread {
  int[] mas;
  int from;
  int to;
  int step;

  JTextArea textArea;

  public Thread_1(int[] mas, int from, int to, int step, JTextArea textArea) {
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

    for (int i = from; i <= to; i += step) {
      if (mas[i] % 2 == 1) {
        if (primaValoare == -1) {
          primaValoare = mas[i];
        } else {
          int s = primaValoare + mas[i];
          textArea.append("\n" + getName() + " -> pereche: " + primaValoare + " + " + mas[i] + " = " + s + "\n");
          try {
            sleep(150);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          primaValoare = -1;
        }
      }
    }

    try{
      sleep(500);
    } catch(InterruptedException e){
      System.out.print(e);
    }

    String numePrenume = "\nCulev Veaceslav, gr.RM-231";
    for (char c : numePrenume.toCharArray()) {
      textArea.append(c + " ");
      try{
        sleep(100);
      } catch(InterruptedException e){
        System.out.print(e);
      }
    }
    System.out.println();
  }
}
