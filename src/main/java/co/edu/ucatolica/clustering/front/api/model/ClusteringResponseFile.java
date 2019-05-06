package co.edu.ucatolica.clustering.front.api.model;

import java.io.IOException;

import org.springframework.core.io.InputStreamResource;

public class ClusteringResponseFile {
	
	private String fileName;
	
	private InputStreamResource resourceFile;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public InputStreamResource getResourceFile() {
		return resourceFile;
	}

	public void setResourceFile(InputStreamResource resourceFile) {
		this.resourceFile = resourceFile;
	}
	
	public long getFileSize() {
		
		try {
			return this.resourceFile.contentLength();
		} catch (IOException e) {
			return 0;
		}
	}

}
