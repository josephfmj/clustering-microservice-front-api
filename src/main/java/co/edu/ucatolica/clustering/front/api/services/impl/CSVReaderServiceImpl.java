package co.edu.ucatolica.clustering.front.api.services.impl;

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

import co.edu.ucatolica.clustering.front.api.services.ICSVReaderService;

@Service
public class CSVReaderServiceImpl implements ICSVReaderService {
	
	private List<CSVRecord> csvRecords;
	
	private List<String> columns;
	
	private Map<String, List<String>> mapRecords;
	
	private CSVParser csvParser;
	
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
			this.csvParser = csvRecords;
			this.csvRecords = this.csvParser.getRecords();
			this.columns = new ArrayList<>(this.csvParser.getHeaderMap().keySet());
			
		} catch (IOException e) {
			
			throw new IllegalArgumentException("El csv no tiene un formato valido o esta vacio");
		}
		
		return this;
	}
	
	@Override
	public ICSVReaderService prepareMapRecord() {
		
		if(this.columns.isEmpty() || this.csvRecords.isEmpty()) {
			throw new IllegalArgumentException("El csv no tiene un formato valido o esta vacio");
		}
		
		this.columns
		.forEach(column -> this.mapRecords.put(column, new ArrayList<>()));
		
		return this;
	}

	@Override
	public Map<String, List<String>> readCSVToMap() {

		this.csvRecords.forEach(this::processRecord);

		return this.mapRecords;
	}
	
	private void processRecord(CSVRecord csvRecord) {
		this.columns
		.forEach(column -> this.mapRecords.get(column).add(csvRecord.get(column)));

	}

}
