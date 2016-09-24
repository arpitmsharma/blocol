package io.g33k.blocol.app;

import io.g33k.blocol.clustering.hac.AverageLinkageStrategy;
import io.g33k.blocol.clustering.hac.Cluster;
import io.g33k.blocol.clustering.hac.ClusteringAlgorithm;
import io.g33k.blocol.clustering.hac.PDistClusteringAlgorithm;
import io.g33k.blocol.clustering.hac.visualization.DendrogramPanel;
import io.g33k.blocol.clustering.utils.CosineSimilarity;
import io.g33k.blocol.clustering.utils.TfIdfCalculator;
import io.g33k.blocol.clustering.utils.TfIdfValuePair;
import io.g33k.blocol.crawler.CrawlingEngine;
import io.g33k.blocol.persistence.dao.ArticleDao;
import io.g33k.blocol.persistence.model.Article;
import io.g33k.blocol.textprocessor.TextProcessingEngine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppStarter {
	private final static Logger log = Logger.getLogger(AppStarter.class);

	public static void main(String[] seedUrls) {

		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"file:src/main/webapp/WEB-INF/spring/appServlet/mongodb-config.xml");
		String[] ctxPath = { "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" };
		ApplicationContext appContext = new ClassPathXmlApplicationContext(
				ctxPath, ctx);
		log.info("Starting Crawl Engine");
		CrawlingEngine crawlEngine = appContext.getBean(CrawlingEngine.class);
		System.out.println(crawlEngine);
		crawlEngine.start(Arrays.asList(seedUrls));

		ArticleDao articleDao = appContext.getBean(ArticleDao.class);
		List<Article> articles = articleDao.findAll();
		HashMap<String, List<String>> articleMap = new HashMap<String, List<String>>(
				articles.size());
		for (Article article : articles) {
			articleMap.put(article.getTitle(), new TextProcessingEngine()
					.preprocessText(article.getTitle()));
		}

		TfIdfCalculator tiCal = new TfIdfCalculator();
		CosineSimilarity cs = new CosineSimilarity();

		int size = articleMap.size();
		double[][] pdist = new double[1][(size) * (size - 1) / 2];
		int k = 0;
		List<List<String>> documents = new ArrayList<List<String>>(
				articleMap.values());
		for (int i = 0; i < size; i++) {
			for (int j = i + 1; j < size; j++) {
				HashMap<String, TfIdfValuePair> map = tiCal.getTfIdfVectorMap(
						documents.get(i), documents.get(j), documents);
				List<Double> docVector1 = new ArrayList<Double>();
				List<Double> docVector2 = new ArrayList<Double>();
				for (Entry<String, TfIdfValuePair> entry : map.entrySet()) {
					docVector1.add(entry.getValue().getTfidfDoc1());
					docVector2.add(entry.getValue().getTfidfDoc2());
				}
				double similarityQuotient = cs.cosineSimilarity(docVector1,
						docVector2);
				System.out.println(similarityQuotient);
				pdist[0][k++] = 10 - 10 * similarityQuotient;
			}
		}

		String[] names = articleMap.keySet().toArray(new String[0]);
		ClusteringAlgorithm alg = new PDistClusteringAlgorithm();
		Cluster cluster = alg.performClustering(pdist, names,
				new AverageLinkageStrategy());
		cluster.toConsole(2);

		SwingUtilities.invokeLater(new Runner(cluster));
	}
}

class Runner implements Runnable {

	private Cluster cluster;

	public Runner(Cluster cluster) {
		super();
		this.cluster = cluster;
	}

	@Override
	public void run() {
		JFrame frame = new JFrame("ClusterFuck");
		frame.setSize(1000, 1000);
		DendrogramPanel dp = new DendrogramPanel();
		dp.setModel(cluster);
		dp.setVisible(true);
		frame.add(dp);
		frame.setVisible(true);
	}
}
