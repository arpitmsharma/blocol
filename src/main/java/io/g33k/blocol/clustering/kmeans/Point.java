package io.g33k.blocol.clustering.kmeans;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Point {
	private double x = 0;
	private double y = 0;
	private int cluster_number = 0;

	public Point(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public int getCluster() {
		return cluster_number;
	}

	public void setCluster(int cluster_number) {
		this.cluster_number = cluster_number;
	}

	// Calculate distance between two points
	protected static double distance(Point point, Point centroid) {
		return Math.sqrt(Math.pow((centroid.getX() - point.getX()), 2)
				+ Math.pow((centroid.getY() - point.getY()), 2));
	}

	// Calculate random point
	protected static Point createRandomPoint(int min, int max) {
		Random r = new Random();
		double x = min + (max - min) * r.nextDouble();
		double y = min + (max - min) * r.nextDouble();
		return new Point(x, y);
	}

	protected static List<Point> createRandomPoints(int min, int max, int number) {
		List<Point> points = new ArrayList<Point>(number);
		for (int i = 0; i < number; i++) {
			points.add(createRandomPoint(min, max));
		}
		return points;
	}

	@Override
	public String toString() {
		return "Point ( " + x + " , " + y + " )";
	}

}
