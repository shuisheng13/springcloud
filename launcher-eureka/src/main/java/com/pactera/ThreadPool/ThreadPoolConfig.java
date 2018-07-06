package com.pactera.ThreadPool;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
//@EnableAsync(自定义使用)
public class ThreadPoolConfig implements AsyncConfigurer {

	private int corePoolSize = 10;// 线程池维护线程的最少数量
	private int maxPoolSize = 30;// 线程池维护线程的最大数量
	private int queueCapacity = 8; // 缓存队列
	private int keepAlive = 60;// 允许的空闲时间

	@Bean
	public TaskExecutor getTaskExecutor() {
		return getThreadPoolTaskExecutor();
	}

	@Override
	public Executor getAsyncExecutor() {
		return getThreadPoolTaskExecutor();
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new AsyncUncaughtExceptionHandler() {
			@Override
			public void handleUncaughtException(Throwable throwable, Method method, Object... object) {
				System.out.println(throwable.getMessage() + ":" + method.getName() + ":" + object);
			}
		};
	}

	public ThreadPoolTaskExecutor getThreadPoolTaskExecutor() {
		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
		threadPoolTaskExecutor.setCorePoolSize(corePoolSize);
		threadPoolTaskExecutor.setMaxPoolSize(maxPoolSize);
		threadPoolTaskExecutor.setQueueCapacity(queueCapacity);
		threadPoolTaskExecutor.setThreadNamePrefix("mqExecutor-");
		// 对拒绝task的处理策略。线程池达到最大调用自己的线程
		threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		threadPoolTaskExecutor.setKeepAliveSeconds(keepAlive);
		threadPoolTaskExecutor.initialize();
		return threadPoolTaskExecutor;
	}
}
