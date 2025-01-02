package com.akash.rest.webservices.restful_web_services.TodoController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



//@CrossOrigin(origins = "http://127.0.0.1:4200")
@CrossOrigin(origins = "http://localhost:4200/")
@RestController
public class TodoController {
	
	
	@Autowired
	private HardcodedTodoService hardcodedTodoService;
	
	@Autowired
	private JpaTodoService jpaTodoService;

	@GetMapping("/todos")
	public List<Todo> getAllTodos() {
		return jpaTodoService.getAllTodos();
	}
	
	@DeleteMapping("users/{username}/todos/{id}")
	public void removeTodo(@PathVariable String username, @PathVariable long id ) {
		 jpaTodoService.removeTodo(username, id);
	}
	
	@GetMapping("{id}")
	public Todo getTodoById(@PathVariable long id) {
		return jpaTodoService.getTodoById(id);
	}
	
	@PostMapping("/users/{username}/todo")
	public Todo saveTodo(@PathVariable String username,@RequestBody Todo todo) {
		//TODO: process POST request
		return jpaTodoService.saveTodo( username, todo);
	}
		
	@PutMapping("/users/{username}/todo/{id}")
	public Todo updateTodo(@PathVariable String username, @PathVariable long id,@RequestBody Todo todo) {
		//TODO: process POST request
		return jpaTodoService.updateTodo( username,id, todo);
	}
	
	
	
}
