package course.concurrency.m3_shared;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Cookie {

    private static final CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String args[]) {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        ExecutorService executor2 = Executors.newCachedThreadPool();
        executor.scheduleAtFixedRate(() -> {
//            executor2.submit(() -> {
//                System.out.println("Submit");
                try {
                    Thread.sleep(50000);
//                    countDownLatch.await(20, TimeUnit.SECONDS);
                } catch (Exception e) {
                    e.printStackTrace();
                }
//            });
            System.out.println("Schedule...");
        }, 0, 2000, TimeUnit.MILLISECONDS);
    }
}
