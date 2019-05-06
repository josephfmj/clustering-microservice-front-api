package co.edu.ucatolica.clustering.front.api.util;

import org.springframework.core.io.InputStreamResource;

public interface IZipFileUtil {
	
	public IZipFileUtil createZipFile(String charset);
	
	public IZipFileUtil attachToZipFile(String fileName, byte[] inputBytes);
	
	public InputStreamResource getZipFile();

}
