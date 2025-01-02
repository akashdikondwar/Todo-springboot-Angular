package com.akash.rest.webservices.restful_web_services.TodoController;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;


@Entity
public class Todo {
	
	@Id
	@GeneratedValue
	private Long id;
	private String username;
	private String description;
	private Date date;
	private Boolean isDone;
	
	public Todo() {
		
	}
	
	public Todo(Long id,String username, String description, Date date, Boolean isDone) {
		this.id = id;
		this.username = username;
		this.description=description;
		this.date = date;
		this.isDone = isDone;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Boolean getIsDone() {
		return isDone;
	}

	public void setIsDone(Boolean isDone) {
		this.isDone = isDone;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username= username;
	}
}
