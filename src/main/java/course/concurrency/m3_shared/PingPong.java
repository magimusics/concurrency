package course.concurrency.m3_shared;

public class PingPong {

    private final static Object lock = new Object();

    public static void ping() {
        synchronized (lock) {
            while (true) {
                System.out.println("Ping");
                lock.notify();
                try {
                    Thread.sleep(500);
                    lock.wait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void pong() {
        synchronized (lock) {
            while (true) {
                System.out.println("Pong");
                lock.notify();
                try {
                    Thread.sleep(500);
                    lock.wait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> ping());
        Thread t2 = new Thread(() -> pong());
        t1.start();
        t2.start();
    }
}
