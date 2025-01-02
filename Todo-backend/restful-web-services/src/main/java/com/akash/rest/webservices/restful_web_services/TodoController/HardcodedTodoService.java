package com.akash.rest.webservices.restful_web_services.TodoController;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:4200")
@Service
public class HardcodedTodoService {
	
	private static long counter=0;
	private static ArrayList<Todo> todos = new ArrayList<>();
	
	static {
		todos.add(new Todo(++counter, "Learn Angular", new Date(), false) );
		todos.add(new Todo(++counter, "Learn React", new Date(), false) );
		todos.add(new Todo(++counter, "Learn Python", new Date(), false) );
		todos.add(new Todo(++counter, "Learn Javascript", new Date(), false) );

	}
	
	public ArrayList<Todo> getAllTodos(){
		return todos;
	}
	
	public Todo removeTodo(String username, long id) {
		for(Todo todo: todos) {
			if(todo.getId() == id ) {
				todos.remove(todo);
				return todo;
			}
		}
		return null;
	}
	
	public Todo getTodoById(long id) {
		for(Todo todo: todos) {
			if(todo.getId() == id)
				return todo;
		}
		return null;
	}

	public Todo saveTodo(String username, Todo todo) {
		// TODO Auto-generated method stub
		todo.setId(++counter);
		todos.add(todo);
		return todo;
	}
	
	public Todo updateTodo(String username, long id, Todo todo) {
		todos.remove(findTodoById(id));
		todos.add(todo);
		return todo;
	}
	
	public Todo findTodoById(long id) {
		for(Todo todo: todos) {
			if(todo.getId()==id)
				return todo;
		}
		return null;
	}
	
}
