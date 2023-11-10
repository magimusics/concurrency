package course.concurrency.m2_async.executors.spring;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class Main {

    public static void main(String args[]) {
        AtomicReference<String> hello = new AtomicReference<>();
        System.out.println(hello.compareAndSet(null, "HELLO"));
        System.out.println(hello.get());
    }

    private static Integer getTime(long timeout, int result) {
        try {
            Thread.sleep(timeout);
            System.out.println("Returns: " + result);
            return result;
        } catch (Exception e) {
            return Integer.MAX_VALUE;
        }
    }
}
