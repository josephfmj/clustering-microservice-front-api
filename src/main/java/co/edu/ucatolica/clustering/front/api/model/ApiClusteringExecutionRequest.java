package co.edu.ucatolica.clustering.front.api.model;

import java.util.Map;

public class ApiClusteringExecutionRequest {

	private Map<String,String> params;

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}
}
