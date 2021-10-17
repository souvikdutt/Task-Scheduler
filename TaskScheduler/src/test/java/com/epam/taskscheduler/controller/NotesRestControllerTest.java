package com.epam.taskscheduler.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.epam.taskscheduler.exception.NotesNotFoundException;
import com.epam.taskscheduler.exception.TaskNotFoundException;
import com.epam.taskscheduler.model.Notes;
import com.epam.taskscheduler.model.Task;
import com.epam.taskscheduler.service.TaskServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest
public class NotesRestControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private TaskServiceImpl taskService;
	
	private Notes note1, note2;
	private ObjectMapper mapper;
	
	@BeforeEach
	void setup() {
		mapper = new ObjectMapper();
		note1 = new Notes(1,"Java",new Task());
		note2 = new Notes(2,"Kotlin",new Task());
	}
	
	@Test
	void getAllNotes() {
		assertDoesNotThrow(() -> {
			Mockito.when(taskService.fetchNotes(Mockito.anyInt())).thenReturn(List.of(note1,note2));
			RequestBuilder reqBuilder = MockMvcRequestBuilders.get("/notes/1");
			mockMvc.perform(reqBuilder)
					.andExpect(jsonPath("$[0].id", is(1)))
					.andExpect(jsonPath("$[0].note", is("Java")))
					.andExpect(jsonPath("$[1].id", is(2)))
					.andExpect(jsonPath("$[1].note", is("Kotlin")));
		});
	}
	
	@Test
	public void addNote() {
		assertDoesNotThrow(() -> {
			Mockito.when(taskService.addNote(Mockito.anyInt(),Mockito.anyString()))
							.thenReturn(note1.getNote());
			RequestBuilder reqBuilder = MockMvcRequestBuilders
							.post("/notes/note")
							.content(mapper.writeValueAsString(note1))
							.contentType(MediaType.APPLICATION_JSON);
			mockMvc.perform(reqBuilder)
					.andExpect(content().string(is("Java")));
		});
	}
	
	@Test
	public void addNote_invalid() {
		assertDoesNotThrow(() -> {
			Mockito.when(taskService.addNote(Mockito.anyInt(),Mockito.anyString()))
							.thenThrow(TaskNotFoundException.class);
			RequestBuilder reqBuilder = MockMvcRequestBuilders
							.post("/notes/note")
							.content(mapper.writeValueAsString(note1))
							.contentType(MediaType.APPLICATION_JSON);
			mockMvc.perform(reqBuilder)
					.andExpect(status().isNotFound());
		});
	}
	
	@Test
	public void addNote_invalid2() {
		assertDoesNotThrow(() -> {
			Mockito.when(taskService.addNote(Mockito.anyInt(),Mockito.anyString()))
							.thenThrow(TaskNotFoundException.class);
			RequestBuilder reqBuilder = MockMvcRequestBuilders
							.post("/notes/note")
							.content(mapper.writeValueAsString(""))
							.contentType(MediaType.APPLICATION_JSON);
			mockMvc.perform(reqBuilder)
					.andExpect(status().isBadRequest());
		});
	}
	
	@Test
	public void putNote() {
		assertDoesNotThrow(() -> {
			Mockito.when(taskService.modifyAnoteForATask(Mockito.anyInt(),Mockito.anyString()))
							.thenReturn(true);
			RequestBuilder reqBuilder = MockMvcRequestBuilders
							.put("/notes")
							.content(mapper.writeValueAsString(note1))
							.contentType(MediaType.APPLICATION_JSON);
			mockMvc.perform(reqBuilder)
							.andExpect(status().isOk());
		});
	}
	
	@Test
	public void putNote_invalid() {
		assertDoesNotThrow(() -> {
			Mockito.when(taskService.modifyAnoteForATask(Mockito.anyInt(),Mockito.anyString()))
							.thenThrow(NotesNotFoundException.class);
			RequestBuilder reqBuilder = MockMvcRequestBuilders
							.put("/notes")
							.content(mapper.writeValueAsString(note1))
							.contentType(MediaType.APPLICATION_JSON);
			mockMvc.perform(reqBuilder)
							.andExpect(status().isNotFound());
		});
	}
	
	@Test
	public void deleteNote() {
		assertDoesNotThrow(() -> {
			Mockito.when(taskService.deleteAnoteForATask(Mockito.anyInt(),Mockito.anyInt()))
							.thenReturn(true);
			RequestBuilder reqBuilder = MockMvcRequestBuilders
							.delete("/notes/1/1")
							.contentType(MediaType.APPLICATION_JSON);
			mockMvc.perform(reqBuilder)
					.andExpect(status().isOk());
		});
	}
	
	@Test
	public void deleteNote_invalid() {
		assertDoesNotThrow(() -> {
			Mockito.when(taskService.deleteAnoteForATask(Mockito.anyInt(),Mockito.anyInt()))
							.thenThrow(NotesNotFoundException.class);
			RequestBuilder reqBuilder = MockMvcRequestBuilders
							.delete("/notes/1/1")
							.contentType(MediaType.APPLICATION_JSON);
			mockMvc.perform(reqBuilder)
					.andExpect(status().isNotFound());
		});
	}
	
}
