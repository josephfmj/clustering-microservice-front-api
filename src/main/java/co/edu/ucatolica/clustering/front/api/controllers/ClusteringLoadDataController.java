package co.edu.ucatolica.clustering.front.api.controllers;

import java.util.Optional;

import co.edu.ucatolica.clustering.front.api.model.UploadResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import co.edu.ucatolica.clustering.front.api.services.ICSVSendFileService;
import co.edu.ucatolica.clustering.front.api.services.IDownLoadFileService;
import co.edu.ucatolica.clustering.front.api.model.ApiClusteringExecutionRequest;
import co.edu.ucatolica.clustering.front.api.model.ClusteringResponseFile;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/data")
public class ClusteringLoadDataController {
	
	@Autowired
	private IDownLoadFileService downLoadFileService;
	
	@Autowired
	private ICSVSendFileService sendFileService;

	@RequestMapping(method = RequestMethod.POST, value = "/upload",  consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UploadResult> sendClusteringData(@RequestPart(name = "clusteringData") ApiClusteringExecutionRequest methodData,
														   @RequestPart("csvFile") MultipartFile csvFile) {

		Optional<UploadResult> result = sendFileService
				.prepareData(methodData, csvFile)
				.readFile()
				.getResponse();
		
		return buildResponseEntity(HttpStatus.BAD_REQUEST, result);

	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/download/{Id}")
	public ResponseEntity<Resource> downLoadClusteringExec(@PathVariable("Id") String executionId) {
		
		final Optional<ClusteringResponseFile> result = downLoadFileService
				.getClusteringResultFile(executionId);
		
		return buildResourceResponseEntity(HttpStatus.BAD_REQUEST, result);

	}
	
	private ResponseEntity<UploadResult> buildResponseEntity(HttpStatus badStatus,
			Optional<UploadResult> result){
				
		return result.isPresent() ? new ResponseEntity<UploadResult>(result.get(), HttpStatus.OK) :
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
