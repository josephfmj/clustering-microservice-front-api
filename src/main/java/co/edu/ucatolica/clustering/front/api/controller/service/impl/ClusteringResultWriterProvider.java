package co.edu.ucatolica.clustering.front.api.controller.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.ucatolica.clustering.front.api.controller.service.IClusteringResultWriter;
import co.edu.ucatolica.clustering.front.api.controller.service.IClusteringResultWriterProvider;

@Service
public class ClusteringResultWriterProvider implements IClusteringResultWriterProvider {

	private Map<String, IClusteringResultWriter> clusteringWriterProvider;
	
	@Autowired
	public ClusteringResultWriterProvider(List<IClusteringResultWriter> clusteringWriterServices) {
		
		this.clusteringWriterProvider = new HashMap<>();		
		clusteringWriterServices
		.forEach(service -> this.clusteringWriterProvider.put(service.getServiceName(), service));
	}
	
	@Override
	public IClusteringResultWriter getService(String serviceName) {
		
		return clusteringWriterProvider.get(serviceName);
	}

}
