import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SistemProducatorConsumator extends JFrame {

    private JTextArea logArea;
    private JProgressBar progressBar;
    private JLabel statusLabel;

    private static final int CAPACITATE_DEPOZIT = 10;
    private static final int NR_PRODUCATORI = 4;
    private static final int NR_CONSUMATORI = 3;
    private static final int LIMITA_CONSUM = 3;
    
    public SistemProducatorConsumator() {
        super("Laborator 4: Varianta 3 (Vocale) - Model Clasic Thread");
        initializareGUI();
        
        Depozit depozit = new Depozit(CAPACITATE_DEPOZIT, logArea, progressBar, statusLabel);
        
        List<Producator> producatori = new ArrayList<>();
        List<Consumator> consumatori = new ArrayList<>();

        for (int i = 1; i <= NR_PRODUCATORI; i++) {
            Producator p = new Producator(depozit, "Producator-" + i);
            producatori.add(p);
            p.start(); 
        }

        for (int i = 1; i <= NR_CONSUMATORI; i++) {
            Consumator c = new Consumator(depozit, "Consumator-" + i, LIMITA_CONSUM);
            consumatori.add(c);
            c.start(); 
        }

        new Thread(() -> {
            try {
                for (Consumator c : consumatori) {
                    c.join();
                }
                
                SwingUtilities.invokeLater(() -> {
                    logArea.append("\n>>> TOȚI CONSUMATORII AU TERMINAT! OPRIND PRODUCĂTORII... <<<\n");
                    statusLabel.setText("Status: FINALIZAT. Oprim Producătorii.");
                    statusLabel.setForeground(Color.RED);
                });

                for (Producator p : producatori) {
                    p.interrupt();
                }

            } catch (InterruptedException e) {
                // Thread-ul Supraveghetor nu este menit să fie întrerupt, dar gestionăm excepția.
            }
        }).start();
    }
    
    private void initializareGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 550);
        setLayout(new BorderLayout());

        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(logArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        progressBar = new JProgressBar(0, CAPACITATE_DEPOZIT);
        progressBar.setStringPainted(true);
        progressBar.setString("Depozit Gol");
        
        statusLabel = new JLabel(" Sistem pornit cu Thread Classic...");
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        bottomPanel.add(statusLabel, BorderLayout.NORTH);
        bottomPanel.add(progressBar, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SistemProducatorConsumator());
    }
}

// -------------------------------------------------------------------------
// CLASA DEPOZIT (RESURSA PARTAJATĂ)
// -------------------------------------------------------------------------

class Depozit {
    private List<Character> buffer;
    private int capacitateMaxima;
    private JTextArea logArea;
    private JProgressBar progressBar;
    private JLabel statusLabel;

    public Depozit(int capacitate, JTextArea logArea, JProgressBar progressBar, JLabel label) {
        this.capacitateMaxima = capacitate;
        this.buffer = new ArrayList<>();
        this.logArea = logArea;
        this.progressBar = progressBar;
        this.statusLabel = label;
    }

    public synchronized void pune(String numeThread, char c1, char c2) throws InterruptedException {
        if (Thread.currentThread().isInterrupted()) throw new InterruptedException();

        while (buffer.size() == capacitateMaxima) {
            log(numeThread + " depozit FULL. Așteaptă...");
            statusLabel.setText("Status: Depozit PLIN!");
            wait(); 
        }

        buffer.add(c1);
        if (buffer.size() < capacitateMaxima) {
            buffer.add(c2);
            log(numeThread + " a pus 2 vocale: [" + c1 + ", " + c2 + "]. Total: " + buffer.size());
        } else {
            log(numeThread + " a pus DOAR [" + c1 + "]. Restul aruncat.");
        }
        
        updateGUI();
        notifyAll();
    }

    public synchronized char ia(String numeThread) throws InterruptedException {
        if (Thread.currentThread().isInterrupted()) throw new InterruptedException();

        while (buffer.isEmpty()) {
            log(numeThread + " vrea să consume, dar depozitul e GOL. Așteaptă...");
            statusLabel.setText("Status: Depozit GOL!");
            wait();
        }

        char valoare = buffer.remove(0);
        log(numeThread + " a consumat: '" + valoare + "'. Rămase: " + buffer.size());
        updateGUI();
        
        notifyAll();
        return valoare;
    }

    private void log(String mesaj) {
        SwingUtilities.invokeLater(() -> {
            logArea.append(mesaj + "\n");
            logArea.setCaretPosition(logArea.getDocument().getLength());
        });
    }

    private void updateGUI() {
        SwingUtilities.invokeLater(() -> {
            int size = buffer.size();
            progressBar.setValue(size);
            progressBar.setString(size + " / " + capacitateMaxima + " vocale");
            
            StringBuilder content = new StringBuilder("Conținut: [ ");
            for(char c : buffer) content.append(c).append(" ");
            content.append("]");
            statusLabel.setText(content.toString());
            
            if (size == 0) progressBar.setForeground(Color.RED);
            else if (size == capacitateMaxima) progressBar.setForeground(Color.RED);
            else progressBar.setForeground(Color.BLUE);
        });
    }
}

// -------------------------------------------------------------------------
// CLASA PRODUCATOR (EXTENDS THREAD)
// -------------------------------------------------------------------------

class Producator extends Thread { 
    private Depozit depozit;
    private String nume;
    private char[] vocale = {'A', 'E', 'I', 'O', 'U', 'a', 'e', 'i', 'o', 'u'};
    private Random random = new Random();

    public Producator(Depozit d, String nume) {
        super(nume);
        this.depozit = d;
        this.nume = nume;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) { 
                if (Thread.currentThread().isInterrupted()) throw new InterruptedException();
                
                char v1 = vocale[random.nextInt(vocale.length)];
                char v2 = vocale[random.nextInt(vocale.length)];
                
                depozit.pune(nume, v1, v2);

                Thread.sleep(random.nextInt(1000) + 500); 
            }
        } catch (InterruptedException e) {
            return; 
        }
    }
}

// -------------------------------------------------------------------------
// CLASA CONSUMATOR (EXTENDS THREAD)
// -------------------------------------------------------------------------

class Consumator extends Thread {
    private Depozit depozit;
    private String nume;
    private int limitaConsum;

    public Consumator(Depozit d, String nume, int limita) {
        super(nume);
        this.depozit = d;
        this.nume = nume;
        this.limitaConsum = limita;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < limitaConsum; i++) {
                depozit.ia(nume);
                Thread.sleep(1000); 
            }
            System.out.println(">>> " + nume + " a terminat.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
