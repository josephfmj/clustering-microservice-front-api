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
import co.edu.ucatolica.clustering.front.api.model.ClaraResponse;
import co.edu.ucatolica.clustering.front.api.util.MapRecordsUtil;

@Service(ClusteringMethodNames.CLARA)
public class ClaraResultWriter implements IClusteringResultWriter {
	
	private static Logger LOGGER = LoggerFactory.getLogger(ClaraResultWriter.class);
	
	private ICreateResponseFileFacade createResponseFile;
	
	private String csvDelimiter;
	
	private String filesCharset;
	
	private List<String> medoidHeaders;
	
	private List<List<String>> medoidRecords;
	
	private List<String> clusterHeaders;
	
	private List<List<String>> clusterRecords;
	
	private ClaraResponse claraResponse;
	
	@Autowired
	public ClaraResultWriter(ICreateResponseFileFacade createResponseFile,
			@Value("${clustering.service.charset-response-file}") String csvDelimiter,
			@Value("${clustering.service.charset-response-file}") String filesCharset) {
		
		this.csvDelimiter = csvDelimiter;
		this.filesCharset = filesCharset;
		this.createResponseFile = createResponseFile; 
		this.medoidRecords = new ArrayList<>();
		String clusterRowNames[] = ClusteringMethodsConstants
				.CLUSTER_RESULT_DEFAULT_COLUMN_NAMES
				.getValue()
				.split(this.csvDelimiter);
		this.clusterHeaders = Arrays.asList(clusterRowNames);
		
	}

	@Override
	public String getServiceName() {
		
		return ClusteringMethodNames.CLARA.toUpperCase();
	}

	@Override
	public IClusteringResultWriter readClusteringResponse(AbstractClusteringMethodResponse response) {
		
		LOGGER.info("leyendo objeto ClaraResponse");
		
		this.claraResponse = (ClaraResponse) response;
		
		this.medoidHeaders = MapRecordsUtil
				.getHeaderFromMap(claraResponse.getResult().getMedoids().get(0));
		
		this.claraResponse
		.getResult()
		.getMedoids()
		.forEach(this::getMedoidRecord);
		
		this.clusterRecords = MapRecordsUtil
				.mapRecordToRecordList(claraResponse.getResult().getClusters());
		return this;
	}

	@Override
	public IClusteringResultWriter writeRecords() {
		
		this.createResponseFile
		.prepare(csvDelimiter, filesCharset)
		.writeCSVFile(medoidHeaders, medoidRecords)
		.attachToZipFile(ClusteringMethodsConstants.CLARA_MEDOIDS_CSV_FILE_NAME)
		.writeCSVFile(clusterHeaders, clusterRecords)
		.attachToZipFile(ClusteringMethodsConstants.CLARA_CLUSTERS_CSV_FILE_NAME);
		
		return this;
	}

	@Override
	public InputStreamResource getResponseFile() {
		
		return this.createResponseFile
				.getResponseFile();
	}
	
	private void getMedoidRecord(Map<String, String> mapRecord) {
		
		this.medoidRecords
		.add(MapRecordsUtil.getRecordFromMap(mapRecord, this.medoidHeaders));
	}

}
