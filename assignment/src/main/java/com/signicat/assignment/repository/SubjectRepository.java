package com.signicat.assignment.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.signicat.assignment.domain.Subject;

@Repository
public interface SubjectRepository extends CrudRepository<Subject,Integer>{

	@Query("SELECT a FROM Subject a WHERE a.username = :username")
	public Optional<Subject> findByUsername(@Param("username") String username);

	 @Transactional
	 @Modifying
	@Query("UPDATE  Subject a SET a.username=:newUsername WHERE a.username = :oldUsername")
	public void updateDetails(@Param("oldUsername") String oldUsername
							,@Param("newUsername") String newUsername );

	//public Subject save(Optional<Subject> user);
}
