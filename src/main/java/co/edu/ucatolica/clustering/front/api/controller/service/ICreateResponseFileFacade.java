package co.edu.ucatolica.clustering.front.api.controller.service;

import java.util.List;

import org.springframework.core.io.InputStreamResource;

import co.edu.ucatolica.clustering.front.api.constant.ClusteringMethodsConstants;

public interface ICreateResponseFileFacade {
	
	public ICreateResponseFileFacade prepare(String csvDelimiter, String charset);
	
	public ICreateResponseFileFacade writeCSVFile(List<String> headers, List<List<String>> records);
	
	public ICreateResponseFileFacade attachToZipFile(ClusteringMethodsConstants csvName);
	
	public InputStreamResource getResponseFile();

}
