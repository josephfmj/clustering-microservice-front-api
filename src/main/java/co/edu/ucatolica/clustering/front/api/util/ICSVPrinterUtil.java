package co.edu.ucatolica.clustering.front.api.util;

import java.util.List;

public interface ICSVPrinterUtil {
	
	public ICSVPrinterUtil createCSVPrinter(String csvDelimiter, String charset);
	
	public ICSVPrinterUtil writeCSVFile(List<String> headers, List<List<String>> records);
	
	public ICSVPrinterUtil clearUtil();
	
	public byte[] getFileResource();

}
