package co.edu.ucatolica.clustering.front.api.model;

import java.util.List;
import java.util.Map;

public class KmeansResult {
	
	private List<Map<String, String>> centers;
	
	private Map<String,String> clusters;

	public List<Map<String, String>> getCenters() {
		return centers;
	}

	public void setCenters(List<Map<String, String>> centers) {
		this.centers = centers;
	}
	
	public Map<String, String> getClusters() {
		return clusters;
	}

	public void setClusters(Map<String, String> clusters) {
		this.clusters = clusters;
	}	

}
