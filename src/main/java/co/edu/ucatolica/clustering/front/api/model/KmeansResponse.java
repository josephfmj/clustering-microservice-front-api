package co.edu.ucatolica.clustering.front.api.model;

public class KmeansResponse extends AbstractClusteringMethodResponse {
	
	private KmeansResult result;

	public KmeansResult getResult() {
		return result;
	}

	public void setResult(KmeansResult result) {
		this.result = result;
	}

}
