package co.edu.ucatolica.clustering.front.api.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import co.edu.ucatolica.clustering.front.api.controller.service.ICSVSendFileService;
import co.edu.ucatolica.clustering.front.api.controller.service.IDownLoadFileService;
import co.edu.ucatolica.clustering.front.api.model.ApiClusteringExecutionRequest;
import co.edu.ucatolica.clustering.front.api.model.ClusteringResponseFile;

@RestController("/data")
public class ClusteringLoadDataController {
	
	@Autowired
	private IDownLoadFileService downLoadFileService;
	
	@Autowired
	private ICSVSendFileService sendFileService;

	@RequestMapping(method = RequestMethod.POST, value = "/upload")
	public ResponseEntity<String> sendClusteringData(@RequestBody ApiClusteringExecutionRequest methodData,
			@RequestParam("csvFile") MultipartFile csvFile) {

		Optional<String> result = sendFileService
				.prepareData(methodData, csvFile)
				.readFile()
				.getResponse();
		
		return buildResponseEntity(HttpStatus.BAD_REQUEST, result);

	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/download/{Id}")
	public ResponseEntity<Resource> downLoadClusteringExec(@PathVariable("Id") String executionId) {
		
		final Optional<ClusteringResponseFile> result = downLoadFileService
				.getClusteringResultFile(executionId);
		
		return buildResourceResponseEntity(HttpStatus.BAD_REQUEST, result);

	}
	
	private ResponseEntity<String> buildResponseEntity(HttpStatus badStatus,
			Optional<String> result){
				
		return result.isPresent() ? new ResponseEntity<String>(result.get(), HttpStatus.OK) :
			new ResponseEntity<>(badStatus);
	}
		
	private ResponseEntity<Resource> buildResourceResponseEntity(HttpStatus badStatus, 
			Optional<ClusteringResponseFile> result) {
		
		if(result.isPresent()) {
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=" + result.get().getFileName())
				    .contentType(MediaType.parseMediaType("application/zip"))
				    .contentLength(result.get().getFileSize())
				    .body(result.get().getResourceFile());
		}
		
		return new ResponseEntity<>(badStatus);
	}

}
