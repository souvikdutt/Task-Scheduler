package com.epam.taskscheduler.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.taskscheduler.exception.NotesNotFoundException;
import com.epam.taskscheduler.exception.SlotNotAvailableException;
import com.epam.taskscheduler.exception.TaskNotFoundException;
import com.epam.taskscheduler.model.Notes;
import com.epam.taskscheduler.model.Task;
import com.epam.taskscheduler.repository.NoteRepository;
import com.epam.taskscheduler.repository.TaskRepository;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

	@InjectMocks
	TaskServiceImpl service;
	@Mock
	TaskRepository taskRepo;
	@Mock
	NoteRepository noteRepo;

	private Task task;
	private Notes note;
	private DateTimeFormatter dateFormat;
	private List<Task> tasks;
	private List<Notes> notes;

	@BeforeEach
	void setup() {
		this.dateFormat = DateTimeFormatter.ofPattern("yyyy-M-d");
		this.tasks = new ArrayList<>();
		this.task = new Task(1, LocalDate.parse("2021-08-15", dateFormat), "Java", LocalTime.parse("10:00"),
				LocalTime.parse("12:00"));
		this.tasks.add(this.task);

		this.notes = new ArrayList<Notes>();
		this.note = new Notes("Service testing");
		this.notes.add(this.note);
		this.task.setNotes(notes);
	}

	@Test
	public void createTask() throws SlotNotAvailableException {
		when(taskRepo.save(Mockito.any(Task.class))).thenReturn(this.task);
		when(taskRepo.findAllByDate(Mockito.any(LocalDate.class))).thenReturn(List.of());
		assertEquals(this.task, service.addNewTask(LocalDate.parse("2021-08-15", dateFormat), this.task));
		verify(taskRepo, atLeastOnce()).save(Mockito.any(Task.class));
	}

	@Test
	void createTask_invalid() {
		when(taskRepo.findAllByDate(Mockito.any(LocalDate.class))).thenReturn(this.tasks);
		assertThrows(SlotNotAvailableException.class,
				() -> service.addNewTask(LocalDate.parse("2021-08-15", dateFormat), this.task));
	}

	@Test
	public void getAllTask() {
		when(taskRepo.findAll()).thenReturn(this.tasks);
		assertEquals("Java", service.displayAllTask().get(0).getTaskTitle());
		assertTrue(service.displayAllTask().size() > 0);
		verify(taskRepo, atLeastOnce()).findAll();
	}

	@Test
	public void getAllTask_invalid() {
		when(taskRepo.findAll()).thenThrow(TaskNotFoundException.class);
		assertThrows(TaskNotFoundException.class, () -> service.displayAllTask());
		verify(taskRepo, atLeastOnce()).findAll();
	}

	@Test
	public void getAllTaskByDate() throws TaskNotFoundException {
		when(taskRepo.findAllByDate(Mockito.any(LocalDate.class))).thenReturn(this.tasks);
		assertEquals(this.tasks, service.fetchAllTask(LocalDate.parse("2021-08-15", dateFormat)));
		verify(taskRepo, atLeastOnce()).findAllByDate(Mockito.any(LocalDate.class));
	}

	@Test
	void getAllTaskByDate_invalid() {
		when(taskRepo.findAllByDate(Mockito.any(LocalDate.class))).thenReturn(List.of());
		assertThrows(TaskNotFoundException.class,
				() -> service.fetchAllTask(LocalDate.parse("2021-08-15", dateFormat)));
	}

	@Test
	public void fetchTaskById() {
		Optional<Task> returnValue = Optional.of((Task) this.task);
		Mockito.<Optional<Task>>when(taskRepo.findById(Mockito.anyInt())).thenReturn(returnValue);
		assertEquals(this.task, service.fetchParticularTask(1));
		verify(taskRepo, atLeastOnce()).findById(Mockito.anyInt());
	}

	@Test
	void fetchTaskById_invalid() {
		when(taskRepo.findById(Mockito.anyInt())).thenThrow(TaskNotFoundException.class);
		assertThrows(TaskNotFoundException.class, () -> service.fetchParticularTask(1));
	}

	@Test
	void updateTask() throws SlotNotAvailableException {
		Optional<Task> returnValue = Optional.of((Task) this.task);
		Mockito.<Optional<Task>>when(taskRepo.findById(Mockito.anyInt())).thenReturn(returnValue);
		when(taskRepo.findAllByDate(Mockito.any(LocalDate.class))).thenReturn(this.tasks);
		when(taskRepo.save(Mockito.any(Task.class))).thenReturn(this.task);
		assertEquals(this.task, service.updateATask(LocalDate.parse("2021-08-15", dateFormat), this.task));
	}
	
	@Test
	void isSlotAvailableForUpdateATask() throws SlotNotAvailableException {
		when(taskRepo.findAllByDate(Mockito.any(LocalDate.class))).thenReturn(this.tasks);
		assertFalse(service.isSlotAvailableForUpdateATask(LocalDate.parse("2021-08-15", dateFormat), 2,
				 LocalTime.parse("09:00"),
					LocalTime.parse("13:00")));
	}
	
	@Test
	void isSlotAvailable() throws SlotNotAvailableException {
		when(taskRepo.findAllByDate(Mockito.any(LocalDate.class))).thenReturn(this.tasks);
		assertFalse(service.isSlotAvailable(LocalDate.parse("2021-08-15", dateFormat),
				 LocalTime.parse("09:00"),
					LocalTime.parse("13:00")));
	}

	@Test
	public void deleteTaskById() {
		when(taskRepo.existsById(Mockito.anyInt())).thenReturn(true);
		doNothing().when(taskRepo).deleteById(Mockito.anyInt());
		assertTrue(service.deleteATask(1));
		verify(taskRepo, atLeastOnce()).existsById(Mockito.anyInt());
	}

	@Test
	public void deleteTaskById_invalid() {
		when(taskRepo.existsById(Mockito.anyInt())).thenReturn(false);
		assertThrows(TaskNotFoundException.class, () -> service.deleteATask(1));
	}

	@Test
	public void addNote() {
		Optional<Task> returnValue = Optional.of((Task) this.task);
		Mockito.<Optional<Task>>when(taskRepo.findById(Mockito.anyInt())).thenReturn(returnValue);
		when(taskRepo.save(this.task)).thenReturn(this.task);
		assertEquals("Java", service.addNote(1, "Java"));
	}

	@Test
	void addNote_invalid() throws TaskNotFoundException {
		when(taskRepo.findById(Mockito.anyInt())).thenThrow(TaskNotFoundException.class);
		assertThrows(TaskNotFoundException.class, () -> service.addNote(2, "Service testing"));
	}

	@Test
	void fetchNotes() {
		when(noteRepo.findAllByTaskId(Mockito.anyInt())).thenReturn(notes);
		assertTrue(service.fetchNotes(1).size() > 0);
	}

	@Test
	void modifyAnoteForATask() throws TaskNotFoundException, NotesNotFoundException {
		Optional<Notes> returnValue = Optional.of((Notes) this.note);
		Mockito.<Optional<Notes>>when(noteRepo.findById(Mockito.anyInt())).thenReturn(returnValue);
		when(noteRepo.save(Mockito.any(Notes.class))).thenReturn(this.note);
		assertTrue(service.modifyAnoteForATask(1, "Mockito"));
	}

	@Test
	void modifyAnoteForATask_invalid() {
		when(noteRepo.findById(Mockito.anyInt())).thenThrow(NotesNotFoundException.class);
		assertThrows(NotesNotFoundException.class, () -> service.modifyAnoteForATask(1, "Souvik"));
		verify(noteRepo, atLeastOnce()).findById(Mockito.anyInt());
	}

	@Test
	void deleteAllNoteOfATask() {
		doNothing().when(noteRepo).deleteAllByTaskId(Mockito.anyInt());
		assertTrue(service.deleteAllNoteOfATask(1));
	}

	@Test
	void deleteAnoteForATask() {
		Optional<Task> returnTask = Optional.of((Task) this.task);
		Mockito.<Optional<Task>>when(taskRepo.findById(Mockito.anyInt())).thenReturn(returnTask);
		Optional<Notes> returnNote = Optional.of((Notes) this.note);
		Mockito.<Optional<Notes>>when(noteRepo.findById(Mockito.anyInt())).thenReturn(returnNote);
		doNothing().when(noteRepo).deleteById(Mockito.anyInt());
		assertTrue(service.deleteAnoteForATask(1, 1));
	}

	@Test
	void deleteAnoteForATask_invalid1() {
		Optional<Task> returnTask = Optional.of((Task) this.task);
		Mockito.<Optional<Task>>when(taskRepo.findById(Mockito.anyInt())).thenReturn(returnTask);
		when(noteRepo.findById(Mockito.anyInt())).thenThrow(NotesNotFoundException.class);
		assertThrows(NotesNotFoundException.class, () -> service.deleteAnoteForATask(1, 1));
	}

	@Test
	void deleteAnoteForATask_invalid2() {
		when(taskRepo.findById(Mockito.anyInt())).thenThrow(TaskNotFoundException.class);
		assertThrows(TaskNotFoundException.class, () -> service.deleteAnoteForATask(1, 1));
	}

}
