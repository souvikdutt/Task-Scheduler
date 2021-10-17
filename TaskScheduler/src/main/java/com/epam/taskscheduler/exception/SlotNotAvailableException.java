package com.epam.taskscheduler.exception;

public class SlotNotAvailableException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public SlotNotAvailableException() {
		super("Time slot is already alloted. Please choose another slot.");
	}
	public SlotNotAvailableException(String msg) {
		super(msg);
	}
}
