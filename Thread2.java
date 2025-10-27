class Thread_2 extends Thread {
    int numere[];
    int from;
    int to;
    int step;

    public Thread_2(int numere[], int from, int to, int step) {
        this.numere = numere;
        this.from = from;
        this.to = to;
        this.step = step;
    }

    @Override
    public void run() {
        int p1 = 1;
        int p2 = 1;
        int c = 0;

        for (int i = from; i != to; i += step) {
            if (i % 2 != 0) {
                if (c < 2) {
                    p1 = p1 * numere[i];
                    c++;
                } else {
                    p2 = p2 * numere[i];
                    c++;
                }
                if (c >= 4) {
                    int diferenta = p1 - p2;
                    System.out.println(" Diferenta este: " + Thread.currentThread().getName() + " " + diferenta);
                    p1 = 1;
                    p2 = 1;
                    c = 0;
                }
            }
        }
    }
}