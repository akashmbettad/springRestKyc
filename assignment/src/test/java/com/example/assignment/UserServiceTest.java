package com.example.assignment;

import java.util.HashSet;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.signicat.assignment.domain.Subject;
import com.signicat.assignment.domain.UserGroup;
import com.signicat.assignment.repository.SubjectRepository;
import com.signicat.assignment.repository.UserGroupRepository;
import com.signicat.assignment.service.SubjectService;

@RunWith(SpringRunner.class)
@ContextConfiguration
public class UserServiceTest {

	@MockBean
	private SubjectRepository repo;
	
	@MockBean
	private UserGroupRepository groupRepo;
	
	@Autowired
	private SubjectService service;

	
	@Test
	public void givenSubObject_whenSaveSubject_thenReturnSubObject() {
		
		Subject user=new Subject(1,"adi","scln");
		UserGroup group=new UserGroup(1,"ROLE_USER");
		Set<UserGroup> gr=new HashSet<UserGroup>();
		
		gr.add(group);
		user.setUserGroup(gr);
		Mockito.when(groupRepo.findByName(user.getUsername())).thenReturn(group);
		Mockito.when(repo.save(user)).thenReturn(user);

		Subject user1=service.saveUserDetails(user);

		Assertions.assertThat(user1).isNull();
	}
}
