package com.epam.taskscheduler.exception;

public class NotesNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 5406966698247277649L;
	public NotesNotFoundException() {
		super("No note found on this ID");
	}
	public NotesNotFoundException(String message) {
		super(message);
	}
}