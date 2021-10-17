package com.epam.taskscheduler.restcontroller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.epam.taskscheduler.exception.NotesNotFoundException;
import com.epam.taskscheduler.exception.SlotNotAvailableException;
import com.epam.taskscheduler.exception.TaskNotFoundException;
import com.epam.taskscheduler.model.ErrorResponse;

@RestControllerAdvice
public class AllRestExceptionHandler {

	@ExceptionHandler(TaskNotFoundException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public ErrorResponse taskNotFoundException(TaskNotFoundException e, WebRequest request) {
		
		ErrorResponse message = new ErrorResponse(LocalDateTime.now(),HttpStatus.NOT_FOUND, e.getMessage(),request.getContextPath());
		
		return message;
		
	}
	
	@ExceptionHandler(NotesNotFoundException.class)
	public ResponseEntity<ErrorResponse> notesNotFoundException(NotesNotFoundException e, WebRequest request) {
		
		ErrorResponse message = new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND, e.getMessage(),request.getContextPath());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
		
	}
	
	@ExceptionHandler(SlotNotAvailableException.class)
	public ResponseEntity<ErrorResponse> slotNotAvailableException(SlotNotAvailableException e, WebRequest request) {
		
		ErrorResponse message = new ErrorResponse(LocalDateTime.now(),HttpStatus.CONFLICT, e.getMessage(),request.getContextPath());
		
		return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
		
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ErrorResponse invalidArgumentHandler(MethodArgumentNotValidException exception, WebRequest request) {
		
		StringBuilder message = new StringBuilder();
		exception.getAllErrors().forEach(err -> message.append(err.getDefaultMessage()).append(", "));
		ErrorResponse response = new ErrorResponse(LocalDateTime.now(),HttpStatus.BAD_REQUEST, message.toString(),request.getContextPath());
		return response;
	}
	
}
