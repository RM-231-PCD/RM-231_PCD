package PCD_LAB4;
import java.util.ArrayList;
import java.util.List;

public class ProducerConsumer {
    public static void main(String[] args) {
        Depozit depozit = new Depozit();

        Producer p1 = new Producer(1, depozit);
        Producer p2 = new Producer(2, depozit);
        Producer p3 = new Producer(3, depozit);
        Consumer c1 = new Consumer(1, depozit);
        Consumer c2 = new Consumer(2, depozit);
        Consumer c3 = new Consumer(3, depozit);
        Consumer c4 = new Consumer(4, depozit);
        Consumer c5 = new Consumer(5, depozit);

        p1.start();
        p2.start();
        p3.start();
        c1.start();
        c2.start();
        c3.start();
        c4.start();
        c5.start();

    }
}

class Producer extends Thread {
    int pozProducer;
    Depozit depozit;
    static int contor = 0;
    public Producer(int pozProducer, Depozit depozit){
        this.pozProducer=pozProducer;
        this.depozit=depozit;
    }
    public void run() {
        int number;
        String binaryString;
        while (true) {
            synchronized (Producer.class) {
                if (contor >= 31) break;
                contor++;
            }
            number = (int) (Math.random() * 20);
            binaryString = Integer.toBinaryString(number);
            depozit.store(binaryString);
            System.out.println("Producatorul " + pozProducer + " a produs numarul: " + binaryString);
            try {
                Thread.sleep((int) (Math.random() * 499)+1 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}



class Consumer extends Thread {

    int pozConsumer;
    Depozit depozit;
    static int contor = 0;
    public Consumer(int pozConsumer, Depozit depozit){
        this.pozConsumer=pozConsumer;
        this.depozit = depozit;
    }

    public void run() {
        String binaryString;
        while (true) {
            synchronized (Consumer.class) {
                if (contor >= 31) break;
                contor++;
            }
            binaryString = depozit.read();
            System.out.println("Consumatorul " + pozConsumer + " a consumat numarul binar: " + binaryString);
            try {
                Thread.sleep((int) (Math.random() * 399)+1 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Depozit {
    List<String> depozit = new ArrayList<>();
    public synchronized void store (String binaryString) {
        while (depozit.size() == 7) {
            try {
                System.out.println("Depozitul este plin.Producatorul asteapta...");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        depozit.add(binaryString);
        notifyAll();
    }
    public synchronized String read() {
        while (depozit.size() < 1) {
            try {
                System.out.println("Depozitul este gol.Consumatorul asteapta...");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String result = depozit.remove(depozit.size()-1);
        notifyAll();
        return result;

    }
}
