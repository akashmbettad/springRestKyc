package com.signicat.assignment.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.signicat.assignment.domain.UserGroup;

@Repository
public interface UserGroupRepository extends CrudRepository<UserGroup,Integer>{

	@Query(name="select a from user_group a where a.name=:name")
	public UserGroup findByName(@Param("name") String name);
	
}
