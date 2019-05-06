package co.edu.ucatolica.clustering.front.api.controller.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import co.edu.ucatolica.clustering.front.api.controller.service.IClusteringClientService;
import co.edu.ucatolica.clustering.front.api.model.AbstractClusteringMethodResponse;
import co.edu.ucatolica.clustering.front.api.model.ClusteringExecutionRequest;
import co.edu.ucatolica.clustering.front.api.model.ClusteringMethods;

@Service
public class ClusteringClientService implements IClusteringClientService {

	private RestTemplate client;
	
	private String clusteringServiceHost;
	
	private String clusteringServiceExecutionUri;
	
	private String clusteringServiceResultUri;
	
	private String clusteringServiceConfigurationUri;
	
	private String clusteringServiceCheckResultUri;
	
	@Autowired
	public ClusteringClientService(RestTemplateBuilder builder, 
			@Value(value = "${clustering.service.host}") String clusteringServiceHost,
			@Value(value = "${clustering.service.execution}") String clusteringServiceExecutionUri,
			@Value(value = "${clustering.service.result}") String clusteringServiceResultUri,
			@Value(value = "${clustering.service.configuration}") String clusteringServiceConfigurationUri,
			@Value(value = "${clustering.service.check-result}") String clusteringServiceCheckResultUri){
		
		this.client = builder.build();
		this.clusteringServiceHost = clusteringServiceHost;
		this.clusteringServiceExecutionUri = clusteringServiceExecutionUri;
		this.clusteringServiceResultUri = clusteringServiceResultUri;
		this.clusteringServiceConfigurationUri = clusteringServiceConfigurationUri;
		this.clusteringServiceCheckResultUri = clusteringServiceCheckResultUri;
	}
	
	@Override
	public ResponseEntity<String> sendToExecClustering(ClusteringExecutionRequest clusteringRequest) {

		final String uriService = buildServiceUri(clusteringServiceHost,clusteringServiceExecutionUri);
		
		return client.postForEntity(uriService, clusteringRequest, String.class);
	}

	@Override
	public ResponseEntity<AbstractClusteringMethodResponse> getClusteringResult(String executionId) {
		
		final String uriService = buildServiceUri(clusteringServiceHost,clusteringServiceResultUri,executionId);
		
		return client.getForEntity(uriService, AbstractClusteringMethodResponse.class);
	}
	
	@Override
	public ResponseEntity<ClusteringMethods> getClusteringMethodConfigurations() {
		
		final String uriService = buildServiceUri(clusteringServiceHost,clusteringServiceConfigurationUri);
		
		return client.getForEntity(uriService, ClusteringMethods.class);
	}
	
	@Override
	public ResponseEntity<String> checkClusteringExec(String executionId) {
		
		final String uriService = buildServiceUri(clusteringServiceHost,clusteringServiceCheckResultUri,executionId);
		
		return client.getForEntity(uriService, String.class);
	}
	
	private String buildServiceUri(String... params) {
		
		return String.join("", params);
	}
}
