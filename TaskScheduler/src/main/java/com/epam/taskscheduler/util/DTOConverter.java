package com.epam.taskscheduler.util;

import com.epam.taskscheduler.dto.TaskDTO;
import com.epam.taskscheduler.model.Task;

public class DTOConverter {
 
    private DTOConverter() {
    }

    public static TaskDTO covertTaskModelToDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO(task);
        return taskDTO;
    }

 
}