package ch.bestvision.abcbank.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PhotoTO {
	
	private Long id;
	
	@NotBlank
	private String fileName;
	
	@NotBlank
	private String fileContentType;
	
	@NotNull
	private byte[] fileData;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}
	
	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getFileData() {
		return fileData;
	}

	public void setFileData(byte[] file) {
		this.fileData = file;
	}
	
}
