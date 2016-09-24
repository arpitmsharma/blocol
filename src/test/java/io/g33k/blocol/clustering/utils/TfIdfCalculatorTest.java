package io.g33k.blocol.clustering.utils;

import io.g33k.blocol.clustering.hac.AverageLinkageStrategy;
import io.g33k.blocol.clustering.hac.Cluster;
import io.g33k.blocol.clustering.hac.ClusteringAlgorithm;
import io.g33k.blocol.clustering.hac.PDistClusteringAlgorithm;
import io.g33k.blocol.clustering.hac.visualization.DendrogramPanel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.junit.Test;

public class TfIdfCalculatorTest {

	@Test
	public void testGetTfIdfVector() {
		List<String> doc1 = Arrays.asList("Lorem", "ipsum", "dolor", "ipsum",
				"sit", "ipsum");
		List<String> doc2 = Arrays.asList("Vituperata", "incorrupte", "at",
				"ipsum", "pro", "quo");
		List<String> doc3 = Arrays.asList("pro", "quo", "disputationi", "id",
				"simul");
		List<List<String>> documents = Arrays.asList(doc1, doc2, doc3);

		TfIdfCalculator tiCal = new TfIdfCalculator();
		CosineSimilarity cs = new CosineSimilarity();

		int size = documents.size();
		double[][] pdist = new double[1][(size) * (size - 1) / 2];
		int k = 0;
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

		String[] names = new String[] { "O1", "O2", "O3" };
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
		JFrame frame = new JFrame("Cluster");
		System.out.println(frame);
		frame.setSize(500, 500);
		DendrogramPanel dp = new DendrogramPanel();
		dp.setModel(cluster);
		dp.setVisible(true);
		frame.add(dp);
		frame.setVisible(true);
	}
}