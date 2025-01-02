package com.akash.rest.webservices.restful_web_services.TodoController;

import java.util.Date;

public class Todo {
	private long id;
	private String description;
	private Date date;
	private Boolean isDone;
	
	public Todo(long id, String description, Date date, Boolean isDone) {
		this.id = id;
		this.description=description;
		this.date = date;
		this.isDone = isDone;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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
}
