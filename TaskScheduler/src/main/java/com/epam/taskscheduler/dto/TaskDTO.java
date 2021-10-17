package com.epam.taskscheduler.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

import com.epam.taskscheduler.model.Task;


public class TaskDTO {
	
	private int taskId;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@FutureOrPresent
	private LocalDate date;
	@NonNull
	@NotBlank
	@Length(min = 3, max=20)
	private String taskTitle;
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime startTime;
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime endTime;
	
	public TaskDTO(Task task) {
        this.taskId = task.getTaskId();
        this.date = task.getDate();
        this.taskTitle = task.getTaskTitle();
        this.startTime = task.getStartTime();
        this.endTime = task.getEndTime();
    }

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
	
	
}
