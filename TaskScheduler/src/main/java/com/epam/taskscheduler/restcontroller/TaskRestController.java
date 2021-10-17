package com.epam.taskscheduler.restcontroller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.epam.taskscheduler.dto.TaskDTO;
import com.epam.taskscheduler.exception.SlotNotAvailableException;
import com.epam.taskscheduler.exception.TaskNotFoundException;
import com.epam.taskscheduler.model.Task;
import com.epam.taskscheduler.service.TaskServiceImpl;

@RestController
public class TaskRestController {
	
	@Autowired
	TaskServiceImpl taskService;
	
	@GetMapping("/tasks")
	public ResponseEntity<List<TaskDTO>> fetchAllTasks() {
		return new ResponseEntity<List<TaskDTO>>(taskService.displayAllTask(), HttpStatus.FOUND);
	}
	
	@GetMapping("/tasks/{taskId}")
	public ResponseEntity<Task> getTask(@PathVariable int taskId) throws TaskNotFoundException {
		return new ResponseEntity<Task>(taskService.fetchParticularTask(taskId), HttpStatus.FOUND);
	}
	
	@PostMapping("/tasks")
	public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) throws SlotNotAvailableException {
		return new ResponseEntity<Task>(taskService.addNewTask(task.getDate(), task), HttpStatus.CREATED);
	}
	
	@PutMapping("/tasks")
	public ResponseEntity<Task> updateTask(@Valid @RequestBody Task task) throws SlotNotAvailableException {
		return new ResponseEntity<Task>(taskService.updateATask(task.getDate(), task), HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/tasks/{taskId}")
	public void deleteTask(@PathVariable int taskId) {
		taskService.deleteATask(taskId);
	}
	
	@GetMapping("/tasks/task/{date}")
	public ResponseEntity<List<Task>> getTasksByDate(@Valid @PathVariable String date) {
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-M-d");
		return new ResponseEntity<List<Task>>(taskService.fetchAllTask(LocalDate.parse(date, dateFormat)), HttpStatus.FOUND);
	}
	
}
