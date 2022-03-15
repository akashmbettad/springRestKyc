package com.signicat.assignment.Exception;

public class UpdateNotSuccessfulException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String msg;

	public UpdateNotSuccessfulException(String msg) {
		super();
		this.msg = msg;
	}
	
	
}
