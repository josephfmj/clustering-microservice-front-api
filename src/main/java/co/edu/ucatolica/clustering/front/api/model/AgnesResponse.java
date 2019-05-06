package co.edu.ucatolica.clustering.front.api.model;

public class AgnesResponse extends AbstractClusteringMethodResponse {
	
	private HierarchicalResult result;
	
	public HierarchicalResult getResult() {
		return result;
	}

	public void setResult(HierarchicalResult result) {
		this.result = result;
	}

}
