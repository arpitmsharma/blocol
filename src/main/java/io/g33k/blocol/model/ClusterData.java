package io.g33k.blocol.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class ClusterData {

	private String name;

	@JsonInclude(Include.NON_EMPTY)
	private String url;

	@JsonInclude(Include.NON_EMPTY)
	private List<ClusterData> children;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<ClusterData> getChildren() {
		return children;
	}

	public void setChildren(List<ClusterData> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return "ClusterData [" + (name != null ? "name=" + name + ", " : "")
				+ (url != null ? "url=" + url + ", " : "")
				+ (children != null ? "children=" + children.toString() : "")
				+ "]";
	}

}
