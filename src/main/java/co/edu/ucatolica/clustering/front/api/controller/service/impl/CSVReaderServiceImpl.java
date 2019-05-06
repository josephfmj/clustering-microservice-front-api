package co.edu.ucatolica.clustering.front.api.controller.service.impl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import co.edu.ucatolica.clustering.front.api.controller.service.ICSVReaderService;

@Service
public class CSVReaderServiceImpl implements ICSVReaderService {
	
	private  boolean csvRecordEmpty;
	
	private List<String> columns;
	
	private Map<String, List<String>> mapRecords;
	
	private CSVParser csvRecords;
	
	public CSVReaderServiceImpl() {
		
		this.columns = new ArrayList<>();
		this.mapRecords = new HashMap<>();
	}

	@Override
	public ICSVReaderService prepareDataToRead(MultipartFile csvFile) {
		
		try(
		
			CSVParser csvRecords = CSVFormat
			.EXCEL
			.withFirstRecordAsHeader()
			.parse(new InputStreamReader(csvFile.getInputStream()));
		
		) {
			this.csvRecords = csvRecords;
			this.csvRecordEmpty = this.csvRecords.getRecords().isEmpty();
			this.columns = new ArrayList<>(this.csvRecords.getHeaderMap().keySet());
			
		} catch (IOException e) {
			
			throw new IllegalArgumentException("El csv no tiene un formato valido o esta vacio");
		}
		
		return this;
	}
	
	@Override
	public ICSVReaderService prepareMapRecord() {
		
		if(this.columns.isEmpty() || this.csvRecordEmpty) {
			throw new IllegalArgumentException("El csv no tiene un formato valido o esta vacio");
		}
		
		this.columns
		.forEach(column -> this.mapRecords.put(column, new ArrayList<>()));
		
		return this;
	}

	@Override
	public Map<String, List<String>> readCSVToMap() {
		
		this.csvRecords
		.forEach(this::processRecord);
		
		return this.mapRecords;
	}
	
	private void processRecord(CSVRecord csvRecord) {
		this.columns
		.forEach(column -> this.mapRecords.get(column).add(csvRecord.get(column)));
	}

}
