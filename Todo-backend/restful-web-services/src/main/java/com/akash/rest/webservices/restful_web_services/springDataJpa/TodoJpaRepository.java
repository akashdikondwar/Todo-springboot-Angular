package com.akash.rest.webservices.restful_web_services.springDataJpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.akash.rest.webservices.restful_web_services.TodoController.Todo;

@Repository
public interface TodoJpaRepository extends JpaRepository<Todo, Long>{
	
//	List<Todo> findByUsername();
}
