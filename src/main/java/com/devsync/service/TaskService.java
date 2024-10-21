package com.devsync.service;

import com.devsync.enums.TaskStatus;
import com.devsync.model.Task;
import com.devsync.repository.TaskRepository;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class TaskService {

    private TaskRepository taskRepository;

    public TaskService(EntityManagerFactory emf) {
        this.taskRepository = new TaskRepository(emf);
    }

    public void addTask(Task task) {
        taskRepository.addTask(task);
    }

    public List<Task> findAllTasks() {
        return taskRepository.findAll();
    }

    public Task findTaskById(Long taskId) {
        return taskRepository.findById(taskId);
    }

    public void deleteTask(Long taskId) {
        taskRepository.deleteTask(taskId);
    }

    public void updateTaskStatus(Long taskId, TaskStatus status) {
        taskRepository.updateTaskStatus(taskId, status);
    }

    public List<Task> findTasksByUser(Long userId) {
        return taskRepository.findTasksByUser(userId);
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Task findById(Long id) {
        return taskRepository.findById(id);
    }

    }
