package lma.config;

import lma.constants.AsyncConfigConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import static lma.constants.AsyncConfigConstants.AWAIT_TERMINATION_SECONDS;
import static lma.constants.AsyncConfigConstants.CORE_POOL_SIZE;
import static lma.constants.AsyncConfigConstants.MAX_POOL_SIZE;
import static lma.constants.AsyncConfigConstants.QUEUE_CAPACITY;
import static lma.constants.AsyncConfigConstants.WAIT_FOR_TASKS_TO_SHUTDOWN;

@Configuration
@EnableAsync(proxyTargetClass = true)
public class SpringAsyncConfig {

    @Bean
    public TaskExecutor threadPollTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(CORE_POOL_SIZE);
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        executor.setQueueCapacity(QUEUE_CAPACITY);
        executor.setWaitForTasksToCompleteOnShutdown(WAIT_FOR_TASKS_TO_SHUTDOWN);
        executor.setAwaitTerminationSeconds(AWAIT_TERMINATION_SECONDS);
        executor.initialize();
        return executor;
    }
}
