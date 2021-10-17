package com.epam.taskscheduler.model;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public class ErrorResponse {

	private LocalDateTime timeStamp;
	private HttpStatus status;
	private String message;
	private String path;
	
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public ErrorResponse() {
		super();
	}
	public ErrorResponse(LocalDateTime timeStamp, HttpStatus status, String message, String path) {
		super();
		this.timeStamp = timeStamp;
		this.status = status;
		this.message = message;
		this.path = path;
	}
	
}
