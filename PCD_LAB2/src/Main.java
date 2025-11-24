package PCD_LAB2;

import javax.swing.*;
import java.awt.*;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Laboratorul 2");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);

        int[] mas = new int[100];
        for (int i = 0; i < mas.length; i++) {
            mas[i] = (int) (Math.random() * 100) + 1;
            textArea.append(" " + mas[i]);
            if(i == 50){
                textArea.append("\n");
            }
        }
        textArea.append("\n");

        Thread_1 t1 = new Thread_1(mas, 0, mas.length - 1, 1, textArea);
        Thread_2 t2 = new Thread_2(mas, mas.length - 1, 0,1, textArea);

        t1.setName("Thread_1");
        t1.start();
        t2.setName("Thread_2");
        t2.start();

        Thread_3 t3 = new Thread_3(mas, 1, mas.length, 1, textArea);
        t3.setName("CornelThread_1");
        t3.start();

        Thread_4 t4 = new Thread_4(mas, 99, 0, -1, textArea);
        t4.setName("CornelThread_2");
        t4.start();


    }

}