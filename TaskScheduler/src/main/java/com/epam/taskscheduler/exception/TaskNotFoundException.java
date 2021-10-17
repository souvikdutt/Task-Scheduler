package com.epam.taskscheduler.exception;

public class TaskNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public TaskNotFoundException() {
		super("Task Not found");
	}
	public TaskNotFoundException(String message) {
		super(message);
	}
	
}
