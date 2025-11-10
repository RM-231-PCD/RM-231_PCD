import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Laboratorul 3");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);

        int[] mas = new int[2150];
        for (int i = 0; i < mas.length; i++) {
            mas[i] = (int) (Math.random() * 2150) + 1;
            textArea.append(" " + mas[i]);
        }
        textArea.append("\n");
        Thread_1 t1 = new Thread_1(mas, 0, mas.length - 1, 1, textArea);
        Thread_2 t2 = new Thread_2(mas, mas.length - 1, 0,1, textArea, t1);
        Thread_3 t3 = new Thread_3(mas, 0, 799, 1, textArea, t2);
        Thread_4 t4 = new Thread_4(mas, 2111, 1456, -1, textArea, t3);

        t1.setName("Thread_1");
        t1.start();
        t2.setName("Thread_2");
        t2.start();
        t3.setName("CornelThread_3");
        t3.start();
        t4.start();
        t4.setName("CornelThread_4");
    }
}