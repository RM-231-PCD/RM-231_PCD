public class ThreadGroup1 {
 public static void main(String[] args) {
  ThreadGroup sys = Thread.currentThread().getThreadGroup();
  Thread curr = Thread.currentThread();
  curr.setPriority(curr.getPriority() + 1);
  new Fir(sys, "1", 3);
  new Fir(sys, "2", 6);
  ThreadGroup g3 = new ThreadGroup(sys, "g3");
  new Fir(g3, "1", 4);
  new Fir(g3, "2", 3);
  new Fir(g3, "3", 5);
  ThreadGroup g2 = new ThreadGroup(sys, "g2");

  new Fir(g2, "A", 1);

  ThreadGroup g4 = new ThreadGroup(g2, "g4");

  ThreadGroup g1 = new ThreadGroup(g4, "g1");

  new Fir(g1, "A", 1);
  new Fir(g1, "B", 3);
  new Fir(g1, "C", 8);
  new Fir(g1, "D", 3);

  sys.list();


 }
}

class Fir extends Thread {
 public Fir(ThreadGroup group, String name, int priority) {
  super(group, name);
  setPriority(priority);
  start();
 }

 @Override
 public void run() {
  System.out.println("Salut de la firul " + getName() +
          " | Grup: " + getThreadGroup().getName() +
          " | Prioritate: " + getPriority());
 }
}
