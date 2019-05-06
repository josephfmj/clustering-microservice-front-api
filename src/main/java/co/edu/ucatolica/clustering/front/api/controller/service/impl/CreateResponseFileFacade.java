package co.edu.ucatolica.clustering.front.api.controller.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import co.edu.ucatolica.clustering.front.api.constant.ClusteringMethodsConstants;
import co.edu.ucatolica.clustering.front.api.controller.service.ICreateResponseFileFacade;
import co.edu.ucatolica.clustering.front.api.util.ICSVPrinterUtil;
import co.edu.ucatolica.clustering.front.api.util.IZipFileUtil;

@Service
public class CreateResponseFileFacade implements ICreateResponseFileFacade{
	
	private static Logger LOGGER = LoggerFactory.getLogger(CreateResponseFileFacade.class);
			
	private ICSVPrinterUtil csvPrinterUtil;
	
	private IZipFileUtil zipFileUtil;
	
	private String csvDelimiter;
	
	private String filesCharset;
	
	@Autowired
	public CreateResponseFileFacade(ICSVPrinterUtil csvPrinterUtil, IZipFileUtil zipFileUtil) {
		
		this.csvPrinterUtil = csvPrinterUtil;
		this.zipFileUtil = zipFileUtil;
		
	}
	
	@Override
	public ICreateResponseFileFacade prepare(String csvDelimiter, String charset) {
		
		this.csvDelimiter = csvDelimiter;
		this.filesCharset = charset;
		zipFileUtil.createZipFile(filesCharset);
		
		return this;
	}
	
	@Override
	public ICreateResponseFileFacade writeCSVFile(List<String> headers, List<List<String>> records) {
		
		this.csvPrinterUtil
		.createCSVPrinter(csvDelimiter, filesCharset)
		.writeCSVFile(headers, records)
		.clearUtil();
		
		return this;
	}

	@Override
	public ICreateResponseFileFacade attachToZipFile(ClusteringMethodsConstants csvName) {
		
		zipFileUtil.attachToZipFile(csvName.getValue(),
				this.csvPrinterUtil.getFileResource());
		
		LOGGER.info("se ha escrito el archivo: {}",
				csvName.getValue()," y se ha agredado al archivo zip");
		
		return this;
	}

	@Override
	public InputStreamResource getResponseFile() {
		
		return zipFileUtil
				.getZipFile();
	}
}
