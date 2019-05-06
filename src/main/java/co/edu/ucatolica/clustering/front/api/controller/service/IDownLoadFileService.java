package co.edu.ucatolica.clustering.front.api.controller.service;

import java.util.Optional;

import co.edu.ucatolica.clustering.front.api.model.ClusteringResponseFile;

public interface IDownLoadFileService {
	
	public Optional<ClusteringResponseFile> getClusteringResultFile(String executionId);

}
