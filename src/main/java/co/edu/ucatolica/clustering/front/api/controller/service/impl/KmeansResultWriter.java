package co.edu.ucatolica.clustering.front.api.controller.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import co.edu.ucatolica.clustering.front.api.constant.ClusteringMethodNames;
import co.edu.ucatolica.clustering.front.api.constant.ClusteringMethodsConstants;
import co.edu.ucatolica.clustering.front.api.controller.service.IClusteringResultWriter;
import co.edu.ucatolica.clustering.front.api.controller.service.ICreateResponseFileFacade;
import co.edu.ucatolica.clustering.front.api.model.AbstractClusteringMethodResponse;
import co.edu.ucatolica.clustering.front.api.model.KmeansResponse;
import co.edu.ucatolica.clustering.front.api.util.MapRecordsUtil;

@Service(ClusteringMethodNames.KMEANS)
public class KmeansResultWriter implements IClusteringResultWriter {
	
	private static Logger LOGGER = LoggerFactory.getLogger(KmeansResultWriter.class);
	
	private ICreateResponseFileFacade createResponseFile;
	
	private String csvDelimiter;
	
	private String filesCharset;
	
	private List<String> centerHeaders;
	
	private List<String> clusterHeaders;
	
	private List<List<String>> centerRecords;
	
	private List<List<String>> clusterRecords;
	
	private KmeansResponse kmeansResponse;
	
	@Autowired
	public KmeansResultWriter(ICreateResponseFileFacade createResponseFile,
			@Value("${clustering.service.charset-response-file}") String csvDelimiter,
			@Value("${clustering.service.charset-response-file}") String filesCharset) {
		
		this.csvDelimiter = csvDelimiter;
		this.filesCharset = filesCharset;
		this.createResponseFile = createResponseFile; 
		this.centerRecords = new ArrayList<>();
		String clusterRowNames[] = ClusteringMethodsConstants
				.CLUSTER_RESULT_DEFAULT_COLUMN_NAMES
				.getValue()
				.split(this.csvDelimiter);
		this.clusterHeaders = Arrays.asList(clusterRowNames);
	}
	
	@Override
	public String getServiceName() {
		
		return ClusteringMethodNames.KMEANS.toUpperCase();
	}
	
	@Override
	public IClusteringResultWriter readClusteringResponse(AbstractClusteringMethodResponse response) {
		
		LOGGER.info("leyendo objeto KmeansResponse");
		
		this.kmeansResponse = (KmeansResponse) response;
		
		this.centerHeaders = MapRecordsUtil
				.getHeaderFromMap(kmeansResponse.getResult().getCenters().get(0));
		
		this.kmeansResponse
		.getResult()
		.getCenters()
		.forEach(this::getCenterRecord);
		
		this.clusterRecords = MapRecordsUtil
				.mapRecordToRecordList(kmeansResponse.getResult().getClusters());		
		
		return this;
		
	}
	
	@Override
	public IClusteringResultWriter writeRecords() {
		
		this.createResponseFile
		.prepare(csvDelimiter, filesCharset)
		.writeCSVFile(centerHeaders,centerRecords)
		.attachToZipFile(ClusteringMethodsConstants.KMEANS_CENTERS_CSV_FILE_NAME)
		.writeCSVFile(clusterHeaders, clusterRecords)
		.attachToZipFile(ClusteringMethodsConstants.KMEANS_CLUSTERS_CSV_FILE_NAME);
		
		return this;
	}

	@Override
	public InputStreamResource getResponseFile() {
		
		return this.createResponseFile
				.getResponseFile();
		
	}
	
	private void getCenterRecord(Map<String, String> mapRecord) {
		
		this.centerRecords
		.add(MapRecordsUtil.getRecordFromMap(mapRecord, this.centerHeaders));
	}

}
