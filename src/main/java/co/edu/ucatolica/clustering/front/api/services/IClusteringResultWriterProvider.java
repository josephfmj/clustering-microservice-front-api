package co.edu.ucatolica.clustering.front.api.services;

public interface IClusteringResultWriterProvider {
	
	public IClusteringResultWriter getService(String serviceName);	

}
