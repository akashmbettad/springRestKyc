package com.signicat.assignment.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.signicat.assignment.domain.Subject;
import com.signicat.assignment.service.SubjectService;

@RestController
public class RegisterController {
	
	@Autowired
	SubjectService userService;

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping("/welcome")
	public ResponseEntity<String> firstRequest(){
		
		return new ResponseEntity<String>("Welcome to signicat",HttpStatus.OK);
	}
	
	@PostMapping("/register")
	public ResponseEntity<Subject> register(@RequestBody Subject subject){
		
		logger.info("Saving user: "+subject);
		Subject user=userService.saveUserDetails(subject);
		
		return new ResponseEntity<Subject>(user,HttpStatus.CREATED);
	}
}
