package io.g33k.blocol.clustering.kmeans;

import java.util.ArrayList;
import java.util.List;

public class Cluster {
	private List<Point> points;
	private Point centroid;
	private int id;

	public Cluster(int id) {
		super();
		this.id = id;
		this.points = new ArrayList<Point>();
		this.centroid = null;
	}

	public List<Point> getPoints() {
		return points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}

	public void addPoint(Point point) {
		points.add(point);
	}

	public void clear() {
		points.clear();
	}

	public Point getCentroid() {
		return centroid;
	}

	public void setCentroid(Point centroid) {
		this.centroid = centroid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void plotCluster() {
		System.out.println(" ***** Plotting Cluster ***** ");
		System.out.println("Cluster : " + id);
		System.out.println("Centroid : " + centroid);
		System.out.println("Points :\n");
		for (Point point : points) {
			System.out.println(point);
		}
		System.out.println(" ***** Plotting Finished ***** ");
	}

}
