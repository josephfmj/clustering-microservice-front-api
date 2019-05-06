package co.edu.ucatolica.clustering.front.api.controller.service;

import org.springframework.http.ResponseEntity;

import co.edu.ucatolica.clustering.front.api.model.AbstractClusteringMethodResponse;
import co.edu.ucatolica.clustering.front.api.model.ClusteringExecutionRequest;
import co.edu.ucatolica.clustering.front.api.model.ClusteringMethods;

public interface IClusteringClientService {
	
	public ResponseEntity<String> sendToExecClustering(ClusteringExecutionRequest clusteringRequest);
	
	public ResponseEntity<AbstractClusteringMethodResponse> getClusteringResult(String executionId);
	
	public ResponseEntity<ClusteringMethods> getClusteringMethodConfigurations();
	
	public ResponseEntity<String> checkClusteringExec(String executionId);

}
