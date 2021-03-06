package co.edu.ucatolica.clustering.front.api.services.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import co.edu.ucatolica.clustering.front.api.model.UploadResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import co.edu.ucatolica.clustering.front.api.services.ICSVReaderService;
import co.edu.ucatolica.clustering.front.api.services.ICSVSendFileService;
import co.edu.ucatolica.clustering.front.api.services.IClusteringClientService;
import co.edu.ucatolica.clustering.front.api.model.ApiClusteringExecutionRequest;
import co.edu.ucatolica.clustering.front.api.model.ClusteringExecutionRequest;

@Service
public class CSVSendFileService implements ICSVSendFileService{
	
	private IClusteringClientService clusteringService;
	
	private ICSVReaderService csvReaderService;
	
	private Map<String, List<String>> mapRecords;
	
	private ClusteringExecutionRequest clusteringRequest;
	
	private MultipartFile csvFile;
	
	@Autowired
	public CSVSendFileService(IClusteringClientService clusteringService,
			ICSVReaderService csvReaderService) {
		
		this.clusteringService = clusteringService;
		this.csvReaderService = csvReaderService;
		this.clusteringRequest = new ClusteringExecutionRequest();
	}

	@Override
	public ICSVSendFileService prepareData(ApiClusteringExecutionRequest methodData, MultipartFile csvFile) {
		
		this.clusteringRequest.setParams(methodData.getParams());	
		this.csvFile = csvFile;
		return this;
	}

	@Override
	public ICSVSendFileService readFile() {
		
		this.mapRecords = csvReaderService
				.prepareDataToRead(csvFile)
				.prepareMapRecord()
				.readCSVToMap();
		
		this.clusteringRequest.setData_frame(mapRecords);
		return this;
	}

	@Override
	public Optional<UploadResult> getResponse() {
		UploadResult response = new UploadResult();

		return Optional
		.ofNullable(clusteringService.sendToExecClustering(clusteringRequest).getBody())
				.map(data -> {
					response.setExecutionId(data);
					return response;
				});
		
	}

}
