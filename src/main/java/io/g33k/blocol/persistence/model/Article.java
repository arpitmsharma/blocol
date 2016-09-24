package io.g33k.blocol.persistence.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "Page_Content")
public class Article {

	@Id
	@Field(order = 1)
	private ObjectId id;

	@Field(order = 2, value = "blog_id")
	private String blogId;

	@Field(order = 3, value = "blog_url")
	private String url;

	@Field(order = 4, value = "blog_title")
	private String title;

	@Field(order = 5, value = "blog_data")
	private String data;

	public ObjectId getId() {
		return id;
	}

	public Article() {
		super();
	}

	public Article(String blogId, String url, String title, String data) {
		super();
		this.blogId = blogId;
		this.url = url;
		this.title = title;
		this.data = data;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getBlogId() {
		return blogId;
	}

	public void setBlogId(String blogId) {
		this.blogId = blogId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "PageContent [" + (id != null ? "id=" + id + ", " : "")
				+ (blogId != null ? "blogId=" + blogId + ", " : "")
				+ (url != null ? "url=" + url + ", " : "")
				+ (title != null ? "title=" + title + ", " : "")
				+ (data != null ? "data=" + data : "") + "]";
	}

}
