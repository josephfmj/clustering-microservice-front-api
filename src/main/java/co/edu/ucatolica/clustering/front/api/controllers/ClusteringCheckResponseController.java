package co.edu.ucatolica.clustering.front.api.controllers;

import co.edu.ucatolica.clustering.front.api.services.IClusteringClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/check")
public class ClusteringCheckResponseController {
	
	@Autowired
	private IClusteringClientService clusteringClientService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/clustering-exec/{Id}")
	public ResponseEntity<String> sendClusteringData(@PathVariable("Id") String executionId) {

		return clusteringClientService
				.checkClusteringExec(executionId);

	}

}
