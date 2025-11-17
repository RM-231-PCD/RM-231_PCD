import java.util.Random;

public class lab2 {
    public static void main(String[] args) {
        int[] mas = new int[100];
        Random rnd = new Random();

        for (int i = 0; i < mas.length; i++) {
            mas[i] = rnd.nextInt(100) + 1;
            System.out.print(mas[i] + " ");
        }
        System.out.println("\n---------------------------");

        SumForward t1 = new SumForward(mas);   
        SumBackward t2 = new SumBackward(mas);

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String text = "Laboratorul 1 PCD varianta 5 studenti: Nume Prenume Josan Nicolae, Nume Prenume Secrieru Maxim";

        for (int i = 0; i < text.length(); i++) {
            System.out.print(text.charAt(i));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
        System.out.println();
    }
}

class SumForward extends Thread {
    private final int[] mas;

    SumForward(int[] mas) {
        this.mas = mas;
    }

    public void run() {
        int suma = 0;
        for (int i = 0; i < mas.length - 1; i += 2) {
            suma += mas[i] * mas[i + 1];
        }
        System.out.println("Suma 1 = " + suma);
    }
}

class SumBackward extends Thread {
    private final int[] mas;

    SumBackward(int[] mas) {
        this.mas = mas;
    }

    public void run() {
        int suma = 0;
        for (int i = mas.length - 2; i >= 0; i -= 2) {
            suma += mas[i] * mas[i + 1];
        }
        System.out.println("Suma 2 = " + suma);
    }
}
