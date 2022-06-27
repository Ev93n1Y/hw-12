class TimeUtils {
    private int seconds = 0;

    public synchronized void add() {
        seconds++;
        notify();
    }

    public synchronized int get() throws InterruptedException {
        wait();
        return seconds;
    }
}
