package io.g33k.blocol.crawler;

import io.g33k.blocol.persistence.dao.ArticleDao;
import io.g33k.blocol.persistence.model.Article;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class CrawlerRunnableImp implements CrawlerRunnable {

	private @Autowired ApplicationContext ctx;
	private @Autowired ArticleDao articleDao;

	private String seedUrl;

	public void setSeedUrl(String seedUrl) {
		this.seedUrl = seedUrl;
	}

	public void run() {
		try {
			crawl(seedUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void crawl(String seedUrl) throws IOException {
		String blogId, url, title, data;
		Document doc = Jsoup.connect(seedUrl).get();
		Elements articles = doc.select("[id^=post]");
		for (Element element : articles) {
			System.out.println("************");
			blogId = element.id();
			System.out.println(blogId);
			Element heading = element.getElementsByClass("entry-title").get(0);
			url = heading.getElementsByTag("a").attr("href");
			System.out.println(url);
			title = heading.text();
			System.out.println(title);
			data = element.getElementsByClass("entry-content").text();
			System.out.println(data);
			Article ac = new Article(blogId, url, title, data);
			articleDao.add(ac);
		}
		System.out.println("########");
	}

	public CrawlerRunnable newRunnableInstance(String url) {
		System.out.println(ctx);
		CrawlerRunnable cr = ctx.getBean(CrawlerRunnable.class);
		cr.setSeedUrl(url);
		return cr;

	}
}
