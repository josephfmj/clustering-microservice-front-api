package co.edu.ucatolica.clustering.front.api.services;

import java.util.Optional;

import co.edu.ucatolica.clustering.front.api.model.UploadResult;
import org.springframework.web.multipart.MultipartFile;

import co.edu.ucatolica.clustering.front.api.model.ApiClusteringExecutionRequest;

public interface ICSVSendFileService {
	
	public ICSVSendFileService prepareData(ApiClusteringExecutionRequest methodData, MultipartFile csvFile);
	
	public ICSVSendFileService readFile();
	
	public Optional<UploadResult> getResponse();

}
