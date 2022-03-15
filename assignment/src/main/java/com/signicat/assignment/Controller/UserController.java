package com.signicat.assignment.Controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.signicat.assignment.Exception.DeleteNotSuccessfulException;
import com.signicat.assignment.Exception.UpdateNotSuccessfulException;
import com.signicat.assignment.domain.Subject;
import com.signicat.assignment.domain.UserRequest;
import com.signicat.assignment.domain.UserResponse;
import com.signicat.assignment.repository.SubjectRepository;
import com.signicat.assignment.security.JwtUtil;
import com.signicat.assignment.service.SubjectService;

@RestController
public class UserController {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private SubjectRepository repo;

	@Autowired
	private SubjectService subjectService;

	//validate user and generate token
	@PostMapping("/login")
	public ResponseEntity<UserResponse> loginUser(@RequestBody UserRequest request) throws Exception{

		//validate credentials
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),
					request.getPassword()));
		}
		catch (DisabledException e) {
			logger.error("User doesn't exist or disabled");
			throw new Exception("USER_DISABLED", e);
		} 
		catch (BadCredentialsException e) {
			logger.error("Invalid credentials");
			throw new Exception("INVALID_CREDENTIALS", e);
		}
		
		UserDetails user= subjectService.loadUserByUsername(request.getUsername());

		String token=jwtUtil.generateToken(user.getUsername());

		UserResponse response= new UserResponse(token,"success!");
		return new ResponseEntity<UserResponse>(response,HttpStatus.OK);
	}


	@GetMapping("/getUser/{id}")
	public ResponseEntity<Optional<Subject>> getSubjectDetails(@PathVariable("id") Integer id){

		Optional<Subject> user=repo.findById(id);
		return new ResponseEntity<Optional<Subject>>(user,HttpStatus.OK);
	}


	@DeleteMapping("/remove") 
	public ResponseEntity<?> deleteUser() { 

		try {
			logger.info("Deleting the user");
			subjectService.deleteUserDetails(); 
			return new ResponseEntity<>("User deleted successfully",HttpStatus.OK);
			
		}
		catch(Exception e) {
			logger.error("Error while deleting the user, check if user exists");
			throw new DeleteNotSuccessfulException("Error while deleting the user");
		}
	}

	@PutMapping("/update/username")
	public ResponseEntity<?> updateUser(@RequestParam("oldUsername") String oldUsername,@RequestParam("newUsername") String newUsername) {

		try {
			logger.info("Updating the username");
			subjectService.updateUsername(oldUsername, newUsername);
			return new ResponseEntity<>("Username updated successfully",HttpStatus.OK);
		}
		catch(Exception e) {
			logger.error("Error while updating username, check if user exists");
			throw new UpdateNotSuccessfulException("Error while updating the username");
		}
	}



}
