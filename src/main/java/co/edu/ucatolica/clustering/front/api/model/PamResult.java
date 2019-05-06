package co.edu.ucatolica.clustering.front.api.model;

import java.util.List;
import java.util.Map;

public class PamResult {
	
	private List<String> medoids;
	
	private Map<String, String> clusters;

	public List<String> getMedoids() {
		return medoids;
	}

	public void setMedoids(List<String> medoids) {
		this.medoids = medoids;
	}

	public Map<String, String> getClusters() {
		return clusters;
	}

	public void setClusters(Map<String, String> clusters) {
		this.clusters = clusters;
	}

}
