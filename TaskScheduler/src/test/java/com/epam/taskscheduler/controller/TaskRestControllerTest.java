package com.epam.taskscheduler.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.epam.taskscheduler.dto.TaskDTO;
import com.epam.taskscheduler.exception.SlotNotAvailableException;
import com.epam.taskscheduler.exception.TaskNotFoundException;
import com.epam.taskscheduler.model.Task;
import com.epam.taskscheduler.service.TaskServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest
public class TaskRestControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	TaskServiceImpl taskService;
	
	private Task task;
	private DateTimeFormatter dateFormat;
	private List<Task> tasks;
	private ObjectMapper mapper;
	
	@BeforeEach
	void setup() {
		mapper = new ObjectMapper();
		mapper.findAndRegisterModules();
		this.dateFormat = DateTimeFormatter.ofPattern("yyyy-M-d");
		this.tasks = new ArrayList<>();
		this.task = new Task(1, LocalDate.parse("2021-08-22", dateFormat), "Java", LocalTime.parse("10:00:00"), LocalTime.parse("12:00:00"));
		this.tasks.add(this.task);
	}
	
	@Test
	void createTask() {
		assertDoesNotThrow(()-> {
				when(taskService.addNewTask(Mockito.any(LocalDate.class), Mockito.any(Task.class)))
				.thenReturn(this.task);
				
				RequestBuilder reqBuilder = MockMvcRequestBuilders
						.post("/tasks")
						.content(mapper.writeValueAsString(task))
						.contentType(MediaType.APPLICATION_JSON);
				
				mockMvc.perform(reqBuilder)
				.andExpect(jsonPath("$.taskId", is(1)))
				.andExpect(jsonPath("$.date", is("2021-08-22")))
				.andExpect(jsonPath("$.taskTitle", is("Java")))
				.andExpect(jsonPath("$.startTime", is("10:00:00")))
				.andExpect(jsonPath("$.endTime", is("12:00:00")));
			}
		);
	}
	
	@Test
	void createTask_invalid() throws Exception {
		
		when(taskService.addNewTask(Mockito.any(LocalDate.class), Mockito.any(Task.class)))
		.thenThrow(SlotNotAvailableException.class);
	
		RequestBuilder reqBuilder = MockMvcRequestBuilders
				.post("/tasks")
				.content(mapper.writeValueAsString(task))
				.contentType(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(reqBuilder)
		.andExpect(status().isConflict());
			
	}
	
	@Test
	void findATask() {
		assertDoesNotThrow(() -> {
			when(taskService.fetchParticularTask(Mockito.anyInt())).thenReturn(this.task);
			RequestBuilder reqBuilder = MockMvcRequestBuilders
					.get("/tasks/1")
					.content(mapper.writeValueAsString(task))
					.contentType(MediaType.APPLICATION_JSON);
			mockMvc.perform(reqBuilder)
			.andExpect(jsonPath("$.taskId", is(1)));
		});
	}
	
	@Test
	void findATask_invalid() {
		assertDoesNotThrow(() -> {
			when(taskService.fetchParticularTask(Mockito.anyInt())).thenThrow(TaskNotFoundException.class);
			RequestBuilder reqBuilder = MockMvcRequestBuilders
					.get("/tasks/1")
					.content(mapper.writeValueAsString(task))
					.contentType(MediaType.APPLICATION_JSON);
			mockMvc.perform(reqBuilder)
			.andExpect(status().isNotFound());
		});
	}
	
	@Test
	void findTaskByDate() {
		assertDoesNotThrow(() -> {
			when(taskService.fetchAllTask(Mockito.any(LocalDate.class))).thenReturn(List.of(task));
			RequestBuilder reqBuilder = MockMvcRequestBuilders
					.get("/tasks/task/2021-08-19")
					.content(mapper.writeValueAsString(tasks))
					.contentType(MediaType.APPLICATION_JSON);
			mockMvc.perform(reqBuilder)
			.andExpect(jsonPath("$[0].taskId", is(1)))
			.andExpect(jsonPath("$[0].date", is("2021-08-22")))
			.andExpect(jsonPath("$[0].taskTitle", is("Java")))
			.andExpect(jsonPath("$[0].startTime", is("10:00:00")))
			.andExpect(jsonPath("$[0].endTime", is("12:00:00")));
		});
	}
	
	@Test
	void findTaskByDate_invalid() {
		assertDoesNotThrow(() -> {
			when(taskService.fetchAllTask(Mockito.any(LocalDate.class))).thenThrow(TaskNotFoundException.class);
			RequestBuilder reqBuilder = MockMvcRequestBuilders
					.get("/tasks/task/2021-08-19")
					.content(mapper.writeValueAsString(tasks))
					.contentType(MediaType.APPLICATION_JSON);
			mockMvc.perform(reqBuilder)
			.andExpect(status().isNotFound());
		});
	}
	
	@Test
	public void deleteTask() {
		assertDoesNotThrow(() -> {
			Mockito.when(taskService.deleteATask(Mockito.anyInt()))
							.thenReturn(true);
			RequestBuilder reqBuilder = MockMvcRequestBuilders
							.delete("/tasks/1")
							.contentType(MediaType.APPLICATION_JSON);
			mockMvc.perform(reqBuilder)
					.andExpect(status().isOk());
		});
	}
	
	@Test
	public void deleteTask_invalid() {
		assertDoesNotThrow(() -> {
			Mockito.when(taskService.deleteATask(Mockito.anyInt()))
							.thenThrow(TaskNotFoundException.class);
			RequestBuilder reqBuilder = MockMvcRequestBuilders
							.delete("/tasks/1")
							.contentType(MediaType.APPLICATION_JSON);
			mockMvc.perform(reqBuilder)
					.andExpect(status().isNotFound());
		});
	}
	
	@Test
	void updateTask() {
		assertDoesNotThrow(()-> {
				when(taskService.updateATask(Mockito.any(LocalDate.class), Mockito.any(Task.class)))
				.thenReturn(this.task);
				
				RequestBuilder reqBuilder = MockMvcRequestBuilders
						.put("/tasks")
						.content(mapper.writeValueAsString(task))
						.contentType(MediaType.APPLICATION_JSON);
				
				mockMvc.perform(reqBuilder)
				.andExpect(jsonPath("$.taskId", is(1)))
				.andExpect(jsonPath("$.date", is("2021-08-22")))
				.andExpect(jsonPath("$.taskTitle", is("Java")))
				.andExpect(jsonPath("$.startTime", is("10:00:00")))
				.andExpect(jsonPath("$.endTime", is("12:00:00")));
			}
		);
	}
	
	@Test
	void updateTask_invalid() {
		assertDoesNotThrow(()-> {
				when(taskService.updateATask(Mockito.any(LocalDate.class), Mockito.any(Task.class)))
				.thenThrow(SlotNotAvailableException.class);
				
				RequestBuilder reqBuilder = MockMvcRequestBuilders
						.put("/tasks")
						.content(mapper.writeValueAsString(task))
						.contentType(MediaType.APPLICATION_JSON);
				
				mockMvc.perform(reqBuilder)
				.andExpect(status().isConflict());
			}
		);
	}
	
	@Test
	void fetchAllTasks() {
		assertDoesNotThrow(() -> {
			TaskDTO taskDto = new TaskDTO(task);
			when(taskService.displayAllTask()).thenReturn(List.of(taskDto));
			RequestBuilder reqBuilder = MockMvcRequestBuilders
					.get("/tasks")
					.content(mapper.writeValueAsString(tasks))
					.contentType(MediaType.APPLICATION_JSON);
			mockMvc.perform(reqBuilder)
			.andExpect(jsonPath("$[0].taskId", is(1)))
			.andExpect(jsonPath("$[0].date", is("2021-08-22")))
			.andExpect(jsonPath("$[0].taskTitle", is("Java")))
			.andExpect(jsonPath("$[0].startTime", is("10:00:00")))
			.andExpect(jsonPath("$[0].endTime", is("12:00:00")));
		});
	}
}
