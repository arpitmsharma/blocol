package io.g33k.blocol.persistence.dao;

import io.g33k.blocol.persistence.model.Article;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleDao {
	private @Autowired MongoTemplate mongoTemplate;

	public boolean exists(Article pc) {
		if (findOne(pc) == null)
			return false;
		else
			return true;
	}

	public String add(Article pc) {
		final String COLLECTION_NAME = mongoTemplate.getCollectionName(pc
				.getClass());
		if (!mongoTemplate.collectionExists(COLLECTION_NAME)) {
			mongoTemplate.createCollection(COLLECTION_NAME);
		}
		mongoTemplate.insert(pc);
		return String.valueOf(pc.getId());
	}

	public void delete(Article pc) {
		final String COLLECTION_NAME = mongoTemplate.getCollectionName(pc
				.getClass());
		mongoTemplate.remove(pc, COLLECTION_NAME);
	}

	public long count(Class<Article> clazz) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").exists(true));
		return mongoTemplate.count(query, clazz);
	}

	public Article findOne(Article pc) {
		final String COLLECTION_NAME = mongoTemplate.getCollectionName(pc
				.getClass());
		return (Article) mongoTemplate.findOne(new Query().addCriteria(null),
				pc.getClass(), COLLECTION_NAME);
	}

	public Article findById(String id) {
		Class<Article> clazz = Article.class;
		return mongoTemplate.findById(id, clazz);
	}

	public List<Article> findAll() {
		Class<Article> clazz = Article.class;
		final String COLLECTION_NAME = mongoTemplate.getCollectionName(clazz);
		return (List<Article>) mongoTemplate.findAll(clazz, COLLECTION_NAME);
	}

}
