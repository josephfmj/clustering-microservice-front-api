package co.edu.ucatolica.clustering.front.api.controller.service.impl;

import java.util.Optional;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import co.edu.ucatolica.clustering.front.api.constant.ClusteringMethodNames;
import co.edu.ucatolica.clustering.front.api.constant.ClusteringMethodsConstants;
import co.edu.ucatolica.clustering.front.api.controller.service.IClusteringClientService;
import co.edu.ucatolica.clustering.front.api.controller.service.IClusteringResultWriterProvider;
import co.edu.ucatolica.clustering.front.api.controller.service.IDownLoadFileService;
import co.edu.ucatolica.clustering.front.api.model.AbstractClusteringMethodResponse;
import co.edu.ucatolica.clustering.front.api.model.ClusteringResponseFile;

@Service
public class DownLoadFileService implements IDownLoadFileService{

	private IClusteringClientService clusteringClientService;
	
	private IClusteringResultWriterProvider resultWriterProvider;
	
	private ClusteringResponseFile clusteringResponseFile;
	
	@Autowired
	public DownLoadFileService(IClusteringClientService clusteringClientService,
			IClusteringResultWriterProvider resultWriterProvider) {
		
		this.clusteringClientService = clusteringClientService;
		this.resultWriterProvider = resultWriterProvider;
		this.clusteringResponseFile = new ClusteringResponseFile();
		
	}
	
	@Override
	public Optional<ClusteringResponseFile> getClusteringResultFile(String executionId) {
		
		Optional<AbstractClusteringMethodResponse> response = Optional
				.ofNullable(this.clusteringClientService.getClusteringResult(executionId).getBody());
		
		return response
				.map(result -> getFile(result));
		
	}
	
	private ClusteringResponseFile getFile(AbstractClusteringMethodResponse response) {
		
		InputStreamResource resource = this.resultWriterProvider
				.getService(response.getMethodName())
				.readClusteringResponse(response)
				.writeRecords()
				.getResponseFile();
		
		if(!ObjectUtils.anyNotNull(resource)){
			
			this.clusteringResponseFile.setFileName(getResponseFileName(response.getMethodName()));
			this.clusteringResponseFile.setResourceFile(resource);
			
			return clusteringResponseFile;
		}
		
		return null;		
	}
	
	private String getResponseFileName(String methodName) {
		
		String fileName;
		
		switch (methodName) {
		
			case ClusteringMethodNames.AGNES:
				fileName = ClusteringMethodsConstants.AGNES_ZIP_FILE_NAME.getValue();
				break;
			case ClusteringMethodNames.CLARA:
				fileName = ClusteringMethodsConstants.CLARA_ZIP_FILE_NAME.getValue();
				break;
			case ClusteringMethodNames.DIANA:
				fileName = ClusteringMethodsConstants.DIANA_ZIP_FILE_NAME.getValue();
				break;
			case ClusteringMethodNames.KMEANS:
				fileName = ClusteringMethodsConstants.KMEANS_ZIP_FILE_NAME.getValue();
				break;
			case ClusteringMethodNames.PAM:
				fileName = ClusteringMethodsConstants.PAM_ZIP_FILE_NAME.getValue();
				break;
			default:
				fileName = StringUtils.EMPTY;
				break;
		}
		
		return fileName;
	}

}
