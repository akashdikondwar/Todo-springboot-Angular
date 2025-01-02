package com.akash.rest.webservices.restful_web_services.TodoController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akash.rest.webservices.restful_web_services.springDataJpa.TodoJpaRepository;

@Service
public class JpaTodoService {
	
	@Autowired
	public TodoJpaRepository todoJpaRepository;
	
	private static Long counter=(long) 0;
	private static ArrayList<Todo> todos = new ArrayList<>();
	
	public List<Todo> getAllTodos(){
		return todoJpaRepository.findAll();
	}
	
	
	public void removeTodo(String username, Long id) {
		 todoJpaRepository.deleteById(id);
	}
	
	public Todo getTodoById(Long id) {
		return todoJpaRepository.findById(id).get();
	}

	public Todo saveTodo(String username, Todo todo) {
		// TODO Auto-generated method stub
		
		todoJpaRepository.save(todo);
		return todo;
	}
	
	public Todo updateTodo(String username, Long id, Todo todo) {
		
		todoJpaRepository.deleteById(id);

//		todos.remove(getTodoById(id));
//		todos.add(todo);
		todoJpaRepository.save(todo);
		return todo;
	}
	
}
