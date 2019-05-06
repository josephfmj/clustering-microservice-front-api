package co.edu.ucatolica.clustering.front.api.util.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Component;

import co.edu.ucatolica.clustering.front.api.util.IZipFileUtil;

@Component
public class ZipFileUtilImpl implements IZipFileUtil {
	
	private static Logger LOGGER = LoggerFactory.getLogger(ZipFileUtilImpl.class);
	
	private static final String DEFAULT_CSV_ENCODING = "UTF-8";
	
	private ZipOutputStream zipOut;
	
	private ByteArrayOutputStream byteArrayFile;

	@Override
	public IZipFileUtil createZipFile(String charset) {
		
		charset = charset == null ? DEFAULT_CSV_ENCODING : charset;
		this.byteArrayFile = new ByteArrayOutputStream();
		this.zipOut = new ZipOutputStream(this.byteArrayFile, Charset.forName(charset));
		
		return this;
		
		
	}

	@Override
	public IZipFileUtil attachToZipFile(String fileName, byte[] inputBytes) {
		
		
		ZipEntry zipEntry = new ZipEntry(fileName);
		zipEntry.setSize(inputBytes.length);
		
		try {
			this.zipOut.putNextEntry(zipEntry);
			this.zipOut.write(inputBytes);
			this.zipOut.closeEntry();
		} catch (IOException exception) {
			LOGGER.error("A ocurrido un error al agregar una entrada al archivo zip, error {}",
					exception.getMessage());
			throw new IllegalArgumentException("No es posible agregar el archivo al zip");
		}
		return this;
	}

	@Override
	public InputStreamResource getZipFile() {
		
		closeStreams();
		
		return new InputStreamResource(new ByteArrayInputStream(this.byteArrayFile.toByteArray()));
		
	}
	
	private void closeStreams() {
		
		try {
			this.zipOut.close();
			this.byteArrayFile.close();
		} catch (IOException exception) {
			LOGGER.error("A ocurrido un error al cerrar el archivo, error {}",
					exception.getMessage());
			throw new IllegalArgumentException("No es posible cerrar el archivo al zip");
		}
	}

}
