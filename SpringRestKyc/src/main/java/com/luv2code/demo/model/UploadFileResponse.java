package com.luv2code.demo.model;

public class UploadFileResponse {
	private String fileDownloadUri;
	private String fileType;
	private long size;

	public UploadFileResponse(String fileName, String fileDownloadUri, long size) {

		this.fileDownloadUri = fileDownloadUri;
		this.fileType = fileType;
		this.size = size;
	}


	public String getFileDownloadUri() {
		return fileDownloadUri;
	}

	public void setFileDownloadUri(String fileDownloadUri) {
		this.fileDownloadUri = fileDownloadUri;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	
}