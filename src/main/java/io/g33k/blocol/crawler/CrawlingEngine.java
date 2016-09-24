package io.g33k.blocol.crawler;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CrawlingEngine {

	private ExecutorService executor;

	private @Autowired CrawlerRunnable crawlerRunnable;

	public void start(List<String> seedUrls) {
		executor = this.getExecutor();
		for (String url : seedUrls) {
			System.out.println(crawlerRunnable);
			executor.submit(crawlerRunnable.newRunnableInstance(url));
		}
		try {
			executor.awaitTermination(20, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public boolean isTerminated() {
		return executor.isTerminated();
	}

	public void shutdown() {
		executor = this.getExecutor();
		if (!executor.isShutdown()) {
			executor.shutdown();
		}
	}

	public ExecutorService getExecutor() {
		if (executor != null)
			return executor;
		return Executors.newCachedThreadPool();
	}
}
