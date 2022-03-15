package com.signicat.assignment.service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.signicat.assignment.domain.Subject;
import com.signicat.assignment.domain.UserGroup;
import com.signicat.assignment.repository.SubjectRepository;
import com.signicat.assignment.repository.UserGroupRepository;

@Service
@PreAuthorize("hasRole('USER_ROLE')")
public class SubjectService implements UserDetailsService{

	@Autowired
	SubjectRepository subRepo;

	@Autowired
	UserGroupRepository groupRepo;

	@Autowired
	private BCryptPasswordEncoder encoder;

	public Optional<Subject> getUserDetails(String username) {

		Optional<Subject> user=subRepo.findByUsername(username);

		return user;
	}

	public Subject saveUserDetails(Subject subject) {

		Set<UserGroup> group=subject.getUserGroup();
		
		groupRepo.saveAll(group);

		Subject user = new Subject();
		user.setUsername(subject.getUsername());
		user.setPassword(
				encoder.encode(
						subject.getPassword()
						)
				);

		user.setUserGroup(group);
		return subRepo.save(user);	
	}

	public void deleteUserDetails() { 

		SecurityContext securityContext = SecurityContextHolder.getContext();
	    String username=securityContext.getAuthentication().getName();
	    
		Optional<Subject> user=subRepo.findByUsername(username);

		if(user.isPresent()) {
			subRepo.delete(user.get());
		}
		else {
			throw new UsernameNotFoundException("no user exists with this username");
		}
	    
	    //subRepo.delete(user.get());
	}
	
	public void updateUsername(String oldUser, String newUser) {
		
		Optional<Subject> user=subRepo.findByUsername(oldUser);
		
		if(user.isPresent()) {
			user.get().setUsername(newUser);
		}
		else {
			throw new UsernameNotFoundException("no user exists with this username");
		}
		
		subRepo.updateDetails(oldUser,newUser);	
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<Subject> user=subRepo.findByUsername(username);

		if(!user.isPresent()) {
			throw new UsernameNotFoundException("User doesn't exost");
		}

		return new org.springframework.security.core.userdetails.User(username,user.get().getPassword(),user.get()
				.getUserGroup().stream()
				.map(userGroup->new SimpleGrantedAuthority(user.get().getUserGroup().getClass().getName()))
				.collect(Collectors.toList()));		
	}
}
