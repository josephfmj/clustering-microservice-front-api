package co.edu.ucatolica.clustering.front.api.util.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import co.edu.ucatolica.clustering.front.api.util.ICSVPrinterUtil;

@Component
public class CSVPrinterUtilImpl implements ICSVPrinterUtil{
	
	private static Logger LOGGER = LoggerFactory.getLogger(CSVPrinterUtilImpl.class);
	
	private static final char DEFAULT_CSV_DELIMITER = ',';
	
	private static final String DEFAULT_CSV_ENCODING = "UTF-8";
	
	private ByteArrayOutputStream byteArrayFile;
	
	private CSVPrinter csvPrinter;
	
	private OutputStreamWriter outPutWriter;
	
	@Override
	public ICSVPrinterUtil createCSVPrinter(String csvDelimiter, String charset) {
		
		final char delimiter = csvDelimiter == null ? DEFAULT_CSV_DELIMITER : csvDelimiter.charAt(0);
		charset = charset == null ? DEFAULT_CSV_ENCODING : charset;
		
		getOutPutPrinter(charset);
		
		try {
			this.csvPrinter = new CSVPrinter(outPutWriter,CSVFormat.EXCEL.withDelimiter(delimiter));
		} catch (IOException exception) {
			
			LOGGER.error("A ocurrido un error al crear el CSVPrinter, error {}", exception.getMessage());
			throw new IllegalArgumentException("No es posible crear el CSVPrinter");
		}
		
		return this;
	}
	
	@Override
	public ICSVPrinterUtil writeCSVFile(List<String> headers, List<List<String>> records) {
		
		try {
			
			this.csvPrinter.printRecord(headers);
			
			for(List<String> record : records) {
				this.csvPrinter.printRecord(record);
			}
			
			LOGGER.info("se ha creado el archivo CSV");
			
		} catch (IOException exception) {
			LOGGER.error("A ocurrido un agregar un registro al csv, error {}",
					exception.getMessage());
			throw new IllegalArgumentException("No es posible agregar el registro al archivo csv");
		}
		
		return this;
	}
	
	@Override
	public ICSVPrinterUtil clearUtil() {
		
		try {
			this.byteArrayFile.close();
			this.outPutWriter.close();
			this.csvPrinter.close();
		} catch (IOException exception) {
			LOGGER.error("A ocurrido un error al cerrar el CSVPrinter, error {}", exception.getMessage());
			throw new IllegalArgumentException("No es posible cerrar el CSVPrinter");
		}
		
		return this;
	}
	
	@Override
	public byte[] getFileResource() {
		
		return this.byteArrayFile.toByteArray();
	}
	
	private void getOutPutPrinter(String charset) {
		
		this.byteArrayFile = new ByteArrayOutputStream();
		
		try {
			
			this.outPutWriter = new OutputStreamWriter(this.byteArrayFile,charset);
			
		} catch (UnsupportedEncodingException exception) {
			
			LOGGER.error("A ocurrido un error al crear el OutPutWriter, error {}", exception.getMessage());
			throw new IllegalArgumentException("No es posible crear el OutPutWriter");
		}
	}	
}
