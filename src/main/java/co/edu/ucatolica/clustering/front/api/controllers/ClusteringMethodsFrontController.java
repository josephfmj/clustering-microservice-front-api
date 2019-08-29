package co.edu.ucatolica.clustering.front.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.edu.ucatolica.clustering.front.api.services.IClusteringClientService;
import co.edu.ucatolica.clustering.front.api.model.ClusteringMethods;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/methods")
public class ClusteringMethodsFrontController {
	
	@Autowired
	private IClusteringClientService clusteringClientService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/all")
	public ResponseEntity<Object[]> getClusteringMethods() {

		return clusteringClientService
				.getClusteringMethodConfigurations();

	}

}
