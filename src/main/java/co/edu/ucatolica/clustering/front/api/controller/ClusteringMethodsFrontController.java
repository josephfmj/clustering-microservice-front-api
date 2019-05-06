package co.edu.ucatolica.clustering.front.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.edu.ucatolica.clustering.front.api.controller.service.IClusteringClientService;
import co.edu.ucatolica.clustering.front.api.model.ClusteringMethods;


@RestController("/methods")
public class ClusteringMethodsFrontController {
	
	@Autowired
	private IClusteringClientService clusteringClientService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/all")
	public ResponseEntity<ClusteringMethods> getClusteringMethods() {

		return clusteringClientService
				.getClusteringMethodConfigurations();

	}

}
