package com.signicat.assignment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.signicat.assignment.domain.UserGroup;
import com.signicat.assignment.repository.UserGroupRepository;

@Service
public class UserGroupService {

	@Autowired
	UserGroupRepository repo;
	
	public UserGroup getUserGroup(String name) {
		
		UserGroup group=repo.findByName(name);
		
		return group;
	}
}
