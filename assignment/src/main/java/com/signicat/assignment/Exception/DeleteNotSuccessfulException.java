package com.signicat.assignment.Exception;

public class DeleteNotSuccessfulException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String msg;

	public DeleteNotSuccessfulException(String msg) {
		super();
		this.msg = msg;
	}
	
	
}
