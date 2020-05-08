package com.jimzhang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@SpringBootApplication
public class SpringbootAsyncApplication {




	public static void main(String[] args) {
		SpringApplication.run(SpringbootAsyncApplication.class, args);
	}

	@EnableAsync
	@Configuration
	class TaskPoolConfig{
		/**
		 * 线程池核心线程数量：线程池创建时候初始化的线程数
		 */
		private  int corePoolSize = 10;
		/**
		 * 线程池最大线程数量：只有在缓冲队列满了之后才会申请超过核心线程数的线程
		 */
		private  int maxPoolSize = 20;
		/**
		 * 当活跃线程数大于核心线程数时，空闲的多余线程最大存活时间，默认 60 s
		 */
		private  int keepAliveSeconds = 60;
		/**
		 * 用来缓冲执行任务的队列，容量为 200，默认 Integer.MAX_VALUE = 2147483647
		 */
		private  int queueCapacity = 200;
		/**
		 * 线程池名的前缀：设置好了之后可以方便我们定位处理任务所在的线程池
		 */
		private String threadNamePrefix = "taskExecutor-";

		@Bean("taskExecutor")
		public Executor taskExecutor() {
			ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
			executor.setCorePoolSize(corePoolSize);
			executor.setMaxPoolSize(maxPoolSize);
			executor.setQueueCapacity(queueCapacity);
			executor.setKeepAliveSeconds(keepAliveSeconds);
			executor.setThreadNamePrefix(threadNamePrefix);
			// 线程池对拒绝任务的处理策略：
			// 这里采用了CallerRunsPolicy 策略，当线程池没有处理能力的时候，该策略会直接在 execute 方法的调用线程中运行被拒绝的任务；
			// 如果执行程序已关闭，则会丢弃该任务
			executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
			return executor;
		}
	}
}
