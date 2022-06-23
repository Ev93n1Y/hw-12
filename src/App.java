public class App {

    public App(long lifetime) throws InterruptedException {
        A a = new A();
        T1 t1 = new T1(a);
        T2 t2 = new T2(a, "Прошло 5 секунд");
        t1.start();
        t2.start();
        Thread.sleep(lifetime * 1000 + 100);
        t1.interrupt();
        t2.interrupt();
    }

    public static void main(String[] args) throws InterruptedException {
        new App(1000);
    }

    static class T1 extends Thread {

        private final A app;

        public T1(A app) {
            this.app = app;
        }

        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                this.app.add();
            }
        }
    }

    static class T2 extends Thread {

        private final A app;
        private final String message;

        public T2(A app, String message) {
            this.app = app;
            this.message = message;
        }

        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                int n;
                try {
                    n = app.last();
                    System.out.print(n+" ");
                    // это можно вынести в отдельный поток
                    if (n % 5 == 0)
                        //System.out.println(" ");
                        System.out.print("\n"+message+"\n");
                    /*if (n % 7 == 0)
                        System.out.println(message);*/
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    static class A {

        private int d = 0;

        public synchronized void add() {
            this.d++;
            notify();
        }

        public synchronized int last() throws InterruptedException {
            wait();
            return this.d;
        }
    }
}
