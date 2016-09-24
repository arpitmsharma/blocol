package io.g33k.blocol.crawler;

import org.springframework.stereotype.Component;

@Component
public interface CrawlerRunnable extends Runnable {

	public void setSeedUrl(String seedUrl);

	public CrawlerRunnable newRunnableInstance(String url);

}
