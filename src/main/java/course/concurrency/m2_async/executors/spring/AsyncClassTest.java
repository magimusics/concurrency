package course.concurrency.m2_async.executors.spring;

import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cglib.proxy.Proxy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Component
@Async
public class AsyncClassTest {

    @Autowired
    public ApplicationContext context;

//    @Autowired
//    @Qualifier("applicationTaskExecutor")
//    private ThreadPoolTaskExecutor executor;

    @Async("asyncExecutor")
    public void runAsyncTask() {
        System.out.println("runAsyncTask: " + Thread.currentThread().getName());
        context.getBean(AsyncClassTest.class).internalTask();
    }

    @Async("asyncExecutor")
    public void internalTask() {
        try {
//            Thread.sleep(2000);
        } catch (Exception e) {}
        System.out.println("internalTask: " + Thread.currentThread().getName());
    }
}
