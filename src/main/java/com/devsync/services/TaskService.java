package com.devsync.services;

import com.devsync.enums.TaskStatus;
import com.devsync.model.Tag;
import com.devsync.model.Task;
import com.devsync.model.User;
import com.devsync.repository.TagRepository;
import com.devsync.repository.TaskRepository;
import com.devsync.repository.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskService {
    private TaskRepository taskRepository;
    private UserRepository userRepository;
    private TagRepository tagRepository;

    public TaskService() {
        taskRepository = new TaskRepository();
        userRepository = new UserRepository();
        tagRepository = new TagRepository();
    }

    public void addTask(Task task, User currentUser, String[] selectedTagIds) {
        // Assign the task to the user
        if (currentUser.getUserType() == User.UserType.MANAGER) {
            Long userId = task.getUser().getId(); // Assuming the user ID is already set in the task
            User assignedUser = userRepository.findById(userId);
            task.setUser(assignedUser);
        } else {
            task.setUser(currentUser);
        }

        // Set tags
        if (selectedTagIds != null) {
            List<Tag> selectedTags = new ArrayList<>();
            for (String tagIdStr : selectedTagIds) {
                Long tagId = Long.valueOf(tagIdStr);
                Tag tag = tagRepository.findById(tagId);
                if (tag != null) {
                    selectedTags.add(tag);
                }
            }
            task.setTags(selectedTags);
        }

        // Add the task to the repository
        taskRepository.addTask(task);
    }

    public void updateTaskStatus(Long taskId, TaskStatus newStatus) {
        taskRepository.updateTaskStatus(taskId, newStatus);
    }

    public List<Task> findAllTasks() {
        return taskRepository.findAll();
    }

    public List<Task> findTasksByUser(Long userId) {
        return taskRepository.findTasksByUser(userId);
    }

    public void deleteTask(Long taskId) {
        taskRepository.deleteTask(taskId);
    }
}
