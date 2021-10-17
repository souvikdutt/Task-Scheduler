package com.epam.taskscheduler.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epam.taskscheduler.dto.TaskDTO;
import com.epam.taskscheduler.exception.NotesNotFoundException;
import com.epam.taskscheduler.exception.SlotNotAvailableException;
import com.epam.taskscheduler.exception.TaskNotFoundException;
import com.epam.taskscheduler.model.Notes;
import com.epam.taskscheduler.model.Task;
import com.epam.taskscheduler.repository.NoteRepository;
import com.epam.taskscheduler.repository.TaskRepository;
import com.epam.taskscheduler.util.DTOConverter;

@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	TaskRepository taskRepo;  
	@Autowired
	NoteRepository noteRepo;

	@Override
	public Task addNewTask(LocalDate date, Task task) throws SlotNotAvailableException {

		if (isSlotAvailable(date, task.getStartTime(), task.getEndTime()))
			task.setDate(date);
		else
			throw new SlotNotAvailableException();

		return taskRepo.save(task);
	}

	@Override
	public List<Task> fetchAllTask(LocalDate date) throws TaskNotFoundException {
		List<Task> tasks = taskRepo.findAllByDate(date);
		if (tasks.isEmpty())
			throw new TaskNotFoundException("No Task found on this date");
		return tasks;
	}
	
	public List<TaskDTO> displayAllTask() {
		return taskRepo.findAll().stream().map(DTOConverter::covertTaskModelToDTO).collect(Collectors.toList());
	}

	@Override
	public Task fetchParticularTask(int taskId) throws TaskNotFoundException {
		return taskRepo.findById(taskId).orElseThrow(()->new TaskNotFoundException());
	}

	@Override
	public String addNote(int taskId, String note) throws TaskNotFoundException {
		List<Notes> notes = new ArrayList<>();
		notes.add(new Notes(note));
		Task task = fetchParticularTask(taskId);
		task.setNotes(notes);
		taskRepo.save(task);
		return note;
	}

	@Override
	public List<Notes> fetchNotes(int taskId) {
		return noteRepo.findAllByTaskId(taskId);
	}

	@Override
	public boolean deleteATask(int taskId) throws TaskNotFoundException {
		if(taskRepo.existsById(taskId)) taskRepo.deleteById(taskId);
		else throw new TaskNotFoundException();
		return true;
	}

	@Override
	public Task updateATask(LocalDate date, Task taskToUpdate) throws SlotNotAvailableException {
		Task updatedTask = null;
		Task task = fetchParticularTask(taskToUpdate.getTaskId());

		if (isSlotAvailableForUpdateATask(date, taskToUpdate.getTaskId(), taskToUpdate.getStartTime(),
				taskToUpdate.getEndTime())) {
			updatedTask = taskRepo.save(taskToUpdate);
		} else {
			throw new SlotNotAvailableException("Time slot is already occupied.");
		}

		return updatedTask;
	}

	@Override
	public boolean modifyAnoteForATask(int noteId, String noteToAdd) throws NotesNotFoundException {
		boolean isModified = false;
		
		Notes note = noteRepo.findById(noteId).orElseThrow(()->new NotesNotFoundException());
		note.setNote(noteToAdd);
		noteRepo.save(note);
		isModified = true;

		return isModified;
	}

	@Override
	public boolean deleteAnoteForATask(int taskId, int noteId) throws NotesNotFoundException {

		Task task = taskRepo.findById(taskId).orElseThrow(()->new TaskNotFoundException());
		Notes note = noteRepo.findById(noteId).orElseThrow(()->new NotesNotFoundException());
		task.getNotes().remove(note);
		noteRepo.deleteById(noteId);

		return true;
	}

	@Transactional
	@Override
	public boolean deleteAllNoteOfATask(int taskId) {
		noteRepo.deleteAllByTaskId(taskId);
		return true;
	}

	public boolean isSlotAvailable(LocalDate date, LocalTime startTime, LocalTime endTime) {
		boolean isSlotAvailable = true;
		List<Task> tasks = taskRepo.findAllByDate(date);
		
		for (Task task : tasks) {
			if ((startTime.isAfter(task.getStartTime()) && startTime.isBefore(task.getEndTime())) || (endTime
					.isAfter(task.getStartTime()) && endTime.isBefore(task.getEndTime())
					|| (startTime.equals(task.getStartTime())
							|| task.getStartTime().isAfter(startTime) && task.getEndTime().isBefore(endTime)))) {
				isSlotAvailable = false;
				break;
			}
		}
		return isSlotAvailable;
	}

	public boolean isSlotAvailableForUpdateATask(LocalDate date, int taskId, LocalTime startTime, LocalTime endTime) {
		boolean isSlotAvailable = true;
		List<Task> tasks = fetchAllTask(date);

		for (Task task : tasks) {
			if (taskId != task.getTaskId())
				if ((startTime.isAfter(task.getStartTime()) && startTime.isBefore(task.getEndTime())) || (endTime
						.isAfter(task.getStartTime()) && endTime.isBefore(task.getEndTime())
						|| (startTime.equals(task.getStartTime())
								|| task.getStartTime().isAfter(startTime) && task.getEndTime().isBefore(endTime)))) {
					isSlotAvailable = false;
					break;
				}
		}

		return isSlotAvailable;
	}

}
