package co.edu.ucatolica.clustering.front.api.controller.service.impl;

import java.util.Arrays;
import java.util.List;

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
import co.edu.ucatolica.clustering.front.api.model.PamResponse;
import co.edu.ucatolica.clustering.front.api.util.MapRecordsUtil;

@Service(ClusteringMethodNames.PAM)
public class PamResultWriter implements IClusteringResultWriter{
	
	private static Logger LOGGER = LoggerFactory.getLogger(PamResultWriter.class);
	
	private ICreateResponseFileFacade createResponseFile;
	
	private String csvDelimiter;
	
	private String filesCharset;
	
	private List<String> clusterHeaders;
	
	private List<List<String>> clusterRecords;
	
	private List<String> medoidHeader;
	
	private List<List<String>> medoidRecord;
	
	private PamResponse pamResponse;
	
	@Autowired
	public PamResultWriter(ICreateResponseFileFacade createResponseFile,
			@Value("${clustering.service.charset-response-file}") String csvDelimiter,
			@Value("${clustering.service.charset-response-file}") String filesCharset) {
		
		this.csvDelimiter = csvDelimiter;
		this.filesCharset = filesCharset;
		this.createResponseFile = createResponseFile; 
		String clusterRowNames[] = ClusteringMethodsConstants
				.CLUSTER_RESULT_DEFAULT_COLUMN_NAMES
				.getValue()
				.split(this.csvDelimiter);
		this.clusterHeaders = Arrays.asList(clusterRowNames);
	}

	@Override
	public String getServiceName() {
		
		return ClusteringMethodNames.PAM.toUpperCase();
	}

	@Override
	public IClusteringResultWriter readClusteringResponse(AbstractClusteringMethodResponse response) {
		
		LOGGER.info("leyendo objeto PamResponse");
		
		this.pamResponse = (PamResponse) response;		
		String medoidColumnName[] = {ClusteringMethodsConstants.PAM_MEDOIDS_COLUMN_NAME.getValue()};
		this.medoidHeader = Arrays.asList(medoidColumnName);		
		this.medoidRecord = MapRecordsUtil
				.listToRecordList(pamResponse.getResult().getMedoids());		
		this.clusterRecords = MapRecordsUtil
				.mapRecordToRecordList(pamResponse.getResult().getClusters());
		
		return this;
	}

	@Override
	public IClusteringResultWriter writeRecords() {
		
		this.createResponseFile
		.prepare(csvDelimiter, filesCharset)
		.writeCSVFile(clusterHeaders,clusterRecords)
		.attachToZipFile(ClusteringMethodsConstants.PAM_CLUSTERS_CSV_FILE_NAME)
		.writeCSVFile(medoidHeader,medoidRecord)
		.attachToZipFile(ClusteringMethodsConstants.PAM_MEDOIDS_CSV_FILE_NAME);
		
		return this;
	}

	@Override
	public InputStreamResource getResponseFile() {
		
		return this.createResponseFile
				.getResponseFile();
	}

}
