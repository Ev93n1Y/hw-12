/*Задание 1
        Напишите программу, которая каждую секунду отображает на экране данные о времени,
        прошедшем от начала сессии (запуска программы).
        Другой ее поток выводит каждые 5 секунд сообщение "Прошло 5 секунд".
        Предусмотрите возможность ежесекундного оповещения потока, воспроизводящего сообщение,
         потоком, отсчитывающим время.
*/
public class Task1 {
    public static void main(String[] args) throws InterruptedException {
        TimeUtils timeUtils = new TimeUtils();
        Thread threadA = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(1000);
                    timeUtils.add();
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        Thread threadB = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                int seconds;
                try {
                    seconds = timeUtils.get();
                    System.out.print(seconds + " ");
                    if (seconds % 5 == 0)
                        System.out.println("--- " + "Прошло 5 секунд");
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        threadA.start();
        threadB.start();
        Thread.sleep(1000 * 1000);
        threadA.interrupt();
        threadB.interrupt();
    }
}
