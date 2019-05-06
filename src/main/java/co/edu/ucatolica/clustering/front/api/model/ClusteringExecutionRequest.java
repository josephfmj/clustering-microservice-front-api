package co.edu.ucatolica.clustering.front.api.model;

import java.util.List;
import java.util.Map;

public class ClusteringExecutionRequest {
	
	private Map<String,String> params;
	
	private Map<String,List<String>> data_frame;

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public Map<String, List<String>> getData_frame() {
		return data_frame;
	}

	public void setData_frame(Map<String, List<String>> data_frame) {
		this.data_frame = data_frame;
	}
	
}
