package com.epam.taskscheduler.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.epam.taskscheduler.model.Notes;

@Repository
public interface NoteRepository extends JpaRepository<Notes, Integer> {

	@Query(value = "select * from notes where task_id=?1", nativeQuery = true)
	List<Notes> findAllByTaskId(int taskId);

	@Modifying
	@Query(value = "delete from Notes n where n.task.taskId=?1")
	void deleteAllByTaskId(int taskId);

}
