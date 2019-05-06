package co.edu.ucatolica.clustering.front.api.controller.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;


public interface ICSVReaderService {
	
	public ICSVReaderService prepareDataToRead(MultipartFile csvFile);
	
	public ICSVReaderService prepareMapRecord();
	
	public Map<String,List<String>> readCSVToMap();

}
