package com.signicat.assignment.Exception;

public class ErrorResponse {

	private String erroCode;
	private String error;
	
	public ErrorResponse(String erroCode, String error) {
		super();
		this.erroCode = erroCode;
		this.error = error;
	}

	public String getErroCode() {
		return erroCode;
	}

	public void setErroCode(String erroCode) {
		this.erroCode = erroCode;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
	
}
