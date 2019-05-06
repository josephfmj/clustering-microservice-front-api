package co.edu.ucatolica.clustering.front.api.model;

import java.util.List;
import java.util.Map;

public class ClaraResult {
	
	private List<Map<String,String>> medoids;
	
	private Map<String,String> clusters;

	public List<Map<String, String>> getMedoids() {
		return medoids;
	}

	public void setMedoids(List<Map<String, String>> medoids) {
		this.medoids = medoids;
	}

	public Map<String, String> getClusters() {
		return clusters;
	}

	public void setClusters(Map<String, String> clusters) {
		this.clusters = clusters;
	}

}
