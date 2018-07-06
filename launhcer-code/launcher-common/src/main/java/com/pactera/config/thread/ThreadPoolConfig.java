package com.pactera.config.thread;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ThreadPoolConfig implements AsyncConfigurer {

	private int corePoolSize = 10;// 线程池维护线程的最少数量
	private int maxPoolSize = 200;// 线程池维护线程的最大数量
	private int queueCapacity =10; // 缓存队列
	private int keepAlive = 60;// 允许的空闲时间

	@Override
	public Executor getAsyncExecutor() {
		return getThreadPoolTaskExecutor();
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new AsyncUncaughtExceptionHandler() {
			@Override
			public void handleUncaughtException(Throwable throwable, Method method, Object... object) {
				System.err.println("*******************线程异常********************");
				throwable.printStackTrace();
//				throw new DataStoreException(ErrorStatus.THREAD_ERROR);
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
