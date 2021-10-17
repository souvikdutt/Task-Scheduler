package com.epam.taskscheduler.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.springframework.lang.NonNull;

import com.epam.taskscheduler.model.Notes;

public class NotesDTO {
	
	int id;
	@NonNull
	@NotBlank
	@Length(max = 50, min = 3)
	String note;
	int taskId;
	
	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	public NotesDTO() {
		
	}
	public NotesDTO(String note) {
		super();
		this.note = note;
	}
	
}
