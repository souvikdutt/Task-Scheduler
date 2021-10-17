package com.epam.taskscheduler.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Notes {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	String note;
	
	@ManyToOne(targetEntity = Task.class)
	@JoinColumn(name = "task_id")
//	@JsonIgnore
	Task task;

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

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}
	
	public Notes() {
		
	}
	public Notes(String note) {
		super();
		this.note = note;
	}

	public Notes(int id, String note, Task task) {
		super();
		this.id = id;
		this.note = note;
		this.task = task;
	}
	
}
