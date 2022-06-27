import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntConsumer;

public class FizzBuzz {

    private final int n;
    private final Semaphore lock;
    private final AtomicInteger counter;

    public FizzBuzz(int n) {
        this.n = n;
        this.lock = new Semaphore(1);
        this.counter = new AtomicInteger(1);
    }

    //check divide by 3 output fizz
    public void fizz(Runnable printFizz) throws InterruptedException {
        int step = n / 3 - n / 15;//calculate number of possible steps
        int i = 0;
        while (i < step) {
            lock.acquire();//acquires a permit and blocking until one is available
            if (counter.get() % 3 == 0 && counter.get() % 15 != 0) {
                printFizz.run();
                counter.incrementAndGet();
                i++;
            }
            lock.release();//release a permit
        }
    }

    //check divide by 5 output buzz
    public void buzz(Runnable printBuzz) throws InterruptedException {
        int step = n / 5 - n / 15;
        int i = 0;
        while (i < step) {
            lock.acquire();
            if (counter.get() % 5 == 0 && counter.get() % 15 != 0) {
                printBuzz.run();
                counter.incrementAndGet();
                i++;
            }
            lock.release();
        }
    }

    //check divide by 15 output fizzbuzz
    public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
        int step = n / 15;
        int i = 0;
        while (i < step) {
            lock.acquire();
            if (counter.get() % 15 == 0) {
                printFizzBuzz.run();
                counter.incrementAndGet();
                i++;
            }
            lock.release();
        }
    }

    public void number(IntConsumer printNumber) throws InterruptedException {
        int step = n - n / 3 - n / 5 + n / 15;
        int i = 0;
        while (i < step) {
            lock.acquire();
            if (counter.get() % 3 != 0 && counter.get() % 5 != 0) {
                printNumber.accept(counter.get());
                counter.incrementAndGet();
                i++;
            }
            lock.release();
        }
    }
}