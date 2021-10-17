package com.epam.taskscheduler.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "tasks")
public class Task {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "task_id")
	private int taskId;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@FutureOrPresent
	private LocalDate date;
	@NonNull
	@NotBlank
	@Length(min = 3, max=20)
	private String taskTitle;
	@DateTimeFormat(pattern = "HH:mm")
//	@FutureOrPresent
	private LocalTime startTime;
	@DateTimeFormat(pattern = "HH:mm")
//	@FutureOrPresent
	private LocalTime endTime;
	@JsonIgnore
	@OneToMany(mappedBy = "task",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Notes> notes;
	
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
	public String getTaskTitle() {
		return taskTitle;
	}
	public void setTaskTitle(String taskTitle) {
		this.taskTitle = taskTitle;
	}
	public LocalTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}
	public LocalTime getEndTime() {
		return endTime;
	}
	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}
	public List<Notes> getNotes() {
		return notes;
	}
	public void setNotes(List<Notes> notes) {
		notes.forEach(note ->{
			note.setTask(this);
		});
		this.notes = notes;
	}
	
	public Task() {
		
	}
	
	public Task(int taskId, String taskTitle, LocalTime startTime, LocalTime endTime) {
		super();
		this.taskId = taskId;
		this.taskTitle = taskTitle;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	public Task(int taskId, LocalDate date, String taskTitle, LocalTime startTime, LocalTime endTime) {
		super();
		this.taskId = taskId;
		this.date = date;
		this.taskTitle = taskTitle;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	public Task(String taskTitle, LocalDate date, LocalTime startTime, LocalTime endTime) {
		super();
		this.taskTitle = taskTitle;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	public Task(String taskTitle, LocalTime startTime, LocalTime endTime) {
		super();
		this.taskTitle = taskTitle;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	@Override
	public String toString() {
		return "TaskSchedulerDTO [taskId=" + taskId + ", date=" + date + ", taskTitle=" + taskTitle + ", startTime="
				+ startTime + ", endTime=" + endTime + "]";
	}
	
}
