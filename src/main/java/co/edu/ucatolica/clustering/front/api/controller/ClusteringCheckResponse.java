package co.edu.ucatolica.clustering.front.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.edu.ucatolica.clustering.front.api.controller.service.IClusteringClientService;

@RestController("/check")
public class ClusteringCheckResponse {
	
	@Autowired
	private IClusteringClientService clusteringClientService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/clustering-exec/{Id}")
	public ResponseEntity<String> sendClusteringData(@PathVariable("Id") String executionId) {

		return clusteringClientService
				.checkClusteringExec(executionId);

	}

}
