package co.edu.ucatolica.clustering.front.api.controller.service;

import org.springframework.core.io.InputStreamResource;

import co.edu.ucatolica.clustering.front.api.model.AbstractClusteringMethodResponse;

public interface IClusteringResultWriter {
	
	public String getServiceName();
	
	public IClusteringResultWriter readClusteringResponse(AbstractClusteringMethodResponse response);
	
	public IClusteringResultWriter writeRecords();
	
	public InputStreamResource getResponseFile();

}
