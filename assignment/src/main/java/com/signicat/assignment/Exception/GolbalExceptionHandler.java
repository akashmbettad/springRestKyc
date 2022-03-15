package com.signicat.assignment.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GolbalExceptionHandler {

	@ExceptionHandler(value=DeleteNotSuccessfulException.class)
	public ResponseEntity<ErrorResponse> handleDeleteNotSuccessfulException() {
		
		ErrorResponse response=new ErrorResponse("Not Modified", "error while deleting the username");
		return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value=UpdateNotSuccessfulException.class)
	public ResponseEntity<ErrorResponse> handleUpdateNotSuccessfulException() {
		
		ErrorResponse response=new ErrorResponse("Not Modified", "error while deleting the username");
		return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
