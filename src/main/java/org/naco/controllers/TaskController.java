package org.naco.controllers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.naco.models.entities.Task;
import org.naco.models.repositories.TaskRepository;

@ApplicationScoped
public class TaskController {

    @Inject
    TaskRepository taskRepository;

    @Transactional
    public void addTask(Task task) {
        taskRepository.persistAndFlush(task);
    }

    public TaskRepository getTaskRepository() {
        return taskRepository;
    }

    @Transactional
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}