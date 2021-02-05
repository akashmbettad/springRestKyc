package com.allianz.clara.bff;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class DemoRestController {
	
	
	@Autowired  
	private UserDaoService service; 
	
	//expose "/" to return "helloWorld" - demo for Hello!
	@GetMapping("/")
	public String sayHello() {
		return "Hello Clara, Time on a server is: "+ LocalDateTime.now();
		
	}
 
	@GetMapping("/users")  
	public List<User> retriveAllUsers()  
	{  
	return service.findAll();  
	}  
	
	//creating a new member - demo for status 200
	@PostMapping("/users")  
	public ResponseEntity<Object> createUser(@RequestBody User user)  
	{  
	User sevedUser=service.save(user);    
	URI location=ServletUriComponentsBuilder.fromCurrentRequest().path("/{policyNumber}").buildAndExpand(sevedUser.getpolicyNumber()).toUri();  
	return ResponseEntity.created(location).build();  
	}  
	
	
	//finding a member using policy member- demo for status 400.
	@GetMapping("/users/{policyNumber}")  
	public User retriveUser1(@PathVariable int policyNumber) throws UserNotFoundException  
	{  
	User user= service.findOne(policyNumber);  
	if(user==null)   
	throw new UserNotFoundException("policyNumber: "+ policyNumber);  
	return user;  
	}  
	
	
	
}
