package com.epam.taskscheduler.service;

import java.time.LocalDate;
import java.util.List;

import com.epam.taskscheduler.dto.TaskDTO;
import com.epam.taskscheduler.exception.NotesNotFoundException;
import com.epam.taskscheduler.exception.SlotNotAvailableException;
import com.epam.taskscheduler.exception.TaskNotFoundException;
import com.epam.taskscheduler.model.Notes;
import com.epam.taskscheduler.model.Task;

public interface TaskService {
	public Task addNewTask(LocalDate date, Task task) throws SlotNotAvailableException;
	public List<Task> fetchAllTask(LocalDate date) throws TaskNotFoundException;
	public Task fetchParticularTask(int taskId) throws TaskNotFoundException;
	public List<TaskDTO> displayAllTask();
	public String addNote(int taskId, String note);
	public List<Notes> fetchNotes(int taskId);
	public boolean modifyAnoteForATask(int choice, String noteToAdd) throws NotesNotFoundException;
	public boolean deleteAnoteForATask(int taskId, int choice) throws NotesNotFoundException;
	public boolean deleteAllNoteOfATask(int taskId);
	public boolean deleteATask(int taskId) throws TaskNotFoundException;
	public Task updateATask(LocalDate date, Task taskToUpdate) throws SlotNotAvailableException;
}
