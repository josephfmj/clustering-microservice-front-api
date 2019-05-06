package co.edu.ucatolica.clustering.front.api.model;

public class PamResponse extends AbstractClusteringMethodResponse {
	
	private PamResult result;

	public PamResult getResult() {
		return result;
	}

	public void setResult(PamResult result) {
		this.result = result;
	}
}
