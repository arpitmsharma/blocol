package io.g33k.blocol.service;

import io.g33k.blocol.clustering.ClusteringEngine;
import io.g33k.blocol.clustering.hac.Cluster;
import io.g33k.blocol.crawler.CrawlingEngine;
import io.g33k.blocol.model.ClusterData;
import io.g33k.blocol.persistence.dao.ArticleDao;
import io.g33k.blocol.persistence.model.Article;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlocolService {
	private @Autowired ArticleDao articleDao;
	private @Autowired CrawlingEngine crawlingEngine;
	private @Autowired ClusteringEngine clusteringEngine;

	public ClusterData doBlocol(List<String> seedUrls) {
		crawlingEngine.start(seedUrls);
		List<Article> articles = articleDao.findAll();
		Cluster cluster = clusteringEngine.cluster(articles);
		return Objects.requireNonNull(getClusterData(cluster));
	}

	protected ClusterData getClusterData(Cluster cluster) {
		ClusterData clusterData = new ClusterData();
		Objects.requireNonNull(cluster);
		if (cluster.isLeaf()) {
			Article article = articleDao.findById(cluster.getName());
			clusterData.setName(article.getTitle());
			clusterData.setUrl(article.getUrl());
			clusterData.setChildren(null);
		} else {
			clusterData.setName(cluster.getName());
			clusterData.setUrl(null);
			List<ClusterData> children = new LinkedList<ClusterData>();
			if (cluster.getChildren() != null)
				for (Cluster child : cluster.getChildren()) {
					children.add(getClusterData(child));
				}
			if (!children.isEmpty())
				clusterData.setChildren(children);
		}
		return clusterData;
	}
}
