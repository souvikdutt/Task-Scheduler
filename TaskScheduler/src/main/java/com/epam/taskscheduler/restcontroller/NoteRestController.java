package com.epam.taskscheduler.restcontroller;

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

import com.epam.taskscheduler.dto.NotesDTO;
import com.epam.taskscheduler.exception.NotesNotFoundException;
import com.epam.taskscheduler.model.Notes;
import com.epam.taskscheduler.service.TaskServiceImpl;

@RestController
public class NoteRestController {

	@Autowired
	TaskServiceImpl taskService;
	
	@GetMapping("/notes/{taskId}")
	public ResponseEntity<List<Notes>> getNotes(@PathVariable int taskId) {
		return new ResponseEntity<List<Notes>>(taskService.fetchNotes(taskId), HttpStatus.FOUND);
	}
	
	@PostMapping("/notes/note")
	public ResponseEntity<String> addNotes(@Valid @RequestBody NotesDTO note) {
		return new ResponseEntity<String>(taskService.addNote(note.getTaskId(), note.getNote()), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/notes/{taskId}/{Id}")
	public void deleteNote( @PathVariable int taskId, @PathVariable int Id) throws NotesNotFoundException {
		taskService.deleteAnoteForATask(taskId, Id);
	}
	
	@PutMapping("/notes")
	public void updateNote(@Valid @RequestBody Notes note) throws NotesNotFoundException {
		taskService.modifyAnoteForATask(note.getId(), note.getNote());
	}
	
}
