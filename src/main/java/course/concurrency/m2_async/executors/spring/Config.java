package course.concurrency.m2_async.executors.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@EnableAsync
public class Config {
    @Bean("asyncExecutor")
    Executor asyncExecutor() {
        return Executors.newFixedThreadPool(10);
    }

    @Bean("asyncExecutor2")
    Executor asyncExecutor2() {
        return Executors.newFixedThreadPool(10);
    }
}
